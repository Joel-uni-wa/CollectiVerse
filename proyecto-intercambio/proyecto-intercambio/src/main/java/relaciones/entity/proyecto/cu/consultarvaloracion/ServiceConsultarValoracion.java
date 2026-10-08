package relaciones.entity.proyecto.cu.consultarvaloracion;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarvaloracion.response.ResponseValoracion;
import relaciones.entity.proyecto.dominio.entity.Valoracion;
import relaciones.entity.proyecto.dominio.repository.RepoValoracion;

@Service
public class ServiceConsultarValoracion {

    private final RepoValoracion repoValoracion;

    public ServiceConsultarValoracion(RepoValoracion repoValoracion) {
        this.repoValoracion = repoValoracion;
    }

    @Transactional(readOnly = true)
    public ResponseValoracion consultarValoracion(int id) {
        Valoracion valoracion = repoValoracion.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Valoracion con id " + id + " no existe"));

        return new ResponseValoracion(
                valoracion.getId(),
                valoracion.getUsuario().getId(),
                valoracion.getUsuario().getNombre(),
                valoracion.getPublicacion().getId(),
                valoracion.getPublicacion().getTitulo(),
                valoracion.getCalifUsuario(),
                valoracion.getCalifPublicacion(),
                valoracion.getComentario(),
                valoracion.getFecha()
        );
    }
}
