const {createApp} = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
      /* Agregar dirección */
      clienteIngresadoId:undefined,
      direcciones:'',
      calle:'',
      numeroDomicilio:'',
      barrio:'',
      departamento:'',
      ciudad:'',
      codigoPostal: '',
      telefono: '',
      /* Editar */
      direccionIp: '',
      calleE:'',
      numeroDomicilioE:'',
      barrioE:'',
      departamentoE:'',
      ciudadE:'',
      codigoPostalE: '',
      calle2:'',
      numeroDomicilio2:'',
      barrio2:'',
      departamento2:'',
      ciudad2:'',
      codigoPostal2: '',
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
          this.clienteIngresadoId = this.clienteIngresado.id
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
    filtrarDireccion(id){
      this.direccionIp = this.direcciones.filter(direccion => direccion.id == id)[0];
      this.calleE = this.direccionIp.calle;
      this.numeroDomicilioE = this.direccionIp.numeroDomicilio;
      this.barrioE = this.direccionIp.barrio;
      this.ciudadE = this.direccionIp.ciudad;
      this.departamentoE = this.direccionIp.departamento;
      this.codigoPostalE = this.direccionIp.codigoPostal;
    },
        agregarDireccion(){
            Swal.fire({
                title: "De verdad quieres agregar esta nueva dirección?",
                text: "Podrás editarla o eliminarla cuando desees",
                icon: "info",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Sí!",
                cancelButtonText: "No!",
              })
                .then((result) => {
                  if (result.isConfirmed) {
                    axios
                      .post('/api/direcciones',`clienteId=${this.clienteIngresadoId}&calle=${this.calle}&numeroDomicilio=${this.numeroDomicilio}&barrio=${this.barrio}&ciudad=${this.ciudad}&departamento=${this.departamento}&codigoPostal=${this.codigoPostal}`)
                      .then((response) => (window.location.href = "/html/perfilCliente.html"))
                      .catch((error) => {
                        Swal.fire({
                          icon: "error",
                          title: "Oops...",
                          text: error.response.data,
                        });
                      });
                  }
                })
                .catch((error) => console.log(error));
        },
        editarDireccion(){
          Swal.fire({
            title: "De verdad quieres editar esta dirección?",
            text: "Podrás editarla nuevamente o eliminarla cuando desees",
            icon: "info",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí!",
            cancelButtonText: "No!",
          })
            .then((result) => {
              if (result.isConfirmed) {
                axios
                  .put('/api/direcciones',`id=${this.direccionIp.id}&calle=${this.calle2}&numeroDomicilio=${this.numeroDomicilio2}&barrio=${this.barrio2}&ciudad=${this.ciudad2}&departamento=${this.departamento2}&codigoPostal=${this.codigoPostal2}`)
                  .then((response) => (window.location.href = "/html/perfilCliente.html"))
                  .catch((error) => {
                    Swal.fire({
                      icon: "error",
                      title: "Oops...",
                      text: error.response.data,
                    });
                  });
              }
            })
            .catch((error) => console.log(error));
        },
        borrarDireccion(id){
            Swal.fire({
                title: "De verdad deseas borrar esta dirección?",
                text: "Tendrás que volverla a agregar para usarla de nuevo",
                icon: "info",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Sí!",
                cancelButtonText: "No!"
              })
                .then((result) => {
                  if (result.isConfirmed) {
                    axios
                      .delete(`/api/direcciones/${id}`)
                      .then((response) => (window.location.href = "/html/perfilCliente.html"))
                      .catch((error) => {
                        Swal.fire({
                          icon: "error",
                          title: "Oops...",
                          text: error.response.data,
                        });
                      });
                  }
                })
                .catch((error) => console.log(error));
        },
        editarNumero(){
          Swal.fire({
            title: "De verdad quieres edite número?",
            text: "Podrás editarlo nuevamente cuando desees",
            icon: "info",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí!",
            cancelButtonText: "No!",
          })
            .then((result) => {
              if (result.isConfirmed) {
                axios
                  .patch('/api/numero',`telefono=${this.telefono}`)
                  .then((response) => (window.location.href = "/html/perfilCliente.html"))
                  .catch((error) => {
                    Swal.fire({
                      icon: "error",
                      title: "Oops...",
                      text: error.response.data,
                    });
                  });
              }
            })
            .catch((error) => console.log(error));
        },
        mostrarInfo(){
            opcPerfilC.classList.remove('show');
            opcPerfilC.classList.remove('showP');
        },
        mostrarDirecciones(){
            opcPerfilC.classList.add('show');
            opcPerfilC.classList.remove('showP');
        },
        mostrarPedidos(){
            opcPerfilC.classList.add('showP');
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
const opcPerfilC = document.querySelector('.opcPerfilC');