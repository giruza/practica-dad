package sergiorus.dad.practicadad;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long>{
	
	List<ProductoEntity> findByNombre(String nombre);
	List<ProductoEntity> findByPrecio(String precio);
	Page<ProductoEntity> findAll(Pageable page);
}
