package br.com.davimonteiro.repository;

import java.io.Serializable;
import java.util.List;

import com.querydsl.core.types.Predicate;

public interface GenericRepository <T, ID extends Serializable> {
	
	<S extends T> S save(S entity);
	
	<S extends T> S update(S entity);
	
	T findById(ID id);
	
	void delete(ID id);
	
	void delete(T entity);
	
	void deleteAll();
	
	List<T> findAll(Predicate... predicates);
	
	Long count(Predicate... predicates);
	
}