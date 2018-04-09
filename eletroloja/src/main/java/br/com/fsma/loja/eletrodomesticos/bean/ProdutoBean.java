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
	
	public Produto getProduto() {
		return this.produto;
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
	
	@PostConstruct
	public void init() {
		if(produto == null) {
			this.produto = new Produto();
		}
	}
	
	public String getCadproduto() {
		return "/view/insere/cad-produto.xhtml?faces-redirect=true";
	}
	
	@Transacional
	public String cadastra() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		if(produtoDao.existe(produto)) {
			setMessage("Produto já está cadastrado!");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
		} else {
			produtoDao.adiciona(produto);
			setMessage("Produto cadastrado com sucesso!");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", message));
			produto = new Produto();
		}
		return null;
	}
	
	public String getListaproduto() {
		produtos = produtoDao.listaTodos();
		return "/view/lista/lista-produto.xhtml?faces-redirect=true";
	}
	
}
