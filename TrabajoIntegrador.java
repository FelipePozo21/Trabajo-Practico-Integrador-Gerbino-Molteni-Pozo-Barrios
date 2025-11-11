package trabajointegrador;
import java.time.LocalDate;
public class TrabajoIntegrador {
    public static void main(String[] args) {
        Microchip microchip1 = new Microchip(10000000000l, "Codigo1", "Veterinaria1", "Saludable");
        Microchip microchip2 = new Microchip(20000000000l, "Codigo2", "Veterinaria2", "Enfermo");
        Microchip microchip3 = new Microchip(30000000000l, "Codigo3", "Veterinaria3", "Saludable");
        Microchip microchip4 = new Microchip(40000000000l, "Codigo4", "Veterinaria4", "Enfermo");
        Microchip microchip5 = new Microchip(50000000000l, "Codigo5", "Veterinaria5", "Saludable");
        
        System.out.println(microchip1.getId());
        System.out.println(microchip2.getCodigo());
        System.out.println(microchip3.getFechaImplementacion());
        System.out.println(microchip4.getObservaciones());
        System.out.println(microchip5.getVeterinaria());
        
        microchip1.setEliminado(true);
        microchip2.setFechaImplementacion(LocalDate.of(2024,12,5));

        microchip4.setVeterinaria("veterinaria42");
        microchip5.setObservaciones("Enfermo");
        
        System.out.println(microchip1.isEliminado());
        System.out.println(microchip2.getFechaImplementacion());
        System.out.println(microchip3.getObservaciones());
        System.out.println(microchip4.getVeterinaria());
        System.out.println(microchip5.getObservaciones());
        
        Mascota mascota1 = new Mascota(11000000000l, "Mascota1", "Perro", "Bully", "Juana");
        Mascota mascota2 = new Mascota(21000000000l, "Mascota2", "Perro", "Pug", "Carla");
        Mascota mascota3 = new Mascota(31000000000l, "Mascota3", "Gato", "Siames", "Carlos");
        Mascota mascota4 = new Mascota(41000000000l, "Mascota4", "Perro", "Boxer", "Bruno");
        Mascota mascota5 = new Mascota(51000000000l, "Mascota5", "Gato", "Esfinge", "Matias");
        
        mascota1.setMicrochip(microchip1);
        mascota2.setMicrochip(microchip2);
        mascota3.setMicrochip(microchip3);
        mascota4.setMicrochip(microchip4);
        mascota5.setMicrochip(microchip5);
        
        mascota1.setDuenio("Natalia");
        mascota2.setEliminado(false);
        mascota3.setRaza("Naranja");
        mascota4.setEspecie("Loro");
        mascota5.setEspecie("Perro");
        
        System.out.println(mascota1);
        System.out.println(mascota2);
        System.out.println(mascota3);
        System.out.println(mascota4);
        System.out.println(mascota5);
    }
    
}
