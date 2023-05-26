package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.DireccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MacartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacartApplication.class, args);
	}

@Bean
public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, DireccionRepositorio direccionRepositorio) {
	return (args) -> {
		// guardarclientes

//		cliente1
		Cliente cliente1 = new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlosandresgoo@gmail.com","6033603","322-567-8909","123");
		clienteRepositorio.save(cliente1);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","6543603","355-567-5555","321");
		clienteRepositorio.save(cliente2);

		Direccion direccion2 = new Direccion("Calle-100Z","100-05","mar de plata");
		cliente2.agregarDirecciones(direccion2);
		direccionRepositorio.save(direccion2);

	};
}
}
