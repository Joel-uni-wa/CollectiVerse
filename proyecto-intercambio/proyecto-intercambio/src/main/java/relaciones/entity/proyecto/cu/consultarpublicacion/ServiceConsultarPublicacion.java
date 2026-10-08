package relaciones.entity.proyecto.cu.consultarpublicacion;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarpublicacion.response.ResponsePublicacion;
import relaciones.entity.proyecto.dominio.entity.Publicacion;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacion;

@Service
public class ServiceConsultarPublicacion {

    private final RepoPublicacion repoPublicacion;

    public ServiceConsultarPublicacion(RepoPublicacion repoPublicacion) {
        this.repoPublicacion = repoPublicacion;
    }

    @Transactional(readOnly = true)
    public ResponsePublicacion consultarPublicacion(int id) {
        Publicacion publicacion = repoPublicacion.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publicacion con id " + id + " no existe"));

        return new ResponsePublicacion(
                publicacion.getId(),
                publicacion.getTitulo(),
                publicacion.getDescripcion(),
                publicacion.getTipo(),
                publicacion.getEstado(),
                publicacion.getFecha(),
                publicacion.getUsuario().getId(),
                publicacion.getUsuario().getNombre(),
                publicacion.getProducto().getId(),
                publicacion.getProducto().getNombre(),
                publicacion.getProducto().getPrecio(),
                publicacion.getIntercambios().stream().map(i -> new ResponsePublicacion.ResponseIntercambio(i.getNombreSolicitado(), i.getDescripcion(), i.getCantidad())).toList()
        );
    }
}
