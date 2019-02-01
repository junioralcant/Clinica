package com.webtolls.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webtolls.springboot.model.Medico;
import com.webtolls.springboot.model.Telefone;
import com.webtolls.springboot.repository.MedicoRepository;
import com.webtolls.springboot.repository.TelefoneRepository;

@Controller // informa que a class vai ser um controle 
public class MedicoController {
	
	@Autowired // intancia a class
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@RequestMapping(method=RequestMethod.GET, value="/cadastromedico") // ativa o metodo quando quando for digitado na url /cadastromedico 
	public ModelAndView inicio() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico");
		andView.addObject("medicoobj", new Medico()); // passa um objeto vazio. o obj é passado pq o nosso formulario de cadastro precisa receber um obj para poder alualizar a pagina, isso acontece por conta do metodo atualizar. esse é o camarada respossável por isso th:object=${medicoobj}, como n estamos atualizando passamos um obj vazio 
		
		// lista os dados dos medicos
		Iterable<Medico> medicoIt = medicoRepository.findAll(); // busca no BD
		andView.addObject("medicos", medicoIt); // essa string "medicos" é a string que fica dentro da tag <tr th:each = "medico : ${medicos}"> no documento html cadastromedico
		
		return andView;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="**/salvarmedico") // os "**" significar que queremos ignorar qualquer coisa que venha ates do /salvarmedico tipo "http://localhost:8080/editarmedico/salvarmedico"
	public ModelAndView salvar(Medico medico) { //salva, lista e retorna para a mesma tela
		
		medicoRepository.save(medico);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico"); //  a tela para qual ele ira retornar
		Iterable<Medico> medicoIt = medicoRepository.findAll(); // busca no BD
		andView.addObject("medicos", medicoIt); // essa string "medicos" é a string que fica dentro da tag <tr th:each = "medico : ${medicos}"> no documento html cadastromedico
		andView.addObject("medicoobj", new Medico());
		
		return andView;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listamedico")
	public ModelAndView medicos() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico"); //  a tela para qual ele ira retornar
		Iterable<Medico> medicoIt = medicoRepository.findAll(); // busca no BD
		
		andView.addObject("medicos", medicoIt); // essa string "medicos" é a string que fica dentro da tag <tr th:each = "medico : ${medicos}"> no documento html cadastromedico
		andView.addObject("medicoobj", new Medico());
		
		return andView;
	}
	
	@GetMapping("/editarmedico/{idmedico}") // é a mesma anotação do @RequestMapping só q ele deixa esplicito que usaremos um GET, dessa forma n é preciso especificar como no @RequestMapping
	public ModelAndView editar(@PathVariable("idmedico") Long idmedico ) { // chama a variavel idmedico q esta na documento html cadastro medico
				
		Optional<Medico> medico = medicoRepository.findById(idmedico);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico");
		andView.addObject("medicoobj", medico.get());
		
		return andView;
	}
	
	@GetMapping("/removermedico/{idmedico}")
	public ModelAndView excluir(@PathVariable("idmedico") Long idmedico) {
		
		medicoRepository.deleteById(idmedico); // delata por id
		
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico"); // retorna para a tela de cadastro
		andView.addObject("medicos", medicoRepository.findAll()); // recarrega todos os medicos
		andView.addObject("medicoobj", new Medico());
		
		return andView;
		
	}
	
	@PostMapping("**/pesquisarmedico")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {//@RequestParam("nomepesquisa") vai intercepitar oq o usuario digitar no campo nomepesquisa
	
		ModelAndView andView = new ModelAndView("cadastro/cadastromedico");
		andView.addObject("medicos", medicoRepository.findMedicoByNome(nomepesquisa));// chama o metodo findMedicoByNome que criamos no nosso repository e faz a pequisa no bd
		andView.addObject("medicoobj", new Medico());
		
		return andView;
	}
	
	@GetMapping("/telefones/{idmedico}") // é a mesma anotação do @RequestMapping só q ele deixa esplicito que usaremos um GET, dessa forma n é preciso especificar como no @RequestMapping
	public ModelAndView telefones(@PathVariable("idmedico") Long idmedico ) { // chama a variavel idmedico q esta na documento html cadastro medico
				
		Optional<Medico> medico = medicoRepository.findById(idmedico);
		
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("medicoobj", medico.get());
		andView.addObject("telefones", telefoneRepository.getTelefones(idmedico)); // carrega os telefones
		
		return andView;
	}
	
	// adiciona e retorna	
	@PostMapping("**/addfoneMedico/{medicoid}")
	public ModelAndView addFoneMedico(Telefone telefone, @PathVariable("medicoid") Long medicoid) {
		
		Medico medico = medicoRepository.findById(medicoid).get();
		telefone.setMedico(medico);
		
		telefoneRepository.save(telefone); // salva os telefones no bd
		
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("medicoobj", medico);
		andView.addObject("telefones", telefoneRepository.getTelefones(medicoid)); // carrega os telefones
		
		return andView;
	}
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone	) {
		
		Medico medico =  telefoneRepository.findById(idtelefone).get().getMedico(); // atribui o telefone a medico  otribuir o obj filho telefone ou pai medico
		
		telefoneRepository.deleteById(idtelefone); // delata por id
		
		ModelAndView andView = new ModelAndView("cadastro/telefones"); // retorna para a tela de cadastro.
		andView.addObject("medicoobj", medico);
		andView.addObject("telefones", telefoneRepository.getTelefones(medico.getId())); // carrega os telefones

		return andView;
		
    }

}
