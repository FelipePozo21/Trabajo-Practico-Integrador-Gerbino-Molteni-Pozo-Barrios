import java.time.LocalDate;
import model.Mascota;
import model.Microchip;

/**
 * Clase principal del sistema de gestión de Mascotas y Microchips.
 * Punto de entrada de la aplicación.
 * 
 * @author Gerbino, Molteni, Pozo, Barrios
 * @version 1.0
 */
import java.time.LocalDate;
import java.time.Month;
import model.Mascota;
import model.Microchip;
import exception.ServiceException;
import model.Mascota;
import model.Microchip;
import service.MascotaService;
import service.MicrochipService;
import java.time.LocalDate;

/**
 * Clase principal del sistema de gestión de Mascotas y Microchips.
 * Punto de entrada de la aplicación.
 * 
 * @author Gerbino, Molteni, Pozo, Barrios
 * @version 1.0
 */
public class Main {

  public static void main(String[] args) {
    
   probarMascota();
   probarMicrochip();
   probarTransaccion();
  }
   
  private static void probarMascota() {
    System.out.println("Prueba insertar mascota");
    
    try {
      MascotaService service = new MascotaService();
      
      Mascota mascota = new Mascota(null, "Pepino", "Cobayo", "Saltarin","Juan Pérez", LocalDate.of(2025, 1, 10));
      
      service.insertar(mascota);
      System.out.println("Mascota insertada: " + mascota.getId());
    } catch (ServiceException e){
      System.err.println("Error: " + e.getMessage());
    }
  }
  
  private static void probarMicrochip() {
    System.out.println("Prueba insertar microchip");
    
    try {
      MicrochipService service = new MicrochipService();
      
      Microchip microchip = new Microchip( null, "CHIP123455", "Veterinaria Central", "Prueba");
      
      service.insertar(microchip);
      System.out.println("Microchip insertado: " + microchip.getId());
    } catch (ServiceException e){
      System.err.println("Error: " + e.getMessage());
    }
  }
  
  private static void probarTransaccion() {
    System.out.println("Prueba transaccion");
    
    try {
      MascotaService service = new MascotaService();
      
      Mascota luna = new Mascota(null, "Mondongo", "Gato", "Siames","Pablo Gonzalez", LocalDate.of(2024, 5, 15));
      Microchip microchip = new Microchip( null, "CHIP123456", "Veterinaria Colitas", "Contactar a X");
      
      service.crearMascotaConMicrochip(luna, microchip);
    } catch (ServiceException e) {
      System.err.println("Error Transaccion: " + e.getMessage());
    }
  }
  
}

