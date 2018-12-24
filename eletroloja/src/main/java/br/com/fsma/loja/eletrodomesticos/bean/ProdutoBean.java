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

import org.primefaces.event.SelectEvent;

import br.com.fsma.loja.eletrodomesticos.modelo.dao.ProdutoDao;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;
import br.com.fsma.loja.eletrodomesticos.tx.Transacional;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoDao produtoDao;
	
	private String message;
	
	private Produto produto;
	
	private List<Produto> produtos = new ArrayList<>();
	
	private String produtoId;

	private boolean disabled = false;
	
	@PostConstruct
	public void init() {
		if(produto == null) {
			this.produto = new Produto();
		}
	}
	
	public String getCadproduto() {
		disabled = false;
		return "/view/insere/cad-produto.xhtml?faces-redirect=true";
	}
	
	public String updateProduto() {
		return "/view/insere/cad-produto.xhtml?faces-redirect=true&includeViewParams=true";
	}
	
	public void listener() {
		if(produtoId != null) {
			disabled = true;
			produto = produtoDao.buscaPorId(Long.valueOf(produtoId));
		}
	}
	
	@Transacional
	public String cadastra() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		if(produtoDao.existe(produto)) {
			produtoDao.atualiza(produto);
			setMessage("Produto atualizado com sucesso!");
		} else {
			produtoDao.adiciona(produto);
			setMessage("Produto cadastrado com sucesso!");
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", message));
		produto = new Produto();
		return null;
	}
	
	@Transacional
	public String getListaproduto() {
		produtos = produtoDao.listaTodos();
		return "/view/lista/lista-produto.xhtml?faces-redirect=true";
	}
	
	public String getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(String produtoId) {
		this.produtoId = produtoId;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Produto getProduto() {
		return this.produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public String getMessage() {
        return message;
    }
	
	public void setMessage(String message) {
        this.message = message;
    }

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
