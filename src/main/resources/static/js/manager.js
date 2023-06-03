const { createApp } = Vue;

createApp({
	data() {
		return {
			rol: '',
			clienteIngresado: '',
			productos: [],
			productosFiltro: '',
			busqueda: '',
			productosActivos: '',
			checkTallaCrear: [],
			checkTallaModificar: [],
			stockXSCrear : "",
			stockSCrear :"",
			stockMCrear : "",
			stockLCrear :"",
			stockXLCrear : "",
			productoCrear: {
				nombre: "",
				precio: "",
				descripcion: "",
				tallas: {},
				imagenesUrl: [],
				categoriaGenero: "",
				subCategoria: "",
			},
			stockXSModificar : "",
			stockSModificar : "",
			stockMModificar : "",
			stockLModificar : "",
			stockXLModificar : "",
			productoModificar: {
				id: "",
				nombre: "",
				precio: "",
				descripcion: "",
				tallas: {},
				imagenesUrl: [],
				categoriaGenero: "",
				subCategoria: "",
			}

		};
	},
	created() {
		this.data();
		this.obtenerProductos();
		// const widget = window.cloudinary.createUploadWidget(
		// 	{ cloud_name: "dtis6pqyq", upload_preset: "upload-test" }, (error, response) => {
		// 		console.log(error)
		// 		console.log(response)
		// 	})
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
	computed: {
		añadirObject() {
			this.checkTallaCrear.map(talla => {
				if (talla === 'XS') {
					this.productoCrear.tallas[talla] = this.stockXSCrear
				}
				if(talla === 'S'){
					this.productoCrear.tallas[talla] = this.stockSCrear
				}
				if(talla === 'M'){
					this.productoCrear.tallas[talla] = this.stockMCrear
				}
				if(talla === 'L'){
					this.productoCrear.tallas[talla] = this.stockLCrear
				}talla === 'XL'
				if(talla === 'XL'){
					this.productoCrear.tallas[talla] = this.stockXLCrear
				}
			})

			this.checkTallaModificar.map(talla => {
				if (talla === 'XS') {
					this.productoModificar.tallas[talla] = this.stockXSModificar
				}
				if(talla === 'S'){
					this.productoModificar.tallas[talla] = this.stockSModificar
				}
				if(talla === 'M'){
					this.productoModificar.tallas[talla] = this.stockMModificar
				}
				if(talla === 'L'){
					this.productoModificar.tallas[talla] = this.stockLModificar
				}
				if(talla === 'XL'){
					this.productoModificar.tallas[talla] = this.stockXLModificar
				}
			})
		},
		updateObject() {
			for (const key in this.productoCrear.tallas) {
				if (!this.checkTallaCrear.includes(key)) {
					delete this.productoCrear.tallas[key]
				}
			}
			for (const key in this.productoCrear.tallas) {
				if (!this.checkTallaModificar.includes(key)) {
					delete this.productoModificar.tallas[key]
				}
			}
		}
	},
	methods: {
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
					this.productosFiltro = this.productos;
					console.log(this.productos);
				})
				.catch(error => console.log(error));
		},
		productosFiltrados() {
			this.productosFiltro = this.productos.filter(producto => {
				return producto.nombre.toLowerCase().includes(this.busqueda.toLowerCase());
			});
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
			axios
				.post(
					'/api/productoTienda',
					this.productoCrear,
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
		},
		abrirWidget() {
			const widget = window.cloudinary.createUploadWidget(
				{ cloud_name: "dtis6pqyq", upload_preset: "upload-test" }, (error, response) => {
					if (!error && response && response.event === 'success') {
						console.log("Subida correctamente", response.info)
						this.productoCrear.imagenesUrl.push(response.info.url)
						console.log(this.productoCrear.imagenesUrl)
					}
				})
			widget.open()
		},
		eliminarImagenSubida(imagenUrl){
			console.log(imagenUrl)
			this.productoCrear.imagenesUrl = this.productoCrear.imagenesUrl.filter(imagen =>{
				!imagen.includes('imagenUrl') 
			})
		},
		desactivarProducto(id) {
			Swal.fire({
				title: 'Seguro que quieres desactivar este producto ?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				confirmButtonText: 'Desactivar',
			}).then(result => {
				if (result.isConfirmed) {
					axios
						.patch(`/api/productoTienda/${id}`)
						.then(response => {
							Swal.fire({
								icon: 'success',
								text: 'Se elimino correctamente',
								showConfirmButton: false,
								timer: 2000,
							}).then(() => (window.location.href = '/manager.html'));
						})
						.catch(error => {
							Swal.fire({
								icon: 'error',
								text: error.response.data,
								confirmButtonColor: '#7c601893',
							});
						});
				}
			});
		},
		activarProducto(id) {
			Swal.fire({
				title: 'Seguro que quieres activar este producto ?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				confirmButtonText: 'Activar',
			}).then(result => {
				if (result.isConfirmed) {
					axios
						.patch(`/api/productoTienda/${id}`)
						.then(response => {
							Swal.fire({
								icon: 'success',
								text: 'Se activo correctamente',
								showConfirmButton: false,
								timer: 2000,
							}).then(() => (window.location.href = '/manager.html'));
						})
						.catch(error => {
							Swal.fire({
								icon: 'error',
								text: error.response.data,
								confirmButtonColor: '#7c601893',
							});
						});
				}
			});
		},
		cargarProductoModificar(id) {
			axios.get(`/api/productoTienda/${id}`)
				.then(response => {
					// VACIAMOS LAS PROPIEDADES PARA QUE NO QUEDEN DATOS DE OTROS PRODUCTOS
					this.checkTallaModificar = []
					this.stockXSModificar = ""
					this.stockSModificar = ""
					this.stockMModificar = ""
					this.stockLModificar = ""
					this.stockXLModificar = ""
					this.productoModificar = response.data
					//PROCEDIMIENTO PARA QUE CUANDO TRAIGA EL PRODUCTO, CARGUE LOS CHECKS QUE CONTIENE Y SI CIERRA EL MODAL, VUELVE A SUS VALORES PREDETERMINADOS CUANDO LO ABRA 
					for (const key in this.productoModificar.tallas) {
						if (!this.checkTallaModificar.includes(key)) {
							if(key === 'XS'){
								this.checkTallaModificar.push(key)
								this.stockXSModificar = this.productoModificar.tallas[key]
							}
							if(key === 'S'){
								this.checkTallaModificar.push(key)
								this.stockSModificar = this.productoModificar.tallas[key]
							}
							if(key === 'M'){
								this.checkTallaModificar.push(key)
								this.stockMModificar = this.productoModificar.tallas[key]
							}
							if(key === 'L'){
								this.checkTallaModificar.push(key)
								this.stockLModificar = this.productoModificar.tallas[key]
							}
							if(key === 'XL'){
								this.checkTallaModificar.push(key)
								this.stockXLModificar = this.productoModificar.tallas[key]
							}
						}
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
		modificarProducto() {
			Swal.fire({
				title: '¿Estas seguro que quieres modificar este producto?',
				inputAttributes: {
					autocapitalize: 'off',
				},
				showCancelButton: true,
				cancelButtonText: 'Cancelar',
				confirmButtonText: 'Confirmar',
				showLoaderOnConfirm: true,
				preConfirm: login => {
					return axios
						.put('/api/productoTienda', this.productoModificar)
						.then(response => {
							Swal.fire({
								icon: 'success',
								text: 'Se modifico correctamente',
								showConfirmButton: false,
								timer: 2000,
							}).then(() => (window.location.href = '/manager.html'));
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
