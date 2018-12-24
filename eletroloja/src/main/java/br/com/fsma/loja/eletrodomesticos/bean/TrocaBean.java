package br.com.fsma.loja.eletrodomesticos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.loja.eletrodomesticos.modelo.dao.ClienteDao;
import br.com.fsma.loja.eletrodomesticos.modelo.dao.TrocaDao;
import br.com.fsma.loja.eletrodomesticos.modelo.dao.VendaDao;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Cliente;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Troca;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Venda;

@Named
@ViewScoped
public class TrocaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Venda venda;
	private Troca troca;
	private List<Venda> vendas = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	
	@Inject
	private VendaDao vendaDao;
	@Inject
	private TrocaDao trocaDao;
	@Inject
	private ClienteDao clienteDao;
	
	@PostConstruct
	public void init() {
		if(troca == null) {
			troca = new Troca();
		}
		if(venda == null) {
			venda = new Venda();
		}
		if(clientes == null) {
			clientes = clienteDao.listaTodos();
		}
	}
	
	public String getCadTroca() {
		return "/view/insere/cad-troca.xhtml?faces-redirect=true";
	}
	
	public String getListaTroca() {
		return "/view/lista/lista-troca.xhtml?faces-redirect=true";
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
