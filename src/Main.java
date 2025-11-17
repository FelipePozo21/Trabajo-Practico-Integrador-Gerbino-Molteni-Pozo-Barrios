import java.time.LocalDate;
import java.time.Month;
import model.Mascota;
import model.Microchip;
import exception.ServiceException;
import service.MascotaService;
import service.MicrochipService;

/**
 * Clase principal del sistema de gestion de Mascotas y Microchips.
 * Punto de entrada de la aplicacion.
 * 
 * @author Gerbino, Molteni, Pozo, Barrios
 * @version 1.0
 */

public class Main {

    public static void main(String[] args) {
        
        // Iniciamos AppMenu con la Interfaz CLI
        
        AppMenu menu = new AppMenu();
        menu.iniciar();
        
        // Salida del sistema manejada por AppMenu
        
    }

}