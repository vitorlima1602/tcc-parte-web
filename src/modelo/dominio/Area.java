package modelo.dominio;

import java.io.Serializable;
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
@Table(name = "areas")
public class Area implements Serializable, Comparable<Area> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "AREA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "AREA_ID", sequenceName = "SEQ_AREA", allocationSize = 1)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nome;

	@OneToMany(mappedBy = "area")
	private List<SubArea> subAreas;

	@Override
	public String toString() {
		return this.id.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<SubArea> getSubAreas() {
		return subAreas;
	}

	public void setSubAreas(List<SubArea> subAreas) {
		this.subAreas = subAreas;
	}

	/**
	 * Método reescrito compareTo(). Usado para ordenar uma lista de
	 * {@link Area}.
	 */
	@Override
	public int compareTo(Area outraArea) {
		if (this.getNome() == null || outraArea.getNome() == null) {
			return 0;
		} else if (this.getNome().compareTo(outraArea.getNome()) < 0) {
			return -1;
		} else if (this.getNome().compareTo(outraArea.getNome()) > 0) {
			return 1;
		}
		return 0;
	}

}
