package br.edu.ifpb.pweb2.bitbank.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.edu.ifpb.pweb2.bitbank.model.Conta;

public class ContaRepository {
    
    private static Map<Integer, Conta> repositorio = new HashMap<Integer, Conta>();

    public static Conta findById(Integer id){
        return repositorio.get(id);
    }

    public static void save(Conta conta){
        repositorio.put(conta.getId(), conta);
    }

    public static List<Conta> findAll() {
        List<Conta> contas = repositorio.values().stream().collect(Collectors.toList());
        return contas;
    }
}
