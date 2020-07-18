package sergiorus.dad.practicadad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ApplicationController {

	@RequestMapping ("/greeting")
	public String greetingsController(Model model) {
		return "greeting_template";
	}
}
