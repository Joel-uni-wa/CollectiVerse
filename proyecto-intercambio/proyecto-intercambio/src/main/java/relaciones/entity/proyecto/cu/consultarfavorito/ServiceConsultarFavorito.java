package relaciones.entity.proyecto.cu.consultarfavorito;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarfavorito.response.ResponseFavorito;
import relaciones.entity.proyecto.dominio.entity.Favorito;
import relaciones.entity.proyecto.dominio.repository.RepoFavorito;

@Service
public class ServiceConsultarFavorito {

    private final RepoFavorito repoFavorito;

    public ServiceConsultarFavorito(RepoFavorito repoFavorito) {
        this.repoFavorito = repoFavorito;
    }

    @Transactional(readOnly = true)
    public ResponseFavorito consultarFavorito(int id) {
        Favorito favorito = repoFavorito.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Favorito con id " + id + " no existe"));

        return new ResponseFavorito(
                favorito.getId(),
                favorito.getUsuario().getId(),
                favorito.getUsuario().getNombre(),
                favorito.getPublicacion().getId(),
                favorito.getPublicacion().getTitulo(),
                favorito.getFecha()
        );
    }
}
