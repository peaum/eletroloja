package br.com.fsma.loja.eletrodomesticos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.loja.eletrodomesticos.modelo.dao.ClienteDao;
import br.com.fsma.loja.eletrodomesticos.modelo.dao.ProdutoDao;
import br.com.fsma.loja.eletrodomesticos.modelo.dao.VendaDao;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Cliente;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Item;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Venda;
import br.com.fsma.loja.eletrodomesticos.tx.Transacional;
import br.com.fsma.loja.eletrodomesticos.validador.ValidaCPF;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private List<Cliente> clientes = new ArrayList<>();
	private List<Venda> vendas = new ArrayList<>();
	private Venda venda;
	private Cliente cliente;
	
	@Inject
	private VendaDao vendaDao;
	@Inject
	private ClienteDao clienteDao;
	@Inject
	private ProdutoDao produtoDao;
	
	private List<Item> itens = new ArrayList<>();
	private Item item;
	private List<Produto> produtos = new ArrayList<>();
	private Produto produto;
	
	private boolean skip;
	
	@PostConstruct
	public void init() {
		if(venda == null) {
			venda = new Venda();
		}
		if(cliente == null) {
			cliente = new Cliente();
		}
		if(item == null) {
			item = new Item();
		}
		if(produtos.isEmpty()) {
			produtos = produtoDao.listaTodos();
		}
	}
	
	public String getCadVenda() {
		return "/view/insere/cad-venda.xhtml?faces-redirect=true";
	}
	
	@Transacional
	public void buscaCliente() {
		if(ValidaCPF.isNotCPF(cliente.getCpf())) {
			FacesContext context = FacesContext.getCurrentInstance();
			setMessage("CPF Inválido!");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
			cliente.setCpf("");
		}
		Cliente c = clienteDao.buscaPorCpf(cliente.getCpf());
		if(c != null) {
			cliente = c;
		}
	}
	
	public void insereItem() {
		Item it = itens.parallelStream().filter(
			i->
				i.getProduto().getId().equals(item.getProduto().getId())
			).findFirst().orElse(null);
		if(it == null) {
			itens.add(item);
		} else {
			it.setQuantidade(it.getQuantidade() + item.getQuantidade());
		}
		item = new Item();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
