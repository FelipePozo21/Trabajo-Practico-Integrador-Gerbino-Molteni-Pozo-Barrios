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

    // IDs creados dinamicamente
    private static Long mascotaId;
    private static Long microchipId;

    public static void main(String[] args) {

        probarInsertarMascota();
        probarInsertarMicrochip();

        probarGetByIdMascota();
        probarGetByIdMicrochip();

        probarActualizarMascota();
        probarActualizarMicrochip();

        probarAsignarMicrochip();

        probarCrearMascotaConMicrochip();

        probarRollbackForzadoAsignacion();

        // Eliminaciones al final para evitar errores en pruebas posteriores
        probarEliminarMascota();
        probarEliminarMicrochip();
    }

    // ============================
    // MASCOTAS
    // ============================

    private static void probarInsertarMascota() {
        System.out.println("\n=== INSERTAR MASCOTA ===");

        MascotaService service = new MascotaService();

        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setEspecie("Gato");
        mascota.setRaza("Siames");
        mascota.setDuenio("Pablo");
        mascota.setFechaNacimiento(LocalDate.of(2023, 3, 10));

        try {
            service.insertar(mascota);
            mascotaId = mascota.getId();  // guardar ID
            System.out.println("Mascota insertada con ID: " + mascotaId);
        } catch (ServiceException e) {
            System.out.println("AVISO: " + e.getMessage());
        }
    }

    private static void probarGetByIdMascota() {
        System.out.println("\n=== LEER MASCOTA POR ID ===");

        MascotaService service = new MascotaService();

        try {
            Mascota mascota = service.getById(mascotaId);
            System.out.println("Mascota: " + mascota);
        } catch (ServiceException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private static void probarActualizarMascota() {
        System.out.println("\n=== ACTUALIZAR MASCOTA ===");

        MascotaService service = new MascotaService();

        try {
            Mascota mascota = service.getById(2L);

            if (mascota == null) {
                System.out.println("No se puede actualizar: mascota no existe.");
                return;
            }

            mascota.setNombre("Mondongo Actualizado");
            service.actualizar(mascota);

            System.out.println("Mascota actualizada.");
        } catch (ServiceException e) {
            System.out.println("AVISO: " + e.getMessage());
        }
    }

    private static void probarEliminarMascota() {
        System.out.println("\n=== ELIMINAR MASCOTA ===");

        MascotaService service = new MascotaService();

        try {
            service.eliminar(mascotaId);
            System.out.println("Mascota eliminada (baja logica).");
        } catch (ServiceException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    // ============================
    // MICROCHIP
    // ============================

    private static void probarInsertarMicrochip() {
        System.out.println("\n=== INSERTAR MICROCHIP ===");

        MicrochipService service = new MicrochipService();

        Microchip microchip = new Microchip();
        microchip.setCodigo("ABC" + System.currentTimeMillis() % 10000);
        microchip.setFechaImplementacion(LocalDate.of(2024, 1, 20));
        microchip.setVeterinaria("Vet Uno");
        microchip.setObservaciones("OK");

        try {
            service.insertar(microchip);
            microchipId = microchip.getId(); // guardar ID
            System.out.println("Microchip insertado con ID: " + microchipId);
        } catch (ServiceException e) {
            System.out.println("AVISO: " + e.getMessage());
        }
    }

    private static void probarGetByIdMicrochip() {
        System.out.println("\n=== LEER MICROCHIP POR ID ===");

        MicrochipService service = new MicrochipService();

        try {
            Microchip mc = service.getById(2L);
            System.out.println("Microchip: " + mc);
        } catch (ServiceException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private static void probarActualizarMicrochip() {
        System.out.println("\n=== ACTUALIZAR MICROCHIP ===");

        MicrochipService service = new MicrochipService();

        try {
            Microchip mc = service.getById(microchipId);

            if (mc == null) {
                System.out.println("No se puede actualizar: microchip no existe (probablemente eliminado).");
                return;
            }

            mc.setObservaciones("Actualizado desde test");
            service.actualizar(mc);

            System.out.println("Microchip actualizado.");
        } catch (ServiceException e) {
            System.out.println("AVISO: " + e.getMessage());
        }
    }

    private static void probarEliminarMicrochip() {
        System.out.println("\n=== ELIMINAR MICROCHIP ===");

        MicrochipService service = new MicrochipService();

        try {
            service.eliminar(microchipId);
            System.out.println("Microchip eliminado (baja logica).");
        } catch (ServiceException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    // ============================
    // RELACION
    // ============================

    private static void probarAsignarMicrochip() {
        System.out.println("\n=== ASIGNAR MICROCHIP A MASCOTA ===");

        MascotaService service = new MascotaService();

        try {
            service.asignarMicrochip(mascotaId, microchipId);
            System.out.println("Microchip asignado correctamente.");
        } catch (ServiceException e) {
            System.out.println("AVISO: " + e.getMessage());
        }
    }

    private static void probarCrearMascotaConMicrochip() {
        System.out.println("\n=== CREAR MASCOTA + MICROCHIP ===");

        MascotaService service = new MascotaService();

        Mascota mascota = new Mascota();
        mascota.setNombre("Rocky");
        mascota.setEspecie("Perro");
        mascota.setRaza("Pitbull");
        mascota.setDuenio("Carlos");
        mascota.setFechaNacimiento(LocalDate.of(2020, 12, 5));

        Microchip mc = new Microchip();
        mc.setCodigo("NEW" + System.currentTimeMillis() % 10000);
        mc.setFechaImplementacion(LocalDate.now());
        mc.setVeterinaria("AnimalCare");
        mc.setObservaciones("Implantado sin complicaciones");

        try {
            service.crearMascotaConMicrochip(mascota, mc);
            System.out.println("Mascota creada con microchip en una sola transaccion.");
        } catch (ServiceException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private static void probarRollbackForzadoAsignacion() {
        System.out.println("\n=== PRUEBA DE ROLLBACK FORZADO ===");

        MascotaService service = new MascotaService();

        try {
            service.asignarMicrochip(mascotaId, 999999L);
        } catch (ServiceException e) {
            System.out.println("Rollback correcto: " + e.getMessage());
        }
    }
}
