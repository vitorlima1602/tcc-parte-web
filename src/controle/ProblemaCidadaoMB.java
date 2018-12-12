package controle;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import controle.util.JSFUtil;
import controle.util.JSONProblema;
import modelo.dao.AreaDAO;
import modelo.dao.ProblemaDAO;
import modelo.dominio.Area;
import modelo.dominio.Cidadao;
import modelo.dominio.Problema;
import modelo.dominio.SubArea;

@ManagedBean(name = "problemaCidadaoMB")
@SessionScoped
public class ProblemaCidadaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * Cidadao que está logado no momento.
	 */
	@ManagedProperty(value = "#{loginCidMB}")
	private LoginCidadaoMB loginCidadao;

	/**
	 * Armazena a latitude inserida na view na hora do registro do problema.
	 */
	private String latitude;
	/**
	 * Armazena a longitude inserida na view na hora do registro do problema.
	 */
	private String longitude;
	/**
	 * Armazena a data do problema.
	 */
	private LocalDate data;
	/**
	 * Armazena a descrição do problema.
	 */
	private String descricao;

	@PostConstruct
	public void init() {
		this.areaSelecionada = new Area();
		this.areas = new ArrayList<Area>();
		this.areas = new AreaDAO().listar();
		this.areas.sort(null);
		this.subAreaSelecionada = new SubArea();
		this.subAreas = new ArrayList<SubArea>();
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public LoginCidadaoMB getLoginCidadao() {
		return loginCidadao;
	}

	public void setLoginCidadao(LoginCidadaoMB loginCidadao) {
		this.loginCidadao = loginCidadao;
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
	 * Registrar um problema apontado no mapa, validando se todos os campos não
	 * são nulos, caso contrário é disparada uma mensagem na view. É necessário
	 * criar um objeto do tipo {@link Point} que armazena a latitude e longitude
	 * de onde o problema foi apontado no mapa.
	 */
	public void registrar() {
		Boolean camposVazio = false;
		RequestContext contexto = RequestContext.getCurrentInstance();

		if (this.areaSelecionada == null) {
			JSFUtil.retornarMensagemFatal("Selecione uma Área", null, null);
			camposVazio = true;
		}
		if (this.subAreaSelecionada == null) {
			JSFUtil.retornarMensagemFatal("Selecione uma Sub-Área", null, null);
			camposVazio = true;
		}
		if (this.latitude == null || this.latitude.equals("") || this.longitude == null 
				|| this.longitude.equals("")) {
			JSFUtil.retornarMensagemFatal("Clique sobre o mapa onde ocorreu o problema", null, null);
			camposVazio = true;
		}
		if (this.data == null) {
			JSFUtil.retornarMensagemFatal("Selecione a data que ocorreu o problema", null, null);
			camposVazio = true;
		}
		if (this.descricao == null || this.descricao.equals("")) {
			JSFUtil.retornarMensagemFatal("Insira a descrição do problema", null, null);
			camposVazio = true;
		}
		if (!camposVazio) {
			Coordinate coordenadas = new Coordinate(Double.valueOf(latitude), Double.valueOf(longitude));
			Point ponto = new GeometryFactory(new PrecisionModel(), 4326).createPoint(coordenadas);
			Cidadao cid = this.loginCidadao.getCidadao();
			Problema problema = new Problema(this.descricao, this.data, ponto, cid, this.getSubAreaSelecionada());
			new ProblemaDAO().salvarProblema(problema);

			this.limparCamposRegistrar();

			JSFUtil.retornarMensagemInfo("Problema registrado com sucesso!", null, null);

			contexto.execute("removerMarcadorProblema(); registrar = false; sidebarRP.hide();");
		}

	}

	/**
	 * Limpa os campos da interface registrar problema
	 */
	public void limparCamposRegistrar() {
		this.areas = new ArrayList<Area>();
		this.areas = new AreaDAO().listar();
		this.subAreas = null;
		this.areaSelecionada = null;
		this.subAreaSelecionada = null;
		this.data = null;
		this.descricao = null;
		this.latitude = null;
		this.longitude = null;
	}

	/**
	 * Consulta todos os problemas do {@link Cidadao} logado e retorna à view.
	 */
	public void consultarProblemas() {
		List<Problema> problemasCidadao = new ProblemaDAO().listarProblemasDoCidadao(this.loginCidadao.getCidadao());
		String json = JSONProblema.jsonProblemas(problemasCidadao);

		RequestContext reqCtx = RequestContext.getCurrentInstance();
		reqCtx.addCallbackParam("parametros", json);
	}
}
