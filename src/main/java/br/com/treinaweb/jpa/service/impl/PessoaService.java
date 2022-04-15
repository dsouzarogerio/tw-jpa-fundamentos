package br.com.treinaweb.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.treinaweb.jpa.models.Pessoa;
import br.com.treinaweb.jpa.service.interfaces.PessoaBuscaPeloNome;
import br.com.treinaweb.jpa.utils.JpaUtils;

public class PessoaService implements PessoaBuscaPeloNome {

	@Override
	public List<Pessoa> all() {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			pessoas = em.createQuery("from Pessoa", Pessoa.class).getResultList();
			return pessoas;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Pessoa getById(Integer id) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			return em.find(Pessoa.class, id);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Pessoa insert(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public Pessoa update(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			/*
			 * Fazendo o processo de sele��o e atualiza��o com a JPA
			 */
			em.merge(entity);

			/*
			 * Utilizando diretamento o Provider(Hibernate) para atualiza��o
			 * 
			 */
//			em.unwrap(Session.class).update(entity);
			em.getTransaction().commit();
			return entity;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void delete(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			Pessoa pessoaASerDeletada = em.find(Pessoa.class, id);
			em.getTransaction().begin();
			em.remove(pessoaASerDeletada);
			em.getTransaction().commit();

		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Pessoa> searchByName(String name) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			/*
			 * Impelementando consulta com JPQL
			 */
//			List<Pessoa> pessoas = em
//					.createQuery("from Pessoa p where lower(p.nome) like lower (concat('%', :nome, '%'))", Pessoa.class)
//					.setParameter("nome", name).getResultList();
			/*
			 * Implementando a consulta com a API Criteria - a partir do JPA 2.0
			 */
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Pessoa> buscaPorNomeCriteria = criteriaBuilder.createQuery(Pessoa.class);
			Root<Pessoa> raiz = buscaPorNomeCriteria.from(Pessoa.class);
			buscaPorNomeCriteria.where(
					criteriaBuilder.like(criteriaBuilder.lower(raiz.get("nome")), "%" + name.toLowerCase() + "%"));
			List<Pessoa> pessoas = em.createQuery(buscaPorNomeCriteria).getResultList();
			return pessoas;

		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

}
