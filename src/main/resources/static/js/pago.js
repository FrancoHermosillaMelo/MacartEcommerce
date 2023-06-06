const {createApp} = Vue;

createApp({
	data() {
		return {
			// Inicializamos las variables
			nombre: '',
			colorCard: '',
			typeCard: '',
			cvv: '',
			number1: '',
			number2: '',
			number3: '',
			number4: '',
			emailClient: '',
			cliente: '',
			id: '', //id del pedido
			pedidoPagar: '', // Pedido que se va a pagar
			params: '', // Parametros del URLSearchParams
		};
	},
	created() {
		this.params = new URLSearchParams(location.search);
		this.id = this.params.get('id');
		this.cargarDatos();
		this.cargarCliente();
		this.cargarPedido();
	},
	methods: {
		createNumberCard() {
			this.cardNumber = this.number1 + '-' + this.number2 + '-' + this.number3 + '-' + this.number4;
			console.log(this.cardNumber);
		},
		cargarDatos() {
			axios
				.get('/api/clientes/pedidos')
				.then(respuesta => {
					this.pedidos = respuesta.data;
					this.verificado = response.data.verificado === true;
				})
				.catch(error => console.log(error));
		},
		cargarPedido() {
			axios.get(`/api/pedidos/${this.id}`).then(respuesta => {
				this.pedidoPagar = respuesta.data;
			});
		},
		cargarCliente() {
			axios
				.get('/api/clientes/actual')
				.then(respuesta => {
					this.cliente = respuesta.data;
					console.log(this.cliente);
				})
				.catch(error => {
					this.cliente = [];
				});
		},
		payCard() {
			Swal.fire({
				title: '¿Está seguro de pagar con esta tarjeta? ' + this.cardNumber,
				inputAttributes: {
					autocapitalize: 'off',
				},
				showCancelButton: true,
				confirmButtonText: 'Confirmar',
				preConfirm: () => {
					Swal.fire({
						title: 'Realizando pago, por favor espere',
						allowEscapeKey: false,
						allowOutsideClick: false,
						didOpen: () => {
							Swal.showLoading();
						},
					});
					return axios
						.post('/api/comprobantes/pdf', {
							pedidoId: this.id,
							type: this.typeCard,
							color: this.colorCard,
							number: this.cardNumber,
							cvv: this.cvv,
							email: this.cliente.correo,
							amount: this.pedidoPagar.montoTotal,
						})
						.then(response => {
							Swal.fire({
								icon: 'success',
								text: 'Pago realizado',
								showConfirmButton: false,
								timer: 3000,
							}).then(() => (window.location.href = '/html/perfilCliente.html'));
						})
						.catch(error => {
							Swal.fire({
								icon: 'error',
								text: error.response.data,
								confirmButtonColor: '#7c601893',
							});
							console.log(error);
						});
				},
				allowOutsideClick: () => !Swal.isLoading(),
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
}).mount('#app');

window.addEventListener('load', () => {
	const loader = document.querySelector('.loader');

	loader.classList.add('loader-hidden');

	loader.addEventListener('transitioned', () => {
		document.body.removeChild('loader');
	});
});
