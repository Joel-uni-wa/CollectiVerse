package relaciones.entity.proyecto.cu.consultarvaloracion;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarvaloracion.response.ResponseValoracion;

@RestController
@RequestMapping("/valoracion")
public class ControllerConsultarValoracion {

    private final ServiceConsultarValoracion serviceConsultarValoracion;

    public ControllerConsultarValoracion(ServiceConsultarValoracion serviceConsultarValoracion) {
        this.serviceConsultarValoracion = serviceConsultarValoracion;
    }

    @GetMapping("/{id}")
    public ResponseValoracion consultarValoracion(@PathVariable int id) {
        return serviceConsultarValoracion.consultarValoracion(id);
    }
}
