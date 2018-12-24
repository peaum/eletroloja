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

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Cliente;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Troca;

@Named
@RequestScoped
public class TrocaDao implements Serializable {

	private static final long serialVersionUID = 1L;

private DAO<Troca> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Troca>(this.em, Troca.class);
	}
	
	public boolean existe(Troca troca) {
		TypedQuery<Troca> query = em.createQuery(
				  " select p from Troca p"
				+ " where p.cliente_id = :pClienteId and p.venda_id = :pVendaId and p.data_troca = :pDataTroca", Troca.class);
		
		query.setParameter("pClienteId", troca.getCliente().getId());
		query.setParameter("pVendaId", troca.getVenda().getId());
		query.setParameter("pDataTroca", troca.getDataTroca());
		
		try {
			@SuppressWarnings("unused")
			Troca resultado = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public void adiciona(Troca troca) {
		dao.adiciona(troca);
	}

	public void atualiza(Troca troca){
		em.merge(troca);
	}

	public void remove(Troca troca) {
		dao.remove(troca);
	}
	
	public Troca buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Troca> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Troca> listaTodos() {
		return dao.listaTodos();
	}
	
	public List<Troca> buscaPorCliente(String cliente_id) {
		String jpql = " select * from Troca t where t.cliente_id = :pClienteId";
		TypedQuery<Troca> query = em.createQuery(jpql, Troca.class);
		query.setParameter("pClienteId", cliente_id.trim().toLowerCase());
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public List<Cliente> buscaClienteSemTroca() {
		String jpql = " select * from Cliente c inner join Troca t"
				+ "where c.id <> t.cliente_id";
		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
