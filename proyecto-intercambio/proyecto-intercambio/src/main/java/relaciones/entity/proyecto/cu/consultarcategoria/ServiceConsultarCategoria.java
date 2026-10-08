package relaciones.entity.proyecto.cu.consultarcategoria;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarcategoria.response.ResponseCategoria;
import relaciones.entity.proyecto.dominio.entity.Categoria;
import relaciones.entity.proyecto.dominio.repository.RepoCategoria;

@Service
public class ServiceConsultarCategoria {

    private final RepoCategoria repoCategoria;

    public ServiceConsultarCategoria(RepoCategoria repoCategoria) {
        this.repoCategoria = repoCategoria;
    }

    @Transactional(readOnly = true)
    public ResponseCategoria consultarCategoria(int id) {
        Categoria categoria = repoCategoria.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoria con id " + id + " no existe"));

        return new ResponseCategoria(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
