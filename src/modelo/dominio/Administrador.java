package modelo.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "ADMINISTRADOR_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ADMINISTRADOR_ID", sequenceName = "SEQ_ADMINISTRADOR", allocationSize = 1)
	private Long id;
	@Column(nullable = false, unique = true, length = 20)
	private String nomeUsuario;
	@Column(nullable = false, length = 15)
	private String primeiroNome;
	@Column(nullable = false, length = 30)
	private String ultimoNome;
	@Column(nullable = false, length = 20)
	private String senha;
	@Column(length = 30)
	private String email;

	@OneToMany(mappedBy = "administrador")
	private List<Problema> problemas;

	public Administrador() {
		super();
	}

	public Administrador(String nomeUsuario, String primeiroNome, String ultimoNome, String senha, String email) {
		this.nomeUsuario = nomeUsuario;
		this.primeiroNome = primeiroNome;
		this.ultimoNome = ultimoNome;
		this.senha = senha;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", nomeUsuario=" + nomeUsuario + ", primeiroNome=" + primeiroNome
				+ ", ultimoNome=" + ultimoNome + ", senha=" + senha + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
		result = prime * result + ((primeiroNome == null) ? 0 : primeiroNome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((ultimoNome == null) ? 0 : ultimoNome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrador other = (Administrador) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null)
				return false;
		} else if (!nomeUsuario.equals(other.nomeUsuario))
			return false;
		if (primeiroNome == null) {
			if (other.primeiroNome != null)
				return false;
		} else if (!primeiroNome.equals(other.primeiroNome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (ultimoNome == null) {
			if (other.ultimoNome != null)
				return false;
		} else if (!ultimoNome.equals(other.ultimoNome))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Problema> getProblemas() {
		return problemas;
	}

	/**
	 * Relaciona a este {@link Administrador} os problemas dele.
	 * 
	 * @param problema
	 *            - um {@link Problema}.
	 */
	public void setProblemas(Problema problema) {
		if (this.problemas == null)
			this.problemas = new ArrayList<Problema>();
		this.problemas.add(problema);
		problema.setAdministrador(this);
	}

	/**
	 * Valida a senha quando retorna um objeto do banco
	 * 
	 * @param senhaDigitada
	 *            - uma {@link String}.
	 * @return
	 */
	public boolean senhaCorreta(String senhaDigitada) {
		if (this.senha.equals(senhaDigitada))
			return true;
		else
			return false;
	}
}
