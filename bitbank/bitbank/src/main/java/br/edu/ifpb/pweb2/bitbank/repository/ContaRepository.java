package br.edu.ifpb.pweb2.bitbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.bitbank.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer>{

}
