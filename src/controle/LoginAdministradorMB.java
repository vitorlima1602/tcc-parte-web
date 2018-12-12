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
	private Administrador admin = new Administrador("usuário não autenticado", null, null, null, null);

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
	 * Valida se o usuário está autenticado.
	 * 
	 * @return <code>true</code> ou <code>false</code>
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * Autentica o usuário do tipo {@link Administrador}.
	 * 
	 * @return para a página loginAdministrador.xhtml caso o usuário não exista ou a
	 *         senha esteja inválida. Para a página mapaAdministrador.xhtml se passou
	 *         na validação do nome do usuário e senha.
	 */
	public String autenticar() {

		AdministradorDAO dao = new AdministradorDAO();

		Administrador administradorNoBanco = dao.obter(this.getNomeUsuario());

		if (administradorNoBanco == null) {
			JSFUtil.retornarMensagemErro("Usuário não existe.", null, null);
			return "loginAdministrador";
		} else if (administradorNoBanco.senhaCorreta(this.getSenha())) {
			this.setAdmin(administradorNoBanco);
			this.autenticado = true;
			JSFUtil.retornarMensagemInfo("Seja Bem Vindo " + administradorNoBanco.getNomeUsuario() + ".", null, null);
			return "mapaAdministrador";
		} else {
			JSFUtil.retornarMensagemErro("Senha inválida.", null, null);
			return "loginAdministrador";
		}

	}

	/**
	 * Efetua o logout do usuário do tipo {@link Administrador}.
	 * 
	 * @return - para a página loginAdministrador.xhtml.
	 */
	public String logout() {
		this.admin = new Administrador("usuário não autenticado", null, null, null, null);
		this.autenticado = false;
		this.nomeUsuario = null;
		this.senha = null;

		// encerra a sessão atual
		JSFUtil.getHttpSession().invalidate();
		return "loginAdministrador";
	}
}
