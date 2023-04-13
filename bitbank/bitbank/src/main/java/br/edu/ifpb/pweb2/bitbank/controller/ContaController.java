package br.edu.ifpb.pweb2.bitbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;


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
    public ModelAndView adicioneConta(Conta conta, ModelAndView modelAndView) {
        Correntista correntista = correntistaRepository.findById(conta.getCorrentista().getId());
        conta.setCorrentista(correntista);
        contaRepository.save(conta);
        modelAndView.setViewName("contas/list");
        modelAndView.addObject("contas", contaRepository.findAll());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Conta conta, ModelAndView modelAndView, RedirectAttributes attrs) {
        Correntista correntista = null;
        Optional<Correntista> opCorrentista = correntistaRepository.findById(conta.getCorrentista().getId());
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

    @RequestMapping("/lista")
    public ModelAndView liste(ModelAndView modelAndView){
        modelAndView.setViewName("/contas/list");
        modelAndView.addObject("contas", contaRepository.findAll());
        return modelAndView;
    }


    
}
