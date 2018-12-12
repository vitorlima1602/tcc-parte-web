package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.dominio.Cidadao;

public class CidadaoDAO {
	private EntityManager manager = JPAUtil.getFabrica().createEntityManager();

	/**
	 * Salva o cidadao no banco.
	 * 
	 * @param cidadao
	 *            - um {@link Cidadao}.
	 * @return - um {@link Cidadao}.
	 */
	public Cidadao salvar(Cidadao cidadao) {

		this.manager.getTransaction().begin();

		this.manager.merge(cidadao);

		this.manager.flush();

		this.manager.getTransaction().commit();

		return cidadao;
	}

	/**
	 * Consulta um {@link Cidadao} a partir de um parametro {@link String} nome
	 * do usuário e retorna um {@link Cidadao}.
	 * 
	 * @param nomeUsuario
	 *            - uma {@link String} que deve ser o nome do usuário.
	 * @return - um {@link Cidadao}.
	 */
	public Cidadao obter(String nomeUsuario) {
		Cidadao resultado;

		Query consulta = this.manager.createQuery("from Cidadao c where c.nomeUsuario = :nomeUsuario");
		consulta.setParameter("nomeUsuario", nomeUsuario);

		try {
			resultado = (Cidadao) consulta.getSingleResult();
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;
	}
}
