package relaciones.entity.proyecto.cu.consultarcategoria;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;

@RestController
@RequestMapping("/categoria")
public class ControllerConsultarCategoria {

    private final ServiceConsultarCategoria serviceConsultarCategoria;

    public ControllerConsultarCategoria(ServiceConsultarCategoria serviceConsultarCategoria) {
        this.serviceConsultarCategoria = serviceConsultarCategoria;
    }

    @GetMapping("/{id}")
    public ResponseCategoria consultarCategoria(@PathVariable int id) {
        return serviceConsultarCategoria.consultarCategoria(id);
    }
}
