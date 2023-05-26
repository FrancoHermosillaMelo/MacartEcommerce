package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.*;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Repositorio.DireccionRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class MacartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacartApplication.class, args);
	}

@Bean
public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, DireccionRepositorio direccionRepositorio, PedidoRepositorio pedidoRepositorio, ComprobanteRepositorio comprobanteRepositorio) {
	return (args) -> {
		// guardarclientes

//		cliente1
		Cliente cliente1 = new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlosandresgoo@gmail.com","6033603","322-567-8909","123");
		clienteRepositorio.save(cliente1);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero","Copacabana","Antioquia","12345");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

		Pedido pedido1 = new Pedido(LocalDateTime.now(),false,150000.00,"Transportadora", PedidoMetodoDePago.TRANSFERENCIA);
		cliente1.agregarPedido(pedido1);
		pedidoRepositorio.save(pedido1);
		Comprobante comprobante1 = new Comprobante(PedidoMetodoDePago.TRANSFERENCIA,"Transportadora",LocalDateTime.now(),150000.00 );
		comprobanteRepositorio.save(comprobante1);


//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","6543603","355-567-5555","321");
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
