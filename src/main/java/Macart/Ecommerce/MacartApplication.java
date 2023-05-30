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

//		cliente1
		Cliente cliente1 = new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlosandresgoo@gmail.com","6033603","322-567-8909",passwordEncoder.encode("123"));
		clienteRepositorio.save(cliente1);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero","Copacabana","Antioquia","12345");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

		Pedido pedido1 = new Pedido(LocalDateTime.now(),false,150000.00,"Transportadora", PedidoMetodoDePago.TRANSFERENCIA);
		cliente1.agregarPedido(pedido1);
		pedidoRepositorio.save(pedido1);

		Comprobante comprobante1 = new Comprobante(PedidoMetodoDePago.TRANSFERENCIA,"Transportadora",LocalDateTime.now(),150000.00 );
		comprobanteRepositorio.save(comprobante1);

		ProductoTienda productoTienda1 = new ProductoTienda("Camisa", 50000.00 , "Muy bonita", 200 , ProductoTiendaTallaSuperior.M, null, null, ProductoTiendaCategoriaGenero.HOMBRE, "Camisas"  );
		productoTiendaRepositorio.save(productoTienda1);

		ProductoTienda productoTienda2 = new ProductoTienda("Camisa blanca", 70000.00 , "Muy bonita", 100 , ProductoTiendaTallaSuperior.M, null, null, ProductoTiendaCategoriaGenero.HOMBRE, "Camisas"  );
		productoTiendaRepositorio.save(productoTienda2);

		PedidoProducto pedidoProducto1 = new PedidoProducto(8, productoTienda1 );
		pedido1.agregarPedidoProducto(pedidoProducto1);
		pedidoProductoRepositorio.save(pedidoProducto1);

		PedidoProducto pedidoProducto2 = new PedidoProducto(2, productoTienda2 );
		pedido1.agregarPedidoProducto(pedidoProducto2);
		pedidoProductoRepositorio.save(pedidoProducto2);






//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","6543603","355-567-5555",passwordEncoder.encode("321"));
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
