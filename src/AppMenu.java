import exception.ServiceException;
import model.Mascota;
import model.Microchip;
import service.MascotaService;
import service.MicrochipService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner;
    private final MascotaService mascotaService;
    private final MicrochipService microchipService;
    
    // MENU PRINCIPAL Y MANEJO DE OPCIONES

    public AppMenu() {
        this.scanner = new Scanner(System.in);
        this.mascotaService = new MascotaService();
        this.microchipService = new MicrochipService();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();
            manejarOpcionPrincipal(opcion);
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("----------------------------------");
        System.out.println("MENU GESTION DE MASCOTAS ");
        System.out.println("----------------------------------");
        System.out.println("1 - CRUD Mascota");
        System.out.println("2 - CRUD Microchip");
        System.out.println("3 - Asignar Microchip a Mascota");
        System.out.println("4 - Crear Mascota con Microchip (Transaccion)");
        System.out.println("5 - Buscar Mascota por Codigo de Microchip");
        System.out.println("0 - Salir");
        System.out.println("----------------------------------");
    }
    
    private int leerOpcion() {
        try {
            System.out.print("Ingrese opcion: ");
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return -1;
        }
    }

    private void manejarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                System.out.println("Iniciando CRUD Mascota");
                menuCrudMascota();
                break;
            case 2:
                System.out.println("Iniciando CRUD Microchip");
                menuCrudMicrochip();
                break;
            case 3:
                System.out.println("Iniciando Asignacion de Microchip a Mascota");
                asignarMicrochipAMascota();
                break;
            case 4:
                System.out.println("Iniciando Creacion de Mascota y Microchip");
                crearMascotaYMicrochip();
                break;
            case 5:
                System.out.println("Iniciando Busqueda de Mascota por Microchip");
                buscarMascotaPorCodigoMicrochip();
                break;
            case 0:
                System.out.println("Saliendo del sistema.");
                break;
            default:
                System.err.println("La opcion no es valida. Vuelva a intentar.");
        }
    }

    // CRUD MASCOTA

    private void menuCrudMascota() {
        int opcion;
        do {
            System.out.println("----------------------------------");
            System.out.println("1 - Crear Mascota");
            System.out.println("2 - Leer Mascota por su ID");
            System.out.println("3 - Listar Mascotas Activas");
            System.out.println("4 - Actualizar Mascota");
            System.out.println("5 - Eliminar Mascota");
            System.out.println("0 - Volver al Menu Principal");
            System.out.println("----------------------------------");
            opcion = leerOpcion();
            scanner.nextLine(); 

            try {
                switch (opcion) {
                    case 1: crearMascota(); break;
                    case 2: leerMascotaPorId(); break;
                    case 3: listarMascotas(); break;
                    case 4: actualizarMascota(); break;
                    case 5: eliminarMascota(); break;
                    case 0: System.out.println("Volviendo al menu principal."); break;
                    default: System.err.println("Opcion invalida.");
                }
            } catch (ServiceException | IllegalArgumentException e) {
                System.err.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void crearMascota() throws ServiceException {
        System.out.println("CREANDO MASCOTA:");
        try {
            Mascota mascota = new Mascota();
            System.out.print("Nombre (obligatorio): ");
            mascota.setNombre(scanner.nextLine());
            System.out.print("Especie (obligatorio): ");
            mascota.setEspecie(scanner.nextLine());
            System.out.print("Raza (opcional, default 'Sin especificar'): ");
            mascota.setRaza(scanner.nextLine());
            System.out.print("Dueno (obligatorio): ");
            mascota.setDuenio(scanner.nextLine());
            mascota.setFechaNacimiento(leerFecha("Fecha de Implantacion (YYYY-MM-DD, opcional, default hoy)"));

            mascotaService.insertar(mascota);
            System.out.println("Mascota creada con ID: " + mascota.getId());

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validacion: " + e.getMessage());
        }
    }

    private void leerMascotaPorId() throws ServiceException {
        System.out.println("LEYENDO MASCOTA POR ID:");
        try {
            System.out.print("Ingrese el ID de la mascota: ");
            Long id = leerLong();
            Mascota mascota = mascotaService.getById(id);

            if (mascota != null) {
                System.out.println("Mascota encontrada: " + mascota);
            } else {
                System.err.println("ID inexistente: No se encontro la mascota activa con el ID " + id);
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida: El ID debe ser un numero.");
        }
    }

    private void listarMascotas() throws ServiceException {
        System.out.println("LISTANDO MASCOTAS ACTIVAS:");
        List<Mascota> mascotas = mascotaService.getAll();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas activas para mostrar.");
            return;
        }
        mascotas.forEach(System.out::println);
    }

    private void actualizarMascota() throws ServiceException {
        System.out.println("ACTUALIZANDO MASCOTA:");
        try {
            System.out.print("Ingrese el ID de la mascota a actualizar: ");
            Long id = leerLong();

            Mascota mascota = mascotaService.getById(id);
            if (mascota == null) {
                System.err.println("ID inexistente. No se encontro la mascota activa con ID " + id);
                return;
            }

            System.out.println("Mascota actual: " + mascota);

            System.out.print("Nuevo Nombre: " + mascota.getNombre());
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) mascota.setNombre(nuevoNombre);

            System.out.print("Nueva Especie: " + mascota.getEspecie());
            String nuevaEspecie = scanner.nextLine();
            if (!nuevaEspecie.trim().isEmpty()) mascota.setEspecie(nuevaEspecie);

            System.out.print("Nueva Raza: " + mascota.getRaza());
            String nuevaRaza = scanner.nextLine();
            if (!nuevaRaza.trim().isEmpty()) mascota.setRaza(nuevaRaza);

            System.out.print("Nuevo Dueno: " + mascota.getDuenio());
            String nuevoDuenio = scanner.nextLine();
            if (!nuevoDuenio.trim().isEmpty()) mascota.setDuenio(nuevoDuenio);

            LocalDate nuevaFechaNac = leerFecha("Nueva Fecha de Nacimiento (YYYY-MM-DD, dejar vacio para no cambiar): ");
            if (nuevaFechaNac != null) mascota.setFechaNacimiento(nuevaFechaNac);
            
            mascotaService.actualizar(mascota);
            System.out.println("Mascota con ID " + id + " actualizada.");

        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida: El ID debe ser un numero.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validacion: " + e.getMessage());
        }
    }

    private void eliminarMascota() throws ServiceException {
        System.out.println("ELIMINANDO MASCOTA:");
        try {
            System.out.print("Ingrese el ID de la mascota a eliminar: ");
            Long id = leerLong();

            mascotaService.eliminar(id);
            System.out.println("Mascota con ID " + id + " eliminada.");
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida: El ID debe ser un numero.");
        }
    }

    // CRUD MICROCHIP

    private void menuCrudMicrochip() {
        int opcion;
        do {
            System.out.println("----------------------------------");
            System.out.println("1 - Crear Microchip");
            System.out.println("2 - Leer Microchip por su ID");
            System.out.println("3 - Listar Microchips Activos");
            System.out.println("4 - Actualizar Microchip");
            System.out.println("5 - Eliminar Microchip");
            System.out.println("0 - Volver al Menu Principal");
            System.out.println("----------------------------------");
            opcion = leerOpcion();
            scanner.nextLine(); 

            try {
                switch (opcion) {
                    case 1: crearMicrochip(); break;
                    case 2: leerMicrochipPorId(); break;
                    case 3: listarMicrochips(); break;
                    case 4: actualizarMicrochip(); break;
                    case 5: eliminarMicrochip(); break;
                    case 0: System.out.println("Volviendo al menu principal."); break;
                    default: System.err.println("Opcion invalida.");
                }
            } catch (ServiceException | IllegalArgumentException e) {
                System.err.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void crearMicrochip() throws ServiceException {
        System.out.println("CREANDO MICROCHIP:");
        try {
            Microchip microchip = new Microchip();
            System.out.print("Codigo (obligatorio y debe ser unico): ");
            microchip.setCodigo(scanner.nextLine().toUpperCase()); 
            System.out.print("Veterinaria (opcional): ");
            microchip.setVeterinaria(scanner.nextLine());
            System.out.print("Observaciones (opcional): ");
            microchip.setObservaciones(scanner.nextLine());
            microchip.setFechaImplementacion(leerFecha("Fecha de Implantacion (YYYY-MM-DD, opcional, default hoy)"));

            microchipService.insertar(microchip);
            System.out.println("Microchip creado con ID: " + microchip.getId());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validacion: " + e.getMessage());
        }
    }

    private void leerMicrochipPorId() throws ServiceException {
        System.out.println("LEYENDO MICROCHIP POR ID:");
        try {
            System.out.print("Ingrese ID del microchip: ");
            Long id = leerLong();

            Microchip mc = microchipService.getById(id);

            if (mc != null) {
                System.out.println("Microchip encontrado: " + mc);
            } else {
                System.err.println("ID inexistente. No se encontro el microchip activo con el ID " + id);
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida. El ID debe ser un numero.");
        }
    }

    private void listarMicrochips() throws ServiceException {
        System.out.println("LISTANDO MICROCHIPS ACTIVOS:");
        List<Microchip> microchips = microchipService.getAll();
        if (microchips.isEmpty()) {
            System.out.println("No hay microchips activos para mostrar.");
            return;
        }
        microchips.forEach(System.out::println);
    }

    private void actualizarMicrochip() throws ServiceException {
        System.out.println("ACTUALIZANDO MICROCHIP:");
        try {
            System.out.print("Ingrese ID del microchip a actualizar: ");
            Long id = leerLong();

            Microchip mc = microchipService.getById(id);
            if (mc == null) {
                System.err.println("ID inexistente. No se encontro el microchip activo con ID " + id);
                return;
            }

            System.out.println("Microchip actual: " + mc);

            System.out.print("Nuevo Codigo (obligatorio y debe ser unico): " + mc.getCodigo());
            String nuevoCodigo = scanner.nextLine().toUpperCase();
            if (!nuevoCodigo.trim().isEmpty()) mc.setCodigo(nuevoCodigo);

            System.out.print("Nueva Veterinaria: " + (mc.getVeterinaria() != null ? mc.getVeterinaria() : ""));
            String nuevaVeterinaria = scanner.nextLine();
            if (!nuevaVeterinaria.trim().isEmpty() || (mc.getVeterinaria() != null && nuevaVeterinaria.isEmpty())) {
                 mc.setVeterinaria(nuevaVeterinaria); 
            }

            System.out.print("Nuevas Observaciones: " + (mc.getObservaciones() != null ? mc.getObservaciones() : ""));
            String nuevasObservaciones = scanner.nextLine();
            if (!nuevasObservaciones.trim().isEmpty() || (mc.getObservaciones() != null && nuevasObservaciones.isEmpty())) {
                mc.setObservaciones(nuevasObservaciones); 
            }

            LocalDate nuevaFechaImpl = leerFecha("Nueva Fecha de Implantacion (YYYY-MM-DD, dejar vacio para no cambiar): ");
            if (nuevaFechaImpl != null) mc.setFechaImplementacion(nuevaFechaImpl);
            
            microchipService.actualizar(mc);
            System.out.println("Microchip con ID " + id + " actualizado.");

        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida. El ID debe ser un numero.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validacion: " + e.getMessage());
        }
    }

    private void eliminarMicrochip() throws ServiceException {
        System.out.println("ELIMINANDO MICROCHIP:");
        try {
            System.out.print("Ingrese ID del microchip a eliminar: ");
            Long id = leerLong();

            microchipService.eliminar(id);
            System.out.println("Microchip con ID " + id + " eliminado.");
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida. El ID debe ser un numero.");
        }
    }
    
    // METODOS DE RELACION Y BUSQUEDA

    private void asignarMicrochipAMascota() {
        System.out.println("ASIGNANDO MICROCHIP A MASCOTA:");
        try {
            System.out.print("Ingrese el ID de la Mascota a asociar: ");
            Long mascotaId = leerLong();
            System.out.print("Ingrese el ID del Microchip a asignar: ");
            Long microchipId = leerLong();

            mascotaService.asignarMicrochip(mascotaId, microchipId);
            System.out.println("Microchip de ID " + microchipId + " asignado a Mascota de ID " + mascotaId);
            
        } catch (InputMismatchException e) {
            System.err.println("Entrada invalida. Ambos IDs deben ser numeros.");
        } catch (ServiceException e) {
            System.err.println("ERROR DE ASIGNACION: " + e.getMessage());
        }
    }
    
    private void crearMascotaYMicrochip() {
        System.out.println("CREAR MASCOTA + MICROCHIP (TRANSACCION):");
        try {
            Mascota mascota = new Mascota();
            System.out.print("Nombre de la Mascota (obligatorio): ");
            mascota.setNombre(scanner.nextLine());
            System.out.print("Especie de la Mascota (obligatorio): ");
            mascota.setEspecie(scanner.nextLine());
            System.out.print("Dueno de la Mascota (obligatorio): ");
            mascota.setDuenio(scanner.nextLine());
            
            Microchip microchip = new Microchip();
            System.out.print("Codigo de Microchip (obligatorio y debe ser unico): ");
            microchip.setCodigo(scanner.nextLine().toUpperCase());
            
            System.out.print("Raza de la Mascota (opcional): ");
            mascota.setRaza(scanner.nextLine());
            System.out.print("Veterinaria del Microchip (opcional): ");
            microchip.setVeterinaria(scanner.nextLine());
            
            mascota.setFechaNacimiento(leerFecha("Fecha de Nacimiento de la Mascota (YYYY-MM-DD, opcional)"));
            microchip.setFechaImplementacion(leerFecha("Fecha de Implantacion del Microchip (YYYY-MM-DD, opcional, default hoy)"));

            mascotaService.crearMascotaConMicrochip(mascota, microchip);
            
            System.out.println("Mascota: " + mascota.getId() + " y  Microchip: " + microchip.getId() + "creados.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validacion: " + e.getMessage());
        } catch (ServiceException e) {
            System.err.println("ERROR TRANSACCIONAL: " + e.getMessage());
        }
    }
    
    private void buscarMascotaPorCodigoMicrochip() {
        System.out.println("BUSQUEDA POR CODIGO DEL MICROCHIP:");
        try {
            System.out.print("Ingrese el Codigo del Microchip (por ejemplo ABC1234): ");
            String codigo = scanner.nextLine().toUpperCase(); 

            Microchip microchip = microchipService.getByCodigo(codigo); 
            
            if (microchip == null) {
                System.err.println("No se encontro un Microchip activo con el codigo " + codigo);
                return;
            }
            
            Mascota mascota = mascotaService.getById(microchip.getId()); 
            
            if (mascota != null && mascota.getMicrochip() != null && mascota.getMicrochip().getId().equals(microchip.getId())) {
                 System.out.println("Mascota encontrada: " + mascota);
            } else {
                 System.out.println("El Microchip fue encontrado, pero no esta asignado a una mascota activa.");
            }
            
        } catch (ServiceException e) {
            System.err.println("ERROR EN BUSQUEDA: " + e.getMessage());
        }
        
    }
    
    // METODOS AUX

    private LocalDate leerFecha(String prompt) {
        System.out.print(prompt + " (dejar vacio para usar el valor por defecto): ");
        String fechaStr = scanner.nextLine();
        if (fechaStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha invalido (debe ser YYYY-MM-DD). Usando el valor por defecto.");
            return null; 
        }
    }
    
    private Long leerLong() throws InputMismatchException {
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        return id;
    }

}