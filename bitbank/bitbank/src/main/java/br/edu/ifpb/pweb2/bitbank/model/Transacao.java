package br.edu.ifpb.pweb2.bitbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// A  anotação @EqualsAndHashCode(exclude  =  "conta") do  Lombok  evita  uma  dependência  circular  no momento de gerar o código dos métodos equals() e hashCode().
// Sem ele, no momento de salvar uma conta com  uma  transação,  a  JPA  (que  usa  estes  métodos),  ficará  infinitamente  calculando-ose  produz  um stack overflow.
@EqualsAndHashCode(exclude = "conta")
@Entity
public class Transacao implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    @NumberFormat(pattern = "###,##0.00")
    private BigDecimal valor;

    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;
}
