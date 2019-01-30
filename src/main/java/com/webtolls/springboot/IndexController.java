package com.webtolls.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // informa q sera um controle MVC
public class IndexController {
	
	@RequestMapping("/") // idetifica se na url tem index e retorna para a pagina index
	public String index() {
		return "index";
	}
}
