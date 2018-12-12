//variavel de atribuição nas camadas
var attr = 'Projeto Final - TCC - Lucas, Rafael e Vitor';

// carrega a camada continentes
var continentesOceanos = L.tileLayer.wms(
		'http://10.0.17.90:8080/geoserver/rioosm/wms', {
			id : 'continentesOceanos',
			layers : 'rioosm:continentesOceanos',
			format : 'image/png',
			transparent : true,
			version : '1.1.0',
			attribution : attr,
			crs : L.CRS.EPSG4326
		});

// variavel que carrega todas as camadas
var mapa = L.map('mapa', {
	center : [ -22.30, -42.00 ], // posição que o mapa inicializa
	zoomDelta : 0.5, // define a escala de zoom dos botões zoom in e zoom out
	zoomSnap : 0.5, // define o valor fracionado de zoom do mapa
	wheelPxPerZoomLevel : 90, // define a velocidade de zoom pelo mouse
	minZoom : 1, // o mínimo de zoom, ou seja, o quão afastado pode ser do
	// mapa
	maxZoom : 17, // o máximo de zoom, ou seja, o quão próximo pode ser do
	// mapa
	zoom : 8, // nível inicial de zoom no mapa
	layers : [ continentesOceanos ]
// camadas que serão carregadas como default
});

// Habilita a geolocalizacao do usuario (necessário o usuário confirmar no
// navegador)
mapa.locate({
	setView : true
});

// O mapa irá iniciar na localização do usuário e no zoom de nível 15 (se
// habilitada pelo mesmo, caso contrário, iniciará na posição definida na
// criação da variável mapa)
function localizacaoUsuario(e) {
	mapa.flyTo(e.latlng, 15);
}

// Ao iniciar o carregamento do mapa, dispara a localização do usuário
mapa.on('locationfound', localizacaoUsuario);

// controle da escala no mapa
L.control.scale().addTo(mapa);

// coordenadas no mapa utilizando o ponteiro do mouse
L.control.mousePosition().addTo(mapa);

var florestas, fronteiras, massa_agua, cidades, ilhas, vilas, estradas_ferro, acessos_autoestradas, autoestradas, estradas_primarias, estradas_secundarias, estradas_terciarias, pistas_aeroportos, estradas_ligacao_primarias, estradas_ligacao_secundarias, estradas_ligacao_terciarias, estradas_tronco, estradas_ligacao_tronco, estradas_servicos, estradas_nao_classificadas, estradas_planejadas, estradas_agricolas_florestais, outras_estradas, ruas_para_pedestres, ruas_residenciais, ruas_residenciais_pref_pedestres, estradas_contrucao, estradas_nao_definidas;

// função para adicionar as camadas ao mapa
function addCamadasMapa(camadas) {
	for (i = 0; i < camadas.length; i++) {
		mapa.addLayer(camadas[i]);
	}
};

