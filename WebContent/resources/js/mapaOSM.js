var mapa = L.map('mapa').setView([ -3.130409, -60.023426 ], 12);
L
		.tileLayer(
				'http://a.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
				{
					attribution : 'Mapas &copy; OpenCycleMap, Dados do Mapa &copy;contribuidores do OpenStreetMap'
				}).addTo(mapa);

//Habilita a geolocalizacao do usuario (necessário o usuário confirmar no
//navegador)
mapa.locate({
	setView : true
});

//O mapa irá iniciar na localização do usuário e no zoom de nível 15 (se
//habilitada pelo mesmo, caso contrário, iniciará na posição definida na
//criação da variável mapa)
function localizacaoUsuario(e) {
	mapa.flyTo(e.latlng, 15);
}

//Ao iniciar o carregamento do mapa, dispara a localização do usuário
mapa.on('locationfound', localizacaoUsuario);

L.control.scale().addTo(mapa);

L.control.mousePosition().addTo(mapa);