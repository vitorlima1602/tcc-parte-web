package modelo.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.hql.spi.ParameterTranslations;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import controle.ProblemaAdministradorMB;
import modelo.dominio.Administrador;
import modelo.dominio.Cidadao;
import modelo.dominio.Problema;
import modelo.dominio.SubArea;

public class ProblemaDAO {
	/**
	 * O manager para ser utilizado neste DAO.
	 */
	private EntityManager manager = JPAUtil.getFabrica().createEntityManager();

	/**
	 * Armazena todos os predicados (expressões avaliados como TRUE, FALSE ou
	 * UNKNOWN e que serão utilizados junto nas condições de pesquisa das
	 * cláusulas WHERE e AND).
	 */
	private List<String> predicados = new LinkedList<String>();

	/**
	 * Armazena todos os parametros das consultas, utilizados na query.
	 */
	private List<Object> parametros = new LinkedList<Object>();

	/**
	 * Valida se é ou não a primeira consulta. Na primeira consulta só
	 * utiliza-se o primeiro predicado da {@link List} chamada
	 * {@link #predicados} e o(s) primeiro(s) parametro(s) da {@link List}
	 * chamado {@link #parametros}. Caso seja <code>false</code>, a partir da
	 * segunda consulta, executa-se o filtro pegando todos os predicados e
	 * parametros necessários.
	 */
	private Boolean ehprimeiro;

	public List<String> getPredicados() {
		return predicados;
	}

	public void setPredicados(List<String> predicados) {
		this.predicados = predicados;
	}

	public List<Object> getParametros() {
		return parametros;
	}

	public void setParametros(List<Object> parametros) {
		this.parametros = parametros;
	}

	/**
	 * Utilizado pelo {@link Cidadao}, serve para persistir um problema
	 * especificado pelo usuário.
	 * 
	 * @param problema
	 *            {@link Problema} que será persistido no banco pelo
	 *            {@link Cidadao}.
	 * @return {@link Problema} que foi gerado pelo usuário {@link Cidadao}F
	 */
	public Problema salvarProblema(Problema problema) {

		this.manager.getTransaction().begin();

		this.manager.merge(problema);

		this.manager.flush();

		this.manager.getTransaction().commit();

		return problema;
	}

	/**
	 * Utilizado para persistir uma resposta de um usuário {@link Administrador}
	 * num {@link Problema}.
	 * 
	 * @param id
	 *            - um {@link Long} identificando o id do {@link Problema} que
	 *            será atualizado.
	 * @param resposta
	 *            - uma {@link String} contendo a resposta dada pelo usuário
	 *            {@link Administrador} que está logado.
	 * @param adminResponde
	 *            - o {@link Administrador} que está respondendo ao
	 *            {@link Problema}.
	 */
	public void salvarResposta(Long id, String resposta, Administrador adminResponde) {

		this.manager.getTransaction().begin();

		this.manager
				.createQuery(
						"update Problema p set p.resposta = :resposta, p.administrador = :adminResponde where p.id = :id")
				.setParameter("resposta", resposta).setParameter("id", id).setParameter("adminResponde", adminResponde)
				.executeUpdate();

		this.manager.flush();

		this.manager.getTransaction().commit();

	}

	/**
	 * Consultar todos os problemas da classe {@link Problema}, recebendo como
	 * parametro o {@link Cidadao} de que se deseja consultar.
	 * 
	 * @param cidadao
	 *            - de qual {@link Cidadao} retornará o(s) problema(s)
	 * @return - um {@link List} <{@link Problema}> contendo os problemas do
	 *         {@link Cidadao}
	 */
	public List<Problema> listarProblemasDoCidadao(Cidadao cidadao) {

		String comandoJPQL = "FROM Problema p where p.cidadao = :cidadao";

		List<Problema> resultado;

		TypedQuery<Problema> query = this.manager.createQuery(comandoJPQL, Problema.class);
		query.setParameter("cidadao", cidadao);

		resultado = query.getResultList();

		return resultado;
	}

	/**
	 * Na maior parte do tempo este método é executado a partir dos métodos
	 * dentro deste DAO, salvo no método removerFiltros() na classe
	 * {@link ProblemaAdministradorMB} que é invocado para repetir sempre a
	 * ultima consulta realizada. Neste método é executada a consulta de fato ao
	 * banco recuperando a {@link List} de {@link Problema} desejada. Ele
	 * utiliza de todo(s) o(s) predicado(s) e parametro(s) necessários para
	 * construir a {@link TypedQuery} do tipo {@link Problema}.
	 * 
	 * @return - um {@link List} <{@link Problema}> contendo os problemas
	 *         consultados.
	 */
	public List<Problema> executarConsulta() {
		String comandoJPQL = "FROM Problema p where " + this.predicados.get(0);

		if (!this.ehprimeiro) {
			for (int i = 1; i < this.predicados.size(); i++) {
				comandoJPQL += " and ";
				comandoJPQL += this.predicados.get(i);
			}
		}

		TypedQuery<Problema> query = this.manager.createQuery(comandoJPQL, Problema.class);

		for (int i = 0; i < this.parametros.size(); i++) {
			query.setParameter((String) this.parametros.get(i), this.parametros.get(++i));
		}

		return query.getResultList();

	}

