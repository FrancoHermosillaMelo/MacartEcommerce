package Macart.Ecommerce;

import Macart.Ecommerce.Modelos.*;
import Macart.Ecommerce.Repositorio.*;
import Macart.Ecommerce.Servicios.Implementacion.EnviarCorreoImplementacion;
import Macart.Ecommerce.Utilidades.ProductoTiendaUtilidades;
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

		Cliente admin = new Cliente("admin", null, "admin", null, "admin@admin.com", "322-567-8909", passwordEncoder.encode("123"),true,"D4BE10");
		clienteRepositorio.save(admin);

		Direccion direccion1 = new Direccion("Calle-47a","50-05","Barrio obrero","Copacabana","Antioquia","12345");
		cliente1.agregarDirecciones(direccion1);
		direccionRepositorio.save(direccion1);

//		Pedido pedido1 = new Pedido(LocalDateTime.now(),false,150000.00,"Transportadora");
//		cliente1.agregarPedido(pedido1);
//		pedidoRepositorio.save(pedido1);



		// PRODUCTOS

		ProductoTienda productoTienda1 = new ProductoTienda("Camisa Blanca", 50000.00 , "Camisa blanca de algodon",List.of("https://live.staticflickr.com/65535/52943190902_f77c1e8be1_b.jpg","https://live.staticflickr.com/65535/52943190892_f386c30847_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CAMISETA",true);
		productoTienda1.agregarTalla("XS", 10);
		productoTienda1.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda1.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda1.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda1);

		ProductoTienda productoTienda2 = new ProductoTienda("Blusa Negra", 70000.00 , "Blusa negra de ribb de algodon", List.of("https://live.staticflickr.com/65535/52944252988_fb0e51c815_z.jpg","https://live.staticflickr.com/65535/52944177705_dfe76ea968_z.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BLUSA", true);
		productoTienda2.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda2.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda2.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda2.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda2.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda2);

		ProductoTienda productoTienda3 = new ProductoTienda("Blusa Blanca", 50000, "Blusa blanca de ribb de algodón",List.of("https://live.staticflickr.com/65535/52943937034_c6e221c620_z.jpg","https://live.staticflickr.com/65535/52943937039_233c0f81c0_z.jpg"),ProductoTiendaCategoriaGenero.MUJER,"BLUSA", true);
		productoTienda3.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda3.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda3.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda3.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda3);

//		ProductoTienda productoTienda4 = new ProductoTienda("Bolso Negro", 40000, "Un bolso hecho con el mas fino cuero", List.of("https://live.staticflickr.com/65535/52943190962_7cc5a9dc6c_b.jpg","https://live.staticflickr.com/65535/52944252948_4f7ce08553_b.jpg"),ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", true);
//
//		productoTiendaRepositorio.save(productoTienda4);

