package sergiorus.dad.apirest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApirestController {

	@RequestMapping("")
	public String init() {
		return "true";
	}
	
	@PostMapping(path="/sendpetitionemail")
	public void sendpetitionemail(@RequestBody Email email) {
		
		System.out.println(email.getContent());
		
	}
}