// função que será executada ouvindo o zoom no mapa e que irá realizar as
// chamadas das camadas conforme o zoom e adiciona-las
mapa
		.on(
				"zoom",
				function() {
					if (mapa.getZoom() >= 8.5) {

						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(florestas)
								&& !mapa.hasLayer(fronteiras)
								&& !mapa.hasLayer(massa_agua)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada florestas
							florestas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'florestas',
												layers : 'rioosm:florestas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada fronteiras
							fronteiras = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'fronteiras',
												layers : 'rioosm:fronteiras',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada massa_agua
							massa_agua = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'massa_agua',
												layers : 'rioosm:massa_agua',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ florestas, fronteiras, massa_agua ]);

						}
					}
					if (mapa.getZoom() >= 9.5) {
						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(cidades) && !mapa.hasLayer(ilhas)
								&& !mapa.hasLayer(vilas)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada cidades
							cidades = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'cidades',
												layers : 'rioosm:cidades',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada ilhas
							ilhas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'ilhas',
												layers : 'rioosm:ilhas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada vilas
							vilas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'vilas',
												layers : 'rioosm:vilas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ cidades, ilhas, vilas ]);
						}
					}
					if (mapa.getZoom() >= 10.5) {
						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(estradas_ferro)
								&& !mapa.hasLayer(acessos_autoestradas)
								&& !mapa.hasLayer(autoestradas)
								&& !mapa.hasLayer(estradas_primarias)
								&& !mapa.hasLayer(estradas_secundarias)
								&& !mapa.hasLayer(estradas_terciarias)
								&& !mapa.hasLayer(pistas_aeroportos)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada estradas_ferro
							estradas_ferro = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_ferro',
												layers : 'rioosm:estradas_ferro',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada acessos_autoestradas
							acessos_autoestradas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'acessos_autoestradas',
												layers : 'rioosm:acessos_autoestradas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada autoestradas
							autoestradas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'autoestradas',
												layers : 'rioosm:autoestradas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_primarias
							estradas_primarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_primarias',
												layers : 'rioosm:estradas_primarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_secundarias
							estradas_secundarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_secundarias',
												layers : 'rioosm:estradas_secundarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_terciarias
							estradas_terciarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_terciarias',
												layers : 'rioosm:estradas_terciarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada pistas_aeroportos
							pistas_aeroportos = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'pistas_aeroportos',
												layers : 'rioosm:pistas_aeroportos',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ estradas_ferro,
									acessos_autoestradas, autoestradas,
									estradas_primarias, estradas_secundarias,
									estradas_terciarias, pistas_aeroportos ]);

						}
					}
					if (mapa.getZoom() >= 11.5) {
						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(estradas_ligacao_primarias)
								&& !mapa.hasLayer(estradas_ligacao_secundarias)
								&& !mapa.hasLayer(estradas_ligacao_terciarias)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada estradas_ligacao_primarias
							estradas_ligacao_primarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_ligacao_primarias',
												layers : 'rioosm:estradas_ligacao_primarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_ligacao_secundarias
							estradas_ligacao_secundarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_ligacao_secundarias',
												layers : 'rioosm:estradas_ligacao_secundarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_ligacao_terciarias
							estradas_ligacao_terciarias = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_ligacao_terciarias',
												layers : 'rioosm:estradas_ligacao_terciarias',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ estradas_ligacao_primarias,
									estradas_ligacao_secundarias,
									estradas_ligacao_terciarias ]);

						}
					}
					if (mapa.getZoom() >= 12.5) {
						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(estradas_tronco)
								&& !mapa.hasLayer(estradas_ligacao_tronco)
								&& !mapa.hasLayer(estradas_servicos)
								&& !mapa.hasLayer(estradas_nao_classificadas)
								&& !mapa.hasLayer(estradas_planejadas)
								&& !mapa
										.hasLayer(estradas_agricolas_florestais)
								&& !mapa.hasLayer(outras_estradas)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada estradas_tronco
							estradas_tronco = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_tronco',
												layers : 'rioosm:estradas_tronco',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_ligacao_tronco
							estradas_ligacao_tronco = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_ligacao_tronco',
												layers : 'rioosm:estradas_ligacao_tronco',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_servicos
							estradas_servicos = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_servicos',
												layers : 'rioosm:estradas_servicos',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_nao_classificadas
							estradas_nao_classificadas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_nao_classificadas',
												layers : 'rioosm:estradas_nao_classificadas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_planejadas
							estradas_planejadas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_planejadas',
												layers : 'rioosm:estradas_planejadas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_agricolas_florestais
							estradas_agricolas_florestais = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_agricolas_florestais',
												layers : 'rioosm:estradas_agricolas_florestais',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada outras_estradas
							outras_estradas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'outras_estradas',
												layers : 'rioosm:outras_estradas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ estradas_tronco,
									estradas_ligacao_tronco, estradas_servicos,
									estradas_nao_classificadas,
									estradas_planejadas,
									estradas_agricolas_florestais,
									outras_estradas ]);

						}
					}
					if (mapa.getZoom() >= 14.5) {
						// primeira chamada, quando as camadas ainda não estão
						// no mapa
						if (!mapa.hasLayer(ruas_para_pedestres)
								&& !mapa.hasLayer(ruas_residenciais)
								&& !mapa
										.hasLayer(ruas_residenciais_pref_pedestres)
								&& !mapa.hasLayer(estradas_contrucao)
								&& !mapa.hasLayer(estradas_nao_definidas)) {

							// chamadas as camadas do geoserver a serem
							// armazenadas nas
							// variáveis correspondentes
							// carrega a camada ruas_para_pedestres
							ruas_para_pedestres = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'ruas_para_pedestres',
												layers : 'rioosm:ruas_para_pedestres',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada ruas_residenciais
							ruas_residenciais = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'ruas_residenciais',
												layers : 'rioosm:ruas_residenciais',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada ruas_residenciais_pref_pedestres
							ruas_residenciais_pref_pedestres = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'ruas_residenciais_pref_pedestres',
												layers : 'rioosm:ruas_residenciais_pref_pedestres',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_contrucao
							estradas_contrucao = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_contrucao',
												layers : 'rioosm:estradas_contrucao',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// carrega a camada estradas_nao_definidas
							estradas_nao_definidas = L.tileLayer
									.wms(
											'http://10.0.17.90:8080/geoserver/rioosm/wms',
											{
												id : 'estradas_nao_definidas',
												layers : 'rioosm:estradas_nao_definidas',
												format : 'image/png',
												transparent : true,
												version : '1.1.0',
												attribution : attr,
												crs : L.CRS.EPSG4326
											});

							// adicionando as camadas ao mapa
							addCamadasMapa([ ruas_para_pedestres,
									ruas_residenciais,
									ruas_residenciais_pref_pedestres,
									estradas_contrucao, estradas_nao_definidas ]);

						}
					}

					// coloca estas camadas na frente de todas as outras
					if (mapa.hasLayer(acessos_autoestradas))
						acessos_autoestradas.bringToFront();
					if (mapa.hasLayer(autoestradas))
						autoestradas.bringToFront();
					if (mapa.hasLayer(cidades))
						cidades.bringToFront();
					if (mapa.hasLayer(ilhas))
						ilhas.bringToFront();
					if (mapa.hasLayer(vilas))
						vilas.bringToFront();
					if (mapa.hasLayer(fronteiras))
						fronteiras.bringToFront();

				});
