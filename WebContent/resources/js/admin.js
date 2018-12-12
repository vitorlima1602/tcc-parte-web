// variavel responsavel pelo controle de camadas no mapa (liga e desliga
// camadas), adicionada após o zoom 8.5 do mapa e retirada quando menor (mais afastado do mapa)
var controleCamadas = L.control.layers(null, null, {
	collapsed : false,
	sortLayers : true,
});

// função para adicionar as camadas ao controlador de camadas
function addControleCamadas(camadas) {
	for (i = 0; i < camadas.length; i++) {
		controleCamadas.addOverlay(camadas[i], camadas[++i]);
	}
};

// função para remover as camadas do controlador de camadas
function removeControleCamadas(camadas) {
	for (i = 0; i < camadas.length; i++) {
		controleCamadas.removeLayer(camadas[i]);
	}
};

// função para verificar se no controlador de camadas existe a camada
function temCamadaNoControle(camada) {
	if (mapa.hasLayer(camada)) {
		for (i = 0; i < controleCamadas._layers.length; i++) {
			if (controleCamadas._layers[i].layer.options.id == camada.options.id)
				return true;
		}
		return false;
	}
};

// função que irá adicionar as camadas ao controlador de camadas (liga e
// desliga)
mapa
		.on(
				"zoom",
				function() {
					if (mapa.getZoom() >= 8.5
							&& !mapa.hasLayer(controleCamadas))
						controleCamadas.addTo(mapa);
					else if (mapa.getZoom() < 8.5)
						mapa.removeControl(controleCamadas);

					if (mapa.getZoom() >= 8.5) {
						if (!temCamadaNoControle(florestas)
								&& !temCamadaNoControle(fronteiras)
								&& !temCamadaNoControle(massa_agua))
							addControleCamadas([ florestas, "Florestas",
									fronteiras, "Fronteiras", massa_agua,
									"Massas de agua" ]);
					}
					if (mapa.getZoom() >= 9.5) {
						if (!temCamadaNoControle(cidades)
								&& !temCamadaNoControle(ilhas)
								&& !temCamadaNoControle(vilas))
							addControleCamadas([ cidades, "Cidades", ilhas,
									"Ilhas", vilas, "Vilas" ]);
					}
					// se o usuário se afastar do ponto em que as camadas não
					// serão mais
					// visíveis, retiro do controlador
					else {

						// removendo todas as camadas do controlador de camadas,
						// testando pelo menos se uma camada (cidades) do grupo
						// já está no mapa
						if (temCamadaNoControle(cidades)
								&& temCamadaNoControle(ilhas)
								&& temCamadaNoControle(vilas))
							removeControleCamadas([ cidades, ilhas, vilas ]);
					}
					if (mapa.getZoom() >= 10.5) {
						if (!temCamadaNoControle(estradas_ferro)
								&& !temCamadaNoControle(acessos_autoestradas)
								&& !temCamadaNoControle(autoestradas)
								&& !temCamadaNoControle(estradas_primarias)
								&& !temCamadaNoControle(estradas_secundarias)
								&& !temCamadaNoControle(estradas_terciarias)
								&& !temCamadaNoControle(pistas_aeroportos))
							addControleCamadas([ estradas_ferro,
									"Estradas de ferro", acessos_autoestradas,
									"Acessos a autoestradas", autoestradas,
									"Autoestradas", estradas_primarias,
									"Estradas primarias", estradas_secundarias,
									"Estradas secundarias",
									estradas_terciarias, "Estradas terciarias",
									pistas_aeroportos, "Pistas de aeroportos" ]);
					} else {
						if (temCamadaNoControle(estradas_ferro)
								&& temCamadaNoControle(acessos_autoestradas)
								&& temCamadaNoControle(autoestradas)
								&& temCamadaNoControle(estradas_primarias)
								&& temCamadaNoControle(estradas_secundarias)
								&& temCamadaNoControle(estradas_terciarias)
								&& temCamadaNoControle(pistas_aeroportos))
							removeControleCamadas([ estradas_ferro,
									acessos_autoestradas, autoestradas,
									estradas_primarias, estradas_secundarias,
									estradas_terciarias, pistas_aeroportos ]);
					}
					if (mapa.getZoom() >= 11.5) {
						if (!temCamadaNoControle(estradas_ligacao_primarias)
								&& !temCamadaNoControle(estradas_ligacao_secundarias)
								&& !temCamadaNoControle(estradas_ligacao_terciarias))
							addControleCamadas([ estradas_ligacao_primarias,
									"Estradas ligacao primarias",
									estradas_ligacao_secundarias,
									"Estradas ligacao secundarias",
									estradas_ligacao_terciarias,
									"Estradas ligacao terciarias" ]);
					} else {
						if (temCamadaNoControle(estradas_ligacao_primarias)
								&& temCamadaNoControle(estradas_ligacao_secundarias)
								&& temCamadaNoControle(estradas_ligacao_terciarias))
							removeControleCamadas([ estradas_ligacao_primarias,
									estradas_ligacao_secundarias,
									estradas_ligacao_terciarias ]);
					}
					if (mapa.getZoom() >= 12.5) {
						if (!temCamadaNoControle(estradas_tronco)
								&& !temCamadaNoControle(estradas_ligacao_tronco)
								&& !temCamadaNoControle(estradas_servicos)
								&& !temCamadaNoControle(estradas_nao_classificadas)
								&& !temCamadaNoControle(estradas_planejadas)
								&& !temCamadaNoControle(estradas_agricolas_florestais)
								&& !temCamadaNoControle(outras_estradas))
							addControleCamadas([ estradas_tronco,
									"Estradas tronco", estradas_ligacao_tronco,
									"Estradas ligacao tronco",
									estradas_servicos, "Estradas servicos",
									estradas_nao_classificadas,
									"Estradas nao classificadas",
									estradas_planejadas, "Estradas planejadas",
									estradas_agricolas_florestais,
									"Estradas agricolas florestais",
									outras_estradas, "Outras estradas" ]);
					} else {
						if (temCamadaNoControle(estradas_tronco)
								&& temCamadaNoControle(estradas_ligacao_tronco)
								&& temCamadaNoControle(estradas_servicos)
								&& temCamadaNoControle(estradas_nao_classificadas)
								&& temCamadaNoControle(estradas_planejadas)
								&& temCamadaNoControle(estradas_agricolas_florestais)
								&& temCamadaNoControle(outras_estradas))
							removeControleCamadas([ estradas_tronco,
									estradas_ligacao_tronco, estradas_servicos,
									estradas_nao_classificadas,
									estradas_planejadas,
									estradas_agricolas_florestais,
									outras_estradas ]);
					}
					if (mapa.getZoom() >= 14.5) {
						if (!temCamadaNoControle(ruas_para_pedestres)
								&& !temCamadaNoControle(ruas_residenciais)
								&& !temCamadaNoControle(ruas_residenciais_pref_pedestres)
								&& !temCamadaNoControle(estradas_contrucao)
								&& !temCamadaNoControle(estradas_nao_definidas))
							addControleCamadas([ ruas_para_pedestres,
									"Ruas para pedestres", ruas_residenciais,
									"Ruas residenciais",
									ruas_residenciais_pref_pedestres,
									"Ruas residenciais pref pedestres",
									estradas_contrucao,
									"Estradas em construcao",
									estradas_nao_definidas,
									"Estradas nao definidas" ]);
					} else {
						if (temCamadaNoControle(ruas_para_pedestres)
								&& temCamadaNoControle(ruas_residenciais)
								&& temCamadaNoControle(ruas_residenciais_pref_pedestres)
								&& temCamadaNoControle(estradas_contrucao)
								&& temCamadaNoControle(estradas_nao_definidas))
							removeControleCamadas([ ruas_para_pedestres,
									ruas_residenciais,
									ruas_residenciais_pref_pedestres,
									estradas_contrucao, estradas_nao_definidas ]);
					}
				});

