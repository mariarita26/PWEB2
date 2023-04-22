package br.edu.ifpb.pweb2.praticas.springcontroladores.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String estilo;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPublicacao;
}
