const { createApp } = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
			productos: [],
			talleSeleccionado: {},
			isCarritoInactivo: true,
			carrito: [],
			carritos: {},
			correo: '',
			correoRegistro: '',
			contraseña: '',
			contraseñaRegistro: '',
			primerNombre: '',
			segundoNombre: '',
			primerApellido: '',
			segundoApellido: '',
			telefono: '',
			clienteId: '',
			productoPorId: "",
			imgProductoPorId: "",
			token: "",
			verificado: false,
		};
	},
	created() {
		this.data();
		this.totalProductos();
		this.clienteId = sessionStorage.getItem('clienteId'); // Obtén el identificador único del cliente desde el sessionStorage
		this.carritos = JSON.parse(localStorage.getItem('carritos')) || {}; // Obtiene los carritos almacenados en el localStorage
		if (!this.carritos[this.clienteId]) {
			this.carritos[this.clienteId] = []; // Crea un carrito vacío para el cliente si no existe
		}
		this.carrito = this.carritos[this.clienteId]; // Asi
	},
	mounted() {
		this.roles();
	},
	methods: {
		totalProductos() {
			axios
				.get('/api/productoTienda')
				.then(response => {
					this.productos = response.data;
				})
				.catch(error => console.log(error));
		},
		data() {
			axios
				.get('/api/clientes/actual')
				.then(response => {
					this.datos = response.data;
					this.clienteIngresado = response.data;
					this.clienteId = response.data.id;
					sessionStorage.setItem('clienteId', this.clienteId); // Almacena el identificador único del cliente en el sessionStorage
					if (!this.carritos[this.clienteId]) {
						this.carritos[this.clienteId] = []; // Crea un carrito vacío para el cliente si no existe
					}
					this.carrito = this.carritos[this.clienteId]; // Asigna el carrito correspondiente al cliente actual
				})
				.catch(error => console.log(error));
		},
		roles() {
			axios
				.get('/api/clientes/actual/rol')
				.then(response => {
					this.rol = response.data;
				})
				.catch(error => {
					console.log(error);
				});
		},
		abrirCarrito() {
			if (this.clienteIngresado.verificado == false) {
				Swal.fire('Debes verificar tu cuenta para entrar al carrito de compra.')
			} else {
				this.isCarritoInactivo = !this.isCarritoInactivo;
			}
		},
		agregarAlCarrito(item) {
			if (this.clienteIngresado.verificado === false) {
				Swal.fire('Debes verificar tu cuenta para añadir los productos al carrito de compra');
			} else {
				if (this.verificado) {
					for (const key in this.talleSeleccionado) {
						if (!key.includes(item.id + key.slice(1))) {
							delete this.talleSeleccionado[key];
						}
					}
					let talles = Object.keys(this.talleSeleccionado);
					talles.map(talle => {
						let nuevoTalle = talle.slice(1);
						delete this.talleSeleccionado[talle];
						this.talleSeleccionado[nuevoTalle] = 1;
					});

					if (!this.productosRepetidos(item.id)) {
						this.carrito.push({
							nombre: item.nombre,
							id: item.id,
							tallas: this.talleSeleccionado,
							stockTallas: item.tallas,
							imagen: item.imagenesUrl[0],
							precio: item.precio,
						});
					}
					this.talleSeleccionado = {};
				}
			}
		},
		productosRepetidos(productoId) {
			return this.carrito.some(item => item.id === productoId);
		},
		agregarCantidadProducto(producto) {
			if (producto.tallas[key] < producto.stockTallas[key]) {
				producto.tallas[key] += 1;
			}
		},
		disminuirCantidadProducto(producto) {
			if (producto.tallas[key] <= producto.stockTallas[key] && producto.tallas[key] > 1) {
				producto.tallas[key] -= 1;
			}
		},
		eliminarTalle(producto, key) {
			delete producto.tallas[key];
		},
		elimarDelCarrito(producto) {
			this.carrito = this.carrito.filter(item => !(item.id === producto.id));
		},
		ingresar() {
			axios
				.post('/api/login', 'correo=' + this.correo + '&contraseña=' + this.contraseña)
				.then(response => {
					Swal.fire({
						icon: 'success',
						text: 'Ingreso Exitoso',
						showConfirmButton: false,
						timer: 2000,
					}).then(() => {
						if (this.correo == 'admin@gmail.com') {
							window.location.replace('/index.html');
						} else {
							window.location.replace('/index.html');
						}
					});
				})
				.catch(error =>
					Swal.fire({
						icon: 'error',
						text: error.response.data,
						confirmButtonColor: '#7c601893',
					})
				);
		},
		register() {
			axios
				.post(
					'/api/clientes',
					'primerNombre=' +
					this.primerNombre +
					'&segundoNombre=' +
					this.segundoNombre +
					'&primerApellido=' +
					this.primerApellido +
					'&segundoApellido=' +
					this.segundoApellido +
					'&telefono=' +
					this.telefono +
					'&correo=' +
					this.correoRegistro +
					'&contraseña=' +
					this.contraseñaRegistro
				)
				.then(response => {
					Swal.fire({
						icon: 'success',
						text: 'Se envio a tu correo la validacion',
						showConfirmButton: false,
						timer: 2000,
					}).then(() => {
						this.correo = this.correoRegistro;
						this.contraseña = this.contraseñaRegistro;
						window.location.replace('/index.html');
						// this.ingresar();
					});
				})
				.catch(error =>
					Swal.fire({
						icon: 'error',
						text: error.response.data,
						confirmButtonColor: '#7c601893',
					})
				);
		},
		obtenerIdProducto(id) {
			axios.get('/api/productoTienda/' + id)
				.then(response => {
					this.productoPorId = response.data
					this.imgProductoPorId = this.productoPorId.imagenesUrl
				})
				.catch(error => console.log(error))
		},
		verificarCuenta() {
			axios.post('/api/clientes/autenticar', 'token=' + this.token)
				.then(response => {
					this.verificado = true;
					Swal.fire({
						icon: 'success',
						text: 'La cuenta se ha verificado exitosamente',
						confirmButtonColor: '#7c601893',
					}).then(() => {
						location.reload();
					});
				})
				.catch(error => {
					Swal.fire({
						icon: 'error',
						text: 'Error al verificar la cuenta: ' + error.response.data,
						confirmButtonColor: '#7c601893',
					});
				});
		},
		salir() {
			Swal.fire({
				title: '¿Estas seguro que quieres salir de tu cuenta?',
				inputAttributes: {
					autocapitalize: 'off',
				},
				showCancelButton: true,
				confirmButtonText: 'Sure',
				showLoaderOnConfirm: true,
				preConfirm: login => {
					return axios
						.post('/api/logout')
						.then(response => {
							window.location.href = '/index.html';
						})
						.catch(error =>
							Swal.fire({
								icon: 'error',
								text: error.response.data,
								confirmButtonColor: '#7c601893',
							})
						);
				},
				allowOutsideClick: () => !Swal.isLoading(),
			});
		},
	},
	computed: {
		primeraMayuscula() {
			this.primerNombre = this.primerNombre.charAt(0).toUpperCase() + this.primerNombre.slice(1);
			this.segundoNombre = this.segundoNombre.charAt(0).toUpperCase() + this.segundoNombre.slice(1);
			this.primerApellido = this.primerApellido.charAt(0).toUpperCase() + this.primerApellido.slice(1);
			this.segundoApellido = this.segundoApellido.charAt(0).toUpperCase() + this.segundoApellido.slice(1);
		},
		guardarDatos() {
			this.carritos[this.clienteId] = this.carrito;
			localStorage.setItem('carritos', JSON.stringify(this.carritos));
		},
		totalDelCarrito() {
			let talles = Object.keys(this.talleSeleccionado);
			return this.carrito.reduce((acc, productoActual) => {
				acc += talles.reduce((acc, talle) => {
					acc += productoActual.tallas[talle];
					console.log(acc);
					console.log(productoActual.tallas[talle]);
					return acc;
				}, 0);
				return acc;
			}, 0);
		},
	},
}).mount('#app');


// Obtén las referencias a las imágenes pequeña y grande
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