// Armazena a ultima consulta feita pelo Administrador, necessária para quando
// ele responder algum problema a consulta ser refeita no final.
var tipoConsulta;

// Legenda dos marcadores (Verde = 'Respondido' e Vermelho = 'A responder')
var legenda = L.control({
	position : 'bottomleft'
});

legenda.onAdd = function(mapa) {

	var div = L.DomUtil.create('div', 'info legend'), labels = [];

	labels.push('<img src="' + imgComResp + '">' + '<i>Respondido</i>');
	labels.push('<img src="' + imgSemResp + '">' + '<i>A responder</i>');

	div.innerHTML = labels.join('<br>');
	return div;
};

legenda.addTo(mapa);

// Classe que extende a classe Icon da Leaflet, para criar os ícones expecificos
// dos problemas
var IconeProblema = L.Icon.extend({
	options : {
		shadowUrl : sombraIcones,
		iconAnchor : [ 11, 40 ],
		popupAnchor : [ -1.5, -38 ],
		shadowAnchor : [ 11, 47 ]
	}
});

// Criando novos ícones a partir da Classe IconeProblema (Ícones Verde e
// Vermelho). As imagens (imgSemResp e imgComResp) estão declaradas no script no
// corpo da página mapaAdministrador
var iconSemResposta = new IconeProblema({
	iconUrl : imgSemResp
}), iconComResposta = new IconeProblema({
	iconUrl : imgComResp
});

