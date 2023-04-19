package br.edu.ifpb.pweb2.bitbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.model.Transacao;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;



@Controller
@RequestMapping("/contas")
public class ContaController {

    // O ModelAndView é um objeto em que você pode definir ao 
    // mesmo tempo atributos e a página para onde o sistema deve ir 
    // ao terminar a execução do método

    @Autowired
    CorrentistaRepository correntistaRepository;

    @Autowired
    ContaRepository contaRepository;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView modelAndView){
        modelAndView.setViewName("contas/form");
        modelAndView.addObject("conta", new Conta(new Correntista()));
        return modelAndView;
    }

    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaRepository.findAll();
    }
    

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Conta conta, ModelAndView modelAndView, RedirectAttributes attrs) {
        Correntista correntista = null;
        java.util.Optional<Correntista> opCorrentista = correntistaRepository.findById(conta.getCorrentista().getId());
        if (opCorrentista.isPresent()) {
            correntista = opCorrentista.get();
            conta.setCorrentista(correntista);
            contaRepository.save(conta);
            modelAndView.setViewName("redirect:contas");
            attrs.addFlashAttribute("mensagem", "Conta cadastrada com sucesso!");
        } else {
            modelAndView.addObject("mensagem", "Correntista com id= "
                +conta.getCorrentista().getId()+ "não encontrado");
            modelAndView.setViewName("contas/form");;
        }
        return modelAndView;
    }

    @RequestMapping()
    public String listAll(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "contas/list";
    }

    @RequestMapping("\nuconta")
    public String getNuConta(){
        return "contas/operacao";
    }

    @RequestMapping(value = "/operacao")
    public ModelAndView operacaoConta(String nuConta, Transacao transacao, ModelAndView mav) {
        String proxPagina = "";
        mav.addObject("menu", "operacao");
        if (nuConta != null && transacao.getValor() == null){
            Conta conta = contaRepository.findByNumeroWithTransacoes(nuConta);
            if (conta != null) {
                mav.addObject("conta", conta);
                mav.addObject("transacao", transacao);
                proxPagina = "contas/operacao";
            } else {
                mav.addObject("mensagem", "Conta inexistente!");
                mav.addObject("menu", "operacao");
                proxPagina = "contas/operacao";
            }
        } else {
            Conta conta = contaRepository.findByNumeroWithTransacoes(nuConta);
            conta.addTransacao(transacao);
            contaRepository.save(conta);
            proxPagina = "redirect:/contas/" + conta.getId() + "/transacoes";
        }
        mav.setViewName(proxPagina);
        return mav;
    }

    @RequestMapping(value = "/{id}/transacoes")
    public String addTransacaoConta(@PathVariable("id") Integer idConta, Model model) {
        Conta conta = contaRepository.findByIdWithTransacoes(idConta);
        model.addAttribute("conta", conta);
        return "contas/transacoes";
    }

}
