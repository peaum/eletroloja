package br.com.fsma.loja.eletrodomesticos.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;

@Named
@RequestScoped
public class ProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Produto> dao;

	@Inject
	private EntityManager em;

	@PostConstruct
	void init() {
		this.dao = new DAO<Produto>(this.em, Produto.class);
	}

	public boolean existe(Produto produto) {
		TypedQuery<Produto> query = em.createQuery(
				" select p from Produto p" + " where p.nome = :pNome and p.marca = :pMarca and p.modelo = :pModelo",
				Produto.class);

		query.setParameter("pNome", produto.getNome());
		query.setParameter("pMarca", produto.getMarca());
		query.setParameter("pModelo", produto.getModelo());

		try {
			@SuppressWarnings("unused")
			Produto resultado = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public void adiciona(Produto produto) {
		dao.adiciona(produto);
	}

	public void atualiza(Produto produto) {
		em.merge(produto);
	}

	public void remove(Produto produto) {
		dao.remove(produto);
	}

	public Produto buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Produto> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	public List<Produto> buscaPorMarca(String marca) {
		String jpql = " select * from Produto p where p.marca = :pMarca";
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		query.setParameter("pMarca", marca.trim().toUpperCase());
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Produto buscaProduto(String nome, String modelo, String marca) {
		TypedQuery<Produto> query = em.createQuery(
				" select p from Produto p" + " where p.nome = :pNome and p.marca = :pMarca and p.modelo = :pModelo",
				Produto.class);

		query.setParameter("pNome", nome);
		query.setParameter("pModelo", modelo);
		query.setParameter("pMarca", marca);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
