package relaciones.entity.proyecto.cu.consultarreporte;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarreporte.response.ResponseReporte;

@RestController
@RequestMapping("/reporte")
public class ControllerConsultarReporte {

    private final ServiceConsultarReporte serviceConsultarReporte;

    public ControllerConsultarReporte(ServiceConsultarReporte serviceConsultarReporte) {
        this.serviceConsultarReporte = serviceConsultarReporte;
    }

    @GetMapping("/{id}")
    public ResponseReporte consultarReporte(@PathVariable int id) {
        return serviceConsultarReporte.consultarReporte(id);
    }
}
