package modelo.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "subareas")
public class SubArea implements Serializable, Comparable<SubArea> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "SUBAREA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SUBAREA_ID", sequenceName = "SEQ_SUBAREA", allocationSize = 1)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nome;

	@ManyToOne
	private Area area;

	@OneToMany(mappedBy = "subArea")
	private List<Problema> problemas;

	@Override
	public String toString() {
		return this.id.toString();
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Problema> getProblemas() {
		return problemas;
	}

	public void setProblemas(Problema problema) {
		if (this.problemas == null)
			this.problemas = new ArrayList<Problema>();
		this.problemas.add(problema);
		problema.setSubArea(this);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((problemas == null) ? 0 : problemas.hashCode());
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
		SubArea other = (SubArea) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
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
		if (problemas == null) {
			if (other.problemas != null)
				return false;
		} else if (!problemas.equals(other.problemas))
			return false;
		return true;
	}

	/**
	 * Método reescrito compareTo(). Usado para ordenar uma lista de
	 * {@link SubArea}F.
	 */
	@Override
	public int compareTo(SubArea outraSubArea) {
		if (this.getNome() == null || outraSubArea.getNome() == null) {
			return 0;
		} else if (this.getNome().compareTo(outraSubArea.getNome()) < 0) {
			return -1;
		} else if (this.getNome().compareTo(outraSubArea.getNome()) > 0) {
			return 1;
		}
		return 0;
	}

}
