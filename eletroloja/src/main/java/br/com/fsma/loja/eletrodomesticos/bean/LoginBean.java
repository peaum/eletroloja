package br.com.fsma.loja.eletrodomesticos.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
//import javax.enterprise.context.ApplicationScoped;
//import javax.faces.view.ViewScoped;
//import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.fsma.loja.eletrodomesticos.modelo.acesso.Usuario;
import br.com.fsma.loja.eletrodomesticos.modelo.dao.UsuarioDao;
import br.com.fsma.loja.eletrodomesticos.tx.Transacional;

@Named
@ViewScoped
public class LoginBean implements Serializable {
	
	private static final String USUARIO_LOGADO = "usuarioLogado";

	private static final long serialVersionUID = 1L;

	@Inject
	private HttpSession session;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
	}
	
	@Transacional
	public String loga() {
		Usuario usuarioAutenticado  = usuarioDao.buscaUsuarioPelaAutenticacao(this.usuario);
		if (usuarioAutenticado != null) {
			usuarioAutenticado.setDataDoUltimoAcesso(LocalDateTime.now());
			usuarioDao.atualiza(usuarioAutenticado);
			session.setAttribute(USUARIO_LOGADO, usuarioAutenticado);
			usuario = usuarioAutenticado;
			return "/view/menu/menu.xhtml?faces-redirect=true";
		}
		String mensagem = "Usuário e/ou senha não inválido!";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensagem));
		return null;
	}

	public String desloga() {
		session.removeAttribute(USUARIO_LOGADO);
		session.invalidate();
		usuario = new Usuario();
		return "/view/login/login.xhtml?faces-redirect=true";
	}
	
}
