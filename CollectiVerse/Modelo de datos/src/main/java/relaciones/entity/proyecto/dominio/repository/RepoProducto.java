package relaciones.entity.proyecto.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relaciones.entity.proyecto.dominio.entity.Producto;

public interface RepoProducto extends JpaRepository<Producto, Integer> {
}
