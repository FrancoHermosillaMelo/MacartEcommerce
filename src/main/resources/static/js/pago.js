const { createApp } = Vue

createApp({
    data() {
        return {
            // Inicializamos las variables
            nombre: "",
            colorCard: "",
            typeCard: "",
            cvv: "",
            number1: "",
            number2: "",
            number3: "",
            number4: "",
            emailClient: "",
            cliente: "",
        }
    },
    created() {
        this.cargarDatos()
        this.cargarCliente()
    },
    methods: {
        createNumberCard() {
            this.cardNumber = this.number1 + "-" + this.number2 + "-" + this.number3 + "-" + this.number4;
            console.log(this.cardNumber)
        },
        cargarDatos() {
            axios.get('/api/clientes/pedidos')
                .then(respuesta => {
                    this.pedidos = respuesta.data
                    console.log(this.pedidos[0]);
                    this.verificado = response.data.verificado === true;
                })
                .catch(error => console.log(error))
        },
        cargarCliente() {
            axios.get('/api/clientes/actual')
                .then(respuesta => {
                    this.cliente = respuesta.data;
                    console.log(this.cliente);
                })
                .catch(error => {
                    this.cliente = []
                })
        },
        payCard() {
            Swal.fire({
                title: '¿Está seguro de pagar con esta tarjeta? ' + this.cardNumber,
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Confirmar',
                preConfirm: () => {
                    console.log(this.pedidos[0].montoTotal);
                    return axios.post('/api/comprobantes/pdf',
                        {
                            "type": this.typeCard,
                            "color": this.colorCard,
                            "number": this.cardNumber,
                            "cvv": this.cvv,
                            "email": this.cliente.correo,
                            "amount": this.pedidos[0].montoTotal

                        })
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                text: 'Pago realizado',
                                showConfirmButton: false,
                                timer: 3000,
                            }).then(() => window.location.href = "/html/perfilCliente.html")
                        })
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                text: error.response.data,
                                confirmButtonColor: "#7c601893",
                            })
                            console.log(error);
                        })
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        }
    },
}).mount("#app");