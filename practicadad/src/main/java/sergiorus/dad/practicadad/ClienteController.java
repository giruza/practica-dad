/*package sergiorus.dad.practicadad;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClienteController {

	//incluir un repositorio de clientes
		@Autowired
		private ClienteRepository repository;
		
		@PostConstruct
		public void init() {
			repository.save(new ClienteEntity("sergio", "sergio@hotmail.com", "1234", "26266266A", false));
			repository.save(new ClienteEntity("admin", "admin@hotmail.com", "0120", "26662662B", false));
		}
}
*/