package controle;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

import controle.util.JSFUtil;
import controle.util.JSONProblema;
import modelo.dao.AreaDAO;
import modelo.dao.ProblemaDAO;
import modelo.dominio.Administrador;
import modelo.dominio.Area;
import modelo.dominio.Problema;
import modelo.dominio.SubArea;

@ManagedBean(name = "problemaAdministradorMB")
@SessionScoped
public class ProblemaAdministradorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Administrador que está logado no momento
	 */
	@ManagedProperty(value = "#{loginAdmMB}")
	private LoginAdministradorMB loginAdm;
	/**
	 * Variável para consulta da data inicial
	 */
	private LocalDate de;
	/**
	 * Variável para consulta da data final
	 */
	private LocalDate ate;
	/**
	 * Variável indicando qual a área selecionada na view de registro do
	 * problema..
	 */
	private Area areaSelecionada;
	/**
	 * Uma lista de áreas que é carregada do banco.
	 */
	private List<Area> areas;

	/**
	 * Variável indicando a sub-área selecionada na view de registro do
	 * problema.
	 */
	private SubArea subAreaSelecionada;
	/**
	 * Uma lista de sub-áreas que é carregada do banco.
	 */
	private List<SubArea> subAreas;
	/**
	 * idProblemaAResponder que está na view e identificará qual o problema será
	 * respondido
	 */
	private Long idProblemaAResponder;
	/**
	 * Resposta para o problema
	 */
	private String resposta;
	/**
	 * Usada no selectoneradio para dizer que tipo de consulta será feita:
	 * 'respondido' ou 'a responder'
	 */
	private String tipoResposta;
	/**
	 * Recebe os quatros pontos do perimetro da consulta por perimetro, apartir
	 * destes quatros pontos criar um poligono para saber quais os problemas que
	 * estão dentre dele
	 */
	private String boundsPerimetro;
	/**
	 * Informa ao ProblemaDAO se é a primeira consulta
	 */
	private Boolean primeiraConsulta = false;
	/**
	 * DAO de problema
	 */
	private ProblemaDAO daoProblema = new ProblemaDAO();
	/**
	 * Armazena a data clicada no filtro
	 */
	private LocalDate dataFiltro;
	/**
	 * Armazena uma lista de datas de problemas que foram consultadas e irão
	 * servir de filtro para novas consultas
	 */
	private Set<LocalDate> datasFiltro;
	/**
	 * Armazena a subArea clicada no filtro
	 */
	private SubArea subAreaFiltro;
	/**
	 * Armazena uma lista de objetos modelo.dominio.SubArea após uma consulta de
	 * problemas e que será usada para novas consultas filtros
	 */
	private Set<SubArea> subAreasFiltro;

	/**
	 * Armazena o tipo de resposta ("A responder" ou "Respondido") no filtro
	 * depois de executada uma consulta
	 */
	private String tipoRespostaFiltro;

	/**
	 * Armazena a lista de tipos de resposta resposta ("A responder" ou
	 * "Respondido") quando alguma consulta é efetuada
	 */
	private Set<String> tiposRespostaFiltro;

	@PostConstruct
	public void init() {
		this.de = null;
		this.ate = null;
		this.areaSelecionada = new Area();
		this.areas = new ArrayList<Area>();
		this.areas = new AreaDAO().listar();
		this.areas.sort(null);
		this.subAreaSelecionada = new SubArea();
		this.subAreas = new ArrayList<SubArea>();
	}

	public LoginAdministradorMB getLoginAdm() {
		return loginAdm;
	}

	public void setLoginAdm(LoginAdministradorMB loginAdm) {
		this.loginAdm = loginAdm;
	}

	public LocalDate getDe() {
		return de;
	}

	public void setDe(LocalDate de) {
		this.de = de;
	}

	public LocalDate getAte() {
		return ate;
	}

	public void setAte(LocalDate ate) {
		this.ate = ate;
	}

	public Area getAreaSelecionada() {
		return areaSelecionada;
	}

	public void setAreaSelecionada(Area areaSelecionada) {
		this.areaSelecionada = areaSelecionada;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public SubArea getSubAreaSelecionada() {
		return subAreaSelecionada;
	}

	public void setSubAreaSelecionada(SubArea subAreaSelecionada) {
		this.subAreaSelecionada = subAreaSelecionada;
	}

	public List<SubArea> getSubAreas() {
		return subAreas;
	}

	public void setSubAreas(List<SubArea> subAreas) {
		this.subAreas = subAreas;
	}

	public Long getIdProblemaAResponder() {
		return idProblemaAResponder;
	}

	public void setIdProblemaAResponder(Long idProblemaAResponder) {
		this.idProblemaAResponder = idProblemaAResponder;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getTipoResposta() {
		return tipoResposta;
	}

	public void setTipoResposta(String tipoResposta) {
		this.tipoResposta = tipoResposta;
	}

	public String getBoundsPerimetro() {
		return boundsPerimetro;
	}

	public void setBoundsPerimetro(String boundsPerimetro) {
		this.boundsPerimetro = boundsPerimetro;
	}

	public Boolean getPrimeiraConsultaData() {
		return primeiraConsulta;
	}

	public void setPrimeiraConsultaData(Boolean primeiraConsulta) {
		this.primeiraConsulta = primeiraConsulta;
	}

	public ProblemaDAO getDaoProblema() {
		return daoProblema;
	}

	public void setDaoProblema(ProblemaDAO daoProblema) {
		this.daoProblema = daoProblema;
	}

	public Set<LocalDate> getDatasFiltro() {
		return datasFiltro;
	}

	public void setDatasFiltro(Set<LocalDate> datasFiltro) {
		this.datasFiltro = datasFiltro;
	}

	public LocalDate getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(LocalDate dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public SubArea getSubAreaFiltro() {
		return subAreaFiltro;
	}

	public void setSubAreaFiltro(SubArea subAreaFiltro) {
		this.subAreaFiltro = subAreaFiltro;
	}

	public Set<SubArea> getSubAreasFiltro() {
		return subAreasFiltro;
	}

	public void setSubAreasFiltro(Set<SubArea> subAreasFiltro) {
		this.subAreasFiltro = subAreasFiltro;
	}

	public String getTipoRespostaFiltro() {
		return tipoRespostaFiltro;
	}

	public void setTipoRespostaFiltro(String tipoRespostaFiltro) {
		this.tipoRespostaFiltro = tipoRespostaFiltro;
	}

	public Set<String> getTiposRespostaFiltro() {
		return tiposRespostaFiltro;
	}

	public void setTiposRespostaFiltro(Set<String> tiposRespostaFiltro) {
		this.tiposRespostaFiltro = tiposRespostaFiltro;
	}

	/**
	 * Método invocado pela view para informar aos metodos de consulta que será
	 * a primeira consulta e não uma consulta/filtro.
	 */
	public void ehprimeiraConsulta() {
		this.primeiraConsulta = true;
		this.daoProblema = new ProblemaDAO();
	}

	/**
	 * Utilizado como um listener para a view, quando selecionar uma área,
	 * carrego somente as sub-áreas necessárias.
	 */
	public void areaEscolhida() {
		if (this.areaSelecionada != null) {
			this.subAreas = new ArrayList<SubArea>();
			List<SubArea> subs = this.getAreaSelecionada().getSubAreas();
			subs.sort(null);
			this.setSubAreas(subs);
		}
	}

	/**
	 * Método utilizado para efetuar um consulta de problemas por data inicial e
	 * final, se as datas estiverem válidas é realizada a consulta com o uso do
	 * DAO {@link #daoProblema} e o retorno da lista problema é passada para o
	 * método {@link #problemasFiltrados(List)} que será capaz de enviar os
	 * problemas para a view.
	 */
	public void consultarPorData() {

		Boolean dataValida = true;
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(new Locale("pt", "br"));

		if (this.de.isAfter(hoje)) {
			JSFUtil.retornarMensagemFatal(
					"A data \'De: \' não pode ser posterior a data de hoje: " 
							+ hoje.format(formatter), null, "de");
			dataValida = false;
		}
		if (this.ate.isAfter(hoje)) {
			JSFUtil.retornarMensagemFatal(
					"A data \'Até: \' não pode ser posterior a data de hoje: " 
							+ hoje.format(formatter), null, "ate");
			dataValida = false;
		}
		if (this.de.isAfter(this.ate)) {
			JSFUtil.retornarMensagemFatal("A data \'De: \' " + this.de.format(formatter)
					+ " não pode ser posterior a data \'Até: \' " 
						+ this.ate.format(formatter), null, "de");
			dataValida = false;
		}

		if (dataValida) {

			List<Problema> problemasPorData = this.daoProblema.
					listarProblemasPorData(de, ate, this.primeiraConsulta);
			this.problemasFiltrados(problemasPorData);

		}
	}

	/**
	 * Método utilizado para consultar problemas por Area e SubArea, utilizando
	 * o DAO {@link #daoProblema} a consulta é realizada no banco e o seu
	 * retorno é passado para o método {@link #problemasFiltrados(List)} onde
	 * será responsável para passar a resposta para a view.
	 */
	public void consultarPorAreaESubarea() {
		List<Problema> problemasPorAreaESubArea = this.daoProblema
				.listarProblemasPorAreaESubarea(this.subAreaSelecionada, this.primeiraConsulta);

		this.problemasFiltrados(problemasPorAreaESubArea);

	}

	/**
	 * Método utilizado para consultar problemas por tipo de resposta, o retorno
	 * do DAO {@link #daoProblema} é passado para o método
	 * {@link #problemasFiltrados(List)} que fica responsável por repassar para
	 * a view a resposta.
	 */
	public void consultarPorResposta() {

		List<Problema> problemasPorResposta = this.daoProblema.
					listarProblemasPorResposta(this.tipoResposta,
				this.primeiraConsulta);

		this.problemasFiltrados(problemasPorResposta);

	}

	/**
	 * Método utilizado para capturar os filtros de uma consulta inicial
	 * (problemasPorData, problemasPorAreaESubArea ou problemasPorResposta),
	 * realizar a consulta a partir de um destes filtros e executar o metodo
	 * {@link #problemasFiltrados(List)} retornando este filtro para a view.
	 */
	public void executarFiltros() {
		List<Problema> problemasFiltrados = null;
		if (this.dataFiltro != null)
			problemasFiltrados = this.daoProblema.problemasFiltrados(this.dataFiltro);
		else if (this.subAreaFiltro != null)
			problemasFiltrados = this.daoProblema.problemasFiltrados(this.subAreaFiltro);
		else if (!this.tipoRespostaFiltro.equals(""))
			problemasFiltrados = this.daoProblema.problemasFiltrados(this.tipoRespostaFiltro);
		this.dataFiltro = null;
		this.subAreaFiltro = null;
		this.tipoRespostaFiltro = "";

		RequestContext contexto = RequestContext.getCurrentInstance();
		contexto.execute("removeFiltro.style.display = 'block';");

		this.problemasFiltrados(problemasFiltrados);
	}

	/**
	 * Remove as opções de filtros quando o usuário clica em voltar na janela de
	 * filtros, executando assim a ultima consulta e removendo assim os últimos
	 * predicados e parametros do DAO {@link #daoProblema}. Ao final é passado
	 * para o método {@link #problemasFiltrados(List)} para que seja o
	 * responsável por entregar a view os problemas retornados.
	 */
	public void removerFiltro() {
		List<Object> parametrosDAO = this.daoProblema.getParametros();
		List<String> predicadosDAO = this.daoProblema.getPredicados();
		RequestContext contexto = RequestContext.getCurrentInstance();
		if (parametrosDAO.size() > 1 && predicadosDAO.size() > 1) {
			int i = 0;

			String ultimoPredicado = predicadosDAO.get(predicadosDAO.size() - 1);

			if (ultimoPredicado.equals(" p.resposta is not null ") || ultimoPredicado.equals(" p.resposta is null "))
				i = 0;
			else if (parametrosDAO.get(parametrosDAO.size() - 1) instanceof LocalDate) {
				i = 4;

			} else if (parametrosDAO.get(parametrosDAO.size() - 1) instanceof SubArea) {
				i = 2;
			}

			if (i != 0) {
				for (int j = parametrosDAO.size(); i > 0; i--) {
					parametrosDAO.remove(--j);
				}
			}

			predicadosDAO.remove(predicadosDAO.size() - 1);

			if (parametrosDAO.size() > 1 && predicadosDAO.size() > 1)
				contexto.execute("removeFiltro.style.display = 'block';");
			else
				contexto.execute("removeFiltro.style.display = 'none';");

			List<Problema> ultimosProblemas = this.daoProblema.executarConsulta();

			this.problemasFiltrados(ultimosProblemas);

		} else {
			contexto.execute("removeFiltro.style.display = 'none';");
		}
	}

	/**
	 * Este método recebe um {@link List}<{@link Problema}> consultado
	 * anteriormente, inicia novas {@link TreeSet} que representam cada uma, uma
	 * lista que irá conter os filtros daquela categoria. Intera sobre a lista
	 * passada e para cada objeto/filtro, adiciona a {@link TreeSet}
	 * correspondente. Ao final repassa a view um {@link Gson} criado pela
	 * classe {@link JSONProblema}.
	 * 
	 * @param lista
	 *            - recebe uma lista de problemas consultados previamente por
	 *            algum outro método de consulta.
	 */
	public void problemasFiltrados(List<Problema> lista) {
		if (lista.size() > 0) {
			lista.sort(null);
			this.datasFiltro = new TreeSet<LocalDate>();
			this.subAreasFiltro = new TreeSet<SubArea>();
			this.tiposRespostaFiltro = new TreeSet<String>();
			for (Problema problema : lista) {
				this.datasFiltro.add(problema.getData());
				this.subAreasFiltro.add(problema.getSubArea());
				if (problema.getResposta() == null)
					this.tiposRespostaFiltro.add(new String("A responder"));
				else
					this.tiposRespostaFiltro.add(new String("Respondido"));
			}
		}
		this.primeiraConsulta = false;

		String json = JSONProblema.jsonProblemas(lista);

		RequestContext contexto = RequestContext.getCurrentInstance();
		contexto.addCallbackParam("parametros", json);

	}

	/**
	 * Persiste no banco as respostas dado pelo {@link Administrador} logado.
	 * Avisa na view caso algum erro ocorra ou informa o sucesso de salvar a
	 * resposta.
	 */
	public void responder() {
		if (this.resposta == null || this.resposta.equals("")) {
			JSFUtil.retornarMensagemAviso("Resposta inválida! Digite sua resposta com no máximo 500 caracteres", null,
					null);
		} else {
			RequestContext contexto = RequestContext.getCurrentInstance();
			try {
				Administrador adminResponde = this.loginAdm.getAdmin();
				this.daoProblema.salvarResposta(this.idProblemaAResponder, this.resposta, adminResponde);
				contexto.execute("erroAoResponder = false");
				JSFUtil.retornarMensagemInfo("Resposta adicionada com sucesso!", null, null);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				contexto.execute("erroAoResponder = true");
				JSFUtil.retornarMensagemErro(
						"Desculpe, mas houve algum erro ao salvar sua resposta. Por favor tente novamente mais tarde!",
						null, null);
			}
		}
	}

	/**
	 * Método utilizado por executar consultas de problemas a partir de um
	 * perímetro (polígono) que é desenhado pelo {@link Administrador} na view.
	 * Os valores do perímetro são armazenados numa {@link String} chamada
	 * {@link #boundsPerimetro}, separa-se as latitudes e longitudes de cada
	 * ponto e armazena num array {@link Coordinate}, sendo os quatro pontos do
	 * polígono e mais um que é o ponto inicial e fechamento do polígono. O
	 * polígono é criado a partir das classes {@link LinearRing} e
	 * {@link Polygon}. A consulta é realizada no DAO {@link #daoProblema}
	 * passando este polígono, o seu retorno é repassado a classe
	 * {@link JSONProblema} que gera um {@link Gson}, o retorno que é json é
	 * repassado a view.
	 */
	public void consultarPorPerimetro() {
		String[] cooString = this.boundsPerimetro.split(" ");
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		Coordinate[] coords = new Coordinate[] {
				new Coordinate(Double.parseDouble(cooString[0]), Double.parseDouble(cooString[1])),
				new Coordinate(Double.parseDouble(cooString[2]), Double.parseDouble(cooString[3])),
				new Coordinate(Double.parseDouble(cooString[4]), Double.parseDouble(cooString[5])),
				new Coordinate(Double.parseDouble(cooString[6]), Double.parseDouble(cooString[7])),
				new Coordinate(Double.parseDouble(cooString[0]), Double.parseDouble(cooString[1])), };

		LinearRing ring = geometryFactory.createLinearRing(coords);
		LinearRing holes[] = null;
		Polygon poligono = geometryFactory.createPolygon(ring, holes);

		List<Problema> problemasPorPerimetro = this.daoProblema.listarProblemasPorPerimetro(poligono);

		String json = JSONProblema.jsonProblemas(problemasPorPerimetro);

		RequestContext contexto = RequestContext.getCurrentInstance();
		contexto.addCallbackParam("parametros", json);
	}

}
