package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.*;
import Macart.Ecommerce.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MacartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacartApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

@Bean
public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, DireccionRepositorio direccionRepositorio, PedidoRepositorio pedidoRepositorio, ComprobanteRepositorio comprobanteRepositorio , PedidoProductoRepositorio pedidoProductoRepositorio, ProductoTiendaRepositorio productoTiendaRepositorio) {
	return (args) -> {
		// guardarclientes

		//	cliente1
		Cliente cliente1 = new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlos@gmail.com","322-567-8909",passwordEncoder.encode("123"));
		clienteRepositorio.save(cliente1);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero","Copacabana","Antioquia","12345");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

		Pedido pedido1 = new Pedido(LocalDateTime.now(),false,150000.00,"Transportadora", PedidoMetodoDePago.TRANSFERENCIA);
		cliente1.agregarPedido(pedido1);
		pedidoRepositorio.save(pedido1);

		Comprobante comprobante1 = new Comprobante(PedidoMetodoDePago.TRANSFERENCIA,"Transportadora",LocalDateTime.now(),150000.00 );
		cliente1.agregarComprobantes(comprobante1);
		comprobanteRepositorio.save(comprobante1);
		clienteRepositorio.save(cliente1);

		// PRODUCTOS

		ProductoTienda productoTienda1 = new ProductoTienda("Camisa Blanca", 50000.00 , "Camisa blanca de algodon", ProductoTiendaTallaSuperior.M, null, List.of("./img/camisaBlanca.jpg","./img/camisaBlanca2.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "Camisa"  );
		productoTiendaRepositorio.save(productoTienda1);

		ProductoTienda productoTienda2 = new ProductoTienda("Blusa Negra", 70000.00 , "Blusa negra de ribb de algodon", ProductoTiendaTallaSuperior.M, null, List.of("./img/blusaNegra.jpg","./img/blusaNegra2.jpg"), ProductoTiendaCategoriaGenero.MUJER, "Blusa");
		productoTiendaRepositorio.save(productoTienda2);

		ProductoTienda productoTienda3 = new ProductoTienda("Blusa Blanca", 50000, "Blusa blanca de ribb de algodón",ProductoTiendaTallaSuperior.S, null, List.of("./img/blusaBlanca.jpg", "./img/blusaBlanca2.jpg"),ProductoTiendaCategoriaGenero.MUJER,"Blusa");
		productoTiendaRepositorio.save(productoTienda3);

		ProductoTienda productoTienda4 = new ProductoTienda("Bolso Jean", 40000, "Un bolso hecho de jean", null, null, List.of("./img/bolso.jpg", "./img/bolso2.jpg"),ProductoTiendaCategoriaGenero.MUJER, "Bolso");
		productoTiendaRepositorio.save(productoTienda4);

		ProductoTienda productoTienda5 = new ProductoTienda("Cartera Jean",45000,"Una cartera hecha de jean", null, null, List.of("./img/cartera.jpg", "./img/cartera2.jpg"), ProductoTiendaCategoriaGenero.MUJER, "Bolso");
		productoTiendaRepositorio.save(productoTienda5);
		// PEDIDOS PRODUCTOS

		PedidoProducto pedidoProducto1 = new PedidoProducto(8, productoTienda1 );
		pedido1.agregarPedidoProducto(pedidoProducto1);
		pedidoProductoRepositorio.save(pedidoProducto1);

		PedidoProducto pedidoProducto2 = new PedidoProducto(2, productoTienda2 );
		pedido1.agregarPedidoProducto(pedidoProducto2);
		pedidoProductoRepositorio.save(pedidoProducto2);


//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","355-567-5555",passwordEncoder.encode("321"));
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
