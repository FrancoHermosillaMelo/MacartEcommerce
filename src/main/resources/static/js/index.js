const {createApp} = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
			productos: [],
			talleSeleccionado: {},
			isCarritoInactivo: true,
			carrito: [],
			correo: '',
			correoRegistro: '',
			contraseña: '',
			contraseñaRegistro: '',
			primerNombre: '',
			segundoNombre: '',
			primerApellido: '',
			segundoApellido: '',
			telefono: '',
			productoPorId: '',
			imgProductoPorId: '',
			pedidoId: '', // ID DEL PEDIDO UNA VEZ CREADO
			montoTotalPedido: 0, // MONTO TOTAL DEL PEDIDO
			token: '',
			verificado: false,
		};
	},
	created() {
		this.data();
		this.totalProductos();
		this.carrito = JSON.parse(localStorage.getItem('carrito')) || [];
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
					this.verificado = response.data.verificado === true;
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
				Swal.fire('Debes verificar tu cuenta para entrar al carrito de compra.');
			} else {
				this.isCarritoInactivo = !this.isCarritoInactivo;
			}
		},
		agregarAlCarrito(item) {
			if (this.rol === 'VISITANTE') {
				Swal.fire('Debes registrarte para poder agregar productos al carrito de compra. Dirígete al inicio para registrarte.');
			} else if (this.clienteIngresado.verificado === false) {
				Swal.fire('Debes verificar tu cuenta para añadir los productos al carrito de compra.');
			} else {
				if (this.verificado === true && (this.rol === 'CLIENTE' || this.rol === 'ADMIN')) {
					for (const key in this.talleSeleccionado) {
						if (!key.includes(item.id + key.slice(1)) && item.id.toString().length === 1) {
							delete this.talleSeleccionado[key];
						} else if (!key.includes(item.id + key.slice(1)) && item.id.toString().length === 2) {
							delete this.talleSeleccionado[key];
						}
					}
					let talles = Object.keys(this.talleSeleccionado);
					talles.map(talle => {
						if (item.id.toString().length === 1) {
							let nuevoTalle = talle.slice(1);
							delete this.talleSeleccionado[talle];
							this.talleSeleccionado[nuevoTalle] = 1;
						} else {
							let nuevoTalle = talle.slice(2);
							delete this.talleSeleccionado[talle];
							this.talleSeleccionado[nuevoTalle] = 1;
						}
					});
					if (!this.productosRepetidos(item.id)) {
						if (!Object.keys(this.talleSeleccionado).length == 0) {
							Toastify({
								text: `${item.nombre} se agrego al carrito`,
								className: 'info',
								duration: 3000,
								offset: {
									x: '0em', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
									y: '40em', // vertical axis - can be a number or a string indicating unity. eg: '2em'
								},
								style: {
									background: '#212529',
								},
							}).showToast();
							this.carrito.push({
								nombre: item.nombre,
								id: item.id,
								tallas: this.talleSeleccionado,
								stockTallas: item.tallas,
								imagen: item.imagenesUrl[0],
								precio: item.precio,
							});
						} else {
							Toastify({
								text: `Por favor, seleccione un talle`,
								className: 'info',
								duration: 3000,
								offset: {
									x: '0em', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
									y: '40em', // vertical axis - can be a number or a string indicating unity. eg: '2em'
								},
								style: {
									background: '#212529',
								},
							}).showToast();
						}
					} else {
						Toastify({
							text: `${item.nombre} ya está en el carrito`,
							className: 'info',
							duration: 3000,
							offset: {
								x: '0em', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
								y: '40em', // vertical axis - can be a number or a string indicating unity. eg: '2em'
							},
							style: {
								background: '#212529',
							},
						}).showToast();
					}
					this.talleSeleccionado = {};
				}
			}
		},
		productosRepetidos(productoId) {
			return this.carrito.some(item => item.id === productoId);
		},
		agregarCantidadProducto(producto, key) {
			if (producto.tallas[key] < producto.stockTallas[key]) {
				producto.tallas[key] += 1;
			}
		},
		disminuirCantidadProducto(producto, key) {
			if (producto.tallas[key] <= producto.stockTallas[key] && producto.tallas[key] > 1) {
				producto.tallas[key] -= 1;
			}
		},
		eliminarTalle(producto, key) {
			delete producto.tallas[key];
		},
		elimarDelCarrito(producto) {
			Toastify({
				text: `${producto.nombre} se elimino del carrito`,
				className: 'info',
				duration: 3000,
				offset: {
					x: '0em', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
					y: '40em', // vertical axis - can be a number or a string indicating unity. eg: '2em'
				},
				style: {
					background: '#212529',
				},
			}).showToast();
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
						text: 'El correo o la contraseña son incorrectas',
						confirmButtonColor: '#7c601893',
					})
				);
		},
		register() {
			Swal.fire({
				title: 'Validando datos',
				allowEscapeKey: false,
				allowOutsideClick: false,
				didOpen: () => {
					Swal.showLoading();
				},
			});
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
		confirmarPedido() {
			if (this.carrito.length == 0) {
				Toastify({
					text: `El carrito está vacio`,
					className: 'info',
					duration: 3000,
					offset: {
						x: '5em', // horizontal axis - can be a number or a string indicating unity. eg: '2em'
						y: '42em', // vertical axis - can be a number or a string indicating unity. eg: '2em'
					},
					style: {
						background: '#212529',
					},
				}).showToast();
			} else {
				Swal.fire({
					icon: 'info',
					title: '¿Deseas crear este pedido?',
					text: 'Si confirmas, se te redireccionara a tus pedidos para poder pagarlo desde ahi',
					cancelButtonText: 'Cancelar',
					showCancelButton: true,
					confirmButtonText: 'Confirmar',
					showLoaderOnConfirm: true,
					preConfirm: login => {
						return axios
							.post('/api/pedidos')
							.then(response => {
								this.pedidoId = response.data;
								this.carrito.map(producto => {
									axios.post('/api/pedidos/carrito', {
										idPedido: this.pedidoId,
										idProducto: producto.id,
										tallas: producto.tallas,
										montoTotal: this.montoTotalPedido,
									});
								});
								Swal.fire({
									icon: 'success',
									text: 'Pedido creado con exito',
									showConfirmButton: false,
									timer: 3000,
								}).then(() => {
									this.carrito = [];
									window.location.href = '/html/perfilCliente.html';
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
					allowOutsideClick: () => !Swal.isLoading(),
				});
			}
		},
		obtenerIdProducto(id) {
			axios
				.get('/api/productoTienda/' + id)
				.then(response => {
					this.productoPorId = response.data;
					this.imgProductoPorId = this.productoPorId.imagenesUrl;
				})
				.catch(error => console.log(error));
		},
		verificarCuenta() {
			axios
				.post('/api/clientes/autenticar', 'token=' + this.token)
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
							this.carrito = [];
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
		totalDelCarrito() {
			this.montoTotalPedido = this.carrito.reduce((acc, productoActual) => {
				let talles = Object.keys(productoActual.tallas);
				acc += talles.reduce((acc, talle) => {
					acc += productoActual.tallas[talle] * productoActual.precio;
					return acc;
				}, 0);
				return acc;
			}, 0);
			return this.montoTotalPedido.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},
		localStorageCarrito() {
			localStorage.setItem('carrito', JSON.stringify(this.carrito));
		},
	},
}).mount('#app');

window.addEventListener('load', () => {
	const loader = document.querySelector('.loader');

	loader.classList.add('loader-hidden');

	loader.addEventListener('transitioned', () => {
		document.body.removeChild('loader');
	});
});

/* Contraseña */
const pwShowHide = document.querySelectorAll('.pw-hide');
pwShowHide.forEach(icon => {
	icon.addEventListener('click', () => {
		let getPwInput = icon.parentElement.querySelector('input');
		if (getPwInput.type === 'password') {
			getPwInput.type = 'text';
			icon.classList.replace('fa-eye-slash', 'fa-eye');
		} else {
			getPwInput.type = 'password';
			icon.classList.replace('fa-eye', 'fa-eye-slash');
		}
	});
});
