package relaciones.entity.proyecto.cu.consultarcoleccion;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarcoleccion.response.ResponseColeccion;

@RestController
@RequestMapping("/coleccion")
public class ControllerConsultarColeccion {

    private final ServiceConsultarColeccion serviceConsultarColeccion;

    public ControllerConsultarColeccion(ServiceConsultarColeccion serviceConsultarColeccion) {
        this.serviceConsultarColeccion = serviceConsultarColeccion;
    }

    @GetMapping("/{id}")
    public ResponseColeccion consultarColeccion(@PathVariable int id) {
        return serviceConsultarColeccion.consultarColeccion(id);
    }
}