// Este objeto é o responsável por receber o geojson que é instanciado na
// passagem dos parametros na function 'parametrosProblemas()' vindos do
// servidor, aqui também especifico o tipo de ícone que o problema representado
// no mapa terá, se a resposta for nula = icone vermelho, ao contrário = icone
// verde
var problemas = new L.GeoJSON(null, {
	pointToLayer : function(feature, latlng) {
		if (feature.properties.resposta == null) {
			return L.marker(latlng, {
				icon : iconSemResposta
			});
		}
		return L.marker(latlng, {
			icon : iconComResposta
		});
	}
});

// Objeto responsável por agrupar os ícones problemas e utilizar o recurso de
// mesclar marcadores próximos uns dos outros
var marcadores = L.markerClusterGroup().bindPopup(
		'Detalhes do problema na janela à esquerda!');

// Adicionando uma janela lateral para mostrar detalhes do problema, utilizando
// a divDetalhesDoProblema construída na página mapaAdministrador
var sidebarDDP = L.control.sidebar(divDetalhesDoProblema, {
	position : 'left'
});
mapa.addControl(sidebarDDP);

// Adicionando uma janela lateral para mostrar filtros a partir da consulta
// inicial
var sidebarFiltros = L.control.sidebar(divFiltrosConsultas, {
	position : 'left'
});
mapa.addControl(sidebarFiltros);

// A cada click num marcador de problema no mapa, adiciono cada valor desse
// problema nos campos correspondentes na divDetalhesProblemas, testo se a
// sidebar e a div estão ou não visiveis, caso contrário aciono elas. Outro
// ponto importante é se há a possibilidade ou não de resposta por parte do
// administrador, habilito ou não a caixa de resposta e o botão correspondente
problemas
		.on(
				'click',
				function(feature) {
					document.getElementById('idProblemaAResponder').value = feature.layer.feature.properties.id;
					document.getElementById('areaC').value = feature.layer.feature.properties.area;
					document.getElementById('subareaC').value = feature.layer.feature.properties.subarea;
					document.getElementById('dataC').value = feature.layer.feature.properties.data;
					document.getElementById('descricaoC').value = feature.layer.feature.properties.descricao;
					document.getElementById('resposta').value = feature.layer.feature.properties.resposta;
					if (!sidebarDDP.isVisible())
						sidebarDDP.show();
					if (divDetalhesDoProblema.style.display == "none")
						divDetalhesDoProblema.style.display = "block";
					if (document.getElementById('resposta').value == '') {
						document.getElementById('resposta').readOnly = false;
						PF('btnResponder').enable();
					} else {
						document.getElementById('resposta').readOnly = true;
						PF('btnResponder').disable();
					}
					mapa.flyTo(feature.latlng);
				});

