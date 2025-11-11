package trabajointegrador;

// Importamos las dependencias necesarios
import java.time.LocalDate;
import java.util.Objects;

public class Microchip {
    // Atributos - Visibilidad privada para el encapsulamiento
    private Long id;
    private Boolean eliminado;
    private String codigo;
    private java.time.LocalDate fechaImplementacion;
    private String veterinaria;
    private String observaciones;
    
    /** 
     * Constructor completo de la clase Microchip
     * @param id Identificador unico del microchip
     * @param codigo Codigo del microchip
     * @param veterinaria Nombre de la veterinaria
     * @param observaciones Observaciones del estado de la mascota
     * eliminado Boolean que indica si el chip fue eliminado, inicializado en falso
     * fechaImplementacion Utiliza java.time para inicializar la fecha de implementacion
     * @throws IllegalArgumentException Si el id es nulo
    */ 
    
    public Microchip(Long id, String codigo, String veterinaria, String observaciones) {
        if(id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        
        // Uso del metodo privado para validar los parametros del tipo String
        validarParametroString(codigo, "Codigo");
        validarParametroString(veterinaria, "Veterinaria");
        validarParametroString(observaciones, "Observaciones");
        
        this.id = id;
        this.eliminado = false;
        this.codigo = codigo;
        this.veterinaria = veterinaria;
        this.observaciones = observaciones;
        
        // inicializacion de la variable fechaImplementacion
        fechaImplementacion = LocalDate.now();
    }

    /** 
      * Constructor vacio para la inicializacion basica   
     */
    public Microchip() {}
    
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
     * @return El id del microchip con un valor Long
     */
    public Long getId() {
        return id;
    }

    /** 
     * @return Retorna el estado del microchip
     */
    public Boolean isEliminado() {
        return eliminado;
    }

    /** 
     * @return Retorna el codigo del microchip
     */
    public String getCodigo() {
        return codigo;
    }

    /** 
     * @return Retorna la fecha de implementacion
     */
    public LocalDate getFechaImplementacion() {
        return fechaImplementacion;
    }

    /** 
     * @return Retorna el nombre de la veterinaria
     */
    public String getVeterinaria() {
        return veterinaria;
    }

    
    /** 
     * @return Retorna las observaciones del animal
     */
    public String getObservaciones() {
        return observaciones;
    }

    // Setters
    
    /** 
     * Establece el estado del Boolean eliminado
     * Este metodo verifica el parametro enviado, si es diferente al de la variable, esta cambia de estado
     * @param eliminado Parametro enviado para cambiar el estado de la variable
     * @throws IllegalArgumentException Si el parametros eliminado es nulo
     */
    public void isEliminado(Boolean eliminado) {
        if (eliminado == null) {
           throw new IllegalArgumentException("Eliminado no puede ser null");
        }
        
        if(!Objects.equals(this.eliminado, eliminado)) {
            this.eliminado = eliminado;
        }
    }

    /** 
     * Establece un cambio de codigo
     * @param codigo Valor del nuevo codigo
     */
    public void setCodigo(String codigo) {
        validarParametroString(codigo, "Codigo");
        
         // Verificacion de eliminado, usa Objects.equals, la cual es una buena practica en java
        if(!Objects.equals(this.codigo, codigo)) {
            this.codigo = codigo;
        }   
    }

    /** 
     * Establece una nueva fecha de implementacion
     * Este metodo sirve para cuando es necesario cambiar de chip
     * @param fechaImplementacion Valor LocalDate que actualiza la variable del mismo nombre
     * @throws IllegalArgumentException Si la fecha es nula o si es una fecha invalida
     */
    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        if (fechaImplementacion == null) {
            throw new IllegalArgumentException("fechaImplementacion no puede ser nulo");
        }
        
        if (fechaImplementacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        
        if(this.fechaImplementacion != fechaImplementacion) {
            this.fechaImplementacion = fechaImplementacion;
        }
    }

    /** 
     * Establece un cambio de veterinaria
     * Este metodo sirve para cuando la veterinaria cambio de nombre
     * Este metodo sirve para cuando cambien de microchip y de veterinaria
     * @param veterinaria El valor de la nueva veterinaria
     */
    public void setVeterinaria(String veterinaria) {
        validarParametroString(veterinaria, "Veterinaria");
        
        if(!this.veterinaria.equals(veterinaria)) {
            this.veterinaria = veterinaria;
        }
    }

    /** 
     * Establece un cambio de estado de la mascota
     * Este metodo sirve por si la mascota se enferma o se cura
     * @param observaciones Pametro que cambia el valor de la variable
     */
    public void setObservaciones(String observaciones) {
        validarParametroString(observaciones, "Observaciones");
        
        if(!this.observaciones.equals(observaciones)) {
            this.observaciones = observaciones;
        }
    }

    @Override
    public String toString() {
        return "Microchip{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", codigo=" + codigo +
                ", fechaImplementacion=" + fechaImplementacion +
                ", veterinaria=" + veterinaria +
                ", observaciones=" + observaciones +
                '}';
    }
}
