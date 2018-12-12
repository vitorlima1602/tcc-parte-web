package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.dominio.Area;
import modelo.dominio.SubArea;

public class SubAreaDAO {
	private EntityManager manager = JPAUtil.getFabrica().createEntityManager();

	/**
	 * Consulta todas as {@link SubArea} do banco.
	 * 
	 * @return - uma {@link SubArea}.
	 */
	public List<SubArea> listar() {

		String comandoJPQL = "FROM SubArea";

		List<SubArea> resultado;

		TypedQuery<SubArea> query = this.manager.createQuery(comandoJPQL, SubArea.class);

		resultado = query.getResultList();

		return resultado;
	}

	/**
	 * Retorna uma única {@link SubArea} a partir do parametro {@link Long} id
	 * que deve ser a id da {@link SubArea} buscada.
	 * 
	 * @param id
	 *            - um {@link Long} indicando a id da {@link SubArea}.
	 * @return - uma {@link SubArea}.
	 */
	public SubArea lerPorId(Long id) {

		SubArea subArea;

		Query consulta = this.manager.createQuery("from SubArea s where s.id = :id");
		consulta.setParameter("id", id);

		try {
			subArea = (SubArea) consulta.getSingleResult();
		} catch (NoResultException e) {
			subArea = null;
		}

		return subArea;

	}
}
