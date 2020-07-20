package sergiorus.dad.practicadad;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

	@Autowired
	private ClienteRepository clientes;
	
	@PostConstruct 
	private void initDatabase(){
		
		clientes.save(new ClienteEntity("sergio", "sergio@hotmail.com", "1234", "26266266A", "ROLE_USER"));
		clientes.save(new ClienteEntity("admin", "admin@hotmail.com", "0120", "26662662B", "ROLE_ADMIN"));
	}
}
