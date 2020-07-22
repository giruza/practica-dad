package sergiorus.dad.practicadad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class ApplicationController {

	/////////////
	//VARIABLES//
	/////////////
	
	//Incluir un repositorio de clientes
	@Autowired
	private ClienteRepository clienteRepository;
		
	//Autenticador
	@Autowired
	public ClienteRepositoryAuthenticationProvider authenticationProvider;
	
	//Incluir un repositorio de productos
	@Autowired
	private ProductoRepository productoRepository;
	
	///////////////////////////
	//REDIRECCIONAR A PAGINAS//
	///////////////////////////
	
	@RequestMapping ("")
	public String init(Model model, HttpServletRequest request) 
	{
		return "redirect:/greeting";
	}
	
	//PÁGINA DE INICIO
	
	@RequestMapping ("/greeting")
	public String greeting(Model model, HttpServletRequest request, HttpSession session) 
	{
		String username = "Bienvenido a PECE componentes";
		
		if(session.getAttribute("username") != null) {
			username = "Hola de nuevo, ";
			username += (String) session.getAttribute("username");
		}
		

		model.addAttribute("username", username);
		
		
		if(request.getUserPrincipal() != null) {
			model.addAttribute("logged", true);
		}else {
			model.addAttribute("logged", false);
		}
		
		model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken());
		return "greeting_template";
	}
	
	//IR A INICIO DE SESION
	
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request)
	{
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken()); 
		return "login_template";
	}
	
	//INTENTAR INICIAR SESION
	
	@PostMapping("/loginattempt")
	public String ourProjectInit (Model model , HttpSession session, @RequestParam String user, @RequestParam String password) throws Exception {
			
		ClienteEntity usuario = clienteRepository.findFirstByName(user);
		
		if (usuario == null) 
		{
			throw new Exception("Usuario no encontrado");
		}
			
		List<GrantedAuthority> roles = new ArrayList<>();
			
		for (String role : usuario.getRoles()) 
		{
			roles.add(new SimpleGrantedAuthority(role));
		}
			
		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(user, password, roles);
		Authentication auth = authenticationProvider.authenticate(authenticationRequest);
		    
		SecurityContext securitycontext = SecurityContextHolder.getContext();
		    
		securitycontext.setAuthentication(auth);
		   
		    
		session.setAttribute("username", user);
		session.setAttribute("password", password);
		session.setAttribute("roles", roles);
		session.setAttribute("token", auth);
		    
		session.setMaxInactiveInterval(600);
		    
		return "redirect:/greeting";
	}
	
	//REGISTRARSE EN LA PÁGINA
	
	@RequestMapping("/logup")
	public String logup(Model model, HttpServletRequest request)
	{
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken());
		
		return "logup_template";
	}
	
	//REGISTRO DE NUEVO USUARIO EN LA BASE DE DATOS
	
	@RequestMapping("/register")
	public String register(Model model, HttpSession session, @RequestParam String name, @RequestParam String password,  @RequestParam String email,  @RequestParam String dni) throws Exception{
		
		ClienteEntity nuevoCliente = clienteRepository.findFirstByName(name);
		
		if(nuevoCliente != null) {
			throw new Exception("Nombre de usuario ya en uso");
		}
		
		ClienteEntity registroCliente = new ClienteEntity(name, password, email, dni, "ROLE_CLIENT");
		clienteRepository.save(registroCliente);
		
		return "redirect:/greeting";
	}
	
	//ERROR DE INICIO DE SESION
	
	@RequestMapping("/loginerror")
	public String loginerror() 
	{
		return "loginerror_template";
	}
	
	/*
	@RequestMapping("/error")
	public String error() 
	{
		return "error_template";
	}*/
	
	//IR A CERRAR SESION
	
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) 
	{
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken());   
		return"logout_template";
	}
	
	@RequestMapping("/logoutsuccess")
	public String logoutsuccess(Model model, HttpServletRequest request) 
	{
		return"redirect:/greeting";
	}
	
	//IR A LA TIENDA
	@RequestMapping("/store")
	public String store(Model model) {
		
		List<ProductoEntity> productos = productoRepository.findAll();
		
		model.addAttribute("productos", productos);
		
		return "store_template";
	}
	
	//AÑADIR UN PRODUCTO AL CARRO
	
	
	
	//IR A LAS ESTADISTICAS
	
	@RequestMapping("/stats")
	
	public String stats(Model model){
		
		return "stats_template";
	}
	
	//////////////////////////////////////
	//INICIALIZACIÓN DE LA BASE DE DATOS//
	//////////////////////////////////////
	
	@PostConstruct
	public void init() {
		
		Iterator iterator = clienteRepository.findAll().iterator();
		
		if(iterator.hasNext()) 
		{
			return;
		}
		
		productoRepository.save(new ProductoEntity("Pantalla X24D", "Pantalla de 24 pulgadas y FullHD", 180));
		productoRepository.save(new ProductoEntity("Teclado Wirecut", "Teclado mecánico y retroiluminación", 75));
		productoRepository.save(new ProductoEntity("Ratón L-96", "Ratón diseñado para zurdos", 26));
		
		clienteRepository.save(new ClienteEntity("sergio", "sergiorg@hotmail.com", "hola", "26256233A", "ROLE_CLIENT"));
		clienteRepository.save(new ClienteEntity("admin", "adminmail@hotmail.com", "admin", "26392112B", "ROLE_ADMIN"));
		
	}
	
}
