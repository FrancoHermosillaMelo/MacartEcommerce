package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
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
public CommandLineRunner initData(ClienteRepositorio clienteRepositorio ) {
	return (args) -> {
		// guardar un cliente
		Cliente cliente1 =new Cliente("Carlos","Andrés","Ruiz","Hinestroza","carlosandresgoo@gmail.com","6033603","322-567-8909","123");
		clienteRepositorio.save(cliente1);
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","6543603","355-567-5555","321");
		clienteRepositorio.save(cliente2);
	};
}
}
