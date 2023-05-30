const {createApp} = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
			productos: '',
			isCarritoInactivo: true,
			correo: '',
			correoRegistro: '',
			contraseña: '',
			contraseñaRegistro: '',
			primerNombre: '',
			segundoNombre: '',
			primerApellido: '',
			segundoApellido: '',
			telefono: '',
		};
	},
	created() {
		this.roles();
		this.data();
		this.totalProductos();
	},
	methods: {
		totalProductos() {
			axios.get('/api/productoTienda').then(response => {
				this.productos = response.data;
				console.log(this.productos);
			});
		},
		data() {
			axios
				.get('/api/clientes/actual')
				.then(response => {
					this.clienteIngresado = response.data;
				})
				.catch(error => console.log(error));
		},
		roles() {
			axios
				.get('/api/clientes/actual/rol')
				.then(response => {
					this.rol = response.data.authority;
				})
				.catch(error => console.log(error));
		},
		abrirCarrito() {
			this.isCarritoInactivo = !this.isCarritoInactivo;
		},
		ingresar() {
			axios
				.post('/api/login', 'correo=' + this.correo + '&contraseña=' + this.contraseña)
				.then(response => {
					if (this.correo == 'admin@gmail.com') {
						window.location.replace('/index.html');
					} else {
						window.location.replace('/index.html');
					}
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
	},
}).mount('#app');