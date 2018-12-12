package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.dominio.Administrador;

public class AdministradorDAO {
	private EntityManager manager = JPAUtil.getFabrica().createEntityManager();

	/**
	 * Salva um {@link Administrador} no banco. Obs.: não foi implementado no
	 * projeto criar novos administradores.
	 * 
	 * @param admin
	 *            - um {@link Administrador}
	 * @return - um {@link Administrador}
	 */
	public Administrador salvar(Administrador admin) {

		this.manager.getTransaction().begin();

		this.manager.merge(admin);

		this.manager.flush();

		this.manager.getTransaction().commit();

		return admin;
	}

	/**
	 * Obter um Administrador do banco, passando para o método o nome do usuário
	 * cadastrado.
	 * 
	 * @param nomeUsuario
	 *            - uma {@link String} que representa o nome do usuário.
	 * @return - um {@link Administrador} do banco.
	 */
	public Administrador obter(String nomeUsuario) {
		Administrador resultado;

		Query consulta = this.manager.createQuery("from Administrador a where a.nomeUsuario = :nomeUsuario");
		consulta.setParameter("nomeUsuario", nomeUsuario);

		try {
			resultado = (Administrador) consulta.getSingleResult();
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;
	}

}
