package sergiorus.dad.practicadad;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PedidoController {

	//incluir un repositorio de pedidos
		@Autowired
		private PedidoRepository repository;
		
		@PostConstruct
		public void init() {
			/*
			ClienteEntity sergio = new ClienteEntity("sergio", "sergio@hotmail.com", "1234", "26266266A", false);
			ClienteEntity admin = new ClienteEntity("admin", "admin@hotmail.com", "0120", "26662662B", false);
			
			ProductoEntity pantalla = new ProductoEntity("Pantalla X24D", "Pantalla de 24 pulgadas y FullHD", 180);
			ProductoEntity teclado = new ProductoEntity("Teclado Wirecut", "Teclado mecánico y retroiluminación", 75);
			ProductoEntity raton = new ProductoEntity("Ratón L-96", "Ratón diseñado para zurdos", 26);
			
			sergio.alCarro(teclado);
			sergio.alCarro(raton);
			
			PedidoEntity p1 = new PedidoEntity("Mi pedido", "mipaypal@hotmail.com", "c/Nueva 3", "Mostoles", sergio, sergio.getCarro());
			repository.save(p1);
			
			admin.alCarro(pantalla);
			admin.alCarro(teclado);
			
			PedidoEntity p2 = new PedidoEntity("Repuestos", "adminpago@hotmail.com", "c/Toronto 12", "Alcorcon", admin, admin.getCarro());
			repository.save(p2);
			*/
		}
}
