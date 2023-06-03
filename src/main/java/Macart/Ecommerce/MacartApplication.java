package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.*;
import Macart.Ecommerce.Repositorio.*;
import Macart.Ecommerce.Servicios.Implementacion.EnviarCorreoImplementacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MacartApplication {
	@Autowired
	private EnviarCorreoImplementacion enviarCorreoImplementacion;

	public static void main(String[] args) {
		SpringApplication.run(MacartApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;


@Bean
public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, DireccionRepositorio direccionRepositorio, PedidoRepositorio pedidoRepositorio, ComprobanteRepositorio comprobanteRepositorio , PedidoProductoRepositorio pedidoProductoRepositorio, ProductoTiendaRepositorio productoTiendaRepositorio) {
	return (args) -> {
		// guardarcliente

		//	cliente1

		Cliente cliente1 = new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlos@gmail.com","322-567-8909",passwordEncoder.encode("123"),true,"D3BA09");
		clienteRepositorio.save(cliente1);

		Cliente admin = new Cliente("admin", null, "admin", null, "carlosandresgoo@gmail.com", "322-567-8909", passwordEncoder.encode("123"),true,"D4BE10");
		clienteRepositorio.save(admin);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero","Copacabana","Antioquia","12345");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

		Pedido pedido1 = new Pedido(LocalDateTime.now(),false,150000.00,"Transportadora", PedidoMetodoDePago.TRANSFERENCIA);
		cliente1.agregarPedido(pedido1);
		pedidoRepositorio.save(pedido1);



		// PRODUCTOS

		ProductoTienda productoTienda1 = new ProductoTienda("Camisa Blanca", 50000.00 , "Camisa blanca de algodon",List.of("XS","S", "M", "L", "XL"),null, List.of("https://live.staticflickr.com/65535/52943190902_f77c1e8be1_b.jpg","https://live.staticflickr.com/65535/52943190892_f386c30847_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CAMISETA", 70 ,true);
		productoTiendaRepositorio.save(productoTienda1);

		ProductoTienda productoTienda2 = new ProductoTienda("Blusa Negra", 70000.00 , "Blusa negra de ribb de algodon", List.of("XS","S", "M", "L"), null, List.of("https://live.staticflickr.com/65535/52944252988_fb0e51c815_z.jpg","https://live.staticflickr.com/65535/52944177705_dfe76ea968_z.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BLUSA", 200, true);
		productoTiendaRepositorio.save(productoTienda2);

		ProductoTienda productoTienda3 = new ProductoTienda("Blusa Blanca", 50000, "Blusa blanca de ribb de algodón",List.of("S", "M", "L"), null, List.of("https://live.staticflickr.com/65535/52943937034_c6e221c620_z.jpg","https://live.staticflickr.com/65535/52943937039_233c0f81c0_z.jpg"),ProductoTiendaCategoriaGenero.MUJER,"BlUSA", 15, true);
		productoTiendaRepositorio.save(productoTienda3);

		ProductoTienda productoTienda4 = new ProductoTienda("Bolso Jean", 40000, "Un bolso hecho de jean", null, null, List.of("https://live.staticflickr.com/65535/52943190962_7cc5a9dc6c_b.jpg","https://live.staticflickr.com/65535/52944252948_4f7ce08553_b.jpg"),ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", 80, true);
		productoTiendaRepositorio.save(productoTienda4);

		ProductoTienda productoTienda5 = new ProductoTienda("Cartera Jean",45000,"Una cartera hecha de jean", null, null, List.of("https://live.staticflickr.com/65535/52943791561_d03c42a9ec_b.jpg","https://live.staticflickr.com/65535/52944177635_05fff2935d_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", 120, true);
		productoTiendaRepositorio.save(productoTienda5);
		// PEDIDOS PRODUCTOS

		PedidoProducto pedidoProducto1 = new PedidoProducto(8, productoTienda1 );
		pedido1.agregarPedidoProducto(pedidoProducto1);
		pedidoProductoRepositorio.save(pedidoProducto1);

		PedidoProducto pedidoProducto2 = new PedidoProducto(2, productoTienda2 );
		pedido1.agregarPedidoProducto(pedidoProducto2);
		pedidoProductoRepositorio.save(pedidoProducto2);


//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","355-567-5555",passwordEncoder.encode("321"),false,"D4BO11");
		clienteRepositorio.save(cliente2);

		Direccion direccion2 = new Direccion("Calle-100Z","100-05","mar de plata","Boca","buenos aires","54321");
		cliente2.agregarDirecciones(direccion2);
		direccionRepositorio.save(direccion2);

		Pedido pedido2 = new Pedido(LocalDateTime.now(),false,320000.00,"domicilio", PedidoMetodoDePago.EFECTIVO);
		cliente2.agregarPedido(pedido2);
		pedidoRepositorio.save(pedido2);

	};
	}


}
