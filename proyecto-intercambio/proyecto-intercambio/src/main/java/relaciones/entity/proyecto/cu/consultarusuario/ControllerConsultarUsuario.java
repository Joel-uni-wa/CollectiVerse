package relaciones.entity.proyecto.cu.consultarusuario;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarusuario.response.ResponseUsuario;

@RestController
@RequestMapping("/usuario")
public class ControllerConsultarUsuario {

    private final ServiceConsultarUsuario serviceConsultarUsuario;

    public ControllerConsultarUsuario(ServiceConsultarUsuario serviceConsultarUsuario) {
        this.serviceConsultarUsuario = serviceConsultarUsuario;
    }

    @GetMapping("/{id}")
    public ResponseUsuario consultarUsuario(@PathVariable int id) {
        return serviceConsultarUsuario.consultarUsuario(id);
    }
}
