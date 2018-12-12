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
	private Cidadao cidadao = new Cidadao("usuário não autenticado", null, null, null, null, null, null, null, null);

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
	 * Valida se o usuário está autenticado.
	 * 
	 * @return <code>true</code> ou <code>false</code>
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * Autentica o usuário do tipo {@link Cidadao}.
	 * 
	 * @return para a página loginCidadao.xhtml caso o usuário não exista ou a
	 *         senha esteja inválida. Para a página mapaCidadao.xhtml se passou
	 *         na validação do nome do usuário e senha.
	 */
	public String autenticar() {

		CidadaoDAO dao = new CidadaoDAO();

		Cidadao cidadaoNoBanco = dao.obter(this.getNomeUsuario());

		if (cidadaoNoBanco == null) {
			JSFUtil.retornarMensagemErro("Usuário não existe.", null, null);
			return "loginCidadao";
		} else if (cidadaoNoBanco.senhaCorreta(this.getSenha())) {
			this.setCidadao(cidadaoNoBanco);
			this.autenticado = true;
			JSFUtil.retornarMensagemInfo("Seja Bem Vindo " + cidadaoNoBanco.getNomeUsuario() + ".", null, null);
			return "mapaCidadao";
		} else {
			JSFUtil.retornarMensagemErro("Senha inválida.", null, null);
			return "loginCidadao";
		}

	}

	/**
	 * Efetua o logout do usuário do tipo {@link Administrador}.
	 * 
	 * @return - para a página loginAdministrador.xhtml.
	 */
	public String logout() {
		this.cidadao = new Cidadao("usuário não autenticado", null, null, null, null, null, null, null, null);
		this.autenticado = false;
		this.nomeUsuario = null;
		this.senha = null;

		// encerra a sessão atual
		JSFUtil.getHttpSession().invalidate();
		return "loginCidadao";
	}
}
