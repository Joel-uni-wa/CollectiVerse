package relaciones.entity.proyecto.cu.consultarimagenproducto;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarimagenproducto.response.ResponseImagenProducto;

@RestController
@RequestMapping("/imagen-producto")
public class ControllerConsultarImagenProducto {

    private final ServiceConsultarImagenProducto serviceConsultarImagenProducto;

    public ControllerConsultarImagenProducto(ServiceConsultarImagenProducto serviceConsultarImagenProducto) {
        this.serviceConsultarImagenProducto = serviceConsultarImagenProducto;
    }

    @GetMapping("/{id}")
    public ResponseImagenProducto consultarImagenProducto(@PathVariable int id) {
        return serviceConsultarImagenProducto.consultarImagenProducto(id);
    }
}
