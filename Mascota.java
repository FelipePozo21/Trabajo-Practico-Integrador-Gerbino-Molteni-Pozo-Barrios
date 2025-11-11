package trabajointegrador;

// Importamos las dependencias necesarias
import java.time.LocalDate;
import java.util.Objects;

/** 
    * Clase que representa una Mascota
    * Clase unidireccional con Microchip
*/
public class Mascota {
    // Atributos - Visibilidad privada para la implementacion del encapsulamiento
    private Long id;
    private Boolean eliminado;
    private String nombre;
    private String especie;
    private String raza;
    private java.time.LocalDate fechaNacimiento;
    private String duenio;
    private Microchip microchip;
        
    /** 
      * Constructor completo de la clase Mascota
      * @param id Identificador unico de la mascota.
      * @param nombre Nombre de la mascota
      * @param especie Especie a la que pertenece
      * @param raza Raza de la mascota
      * @param duenio Duenio de la mascota
      * eliminado Boolean que indica si la mascota fue eliminada del sistema, false por defecto
      * fechaNacimiento Utiliza java.time para inicializar una fecha de nacimiento por defecto
      * @throws IllegalArgumentException Si el parametro id es nulo
     */
    public Mascota(Long id, String nombre, String especie, String raza, String duenio) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        
        // Uso de metodo privado para validar los parametros del tipo String
        validarParametroString(nombre, "Nombre");
        validarParametroString(especie, "Especie");
        validarParametroString(raza, "Raza");
        validarParametroString(duenio, "Duenio");

        this.id = id;
        this.eliminado = false;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.duenio = duenio;
        
        // Inicializacion de la variable fechaNacimiento
        fechaNacimiento = LocalDate.now();
    }

    /** 
      * Constructor vacio para la inicializacion basica   
     */
    public Mascota() {
        // lo piden en el pdf
        // esto lo deje vacio por si tienen que agregar algo a futuro, sino agregamos un sout simple
    }
    
    /** 
     * Valida si un String es nulo o esta vacio
     * @param valor El String a validar
     * @param nombre El nombre de la cadena
     * @throws IllegalArgumentException Si la cadena es nula o esta vacia
     */
    private void validarParametroString(String valor, String nombre) {
        if (valor == null) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo");
        }
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " no puede estar vacio");
        }
    }
    
    // Getters
    /** 
     * @return El id de la mascota como un valor Long
     */
    public Long getId() {
        return id;
    }

    /** 
     * @return Retorna el estado de la mascota en el sistema
     */
    public Boolean isEliminado() {
        return eliminado;
    }

    /** 
     * @return El nombre de la mascota con un String
     */
    public String getNombre() {
        return nombre;
    }

    /** 
     * @return La especie de la mascota con un valor String
     */
    public String getEspecie() {
        return especie;
    }

    /** 
     * @return La raza de la mascota con un valor String
     */
    public String getRaza() {
        return raza;
    }

    /** 
     * @return La fecha de nacimiento de la mascota con un valor java.time
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /** 
     * @return El duenio de la mascota con un valor String
     */
    public String getDuenio() {
        return duenio;
    }

    /** 
     * @return El microchip de la mascota 
     */
    public Microchip getMicrochip() {
        return microchip;
    }

    // Setters
    
    /** 
     * Establece el estado del Boolean eliminado
     * Este metodo verifica el parametro enviado, si es diferente al de la variable, esta cambia de estado
     * No es necesaria borrar la variable, solo cambie de estado
     * @param eliminado Valor enviado para cambiar el estado de la variable
     * @throws IllegalArgumentException Si el parametro eliminado es nulo
     */
    public void setEliminado(Boolean eliminado) {  
        if(eliminado == null) {
            throw new IllegalArgumentException("El estado eliminado no puede ser null");
        }
        
        // Verificacion de eliminado, usa Objects.equals, la cual es una buena practica en java
        if(!Objects.equals(this.eliminado, eliminado)) {
            this.eliminado = eliminado;
        }
    }

    /** 
     * Establece un cambio de especie
     * Este medoto sirve para cuando hay un error al ingresar los datos
     * @param especie Valor de la especie corregida de la mascota
     */
    public void setEspecie(String especie) {
        validarParametroString(especie, "Especie");
        
        if(!Objects.equals(this.especie, especie)) {
            this.especie = especie;
        }
    }
    
     /** 
     * Establece el cambio de nombre de la mascota
     * Este medoto sirve para cuando se da en adopcion, y el nuevo duenio le cambia el nombre
     * Este metodo sirve para cuando se escribe mal el nombre
     * @param nombre Valor del nuevo nombre
     */
    public void setNombre(String nombre){
        validarParametroString(nombre, "Nombre");
        
        if(!Objects.equals(this.nombre, nombre)) {
            this.nombre = nombre;
        }
    }

    /** 
     * Establece un cambio de la raza
     * Este medoto sirve para cuando hay un error al ingresar los datos
     * @param raza Valor de la raza corregida de la mascota
     */
    public void setRaza(String raza) {
        validarParametroString(raza, "Raza");
        
        if(!Objects.equals(this.raza, raza)) {
            this.raza = raza;
        }
    }

    /** 
     * Establece un cambio de duenio
     * Este medoto se usaria cuando la mascota se da en adopcion
     * @param duenio Valor del nuevo duenio
     */
    public void setDuenio(String duenio) {
        validarParametroString(duenio, "Duenio");
        
        if(!Objects.equals(this.duenio, duenio)) {
            this.duenio = duenio;
        }
    }
    
     /** 
     * Establece una asociacion unidireccional con Microchip
     * Cada mascota esta asociada a un microchip
     * Este metodo verifica que el microchip enviado no sea null ni sea el mismo del que esta asignado
     * @param m Valor del microchip
     */
    public void setMicrochip(Microchip m) {
        if (m != microchip && m != null) {
            this.microchip = m;
        }
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", eliminado=" +eliminado +
                ", nombre=" + nombre +
                ", especie=" + especie +
                ", raza=" + raza +
                ", fechaNacimiento=" + fechaNacimiento +
                ", duenio=" + duenio +
                ", microchip=" + microchip +
                '}';
    }
}
