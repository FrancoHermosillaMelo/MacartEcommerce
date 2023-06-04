//Obtén las referencias a las imágenes pequeña y grande
var imagenPequena = document.getElementById('imagenPequena');
var imagenGrande = document.getElementById('imagenGrande');

// Manejador de eventos para hacer clic en la imagen pequeña
imagenPequena.addEventListener('click', function () {
	// Obtén las URLs de las imágenes pequeña y grande
	var urlImagenPequena = imagenPequena.src;
	var urlImagenGrande = imagenGrande.src;

	// Intercambia las URLs de las imágenes
	imagenPequena.src = urlImagenGrande;
	imagenGrande.src = urlImagenPequena;
});
