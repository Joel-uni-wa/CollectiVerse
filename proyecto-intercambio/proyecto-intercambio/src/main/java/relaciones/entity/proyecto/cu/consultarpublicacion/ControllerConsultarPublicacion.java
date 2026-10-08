package relaciones.entity.proyecto.cu.consultarpublicacion;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarpublicacion.response.ResponsePublicacion;

@RestController
@RequestMapping("/publicacion")
public class ControllerConsultarPublicacion {

    private final ServiceConsultarPublicacion serviceConsultarPublicacion;

    public ControllerConsultarPublicacion(ServiceConsultarPublicacion serviceConsultarPublicacion) {
        this.serviceConsultarPublicacion = serviceConsultarPublicacion;
    }

    @GetMapping("/{id}")
    public ResponsePublicacion consultarPublicacion(@PathVariable int id) {
        return serviceConsultarPublicacion.consultarPublicacion(id);
    }
}
