package relaciones.entity.proyecto.cu.consultarproducto;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarproducto.response.ResponseProducto;

@RestController
@RequestMapping("/producto")
public class ControllerConsultarProducto {

    private final ServiceConsultarProducto serviceConsultarProducto;

    public ControllerConsultarProducto(ServiceConsultarProducto serviceConsultarProducto) {
        this.serviceConsultarProducto = serviceConsultarProducto;
    }

    @GetMapping("/{id}")
    public ResponseProducto consultarProducto(@PathVariable int id) {
        return serviceConsultarProducto.consultarProducto(id);
    }
}
