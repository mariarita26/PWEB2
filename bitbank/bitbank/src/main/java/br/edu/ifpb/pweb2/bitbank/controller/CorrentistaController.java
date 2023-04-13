package br.edu.ifpb.pweb2.bitbank.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    CorrentistaRepository correntistaRepository;
    
    @RequestMapping("/form")
    public String getForm(Correntista correntista, Model model){
        model.addAttribute("correntista", correntista);
        return "correntistas/form";
    }

    // @RequestMapping("/save")
    // public String save(Correntista correntista, Model model){
    //     CorrentistaRepository.save(correntista);
    //     model.addAttribute("correntistas", CorrentistaRepository.findAll());
    //     return "correntistas/list";
    // }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes redAttrs) {
        CorrentistaRepository.save(correntista);
        model.addObject("correntistas", CorrentistaRepository.findAll());
        model.setViewName("redirect:correntistas");
        redAttrs.addFlashAttribute("mensagem", "Correntista cadastrado com sucesso!");
        return model;
    }

}