	/**
	 * Método invocado somente na primeira consulta por data (sem filtro), é
	 * adicionado no {@link List} {@link #predicados} o predicado necessário,
	 * bem como os seus parametros (as datas 'de' e 'ate' que são do tipo
	 * {@link LocalDate}). Ele também recebe um {@link Boolean} indicando para o
	 * DAO que é a primeira consulta.
	 * 
	 * @param de
	 *            - um {@link LocalDate} indicando a data inicial.
	 * @param ate
	 *            - um {@link LocalDate} indicando a data final.
	 * @param ehprimeiro
	 *            - um {@link Boolean} indicando que é a primeira consulta por
	 *            data.
	 * @return - um {@link List} de {@link Problema} contendo todos os problemas
	 *         por data consultado.
	 */
	public List<Problema> listarProblemasPorData(LocalDate de, LocalDate ate, Boolean ehprimeiro) {
		predicados.add(" p.data >= :de and p.data <= :ate ");
		parametros.add(new String("de"));
		parametros.add(de);
		parametros.add(new String("ate"));
		parametros.add(ate);

		this.ehprimeiro = ehprimeiro;

		return executarConsulta();

	}

	/**
	 * Neste método podem ser passados três tipos de objetos (um
	 * {@link LocalDate}, uma {@link SubArea} ou uma {@link String}), pois o
	 * mesmo método atende a estes três parametros recebendo um {@link Object}.
	 * Verificando o instanceof do {@link Object} sabemos qual o predicado
	 * devemos adicionar ao {@link List} {@link #predicados} e passamos a
	 * {@link List} {@link #parametros} o {@link Object} parseado como objeto
	 * necessário e adicionando a "chave de busca".
	 * 
	 * @param paramFiltro
	 *            - um parametro do tipo {@link Object} que será o parametro
	 *            para executar o filtro.
	 * @return um {@link List} de {@link Problema} filtrado a partir do
	 *         parametro {@link Object} passado.
	 */
	public List<Problema> problemasFiltrados(Object paramFiltro) {
		this.ehprimeiro = false;

		if (paramFiltro instanceof LocalDate) {
			this.predicados.add(" p.data >= :de and p.data <= :ate ");
			this.parametros.add(new String("de"));
			this.parametros.add((LocalDate) paramFiltro);
			this.parametros.add(new String("ate"));
			this.parametros.add((LocalDate) paramFiltro);
		}
		if (paramFiltro instanceof SubArea) {
			this.predicados.add(" p.subArea = :subFiltro ");
			this.parametros.add(new String("subFiltro"));
			this.parametros.add((SubArea) paramFiltro);
		}
		if (paramFiltro instanceof String) {
			String o = (String) paramFiltro;
			if (o.equals("Respondido"))
				this.predicados.add(" p.resposta is not null ");
			else
				this.predicados.add(" p.resposta is null ");
		}

		return this.executarConsulta();

	}

	/**
	 * Método invocado somente na primeira consulta por AreaESubArea (sem
	 * filtro), é adicionado no {@link List} {@link #predicados} o predicado
	 * necessário, bem como o seu parametro (sub que é do tipo {@link SubArea}).
	 * Ele também recebe um {@link Boolean} indicando para o DAO que é a
	 * primeira consulta.
	 * 
	 * @param sub
	 *            - uma {@link SubArea} que será consultada.
	 * @param ehprimeiro
	 *            - um {@link Boolean} indicando que é a primeira consulta por
	 *            AreaESubArea.
	 * @return - um {@link List} de {@link Problema}.
	 */
	public List<Problema> listarProblemasPorAreaESubarea(SubArea sub, Boolean ehprimeiro) {
		this.predicados.add(" p.subArea = :sub ");
		this.parametros.add(new String("sub"));
		this.parametros.add(sub);

		this.ehprimeiro = ehprimeiro;

		return executarConsulta();
	}

	/**
	 * Método invocado somente na primeira consulta por Resposta (sem filtro), é
	 * adicionado no {@link List} {@link #predicados} o predicado necessário.
	 * Ele também recebe um {@link Boolean} indicando para o DAO que é a
	 * primeira consulta.
	 * 
	 * @param tipoResposta
	 *            - uma {@link String} que deve conter o texto "respondido" ou
	 *            "a-responder".
	 * @param ehprimeiro
	 *            - um {@link Boolean} indicando que é a primeira consulta por
	 *            Resposta.
	 * @return - um {@link List} de {@link Problema}.
	 */
	public List<Problema> listarProblemasPorResposta(String tipoResposta, Boolean ehprimeiro) {

		if (tipoResposta.equals("respondido"))
			this.predicados.add(" p.resposta is not null ");
		else
			this.predicados.add(" p.resposta is null ");

		this.ehprimeiro = ehprimeiro;

		return executarConsulta();

	}

	/**
	 * Método utilizado para saber quais os problemas estão contidos dentro de
	 * um perímetro passado do tipo {@link Polygon}. As coordenadas deste
	 * polígono já devem ter sido adicionadas ao objeto {@link Polygon} passado.
	 * Se no atributo chamado local que é do tipo {@link Point} da classe
	 * {@link Problema} tiver as coordenadas latitude e longitude e essas
	 * estejam "dentro" do polígono passado, este(s) problema(s) será(ão)
	 * retornado(s).
	 * 
	 * @param poligono
	 *            - um {@link Polygon} que representa o perímetro que se quer
	 *            consultar.
	 * @return - um {@link List} de {@link Problema}.
	 */
	public List<Problema> listarProblemasPorPerimetro(Polygon poligono) {
		String comandoJPQL = "FROM Problema p where within(p.local, :poligono) = true";

		List<Problema> resultado;
		TypedQuery<Problema> query = this.manager.createQuery(comandoJPQL, Problema.class);
		query.setParameter("poligono", poligono);

		resultado = query.getResultList();

		return resultado;
	}
}
