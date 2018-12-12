package controle.util;

import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class JSFUtil {
	public static void retornarMensagemErro(String mensagem, String detalhe, String idComponentePagina) {
		retornarMensagem(FacesMessage.SEVERITY_ERROR, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemFatal(String mensagem, String detalhe, String idComponentePagina) {
		retornarMensagem(FacesMessage.SEVERITY_FATAL, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemAviso(String mensagem, String detalhe, String idComponentePagina) {
		retornarMensagem(FacesMessage.SEVERITY_WARN, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemInfo(String mensagem, String detalhe, String idComponentePagina) {
		retornarMensagem(FacesMessage.SEVERITY_INFO, mensagem, detalhe, idComponentePagina);
	}

	private static void retornarMensagem(Severity status, String mensagem, String detalhe, String idComponentePagina) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(idComponentePagina, new FacesMessage(status, mensagem, detalhe));
	}

	public static HttpSession getHttpSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

		return session;
	}

	public static Object getVariavelApplication(String nomeDaVariavel) {
		ELContext elContexto = FacesContext.getCurrentInstance().getELContext();
		Object obj = FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContexto, null,
				nomeDaVariavel);

		return obj;
	}

	public static String getParametro(String nomeDoParametro) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		String valor = map.get(nomeDoParametro);

		return valor;
	}

	public static Long getParametroLong(String nomeDoParametro) {
		String valor = getParametro(nomeDoParametro);
		Long valorLong;
		try {
			valorLong = new Long(valor);
		} catch (Exception e) {
			valorLong = null;
		}

		return valorLong;
	}
}
