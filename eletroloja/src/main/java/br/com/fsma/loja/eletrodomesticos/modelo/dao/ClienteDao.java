package br.com.fsma.loja.eletrodomesticos.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Cliente;

public class ClienteDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DAO<Cliente> dao;
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Cliente>(this.em, Cliente.class);
	}

	public boolean existe(Cliente cliente) {
		TypedQuery<Cliente> query = em.createQuery(
				  " select c from Cliente c"
				+ " where c.nome = :cNome and c.endereco = :cEndereco", Cliente.class);
		
		query.setParameter("cNome", cliente.getNome());
		query.setParameter("cEndereco", cliente.getEndereco());
		
		try {
			@SuppressWarnings("unused")
			Cliente resultado = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public void adiciona(Cliente cliente) {
		dao.adiciona(cliente);
	}

	public void atualiza(Cliente cliente){
		em.merge(cliente);
	}

	public void remove(Cliente cliente) {
		dao.remove(cliente);
	}
	
	public Cliente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Cliente> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Cliente> listaTodos() {
		return dao.listaTodos();
	}
	
	public List<Cliente> buscaPorNome(String nome) {
		String jpql = " select * from Cliente c where c.nome = :cNome";
		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		query.setParameter("cNome", nome.trim().toLowerCase());
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
}
