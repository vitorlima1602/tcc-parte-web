package controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import controle.util.JSFUtil;
import modelo.dao.CidadaoDAO;
import modelo.dominio.Cidadao;

@ManagedBean(name = "cadastroCidadaoMB")
@RequestScoped
public class CadastroCidadaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cidadao novo;
	private String nomeUsuario;
	private String primeiroNome;
	private String ultimoNome;
	private String senha;
	private String email;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;

	public Cidadao getNovo() {
		return novo;
	}

	public void setNovo(Cidadao novo) {
		this.novo = novo;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Cadastrando um {@link Cidadao}, caso ele não exista no banco.
	 * 
	 * @return - para a página mapaCidadao.xhtml caso o cadastro seja efetuado com
	 *         sucesso (login automático). Para a página cadastrarCidadao.xhtml, caso
	 *         o usuário já exista ou algum campo obrigatório não seja
	 *         preenchido.
	 */
	public String cadastrar() {
		if (this.nomeUsuario != null && !this.nomeUsuario.equals("") && this.primeiroNome != null
				&& !this.primeiroNome.equals("") && this.ultimoNome != null && !this.ultimoNome.equals("")
				&& this.senha != null && !this.senha.equals("")) {
			CidadaoDAO dao = new CidadaoDAO();
			Cidadao cidadaoDoBanco = dao.obter(this.nomeUsuario);
			if (cidadaoDoBanco == null) {
				this.novo = new Cidadao(this.nomeUsuario, this.primeiroNome, this.ultimoNome, this.senha, this.email,
						this.logradouro, this.numero, this.bairro, this.cidade);
				new CidadaoDAO().salvar(novo);
				JSFUtil.retornarMensagemInfo(
						"Cadastro efetuado com sucesso. Seja Bem Vindo(a) " + novo.getNomeUsuario() + ".", null, null);
				return "mapaCidadao";
			} else {
				JSFUtil.retornarMensagemAviso("Usuário já existe.", null, null);
				return "cadastrarCidadao";
			}
		} else {
			JSFUtil.retornarMensagemErro("Os campos com * são obrigatórios.", null, null);
			return "cadastrarCidadao";
		}
	}

}
