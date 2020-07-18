package sergiorus.dad.practicadad;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductoController {

	//incluir un repositorio de productos
	@Autowired
	private ProductoRepository repository;
	
	//incluir varios productos en la base de datos
	@PostConstruct
	public void init() {
		repository.save(new ProductoEntity("Pantalla X24D", "Pantalla de 24 pulgadas y FullHD", 180, 32));
		repository.save(new ProductoEntity("Teclado Wirecut", "Teclado mecánico y retroiluminación", 75, 26));
		repository.save(new ProductoEntity("Ratón L-96", "Ratón diseñado para zurdos", 26, 84));
	}
	
	@RequestMapping("/")
	public String productos(Model model) {
		
		model.addAttribute("productos", repository.findAll());
		
		return "productos_template";
	}
}
