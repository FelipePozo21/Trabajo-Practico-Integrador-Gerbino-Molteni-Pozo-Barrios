package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase que representa una Mascota.
 * Relacion unidireccional con Microchip.
 */
public class Mascota {
    
    // Atributos
    private Long id;
    private Boolean eliminado;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private String duenio;
    private Microchip microchip;
    
    /**
     * Constructor completo de la clase Mascota.
     * 
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param especie Especie a la que pertenece
     * @param raza Raza de la mascota (puede ser null)
     * @param duenio Dueño de la mascota
     * @param fechaNacimiento FechaNacimiento de la mascota (puede ser null)
     * @throws IllegalArgumentException Si algún parámetro obligatorio es nulo o vacío
     */
    public Mascota(Long id, String nombre, String especie, String raza, String duenio, LocalDate fechaNacimiento) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        
        validarParametroString(nombre, "Nombre");
        validarParametroString(especie, "Especie");
        validarParametroString(duenio, "Duenio");

        
        this.id = id;
        this.eliminado = false;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = validarParametroRaza(raza);
        this.duenio = duenio;
        this.fechaNacimiento = validarParametroDate(fechaNacimiento);
    }
    
    public Mascota() {
        this.eliminado = false;
    }
    
    /**
     * Valida si un String es nulo o está vacío.
     * 
     * @param valor El String a validar
     * @param nombre El nombre del parámetro
     * @throws IllegalArgumentException Si la cadena es nula o está vacía
     */
    private void validarParametroString(String valor, String nombre) {
        if (valor == null) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo");
        }
        if (valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " no puede estar vacio");
        }
    }
    /**
     * Si el valor de raza es null o vacio retorna un valor por defecto
     * 
     * @param raza El String a validar
     */
    private String validarParametroRaza(String raza) {
      if (raza == null || raza.trim().isEmpty()) {
        return "Sin especificar";
      }
      return raza;
    }
    
    /**
      * Valida la fecha de nacimiento, asigna valor default en caso de ser null.
      * @param fechaNacimiento Objeto {@link LocalDate} que representa la fecha de nacimiento.
      *                        Se crea normalmente con LocalDate.of(anio, mes, dia),
      *                        por ejemplo: LocalDate.of(2020, 5, 15).
      */
    private LocalDate validarParametroDate(LocalDate fechaNacimiento){
      if ( fechaNacimiento == null ){
        return LocalDate.now();
      }
      if ( fechaNacimiento.isAfter(LocalDate.now()) ){
        throw new IllegalArgumentException("La fecha de nacimiento no puede ser un valor futuro");
      }
      return fechaNacimiento;
    }
    
    // Getters
    
    public Long getId() {
        return id;
    }
    
    public Boolean isEliminado() {
        return eliminado;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEspecie() {
        return especie;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public String getDuenio() {
        return duenio;
    }
    
    public Microchip getMicrochip() {
        return microchip;
    }
    
    // Setters
    
    /**
     * Establece el id de la mascota.
     * Usado principalmente por el DAO después de insertar.
     * 
     * @param id Nuevo id
     */
    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        this.id = id;
    }
    
    /**
     * Establece el estado de eliminado (baja lógica).
     * 
     * @param eliminado Nuevo estado
     * @throws IllegalArgumentException Si el parámetro es nulo
     */
    public void setEliminado(Boolean eliminado) {  
        if (eliminado == null) {
            throw new IllegalArgumentException("El estado eliminado no puede ser null");
        }
        
        if (!Objects.equals(this.eliminado, eliminado)) {
            this.eliminado = eliminado;
        }
    }
    
    /**
     * Establece la especie de la mascota.
     * 
     * @param especie Nueva especie
     */
    public void setEspecie(String especie) {
        validarParametroString(especie, "Especie");
        
        if (!Objects.equals(this.especie, especie)) {
            this.especie = especie;
        }
    }
    
    /**
     * Establece el nombre de la mascota.
     * 
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        validarParametroString(nombre, "Nombre");
        
        if (!Objects.equals(this.nombre, nombre)) {
            this.nombre = nombre;
        }
    }
    
    /** 
     * Establece un cambio de la raza
     * Este medoto sirve para cuando hay un error al ingresar los datos
     * @param raza Valor de la raza corregida de la mascota
     */
    public void setRaza(String raza) {
        String nuevaRaza = validarParametroRaza(raza);
        if (!Objects.equals(this.raza, nuevaRaza)) {
            this.raza = nuevaRaza;
        }
    }
    
    /**
     * Establece la fecha de nacimiento de la mascota.
     * 
     * @param fechaNacimiento Objeto {@link LocalDate} que representa la fecha de nacimiento.
     *                        Se crea normalmente con LocalDate.of(anio, mes, dia),
     *                        por ejemplo: LocalDate.of(2020, 5, 15).
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
      LocalDate nuevaFecha = validarParametroDate(fechaNacimiento);
      if (!Objects.equals(this.fechaNacimiento, nuevaFecha)) {
      this.fechaNacimiento = nuevaFecha;
      }
    }
    
    /**
     * Establece el dueño de la mascota.
     * 
     * @param duenio Nuevo dueño
     */
    public void setDuenio(String duenio) {
        validarParametroString(duenio, "Duenio");
        
        if (!Objects.equals(this.duenio, duenio)) {
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
                ", eliminado=" + eliminado +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", duenio='" + duenio + '\'' +
                ", microchip=" + (microchip != null ? microchip.getCodigo() : "SIN MICROCHIP") +
                '}';
    }
}