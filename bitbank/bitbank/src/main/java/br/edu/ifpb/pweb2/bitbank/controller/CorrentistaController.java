package br.edu.ifpb.pweb2.bitbank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes redAttrs) {
        correntistaRepository.save(correntista);
        model.addObject("correntistas", correntistaRepository.findAll());
        model.setViewName("redirect:correntistas");
        redAttrs.addFlashAttribute("mensagem", "Correntista cadastrado com sucesso!");
        return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAll(ModelAndView model) {
        model.addObject("correntistas", correntistaRepository.findAll());
        model.setViewName("correntistas/list");
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.setViewName("correntista/form");
        Optional<Correntista> opCorrentista = correntistaRepository.findById(id);
        if (opCorrentista.isPresent()) {
            model.addObject("correntista", opCorrentista.get());
        } else {
            model.addObject("mensagem", "Correntista com id "+ id + " não encontrado.");
        }
        return model;
    }

}
