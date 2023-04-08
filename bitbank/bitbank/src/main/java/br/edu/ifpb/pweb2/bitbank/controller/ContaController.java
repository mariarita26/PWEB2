// package br.edu.ifpb.pweb2.bitbank.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;

// import br.edu.ifpb.pweb2.bitbank.model.Conta;
// import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;

// @Controller
// @RequestMapping("/contas")
// public class ContaController {
    
//     @RequestMapping("/formContas")
//     public String getForm(Conta conta, Model model){
//         model.addAttribute("conta", conta);
//         return "contas/formContas";
//     }

//     @RequestMapping("/save")
//     public String save(Conta conta, Model model){
//         ContaRepository.save(conta);
//         model.addAttribute("contas", ContaRepository.findAll());
//         return "contas/listContas";
//     }
// }
