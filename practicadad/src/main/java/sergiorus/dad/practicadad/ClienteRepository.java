package sergiorus.dad.practicadad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>, CrudRepository<ClienteEntity, Long>{
	
	//añadir metodos de busqueda de clientes
	ClienteEntity findByName(String name);
	ClienteEntity findFirstByName(String name);
	List<ClienteEntity> findAll();
}
