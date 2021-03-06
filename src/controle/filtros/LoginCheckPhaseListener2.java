package controle.filtros;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import controle.LoginAdministradorMB;
import controle.LoginCidadaoMB;
import controle.util.JSFUtil;

@SuppressWarnings("serial")
public class LoginCheckPhaseListener2 implements PhaseListener {

	// CICLO DE VIDA DO JSF
	// 1. Restore view
	// 2. Apply request values; process events
	// 3. Process validations; process events
	// 4. Update model values; process events
	// 5. Invoke application; process events
	// 6. Render response

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW; // executar na primeira fase (in�cio do
										// processamento)
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		boolean cidadaoAutenticado = false;
		boolean adminAutenticado = false;

		LoginCidadaoMB loginCidMB = (LoginCidadaoMB) JSFUtil.getVariavelApplication("loginCidMB");
		LoginAdministradorMB loginAdmMB = (LoginAdministradorMB) JSFUtil.getVariavelApplication("loginAdmMB");

		if (loginCidMB != null)
			cidadaoAutenticado = loginCidMB.isAutenticado();

		if (loginAdmMB != null)
			adminAutenticado = loginAdmMB.isAutenticado();

		FacesContext contexto = event.getFacesContext();

		boolean estaNaPaginaIndex = contexto.getViewRoot().getViewId().lastIndexOf("index") > -1 ? true : false;
		boolean estaNaPaginaLoginCidadao = contexto.getViewRoot().getViewId().lastIndexOf("loginCidadao") > -1 ? true
				: false;
		boolean estaNaPaginaLoginAdmin = contexto.getViewRoot().getViewId().lastIndexOf("loginAdministrador") > -1
				? true : false;

		if (!estaNaPaginaIndex)
			estaNaPaginaIndex = contexto.getViewRoot().getViewId().lastIndexOf("sessaoExpirada") > -1 ? true : false;

		if (!estaNaPaginaLoginCidadao)
			estaNaPaginaLoginCidadao = contexto.getViewRoot().getViewId().lastIndexOf("sessaoExpirada") > -1 ? true
					: false;

		if (!estaNaPaginaLoginAdmin)
			estaNaPaginaLoginAdmin = contexto.getViewRoot().getViewId().lastIndexOf("sessaoExpirada") > -1 ? true
					: false;

		if (!estaNaPaginaIndex && !estaNaPaginaLoginCidadao && !estaNaPaginaLoginAdmin
				&& !cidadaoAutenticado && !adminAutenticado) {
			NavigationHandler nh = contexto.getApplication().getNavigationHandler();
			nh.handleNavigation(contexto, null, "sessaoExpirada.jsf");
		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

}
