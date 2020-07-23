package sergiorus.dad.practicadad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

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
import org.springframework.web.client.RestTemplate;


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
	
	//Incluir un repositorio de pedidos
	@Autowired
	private PedidoRepository pedidoRepository;
	
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
	
	@RequestMapping("/loginattempt")
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
		
		List<ProductoEntity> carro = new ArrayList<ProductoEntity>();
		    
		session.setAttribute("username", user);
		session.setAttribute("password", password);
		session.setAttribute("roles", roles);
		session.setAttribute("token", auth);
		session.setAttribute("carro", carro);
		    
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
		
		ClienteEntity registroCliente = new ClienteEntity(name, email, password, dni, "ROLE_CLIENT");
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
	
	@RequestMapping("/addproduct")
	public String addproduct(Model model, String id, HttpSession session) {
		
		long id_long = Long.parseLong(id);
		ProductoEntity producto = productoRepository.findFirstById(id_long);
		
		System.out.println("Añadido producto " + producto.getNombre() + " al usuario " + session.getAttribute("username").toString());
		
		List<ProductoEntity> carro = (List<ProductoEntity>) session.getAttribute("carro");
		carro.add(producto);
		session.setAttribute("carro", carro);
		
		return "redirect:/store";
	}
	
	//VISUALIZAR EL CONTENIDO DEL CARRO
	
	@RequestMapping("/cart")
	public String cart(Model model, HttpSession session) {
		
		List<ProductoEntity> carro = (List<ProductoEntity>) session.getAttribute("carro");
		model.addAttribute("productos", carro);
		
		int total = 0;
		int precio;
		
		for(ProductoEntity producto: carro) {
			precio = producto.getPrecio();
			total += precio;
		}
		
		model.addAttribute("total", total);
		
		return "cart_template";
	}
	
	//CREAR UN PEDIDO
	
	@RequestMapping("/createpetition")
	public String createpetition(Model model) {
		return "petition_template";
	}
	
	
	//FORMALIZAR PEDIDO
	
	@RequestMapping("/submitpetition")
	public String submitpetition(Model model, HttpSession session, HttpServletRequest request, @RequestParam String nombrepedido, @RequestParam String paypal, @RequestParam String direccion, @RequestParam String municipio) {
		
		List<ProductoEntity> carro = (List<ProductoEntity>) session.getAttribute("carro");
		List<ProductoEntity> productos = new ArrayList<ProductoEntity>();
		List<String> usados = new ArrayList<String>();
		String nameproduct;
		
		String nombre = request.getUserPrincipal().getName();
		ClienteEntity cliente = clienteRepository.findByName(nombre);
		
		for (ProductoEntity producto: carro){
			nameproduct = producto.getNombre();
			if(!usados.contains(nameproduct)) {
				usados.add(nameproduct);
				productos.add(producto);
			}
		}
		
		PedidoEntity nuevoPedido = new PedidoEntity(nombrepedido, paypal, direccion, municipio, cliente, productos);
		pedidoRepository.save(nuevoPedido);
		
		session.setAttribute("ultimopedido", nuevoPedido.getId());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken()); 
		
		return "petitionsuccess_template";
	}
	
	//ENVIAR CORREO CON EL PEDIDO
	
	@PostMapping("/sendpetitionmail")
	public String sendpetitionmail(Model model, HttpSession session) throws URISyntaxException {
		
		System.out.println("Intenta enviar pedido");
		
		Long pedido_id = (Long) session.getAttribute("ultimopedido");
		PedidoEntity pedido = pedidoRepository.findFirstById(pedido_id);
		
		//Ahora configuramos el envío del correo
		
		String sender_email = "pececomponentesdad@gmail.com";
		String receiver_email = pedido.getEmail();
		String title = "Se ha realizado tu pedido " + pedido.getNombre_pedido() + " en PECE componentes.";
		String content = pedido.getContentString();
		
		RestTemplate rest = new RestTemplate();
		
		//String url ="http://localhost:9997/sendpetitionemail";
		String url = "http://myhaproxy:9999/sendpetitionemail";
		URI uri = new URI(url);
		Email email = new Email(sender_email, receiver_email, title, content);
		
		rest.postForEntity(uri, email, String.class).getBody();
		
		System.out.println("Ha hecho todo esto");
		
		return "redirect:/greeting";
	}
	
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
