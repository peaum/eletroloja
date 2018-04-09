package br.com.fsma.loja.eletrodomesticos.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Venda;

public class VendaDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DAO<Venda> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Venda>(this.em, Venda.class);
	}
	
	public void adiciona(Venda venda) {
		dao.adiciona(venda);
	}

	public void atualiza(Venda venda){
		em.merge(venda);
	}

	public void remove(Venda venda) {
		dao.remove(venda);
	}
	
	public Venda buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Venda> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}
	
	public List<Venda> buscaPorCliente(Long idCliente) {
		String jpql = " select * from Venda v where v.cliente = :pCliente";
		TypedQuery<Venda> query = em.createQuery(jpql, Venda.class);
		query.setParameter("pCliente", idCliente);
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
}
