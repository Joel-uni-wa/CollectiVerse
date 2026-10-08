package relaciones.entity.proyecto.cu.consultarimagenproducto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;
import relaciones.entity.proyecto.dominio.entity.ImagenProducto;
import relaciones.entity.proyecto.dominio.repository.RepoImagenProducto;

@Service
public class ServiceConsultarImagenProducto {

    private final RepoImagenProducto repoImagenProducto;

    public ServiceConsultarImagenProducto(RepoImagenProducto repoImagenProducto) {
        this.repoImagenProducto = repoImagenProducto;
    }

    @Transactional(readOnly = true)
    public ResponseImagenProducto consultarImagenProducto(int id) {
        ImagenProducto imagenProducto = repoImagenProducto.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Imagen de producto con id " + id + " no existe"));

        return new ResponseImagenProducto(
                imagenProducto.getId(),
                imagenProducto.getProducto().getId(),
                imagenProducto.getProducto().getNombre(),
                imagenProducto.getUrl(),
                imagenProducto.getOrden()
        );
    }
}