// Função que é chamada na página depois de cada consulta realizada, recebe um
// json vindo do servidor que contém todos os parametros para a construção do
// geojson necessário para a plotagem no mapa
function parametrosProblemas(xhr, status, args) {
	var params = JSON.parse(args.parametros);
	removerConsultas();
	if (params.length > 0) {
		var geojson = {};
		geojson['type'] = 'FeatureCollection';
		geojson['features'] = [];
		for (var k = 0; k < params.length; k++) {
			var newFeature = {
				"type" : "Feature",
				"geometry" : {
					"type" : "Point",
					"coordinates" : [ parseFloat(params[k]),
							parseFloat(params[++k]) ]
				},
				"properties" : {
					"id" : params[++k],
					"area" : params[++k],
					"subarea" : params[++k],
					"data" : params[++k],
					"descricao" : params[++k],
					"resposta" : params[++k]
				}
			}
			geojson['features'].push(newFeature);
		}
		problemas.addData(geojson);
		marcadores.addLayer(problemas);
		mapa.addLayer(marcadores);
		if (tipoConsulta != 'perimetro')
			mapa.fitBounds(marcadores.getBounds());
		if (divFiltrosConsultas.style.display == "none")
			divFiltrosConsultas.style.display = "block";
		if (!sidebarFiltros.isVisible()
				&& (tipoConsulta == 'data' || tipoConsulta == 'area' || tipoConsulta == 'resposta'))
			sidebarFiltros.show();
	} else {
		if (tipoConsulta != 'perimetro') {
			alert("Não existem problemas a serem exibidos!");
			botoesEstadoInicial();
		}
	}
}

// Remove do mapa as camadas problemas == geojson e os marcadores ou grupo de
// marcador == efeito de mesclagem. Esta remoção é necessária toda vez que uma
// nova consulta é realizada.
function removerConsultas() {
	if (mapa.hasLayer(problemas) || mapa.hasLayer(marcadores)) {
		mapa.removeLayer(problemas);
		problemas.clearLayers();
		marcadores.clearLayers();
		mapa.removeLayer(marcadores);
	}
}

// Só fecha as popup's dos marcadores
function fecharPopup() {
	marcadores.closePopup();
}

// Variável areaSelect utilizada para instanciar o plugin AreaSelect (consulta
// por perímetro). Variável bounds utilizada para receber todos os pontos
// (latitude e longitude) do polígono (perímetro).
var areaSelect, bounds;

// Inicia o plugin AreaSelect; guarda no bounds; atualiza o form para enviar ao
// servidor; indica a quem verifica a variavel tipoConsulta; e define um
// listener de change para o AreaSelect que ao mudar atualiza os dados no form e
// na variavel bounds.
function iniciarAreaSelect() {
	areaSelect = L.areaSelect().addTo(mapa);
	bounds = areaSelect.getBoundsArea();
	consultarBoundsPerimetro();
	tipoConsulta = 'perimetro';
	areaSelect.on("change", function() {
		bounds = areaSelect.getBoundsArea();
		consultarBoundsPerimetro();
	});
};

// Desativa o listener do plugin AreaSelect e o retira do mapa, remove os
// problemas no mapa.
function encerrarAreaSelect() {
	areaSelect.off("change");
	removerConsultas();
	areaSelect.remove();
	areaSelect = null;
};

// Função que atualiza o form com os valores do polígono (perímetro) que se está
// consultando.
function consultarBoundsPerimetro() {
	if (document.getElementById('boundsPerimetro')) {
		document.getElementById('boundsPerimetro').value = bounds;
		consultarPorPerimetro();
	}
}

