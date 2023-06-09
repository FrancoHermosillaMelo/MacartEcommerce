const {createApp} = Vue;

createApp({
	data() {
		return {
			sexo: '',
			categoriaTipo: '',
			check: [],
			checkCategoria: [],
			checkCatalogo: new URLSearchParams(location.search).get('check'),
			productosFiltrados: {},
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
			checkTallas: [], // CHECKS DE LOS FILTROS DE TALLAS
			token: '',
			verificado: false,
			precioDesde: 0,
			precioHasta: 300000,
			pedidoId: '', // ID DEL PEDIDO UNA VEZ CREADO
			montoTotalPedido: 0, // MONTO TOTAL DEL PEDIDO
		};
	},
	created() {
		// this.roles();
		this.data();
		this.totalProductos();
		this.carrito = JSON.parse(localStorage.getItem('carrito')) || [];
	},
	mounted() {
		this.roles();
		this.check.push(this.checkCatalogo);
	},
	methods: {
		totalProductos() {
			axios
				.get('/api/productoTienda')
				.then(response => {
					this.productos = response.data;
					this.productosFiltrados = this.productos;
					this.sexo = Array.from(new Set(this.productos.map(sexo => sexo.categoriaGenero)));
					this.categoriaTipo = Array.from(new Set(this.productos.map(tipo => tipo.subCategoria)));
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
						} else if (!key.includes(item.id + key.slice(2)) && item.id.toString().length === 2) {
							delete this.talleSeleccionado[key];
						}
					}
					let talles = Object.keys(this.talleSeleccionado);
					talles.map(talle => {
						if (item.id.toString().length === 1) {
							let nuevoTalle = talle.slice(1);
							delete this.talleSeleccionado[talle];
							this.talleSeleccionado[nuevoTalle] = 1;
						} else if (item.id.toString().length === 2) {
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
			} else {
				Toastify({
					text: `Sin stock`,
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
					this.correo = this.correoRegistro;
					this.contraseña = this.contraseñaRegistro;
					this.ingresar();
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

		salir() {
			Swal.fire({
				title: '¿Estas seguro que quieres salir de tu cuenta?',
				inputAttributes: {
					autocapitalize: 'off',
				},
				showCancelButton: true,
				cancelButtonText: 'Cancelar',
				confirmButtonText: 'Salir',
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
		filtroCruzados() {
			let filtroProductoGenero = this.productos.filter(producto => {
				return this.check.includes(producto.categoriaGenero) || this.check == 0;
			});
			let filtroProductoSubCategoriaYGenero = filtroProductoGenero.filter(producto => {
				return this.checkCategoria.includes(producto.subCategoria) || this.checkCategoria == 0;
			});

			let filtroProductoTallas = filtroProductoSubCategoriaYGenero.filter(producto =>{
				let tallasProductos = Object.keys(producto.tallas)

				for(let talla of this.checkTallas){
					return tallasProductos.includes(talla)
				}
				return this.checkTallas == 0
			})

			let filtroPorPrecio = filtroProductoTallas.filter(producto => {
			 	if (parseInt(this.precioDesde) > 0 && parseInt(this.precioHasta) == 300000) {
			 		return true;
			 	}
			 	return producto.precio >= parseInt(this.precioDesde) && producto.precio <= parseInt(this.precioHasta);
			 });

			 this.productosFiltrados = filtroPorPrecio;
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
