package br.com.davimonteiro.repository.impl;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.repository.NoRepositoryBean;

import com.google.common.base.Throwables;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.davimonteiro.repository.GenericRepository;

@NoRepositoryBean
public class GenericRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Inject
	private JPAQueryFactory queryFactory;

	protected Class<T> type;

	protected EntityPathBase<T> qType;

	public GenericRepositoryImpl(Class<T> type, EntityPathBase<T> qType) {
		this.type = type;
		this.qType = qType;
	}

	public JPAQueryFactory query() {
		return queryFactory;
	}

	@Override
	public <S extends T> S save(S entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <S extends T> S update(S entity) {
		entityManager.merge(entity);
		return entity;
	}

	@Override
	public T findById(ID id) {
		return entityManager.find(type, id);
	}

	@Override
	public void delete(ID id) {
		T entity = findById(id);
		if (isNull(entity)) {
			Throwables.propagate(new Exception("Entidade não encontrada."));
		}
		delete(entity);
	}

	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	@Override
	public void deleteAll() {
		for (T element : findAll()) {
			delete(element);
		}
	}
	
	@Override
	public List<T> findAll(Predicate... predicates) {
		return buildQuery().where(predicates).fetch();
	}
	
	@Override
	public Long count(Predicate... predicates) {
		return buildQuery().where(predicates).fetchCount();
	}
	
	protected JPAQuery<T> buildQuery() {
		return query().selectFrom(qType);
	}
	
}
