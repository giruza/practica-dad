package sergiorus.dad.practicadad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ApplicationController {

	@RequestMapping ("/greeting")
	public String greetingController(Model model, HttpServletRequest request) {
		model.addAttribute("estimado cliente de PECE componentes", request.isUserInRole("CLIENT"));
		model.addAttribute("administrador", request.isUserInRole("ADMIN"));
		return "greeting_template";
	}
	
	//cargamos en el modelo el token para csrf
	
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) 
	{
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
		model.addAttribute("token", token.getToken());   
		return"login_template";
	}
	
}
