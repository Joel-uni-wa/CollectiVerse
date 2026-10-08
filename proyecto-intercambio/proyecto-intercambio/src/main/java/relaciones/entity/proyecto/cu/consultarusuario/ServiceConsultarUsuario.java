package relaciones.entity.proyecto.cu.consultarusuario;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import relaciones.entity.proyecto.cu.consultarusuario.response.ResponseUsuario;
import relaciones.entity.proyecto.dominio.entity.Usuario;
import relaciones.entity.proyecto.dominio.repository.RepoUsuario;

@Service
public class ServiceConsultarUsuario {

    private final RepoUsuario repoUsuario;

    public ServiceConsultarUsuario(RepoUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    @Transactional(readOnly = true)
    public ResponseUsuario consultarUsuario(int id) {
        Usuario usuario = repoUsuario.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no existe"));

        return new ResponseUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getTipo()
        );
    }
}
