package modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "problemas")
public class Problema implements Serializable, Comparable<Problema> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "PROBLEMA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PROBLEMA_ID", sequenceName = "SEQ_PROBLEMA", allocationSize = 1)
	private Long id;
	@Column(nullable = false, length = 500)
	private String descricao;
	@Column(length = 500)
	private String resposta;
	@Column(nullable = false)
	private LocalDate data;

	@Column(nullable = false)
	private Point local;

	@ManyToOne
	private Cidadao cidadao;
	@ManyToOne
	private Administrador administrador;
	@ManyToOne
	private SubArea subArea;

	public Problema() {
		super();
	}

	public Problema(String descricao, LocalDate data, Point local, Cidadao cidadao, SubArea subArea) {
		super();
		this.descricao = descricao;
		this.data = data;
		this.local = local;
		this.cidadao = cidadao;
		this.subArea = subArea;
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((administrador == null) ? 0 : administrador.hashCode());
		result = prime * result + ((cidadao == null) ? 0 : cidadao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
		result = prime * result + ((subArea == null) ? 0 : subArea.hashCode());
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
		Problema other = (Problema) obj;
		if (administrador == null) {
			if (other.administrador != null)
				return false;
		} else if (!administrador.equals(other.administrador))
			return false;
		if (cidadao == null) {
			if (other.cidadao != null)
				return false;
		} else if (!cidadao.equals(other.cidadao))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		if (subArea == null) {
			if (other.subArea != null)
				return false;
		} else if (!subArea.equals(other.subArea))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Point getLocal() {
		return local;
	}

	public void setLocal(Point local) {
		this.local = local;
	}

	public Cidadao getCidadao() {
		return cidadao;
	}

	public void setCidadao(Cidadao cidadao) {
		this.cidadao = cidadao;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public SubArea getSubArea() {
		return subArea;
	}

	public void setSubArea(SubArea subArea) {
		this.subArea = subArea;
	}
	
	/**
	 * Método reescrito compareTo(). Usado para ordenar uma lista de
	 * {@link Problema}.
	 */
	@Override
	public int compareTo(Problema outroProblema) {
		if (this.getData() == null || outroProblema.getData() == null) {
			return 0;
		} else if (this.getData().isBefore(outroProblema.getData())) {
			return -1;
		} else if (this.getData().isAfter(outroProblema.getData())) {
			return 1;
		}
		return 0;
	}

}
