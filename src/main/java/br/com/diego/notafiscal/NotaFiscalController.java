package br.com.diego.notafiscal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotaFiscalController {
	@Autowired
	private NotaFiscalRepository repository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("listanotasfiscais")
	public String listaNotasFiscais(Model model) {
		Iterable<NotaFiscal> nf = repository.findAll();
		model.addAttribute("notasfiscais", nf);
		return "listanotasfiscais";
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam("nome") String nome, @RequestParam("valor") Double valor,
			@RequestParam("imposto") String imposto, Model model) {
		Imposto impostoSelecionado;

		if (imposto.toUpperCase().trim().compareTo("ICMS") == 0) {
			impostoSelecionado = new ICMS();
		} else {
			impostoSelecionado = new ISS();
		}

		NotaFiscal nf = new NotaFiscal(nome, impostoSelecionado.valorImposto().doubleValue(), valor);
		repository.save(nf);
		Iterable<NotaFiscal> nflist = repository.findAll();
		model.addAttribute("notasfiscais", nflist);
		return "listanotasfiscais";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Integer id, Model model) {
		NotaFiscal notaFiscal = repository.findOne(id.longValue());

		if (notaFiscal != null) {
			repository.delete(notaFiscal);
		}

		Iterable<NotaFiscal> nflist = repository.findAll();
		model.addAttribute("notasfiscais", nflist);

		return "listanotasfiscais";
	}
}