package controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import controle.util.JSFUtil;
import modelo.dao.CidadaoDAO;
import modelo.dominio.Administrador;
import modelo.dominio.Cidadao;

@ManagedBean(name = "loginCidMB")
@SessionScoped
public class LoginCidadaoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean autenticado = false;
	private Cidadao cidadao = new Cidadao("usu�rio n�o autenticado", null, null, null, null, null, null, null, null);

	private String nomeUsuario;
	private String senha;

	public Cidadao getCidadao() {
		return cidadao;
	}

	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
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
	 * Autentica o usu�rio do tipo {@link Cidadao}.
	 * 
	 * @return para a p�gina loginCidadao.xhtml caso o usu�rio n�o exista ou a
	 *         senha esteja inv�lida. Para a p�gina mapaCidadao.xhtml se passou
	 *         na valida��o do nome do usu�rio e senha.
	 */
	public String autenticar() {

		CidadaoDAO dao = new CidadaoDAO();

		Cidadao cidadaoNoBanco = dao.obter(this.getNomeUsuario());

		if (cidadaoNoBanco == null) {
			JSFUtil.retornarMensagemErro("Usu�rio n�o existe.", null, null);
			return "loginCidadao";
		} else if (cidadaoNoBanco.senhaCorreta(this.getSenha())) {
			this.setCidadao(cidadaoNoBanco);
			this.autenticado = true;
			JSFUtil.retornarMensagemInfo("Seja Bem Vindo " + cidadaoNoBanco.getNomeUsuario() + ".", null, null);
			return "mapaCidadao";
		} else {
			JSFUtil.retornarMensagemErro("Senha inv�lida.", null, null);
			return "loginCidadao";
		}

	}

	/**
	 * Efetua o logout do usu�rio do tipo {@link Administrador}.
	 * 
	 * @return - para a p�gina loginAdministrador.xhtml.
	 */
	public String logout() {
		this.cidadao = new Cidadao("usu�rio n�o autenticado", null, null, null, null, null, null, null, null);
		this.autenticado = false;
		this.nomeUsuario = null;
		this.senha = null;

		// encerra a sess�o atual
		JSFUtil.getHttpSession().invalidate();
		return "loginCidadao";
	}
}
