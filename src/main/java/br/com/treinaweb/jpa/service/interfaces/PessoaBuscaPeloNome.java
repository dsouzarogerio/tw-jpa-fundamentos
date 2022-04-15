package br.com.treinaweb.jpa.service.interfaces;

import java.util.List;

import br.com.treinaweb.jpa.models.Pessoa;

public interface PessoaBuscaPeloNome extends CrudService<Pessoa, Integer>{
	List<Pessoa> searchByName(String name);
}
