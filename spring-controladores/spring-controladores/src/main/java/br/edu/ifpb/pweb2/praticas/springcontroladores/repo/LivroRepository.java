package br.edu.ifpb.pweb2.praticas.springcontroladores.repo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.praticas.springcontroladores.model.Livro;

@Component
@Scope("application")
public class LivroRepository {
	private Map<Integer, Livro> repositorio = new HashMap<Integer, Livro>();

	public Livro findById(Integer id) {
		return repositorio.get(id);
	}

	public void insert(Livro Livro) {
		Integer id = getMaxId();
		Livro.setId(id);
		repositorio.put(id, Livro);
	}

	public void update(Livro Livro) {
		repositorio.put(Livro.getId(), Livro);
	}

	public List<Livro> findAll() {
		List<Livro> Livros = repositorio
				.values().stream().collect(Collectors.toList());
		return Livros;
	}

	private Integer getMaxId() {
		List<Livro> Livros = findAll();
		if (Livros == null || Livros.isEmpty())
			return 1;
		Livro contaMaxId = Livros
				.stream()
				.max(Comparator.comparing(Livro::getId))
				.orElseThrow(NoSuchElementException::new);
		return contaMaxId.getId() == null ? 1 : contaMaxId.getId() + 1;
	}
}
