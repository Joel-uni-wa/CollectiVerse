package relaciones.entity.proyecto.cu.registrarpublicacion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relaciones.entity.proyecto.cu.consultarpublicacion.ServiceConsultarPublicacion;
import relaciones.entity.proyecto.cu.consultarpublicacion.response.ResponsePublicacion;
import relaciones.entity.proyecto.cu.registrarpublicacion.request.RequestPublicacion;
import relaciones.entity.proyecto.dominio.entity.*;
import relaciones.entity.proyecto.dominio.repository.*;
import relaciones.entity.proyecto.excepcion.ConflictoException;
import relaciones.entity.proyecto.excepcion.SolicitudInvalidaException;
import relaciones.entity.proyecto.util.Validar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceRegistrarPublicacion {

    private final RepoUsuario repoUsuario;
    private final RepoProducto repoProducto;
    private final RepoPublicacion repoPublicacion;
    private final ServiceConsultarPublicacion serviceConsultarPublicacion;

    public ServiceRegistrarPublicacion(
            RepoUsuario repoUsuario,
            RepoProducto repoProducto,
            RepoPublicacion repoPublicacion,
            ServiceConsultarPublicacion serviceConsultarPublicacion) {
        this.repoUsuario = repoUsuario;
        this.repoProducto = repoProducto;
        this.repoPublicacion = repoPublicacion;
        this.serviceConsultarPublicacion = serviceConsultarPublicacion;
    }

    @Transactional
    public ResponsePublicacion registrarPublicacion(RequestPublicacion request) {
        Validar.cuerpo(request);

        int idUsuario = Validar.idObligatorio(request.idUsuario(), "idUsuario");
        int idProducto = Validar.idObligatorio(request.idProducto(), "idProducto");
        String titulo = Validar.texto(request.titulo(), "titulo", 100);
        String descripcion = Validar.textoOpcional(request.descripcion(), "descripcion", 255);
        String tipo = Validar.texto(request.tipo(), "tipo", 20).toUpperCase();
        if (!tipo.equals("VENTA") && !tipo.equals("INTERCAMBIO") && !tipo.equals("AMBOS")) {
            throw new SolicitudInvalidaException("El tipo debe ser VENTA, INTERCAMBIO o AMBOS");
        }

        List<RequestPublicacion.RequestIntercambio> intercambios =
                request.intercambios() == null ? List.of() : request.intercambios();
        if (tipo.equals("VENTA") && !intercambios.isEmpty()) {
            throw new SolicitudInvalidaException(
                    "Una publicacion de tipo VENTA no puede tener productos a cambio");
        }
        if (!tipo.equals("VENTA") && intercambios.isEmpty()) {
            throw new SolicitudInvalidaException(
                    "Una publicacion de tipo " + tipo + " debe indicar al menos un producto a cambio");
        }

        Usuario usuario = repoUsuario.findById(idUsuario)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El usuario con id " + idUsuario + " no existe"));
        Producto producto = repoProducto.findById(idProducto)
                .orElseThrow(() -> new SolicitudInvalidaException(
                        "El producto con id " + idProducto + " no existe"));

        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuario);
        publicacion.setProducto(producto);
        publicacion.setTitulo(titulo);
        publicacion.setDescripcion(descripcion);
        publicacion.setTipo(tipo);
        publicacion.setEstado("ACTIVA");

        for (RequestPublicacion.RequestIntercambio item : intercambios) {
            Validar.cuerpo(item);
            String nombreSolicitado = Validar.texto(item.nombreSolicitado(), "nombreSolicitado", 100);
            String descItem = Validar.textoOpcional(item.descripcion(), "descripcion (producto a cambio)", 255);
            int cantidadItem = item.cantidad() == null ? 1 : Validar.minimo(item.cantidad(), "cantidad", 1);
            publicacion.agregarIntercambio(nombreSolicitado, descItem, cantidadItem);
        }

        Publicacion guardado = repoPublicacion.save(publicacion);

        return serviceConsultarPublicacion.consultarPublicacion(guardado.getId());
    }
}