//		ProductoTienda productoTienda5 = new ProductoTienda("Cartera Negra",45000,"Una cartera hecha con el mas fino cuero",List.of("https://live.staticflickr.com/65535/52943791561_d03c42a9ec_b.jpg","https://live.staticflickr.com/65535/52944177635_05fff2935d_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda5);

		ProductoTienda productoTienda6 = new ProductoTienda("Pantalon Verde", 60000, "Pantalon color verde, muy suave y ligero",List.of("https://live.staticflickr.com/65535/52943936929_fe078f999e_b.jpg","https://live.staticflickr.com/65535/52943936934_d00575a15d_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "PANTALON", true);
		productoTienda6.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda6.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda6.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda6);

		ProductoTienda productoTienda7 = new ProductoTienda("Buzo Vespertine", 50000, "Buzo de Algodon color azul, suave al tacto",List.of("https://live.staticflickr.com/65535/52945596815_17753866c1_b.jpg", "https://live.staticflickr.com/65535/52945671943_20e3260ecc_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BUZO", true);
		productoTienda7.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda7.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda7.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda7.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda7);

		ProductoTienda productoTienda8 = new ProductoTienda("Buzo Vabel", 45000, "Buzo de Algodon color crema, suave al tacto",List.of("https://live.staticflickr.com/65535/52944611137_69d6f7a608_b.jpg", "https://live.staticflickr.com/65535/52945207201_632576051e_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BUZO", true);
		productoTienda8.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda8.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda8.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda8.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda8.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda8);

		ProductoTienda productoTienda9 = new ProductoTienda("Buzo Tekin", 40000, "Buzo de Algodon color negro, suave al tacto", List.of("https://live.staticflickr.com/65535/52945671978_c6684c5a0b_b.jpg", "https://live.staticflickr.com/65535/52945351739_0801580093_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BUZO", true);
		productoTienda9.agregarTalla("XS", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda9.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda9.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda9.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda9);

//		ProductoTienda productoTienda10 = new ProductoTienda("Riñonera Marqet", 20000, "Riñonera espaciosa, comoda al uso, excelente calidad",List.of("https://live.staticflickr.com/65535/52944611207_fc61dd7180_b.jpg", "https://live.staticflickr.com/65535/52944611232_b87e4401f5_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda10);

		ProductoTienda productoTienda11 = new ProductoTienda("Pantalon Queen", 65000, "Pantalon color negro, muy suave y ligero", List.of("https://live.staticflickr.com/65535/52945672068_39d0333d8f_b.jpg", "https://live.staticflickr.com/65535/52945351814_4b9d83f934_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "PANTALON", true);
		productoTienda11.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda11.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda11.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda11);

		ProductoTienda productoTienda12 = new ProductoTienda("Pantalon Keet", 60000, "Pantalon color marron, muy suave y ligero",List.of("https://live.staticflickr.com/65535/52945351829_16f1aecc89_b.jpg", "https://live.staticflickr.com/65535/52945351834_9154069135_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "PANTALON", true);
		productoTienda12.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda12.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda12.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda12);

		ProductoTienda productoTienda13 = new ProductoTienda("Pantalon Whiteen", 62000, "Pantalon color blanco, muy suave y ligero",List.of("https://live.staticflickr.com/65535/52945351839_6906dcaffd_b.jpg", "https://live.staticflickr.com/65535/52945207391_4853f36a77_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "PANTALON", true);
		productoTienda13.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda13.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda13.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda13);

		ProductoTienda productoTienda14 = new ProductoTienda("Pantalon King", 65000, "Pantalon color negro, muy suave y ligero",List.of("https://live.staticflickr.com/65535/52944611347_c4dfc708b4_b.jpg", "https://live.staticflickr.com/65535/52945207471_1e5760b0f1_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "PANTALON", true);
		productoTienda14.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda14.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda14.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda14);

		ProductoTienda productoTienda15 = new ProductoTienda("Pantalon Yeah", 62000, "Pantalon color gris, muy suave y ligero",List.of("https://live.staticflickr.com/65535/52945672188_60c954f2d1_b.jpg", "https://live.staticflickr.com/65535/52945597065_1ddda5c438_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "PANTALON", true);
		productoTienda15.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda15.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda15.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda15);

//		ProductoTienda productoTienda16 = new ProductoTienda("Gorron Yey", 20000, "Gorron color azul, ligero y comodo",List.of("https://live.staticflickr.com/65535/52945672208_0e1978f327_b.jpg", "https://live.staticflickr.com/65535/52945352024_8292d993ea_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda16);
//
//		ProductoTienda productoTienda17 = new ProductoTienda("Gorra El Rey", 18000, "Gorra color azul, ligera y comoda",List.of("https://live.staticflickr.com/65535/52945597075_f4d0ce2751_b.jpg", "https://live.staticflickr.com/65535/52945672248_870483194f_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda17);

		ProductoTienda productoTienda18 = new ProductoTienda("Falda Queen", 40000, "Falda color rojo, Excelente calidad, muy comoda",List.of("https://live.staticflickr.com/65535/52945597125_245069dc47_b.jpg", "https://live.staticflickr.com/65535/52945672243_4b74d4c0e3_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "FALDA", true);
		productoTienda18.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda18.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda18.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda18);

		ProductoTienda productoTienda19 = new ProductoTienda("Falda Dark", 38000, "Falda color negro, Excelente calidad, muy comoda",List.of("https://live.staticflickr.com/65535/52944611417_b18186a2de_b.jpg", "https://live.staticflickr.com/65535/52944611422_571c042299_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "FALDA", true);
		productoTienda19.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda19.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda19.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda19);

		ProductoTienda productoTienda20 = new ProductoTienda("Falda Esco", 35000, "Falda tipo leñadora, Excelente calidad, muy comoda",List.of("https://live.staticflickr.com/65535/52945207596_2f145c4c3d_b.jpg", "https://live.staticflickr.com/65535/52945597210_d1898c8610_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "FALDA", true);
		productoTienda20.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda20.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda20.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda20);

//		ProductoTienda productoTienda21 = new ProductoTienda("Cinturon Cuero", 25000, "Cinturon excelente calidad de cuero",List.of("https://live.staticflickr.com/65535/52945672318_2b4d3b2851_b.jpg", "https://live.staticflickr.com/65535/52945672393_e5651207cd_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda21);
//
//		ProductoTienda productoTienda22 = new ProductoTienda("Cinturon Tachuelas", 20000, "Cinturon excelente calidad de cuero y con tachuelas",List.of("https://live.staticflickr.com/65535/52945672313_ce64173815_b.jpg", "https://live.staticflickr.com/65535/52944611487_201cd8ec72_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda22);

		ProductoTienda productoTienda23 = new ProductoTienda("Chaqueta Lok", 90000, "Chaqueta negra de excelente calidad",List.of("https://live.staticflickr.com/65535/52944611507_b02d9d7748_b.jpg", "https://live.staticflickr.com/65535/52945597395_1e70ba14bf_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "CHAQUETA", true);
		productoTienda23.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda23.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda23.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda23.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda23.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda23);

		ProductoTienda productoTienda24 = new ProductoTienda("Chaqueta Sake", 88000, "Chaqueta verde de excelente calidad",List.of("https://live.staticflickr.com/65535/52944611537_b95ceb58a7_b.jpg", "https://live.staticflickr.com/65535/52944611582_dd0e6fb592_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "CHAQUETA", true);
		productoTienda24.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda24.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda24.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda24.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda24.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda24);

		ProductoTienda productoTienda25 = new ProductoTienda("Chaqueta Keke", 92000, "Chaqueta marron de excelente calidad",List.of("https://live.staticflickr.com/65535/52945352219_19e7a4efe2_b.jpg", "https://live.staticflickr.com/65535/52944611632_8844cd1e24_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "CHAQUETA", true);
		productoTienda25.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda25.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda25.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda25.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda25.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda25);

		ProductoTienda productoTienda26 = new ProductoTienda("Chaqueta LOL", 90000, "Chaqueta plateada de excelente calidad",List.of("https://live.staticflickr.com/65535/52945672453_0a4bf5b8df_b.jpg", "https://live.staticflickr.com/65535/52944611637_7f9617d822_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CHAQUETA", true);
		productoTienda26.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda26.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda26.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda26.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda26.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda26);

		ProductoTienda productoTienda27 = new ProductoTienda("Chaqueta Dark", 92000, "Chaqueta negra de excelente calidad",List.of("https://live.staticflickr.com/65535/52945207801_bb9002c242_b.jpg", "https://live.staticflickr.com/65535/52945352314_c6414ae572_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CHAQUETA", true);
		productoTienda27.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda27.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda27.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda27.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda27.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda27);

		ProductoTienda productoTienda28 = new ProductoTienda("Chaqueta Melo", 100000, "Chaqueta azul de excelente calidad",List.of("https://live.staticflickr.com/65535/52945352289_963700cbd2_b.jpg", "https://live.staticflickr.com/65535/52944611662_ae7c832d7a_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CHAQUETA", true);
		productoTienda28.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda28.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda28.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda28.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda28.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda28);

		ProductoTienda productoTienda29 = new ProductoTienda("Camiseta Mendo", 35000, "Camiseta verde de excelente calidad",List.of("https://live.staticflickr.com/65535/52945352329_63987b221d_b.jpg", "https://live.staticflickr.com/65535/52945207851_9769ec6121_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CAMISETA", true);
		productoTienda29.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda29.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda29.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda29.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda29.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda29);

		ProductoTienda productoTienda30 = new ProductoTienda("Camiseta Ruiz", 30000, "Camiseta gris de excelente calidad",List.of("https://live.staticflickr.com/65535/52945672548_97e9262361_b.jpg", "https://live.staticflickr.com/65535/52945207881_146c7776b9_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "CAMISETA", true);
		productoTienda30.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda30.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda30.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda30.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda30.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda30);

		ProductoTienda productoTienda31 = new ProductoTienda("Buzo San", 45000, "Buzo blanco de excelente calidad",List.of("https://live.staticflickr.com/65535/52945672573_ed5552c1d5_b.jpg", "https://live.staticflickr.com/65535/52945672668_3d61a7d6df_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "BUZO", true);
		productoTienda31.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda31.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda31.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda31.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda31.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda31);

		ProductoTienda productoTienda32 = new ProductoTienda("Buzo Chan", 50000, "Buzo verde de excelente calidad",List.of("https://live.staticflickr.com/65535/52945352389_25fd0fd77b_b.jpg", "https://live.staticflickr.com/65535/52944611832_9e10380b56_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "BUZO", true);
		productoTienda32.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda32.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda32.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda32.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda32.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda32);

		ProductoTienda productoTienda33 = new ProductoTienda("Buzo Dark", 55000, "Buzo negro de excelente calidad",List.of("https://live.staticflickr.com/65535/52945597625_0da35d5bd9_b.jpg", "https://live.staticflickr.com/65535/52944611797_3c8847d0ab_b.jpg"), ProductoTiendaCategoriaGenero.HOMBRE, "BUZO", true);
		productoTienda33.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda33.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda33.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda33.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda33.agregarTalla("XL", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda33);

//		ProductoTienda productoTienda34 = new ProductoTienda("Bufanda Queen", 34000, "Bufanda de excelente calidad",List.of("https://live.staticflickr.com/65535/52945208046_ee55676ab6_b.jpg", "https://live.staticflickr.com/65535/52945672708_429d08d34c_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "ACCESORIOS", true);
//		productoTiendaRepositorio.save(productoTienda34);

		ProductoTienda productoTienda35 = new ProductoTienda("Blusa Blur", 40000 , "Blusa azul de algodon, excelente calidad", List.of("https://live.staticflickr.com/65535/52945208041_b75326228c_b.jpg", "https://live.staticflickr.com/65535/52945208071_21be2bb245_b.jpg"), ProductoTiendaCategoriaGenero.MUJER, "BLUSA", true);
		productoTienda35.agregarTalla("XS",ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda35.agregarTalla("S", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda35.agregarTalla("M", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTienda35.agregarTalla("L", ProductoTiendaUtilidades.randomNumber(1,50));
		productoTiendaRepositorio.save(productoTienda35);


		// PEDIDOS PRODUCTOS

//		PedidoProducto pedidoProducto1 = new PedidoProducto(8, productoTienda1 );
//		pedido1.agregarPedidoProducto(pedidoProducto1);
//		pedidoProductoRepositorio.save(pedidoProducto1);
//
//		PedidoProducto pedidoProducto2 = new PedidoProducto(2, productoTienda2 );
//		pedido1.agregarPedidoProducto(pedidoProducto2);
//		pedidoProductoRepositorio.save(pedidoProducto2);


//		cliente2
		Cliente cliente2 =new Cliente("Ivan","Ezequiel","Miguel","Hernandez","Ivan@gmail.com","355-567-5555",passwordEncoder.encode("321"),false,"D4BO11");
		clienteRepositorio.save(cliente2);

		Direccion direccion2 = new Direccion("Calle-100Z","100-05","mar de plata","Boca","buenos aires","54321");
		cliente2.agregarDirecciones(direccion2);
		direccionRepositorio.save(direccion2);

//		Pedido pedido2 = new Pedido(LocalDateTime.now(),false,320000.00,"domicilio");
//		cliente2.agregarPedido(pedido2);
//		pedidoRepositorio.save(pedido2);
	};
	}


}
