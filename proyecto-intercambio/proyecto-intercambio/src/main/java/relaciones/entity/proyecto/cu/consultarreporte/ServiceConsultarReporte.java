package relaciones.entity.proyecto.cu.consultarreporte;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;
import relaciones.entity.proyecto.dominio.entity.Reporte;
import relaciones.entity.proyecto.dominio.repository.RepoReporte;

@Service
public class ServiceConsultarReporte {

    private final RepoReporte repoReporte;

    public ServiceConsultarReporte(RepoReporte repoReporte) {
        this.repoReporte = repoReporte;
    }

    @Transactional(readOnly = true)
    public ResponseReporte consultarReporte(int id) {
        Reporte reporte = repoReporte.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reporte con id " + id + " no existe"));

        return new ResponseReporte(
                reporte.getId(),
                reporte.getUsuario().getId(),
                reporte.getUsuario().getNombre(),
                reporte.getPublicacion().getId(),
                reporte.getPublicacion().getTitulo(),
                reporte.getMotivo(),
                reporte.getEstado(),
                reporte.getFecha()
        );
    }
}
