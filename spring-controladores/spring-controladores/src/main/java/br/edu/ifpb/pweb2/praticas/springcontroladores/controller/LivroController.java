package br.edu.ifpb.pweb2.praticas.springcontroladores.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.praticas.springcontroladores.model.Livro;
import br.edu.ifpb.pweb2.praticas.springcontroladores.repo.LivroRepository;

@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @RequestMapping("/form")
    public ModelAndView getFormLivro(ModelAndView modelAndView) {
        // informar qual a pág de retorno utilizando o setViewName
        modelAndView.setViewName("livros/form-livro");
        modelAndView.addObject("livro", new Livro());
        return modelAndView;
    }

    @ModelAttribute("estilos")
    public Map<String, String> getEstilos() {
        Map<String, String> estilos = new LinkedHashMap<String, String>();
        estilos.put("1", "Romance");
        estilos.put("2", "Terror");
        estilos.put("3", "Suspense");
        estilos.put("4", "Biografia");
        return estilos;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String cadastreLivro(Livro livro, ModelAndView model, RedirectAttributes attr) {
        if (livro.getId() == null) {
            livroRepository.insert(livro);
        } else {
            livroRepository.update(livro);
        }
        attr.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
        // model.addAttribute("livros", livroRepository.findAll());
        return "redirect:/livros";
    }

    @RequestMapping(method = { RequestMethod.GET })
    public ModelAndView listeLivrosMatriculados(ModelAndView model) {
        model.setViewName("/livros/list-livro");
        model.addObject("livros", livroRepository.findAll());
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView busqueLivro(@PathVariable("id") Integer id, ModelAndView model) {
        Livro livro = livroRepository.findById(id);
        model.setViewName("/livros/list-livro");
        model.addObject("livros", Collections.singleton(livro));
        return model;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeLivro(@PathVariable("id") Integer id, ModelAndView model) {
        Livro livro = livroRepository.findById(id);
        model.setViewName("/livros/form-livro");
        model.addObject("livro", livro);
        return model;
    }

}
