package controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import controle.util.JSFUtil;
import modelo.dao.AdministradorDAO;
import modelo.dominio.Administrador;

@ManagedBean(name = "loginAdmMB")
@SessionScoped
public class LoginAdministradorMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean autenticado = false;
	private Administrador admin = new Administrador("usu�rio n�o autenticado", null, null, null, null);

	private String nomeUsuario;
	private String senha;

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Valida se o usu�rio est� autenticado.
	 * 
	 * @return <code>true</code> ou <code>false</code>
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * Autentica o usu�rio do tipo {@link Administrador}.
	 * 
	 * @return para a p�gina loginAdministrador.xhtml caso o usu�rio n�o exista ou a
	 *         senha esteja inv�lida. Para a p�gina mapaAdministrador.xhtml se passou
	 *         na valida��o do nome do usu�rio e senha.
	 */
	public String autenticar() {

		AdministradorDAO dao = new AdministradorDAO();

		Administrador administradorNoBanco = dao.obter(this.getNomeUsuario());

		if (administradorNoBanco == null) {
			JSFUtil.retornarMensagemErro("Usu�rio n�o existe.", null, null);
			return "loginAdministrador";
		} else if (administradorNoBanco.senhaCorreta(this.getSenha())) {
			this.setAdmin(administradorNoBanco);
			this.autenticado = true;
			JSFUtil.retornarMensagemInfo("Seja Bem Vindo " + administradorNoBanco.getNomeUsuario() + ".", null, null);
			return "mapaAdministrador";
		} else {
			JSFUtil.retornarMensagemErro("Senha inv�lida.", null, null);
			return "loginAdministrador";
		}

	}

	/**
	 * Efetua o logout do usu�rio do tipo {@link Administrador}.
	 * 
	 * @return - para a p�gina loginAdministrador.xhtml.
	 */
	public String logout() {
		this.admin = new Administrador("usu�rio n�o autenticado", null, null, null, null);
		this.autenticado = false;
		this.nomeUsuario = null;
		this.senha = null;

		// encerra a sess�o atual
		JSFUtil.getHttpSession().invalidate();
		return "loginAdministrador";
	}
}
