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

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Item;

@Named
@RequestScoped
public class ItemDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DAO<Item> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Item>(this.em, Item.class);
	}
	
	public boolean existe(Item item) {
		TypedQuery<Item> query = em.createQuery(
				  " select i from Item p"
				+ " where i.venda_id = :iVendaId and i.produto_id = iProdutoId", Item.class);
		
		query.setParameter("iVendaId", item.getVenda().getId());
		query.setParameter("iProdutoId", item.getProduto().getId());
		
		try {
			@SuppressWarnings("unused")
			Item resultado = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public void adiciona(Item item) {
		dao.adiciona(item);
	}

	public void atualiza(Item item){
		em.merge(item);
	}

	public void remove(Item item) {
		dao.remove(item);
	}
	
	public Item buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Item> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Item> listaTodos() {
		return dao.listaTodos();
	}
	
	public List<Item> buscaPorProduto(long produtoId) {
		String jpql = " select * from Item i where i.produto_id = :pProdutoId";
		TypedQuery<Item> query = em.createQuery(jpql, Item.class);
		query.setParameter("pProdutoId", produtoId);
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public List<Item> buscaPorVenda(long vendaId) {
		String jpql = " select * from Item i where i.venda_id = :pVendaId";
		TypedQuery<Item> query = em.createQuery(jpql, Item.class);
		query.setParameter("pVendaId", vendaId);
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
}

