package relaciones.entity.proyecto.cu.consultarcoleccion;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarcoleccion.response.ResponseColeccion;
import relaciones.entity.proyecto.dominio.entity.Coleccion;
import relaciones.entity.proyecto.dominio.repository.RepoColeccion;

@Service
public class ServiceConsultarColeccion {

    private final RepoColeccion repoColeccion;

    public ServiceConsultarColeccion(RepoColeccion repoColeccion) {
        this.repoColeccion = repoColeccion;
    }

    @Transactional(readOnly = true)
    public ResponseColeccion consultarColeccion(int id) {
        Coleccion coleccion = repoColeccion.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Coleccion con id " + id + " no existe"));

        return new ResponseColeccion(
                coleccion.getId(),
                coleccion.getCategoria().getId(),
                coleccion.getCategoria().getNombre(),
                coleccion.getNombre(),
                coleccion.getDescripcion()
        );
    }
}
