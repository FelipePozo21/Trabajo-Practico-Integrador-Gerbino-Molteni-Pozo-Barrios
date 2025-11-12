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
import model.Mascota;
import model.Microchip;

/**
 * Clase principal del sistema de gestión de Mascotas y Microchips.
 * Punto de entrada de la aplicación.
 * 
 * @author Gerbino, Molteni, Pozo, Barrios
 * @version 1.0
 */
public class Main {

  public static void main(String[] args) {
    ////////////////////////////////////PRUEBAS/////////////////////////////////////////////////////////////////
    // Crear un microchip y probar metodos
    Microchip microchip = new Microchip(10000000000L, "Codigo1", "Veterinaria1", "Saludable");
    System.out.println(microchip.getId());
    System.out.println(microchip.getCodigo());   
    microchip.setEliminado(true);
    microchip.setFechaImplementacion(LocalDate.of(2024, 12, 5));
    microchip.setVeterinaria("NuevaVeterinaria");
    microchip.setObservaciones("Enfermo");   
    System.out.println(microchip.isEliminado());
    System.out.println(microchip.getFechaImplementacion());
    System.out.println(microchip.getVeterinaria());
    System.out.println(microchip.getObservaciones());
    // Crear una mascota y probar metodos
    Mascota mascota = new Mascota(11000000000L, "Mascota1", "Perro", null, "Juana",null);
    System.out.println(mascota);
    mascota.setMicrochip(microchip);
    mascota.setDuenio("Natalia");
    mascota.setEspecie("Loro");
    mascota.setRaza("Boxer");
    System.out.println(mascota);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  }
}

