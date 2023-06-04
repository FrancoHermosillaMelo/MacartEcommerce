const {createApp} = Vue;

createApp({
	data() {
		return {
			pedidos: [],
			pagado: [],
			noPagado: [],
			rol: '',
			clienteIngresado: '',
			/* Agregar dirección */
			clienteIngresadoId: '',
			direcciones: '',
			calle: '',
			numeroDomicilio: '',
			barrio: '',
			departamento: '',
			ciudad: '',
			codigoPostal: '',
			/* Editar */
			direccionIp: '',
			calleE: '',
			numeroDomicilioE: '',
			barrioE: '',
			departamentoE: '',
			ciudadE: '',
			codigoPostalE: '',
			telefonoE: '',
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
					this.telefonoE = this.clienteIngresado.telefono;
					this.clienteIngresadoId = this.clienteIngresado.id;
					this.pedidos = this.clienteIngresado.pedidos.sort((x, y) => y.id - x.id);
					this.pagado = this.pedidos.filter(pedido => pedido.pagado == true);
					this.noPagado = this.pedidos.filter(pedido => pedido.pagado == false);
					this.direcciones = this.clienteIngresado.direcciones.sort((x, y) => x.id - y.id);
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
		filtrarDireccion(id) {
			this.direccionIp = this.direcciones.filter(direccion => direccion.id == id)[0];
			this.calleE = this.direccionIp.calle;
			this.numeroDomicilioE = this.direccionIp.numeroDomicilio;
			this.barrioE = this.direccionIp.barrio;
			this.ciudadE = this.direccionIp.ciudad;
			this.departamentoE = this.direccionIp.departamento;
			this.codigoPostalE = this.direccionIp.codigoPostal;
		},
		agregarDireccion() {
			Swal.fire({
				title: '¿De verdad quieres agregar esta nueva dirección?',
				text: 'Podrás editarla o eliminarla cuando desees',
				icon: 'info',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Sí!',
				cancelButtonText: 'No!',
			})
				.then(result => {
					if (result.isConfirmed) {
						axios
							.post(
								'/api/direcciones',
								`clienteId=${this.clienteIngresadoId}&calle=${this.calle}&numeroDomicilio=${this.numeroDomicilio}&barrio=${this.barrio}&ciudad=${this.ciudad}&departamento=${this.departamento}&codigoPostal=${this.codigoPostal}`
							)
							.then(response => (window.location.href = '/html/perfilCliente.html'))
							.catch(error => {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: error.response.data,
								});
							});
					}
				})
				.catch(error => console.log(error));
		},
		editarDireccion() {
			Swal.fire({
				title: '¿De verdad quieres editar esta dirección?',
				text: 'Podrás editarla nuevamente o eliminarla cuando desees',
				icon: 'info',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Sí!',
				cancelButtonText: 'No!',
			})
				.then(result => {
					if (result.isConfirmed) {
						axios
							.put(
								'/api/direcciones',
								`id=${this.direccionIp.id}&calle=${this.calleE}&numeroDomicilio=${this.numeroDomicilioE}&barrio=${this.barrioE}&ciudad=${this.ciudadE}&departamento=${this.departamentoE}&codigoPostal=${this.codigoPostalE}`
							)
							.then(response => (window.location.href = '/html/perfilCliente.html'))
							.catch(error => {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: error.response.data,
								});
							});
					}
				})
				.catch(error => console.log(error));
		},
		borrarDireccion(id) {
			Swal.fire({
				title: '¿De verdad deseas borrar esta dirección?',
				text: 'Tendrás que volverla a agregar para usarla de nuevo',
				icon: 'info',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Sí!',
				cancelButtonText: 'No!',
			})
				.then(result => {
					if (result.isConfirmed) {
						axios
							.delete(`/api/direcciones/${id}`)
							.then(response => (window.location.href = '/html/perfilCliente.html'))
							.catch(error => {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: error.response.data,
								});
							});
					}
				})
				.catch(error => console.log(error));
		},
		editarNumero() {
			Swal.fire({
				title: '¿De verdad quieres editar tu número?',
				text: 'Podrás editarlo nuevamente cuando desees',
				icon: 'info',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Sí!',
				cancelButtonText: 'No!',
			})
				.then(result => {
					if (result.isConfirmed) {
						axios
							.patch('/api/numero', `telefono=${this.telefonoE}`)
							.then(response => (window.location.href = '/html/perfilCliente.html'))
							.catch(error => {
								Swal.fire({
									icon: 'error',
									title: 'Oops...',
									text: error.response.data,
								});
							});
					}
				})
				.catch(error => console.log(error));
		},
		mostrarInfo() {
			todoPerfil.classList.remove('show');
			todoPerfil.classList.remove('showP');
		},
		mostrarDirecciones() {
			todoPerfil.classList.add('show');
			todoPerfil.classList.remove('showP');
		},
		mostrarPedidos() {
			todoPerfil.classList.add('showP');
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
	computed: {},
}).mount('#app');
const todoPerfil = document.querySelector('.todoPerfil');
window.addEventListener('load', () => {
	const loader = document.querySelector('.loader');

	loader.classList.add('loader-hidden');

	loader.addEventListener('transitioned', () => {
		document.body.removeChild('loader');
	});
});
