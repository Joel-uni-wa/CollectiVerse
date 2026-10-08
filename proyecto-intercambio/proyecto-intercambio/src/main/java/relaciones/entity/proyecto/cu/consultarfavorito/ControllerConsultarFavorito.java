package relaciones.entity.proyecto.cu.consultarfavorito;

import org.springframework.web.bind.annotation.*;
import relaciones.entity.proyecto.cu.consultarfavorito.response.ResponseFavorito;

@RestController
@RequestMapping("/favorito")
public class ControllerConsultarFavorito {

    private final ServiceConsultarFavorito serviceConsultarFavorito;

    public ControllerConsultarFavorito(ServiceConsultarFavorito serviceConsultarFavorito) {
        this.serviceConsultarFavorito = serviceConsultarFavorito;
    }

    @GetMapping("/{id}")
    public ResponseFavorito consultarFavorito(@PathVariable int id) {
        return serviceConsultarFavorito.consultarFavorito(id);
    }
}
