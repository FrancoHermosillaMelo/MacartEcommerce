const {createApp} = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
		};
	},
	created() {
		this.roles();
		this.data();
	},
	methods: {
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
	},
}).mount('#app');
