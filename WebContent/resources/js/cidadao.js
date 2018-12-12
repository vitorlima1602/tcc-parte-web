//Marcador usado para registrar o problema
var marcador = L.marker([], {
	draggable : true
});

// Função que será acionada pelo click no mapa ou pelo arrastar final do
// marcador/problema, atualizando sempre os inputtexts Latitude e Longitude no
// mapaCidadao
var posicao, lat, lon;
var registrar = false;
function posicaoProblema(e) {
	if (registrar) {
		if (!mapa.hasLayer(marcador)) {
			posicao = e.latlng;
			marcador.setLatLng(e.latlng).addTo(mapa);
		} else {
			posicao = marcador.getLatLng();
		}
		posicao = posicao.toString().split(' ', 2);
		lat = posicao[0].replace(/[LatLng(]|[,]/g, "");
		lon = posicao[1].replace(/[)]/, "");
		if (document.getElementById('latitude')
				&& document.getElementById('longitude')) {
			document.getElementById('latitude').value = lat;
			document.getElementById('longitude').value = lon;
		}
		marcador
				.bindPopup(
						"Seu problema será registrado aqui! Se precisar corrigir, você pode me arrastar até o ponto correto!")
				.openPopup();
	}
};

mapa.on('click', posicaoProblema);

marcador.on('moveend', posicaoProblema);

// Função para remover o marcador registrar/problema do mapa, quando finalizar o
// registro ou for cancelado
function removerMarcadorProblema() {
	if (mapa.hasLayer(marcador)) {
		mapa.removeLayer(marcador);
		registrar = false;
	}
};

//
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
// corpo da página mapaCidadao
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
		'Consulte detalhes à sua direita!');

// Adicionando uma janela lateral para mostrar detalhes do problema, utilizando
// a divDetalhesDoProblema construída na página mapaCidadao
var sidebarDDP = L.control.sidebar(divDetalhesDoProblema, {
	position : 'right'
});
mapa.addControl(sidebarDDP);

// A cada click num marcador de problema no mapa, adiciono cada valor desse
// problema nos campos correspondentes na divDetalhesProblemas, testo se a
// sidebar e a div estão ou não visiveis, caso contrário aciono elas. Outro
// ponto importante é se há a possibilidade ou não de resposta por parte do
// administrador, habilito ou não a caixa de resposta e o botão correspondente
problemas
		.on(
				'click',
				function(feature) {
					document.getElementById('areaC').value = feature.layer.feature.properties.area;
					document.getElementById('subareaC').value = feature.layer.feature.properties.subarea;
					document.getElementById('dataC').value = feature.layer.feature.properties.data;
					document.getElementById('descricaoC').value = feature.layer.feature.properties.descricao;
					document.getElementById('resposta').value = feature.layer.feature.properties.resposta;
					if (document.getElementById('resposta').value == '')
						document.getElementById('resposta').value = 'Ninguém respondeu ao seu problema! Favor aguardar...';
					if (!sidebarDDP.isVisible())
						sidebarDDP.show();
					if (divDetalhesDoProblema.style.display == "none")
						divDetalhesDoProblema.style.display = "block";
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
		mapa.fitBounds(marcadores.getBounds());
	} else
		alert("Não existem problemas a serem exibidos!");
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

// Adiciono uma janela lateral à direita, como um container para os componentes
// do PrimeFaces - Tela de registro do problema
var sidebarRP = L.control.sidebar(divRegistrarProblema, {
	position : 'right'
});
mapa.addControl(sidebarRP);

// Adicionando os botões
L.easyButton({
	states : [ {
		icon : 'fa fa-bullhorn',
		title : 'Registrar',
		onClick : function() {
			if (divRegistrarProblema.style.display == "none") {
				atualizaFormRP();
				divRegistrarProblema.style.display = "block";
			}
			sidebarRP.toggle();
			registrar = true
		}
	} ]
}).addTo(mapa);

L.easyButton({
	states : [ {
		stateName : 'mostrar-problemas',
		icon : 'fa fa-map-marker',
		title : 'Ver problemas',
		onClick : function(control) {
			consultarProblemas();
			control.state('esconder-problemas');
		}
	}, {
		stateName : 'esconder-problemas',
		icon : 'fa fa-undo',
		title : 'Esconder problemas',
		onClick : function(control) {
			mapa.removeLayer(problemas);
			problemas.clearLayers();
			marcadores.clearLayers();
			mapa.removeLayer(marcadores);
			sidebarDDP.hide();
			control.state('mostrar-problemas');
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