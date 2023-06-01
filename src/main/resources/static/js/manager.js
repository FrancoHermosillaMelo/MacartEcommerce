const {createApp} = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
			productos: [],
			nombre: '',
			precio: '',
			descripcion: '',
			talleSuperior: '',
			talleInferior: '',
			imagen: '',
			genero: '',
			categoriaSub: '',
			productos: '',
		};
	},
	created() {
		this.data();
		this.obtenerProductos();
		this.clienteId = sessionStorage.getItem('clienteId'); // Obtén el identificador único del cliente desde el sessionStorage
		this.carritos = JSON.parse(localStorage.getItem('carritos')) || {}; // Obtiene los carritos almacenados en el localStorage
		if (!this.carritos[this.clienteId]) {
			this.carritos[this.clienteId] = []; // Crea un carrito vacío para el cliente si no existe
		}
		this.carrito = this.carritos[this.clienteId]; // Asi
	},
	mounted() {
		this.roles();
		console.log(this.$refs)
	},
	methods: {
		totalProductos() {
			axios.get('/api/productoTienda').then(response => {
				this.productos = response.data;
			});
		},
		data() {
			axios
				.get('/api/clientes/actual')
				.then(response => {
					this.datos = response.data;
					this.clienteIngresado = response.data;
					console.log(this.clienteIngresado);
					this.clienteId = response.data.id;
					sessionStorage.setItem('clienteId', this.clienteId); // Almacena el identificador único del cliente en el sessionStorage
					if (!this.carritos[this.clienteId]) {
						this.carritos[this.clienteId] = []; // Crea un carrito vacío para el cliente si no existe
					}
					this.carrito = this.carritos[this.clienteId]; // Asigna el carrito correspondiente al cliente actual
				})
				.catch(error => console.log(error));
		},
		obtenerProductos() {
			axios
				.get('/api/productoTienda')
				.then(response => {
					this.productos = response.data;
					console.log(this.productos);
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

		añadirProducto() {
			imagen = this.$refs.image.files
			console.log(imagen)
			 axios
			 	.post(
			 		'/api/productoTienda',
			 		{
			 			nombre: this.nombre,
			 			precio: this.precio,
			 			descripcion: this.descripcion,
			 			tallaSuperior: this.talleSuperior,
			 			tallaInferior: this.talleInferior,
			 			imagenesUrl: imagen,
			 			categoriaGenero: this.genero,
			 			subCategoria: this.categoriaSub,
						stock: 20,
			 		},
			 		{
			 			headers: {
			 				'Content-Type': 'multipart/form-data',
			 			},
			 		}
			 	)
			 	.then(response => {
			 		Swal.fire({
			 			icon: 'success',
			 			text: 'Añadiste el producto con exito',
			 			showConfirmButton: false,
			 			timer: 2000,
			 		});
			 		console.log(response);
			 	});
		// 	 axios
		// 	 	.post(
		// 	 		'/api/productoTienda',
		// 	 		'nombre=' +
		// 	 			this.nombre +
		// 	 			'&precio=' +
		// 	 			this.precio +
		// 	 			'&descripcion=' +
		// 	 			this.descripcion +
		// 	 			'&tallaSuperior=' +
		// 	 			this.talleSuperior +
		// 	 			'&tallaInferior=' +
		// 	 			this.talleInferior +
		// 	 			'&imagenesUrl=' +
		// 	 			// this.$refs.input-file.files +
		// 	 			'&categoriaGenero=' +
		// 	 			this.genero +
		// 	 			'&subCategoria=' +
		// 	 			this.categoriaSub
		// 	 	)
		// 	 	.then(response => {
		// 	 		Swal.fire({
		// 	 			icon: 'success',
		// 	 			text: 'Añadiste el producto con exito',
		// 	 			showConfirmButton: false,
		// 	 			timer: 2000,
		// 	 		});
		// 	 		console.log(response);
		// 	 	})
		// 	 	.catch(error => console.log(error));
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
}).mount('#app');
