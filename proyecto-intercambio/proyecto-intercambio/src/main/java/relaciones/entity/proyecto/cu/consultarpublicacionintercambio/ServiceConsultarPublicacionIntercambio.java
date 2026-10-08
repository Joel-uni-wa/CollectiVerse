package relaciones.entity.proyecto.cu.consultarpublicacionintercambio;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;
import relaciones.entity.proyecto.dominio.entity.PublicacionIntercambio;
import relaciones.entity.proyecto.dominio.repository.RepoPublicacionIntercambio;

@Service
public class ServiceConsultarPublicacionIntercambio {

    private final RepoPublicacionIntercambio repoPublicacionIntercambio;

    public ServiceConsultarPublicacionIntercambio(RepoPublicacionIntercambio repoPublicacionIntercambio) {
        this.repoPublicacionIntercambio = repoPublicacionIntercambio;
    }

    @Transactional(readOnly = true)
    public ResponsePublicacionIntercambio consultarPublicacionIntercambio(int id) {
        PublicacionIntercambio publicacionIntercambio = repoPublicacionIntercambio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Intercambio de publicacion con id " + id + " no existe"));

        return new ResponsePublicacionIntercambio(
                publicacionIntercambio.getId(),
                publicacionIntercambio.getPublicacion().getId(),
                publicacionIntercambio.getPublicacion().getTitulo(),
                publicacionIntercambio.getNombreSolicitado(),
                publicacionIntercambio.getDescripcion(),
                publicacionIntercambio.getCantidad()
        );
    }
}
