package sergiorus.dad.practicadad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ApplicationController {

	@RequestMapping ("/greetings")
	public String greetingsController(Model model) {
		return "greetings_template";
	}
}
