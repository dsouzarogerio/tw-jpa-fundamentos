package br.com.treinaweb.jpa.service.interfaces;

import java.util.List;

public interface CrudService<T, K> {
	
	List<T> all();
	T getById(K id);
	T insert(T entity);
	T update(T entity);
	void delete(T entity);
	void deleteById(K id);
}
