package sergiorus.dad.practicadad;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{

	List<PedidoEntity> findByCliente_Name(String name);
	PedidoEntity findFirstById(Long id);
}
