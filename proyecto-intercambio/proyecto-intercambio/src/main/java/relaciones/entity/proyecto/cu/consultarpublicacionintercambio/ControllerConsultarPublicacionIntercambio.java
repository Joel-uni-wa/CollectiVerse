package relaciones.entity.proyecto.cu.consultarpublicacionintercambio;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarpublicacionintercambio.response.ResponsePublicacionIntercambio;

@RestController
@RequestMapping("/publicacion-intercambio")
public class ControllerConsultarPublicacionIntercambio {

    private final ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio;

    public ControllerConsultarPublicacionIntercambio(ServiceConsultarPublicacionIntercambio serviceConsultarPublicacionIntercambio) {
        this.serviceConsultarPublicacionIntercambio = serviceConsultarPublicacionIntercambio;
    }

    @GetMapping("/{id}")
    public ResponsePublicacionIntercambio consultarPublicacionIntercambio(@PathVariable int id) {
        return serviceConsultarPublicacionIntercambio.consultarPublicacionIntercambio(id);
    }
}
