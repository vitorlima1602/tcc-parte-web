package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.dominio.Area;

public class AreaDAO {
	private EntityManager manager = JPAUtil.getFabrica().createEntityManager();

	/**
	 * Lista todas as Áreas do banco.
	 * 
	 * @return - um {@link List} de {@link Area}.
	 */
	public List<Area> listar() {

		String comandoJPQL = "FROM Area";

		List<Area> resultado;

		TypedQuery<Area> query = this.manager.createQuery(comandoJPQL, Area.class);

		resultado = query.getResultList();

		return resultado;
	}

	/**
	 * Retorna uma única {@link Area} depois de consultar a partir do parametro
	 * {@link Long} id passado.
	 * 
	 * @param id
	 *            - um {@link Long} indicando o id da {@link Area} buscada.
	 * @return - uma {@link Area}.
	 */
	public Area lerPorId(Long id) {

		Area area;

		Query consulta = this.manager.createQuery("from Area a where a.id = :id");
		consulta.setParameter("id", id);

		try {
			area = (Area) consulta.getSingleResult();
		} catch (NoResultException e) {
			area = null;
		}

		return area;

	}
}