// Função que retorna os botões ao seu estado inicial
function botoesEstadoInicial() {
	if (buttonData._currentState.stateName == 'esconder-consulta-data')
		buttonData.state('mostrar-consultar-data');
	if (buttonArea._currentState.stateName == 'esconder-consulta-area')
		buttonArea.state('mostrar-consulta-area');
	if (buttonResposta._currentState.stateName == 'esconder-consulta-resposta')
		buttonResposta.state('mostrar-consulta-resposta');

	if (sidebarFiltros.isVisible())
		sidebarFiltros.hide();
	removerConsultas();
};

// Adicionando os botões para interação com o usuário
var buttonData = L.easyButton({
	states : [ {
		stateName : 'mostrar-consultar-data',
		icon : 'fa fa-calendar',
		title : 'Consultar problemas por data',
		onClick : function(control) {
			if (areaSelect != null) {
				encerrarAreaSelect();
				buttonPerimetro.state('consultar-perimetro');
			}
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			botoesEstadoInicial();
			tipoConsulta = 'data';
			control.state('esconder-consulta-data');
			PF('wConsultarData').show();
		}
	}, {
		stateName : 'esconder-consulta-data',
		icon : 'fa fa-undo',
		title : 'Fechar consulta por data',
		onClick : function(control) {
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			removerConsultas();
			control.state('mostrar-consultar-data');
		}
	} ]
}).addTo(mapa);

var buttonArea = L.easyButton({
	states : [ {
		stateName : 'mostrar-consulta-area',
		icon : 'fa fa-list',
		title : 'Consultar problemas por área e sub-área',
		onClick : function(control) {
			if (areaSelect != null) {
				encerrarAreaSelect();
				buttonPerimetro.state('consultar-perimetro');
			}
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			botoesEstadoInicial();
			tipoConsulta = 'area';
			control.state('esconder-consulta-area');
			PF('wConsultarArea').show();
		}
	}, {
		stateName : 'esconder-consulta-area',
		icon : 'fa fa-undo',
		title : 'Fechar consulta por área e sub-área',
		onClick : function(control) {
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			removerConsultas();
			control.state('mostrar-consulta-area');
		}
	} ]
}).addTo(mapa);

var buttonResposta = L.easyButton({
	states : [ {
		stateName : 'mostrar-consulta-resposta',
		icon : 'fa fa-map-marker',
		title : 'Consultar problemas por resposta',
		onClick : function(control) {
			if (areaSelect != null) {
				encerrarAreaSelect();
				buttonPerimetro.state('consultar-perimetro');
			}
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			botoesEstadoInicial();
			tipoConsulta = 'resposta';
			control.state('esconder-consulta-resposta');
			PF('wConsultarTipoResposta').show();
		}
	}, {
		stateName : 'esconder-consulta-resposta',
		icon : 'fa fa-undo',
		title : 'Fechar consulta por resposta',
		onClick : function(control) {
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			removerConsultas();
			control.state('mostrar-consulta-resposta');
		}
	} ]
}).addTo(mapa);

var buttonPerimetro = L.easyButton({
	states : [ {
		stateName : 'consultar-perimetro',
		icon : 'fa fa-square',
		title : 'Consultar problemas a partir de um perímetro',
		onClick : function(control) {
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			botoesEstadoInicial();
			iniciarAreaSelect();
			control.state('esconder-consulta');
		}
	}, {
		stateName : 'esconder-consulta',
		icon : 'fa fa-undo',
		title : 'Fechar Consulta por perímetro',
		onClick : function(control) {
			if (sidebarFiltros.isVisible())
				sidebarFiltros.hide();
			if (sidebarDDP.isVisible())
				sidebarDDP.hide();
			encerrarAreaSelect();
			control.state('consultar-perimetro');
		}
	} ]
}).addTo(mapa);

L.easyButton({
	states : [ {
		icon : 'fa fa-sign-out',
		title : 'Sair do sistema',
		onClick : function() {
			PF('sair').show();
		}
	} ]
}).addTo(mapa);