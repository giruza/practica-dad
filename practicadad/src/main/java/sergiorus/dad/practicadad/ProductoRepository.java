package sergiorus.dad.practicadad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long>{
	
	List<ProductoEntity> findByNombre(String nombre);
	List<ProductoEntity> findByPrecio(String precio);
}
