package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Microchip {
    private Long id;
    private Boolean eliminado;
    private String codigo;
    private LocalDate fechaImplementacion;
    private String veterinaria;
    private String observaciones;
    
    /**
     * Constructor completo.
     * @param id Identificador unico (puede ser null para nuevos registros)
     * @param codigo Código del microchip (obligatorio)
     * @param veterinaria Nombre de la veterinaria (opcional)
     * @param observaciones Estado o comentarios (opcional)
     */
    public Microchip(Long id, String codigo, String veterinaria, String observaciones) {
        validarNotNull(codigo, "Codigo");
        
        this.id = id;
        this.eliminado = false;
        this.codigo = codigo;
        this.veterinaria = veterinaria;
        this.observaciones = observaciones;
        this.fechaImplementacion = LocalDate.now();
    }
    
    /**
     * Constructor vacio.
     */
    public Microchip() {
        this.eliminado = false;
    }
    
    /**
     * Valida que un String no sea null ni vacío (para campos obligatorios).
     */
    private void validarNotNull(String valor, String nombre) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo o vacío");
        }
    }
    
    /**
     * Valida que un String no esté vacío si no es null (para campos opcionales).
     */
    private void validarNotEmpty(String valor, String nombre) {
        if (valor != null && valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " no puede ser un campo vacío");
        }
    }
    
    // Getters
    
    public Long getId() { 
        return id; 
    }
    
    public Boolean isEliminado() { 
        return eliminado; 
    }
    
    public String getCodigo() { 
        return codigo; 
    }
    
    public LocalDate getFechaImplementacion() { 
        return fechaImplementacion; 
    }
    
    public String getVeterinaria() { 
        return veterinaria; 
    }
    
    public String getObservaciones() { 
        return observaciones; 
    }
    
    // Setters
    
    /**
     * Establece el ID (usado por el DAO despues de insertar).
     */
    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        this.id = id;
    }
    
    /**
     * Establece el estado de eliminado (baja logica).
     */
    public void setEliminado(Boolean eliminado) {
        if (eliminado == null) {
            throw new IllegalArgumentException("Eliminado no puede ser null");
        }
        if (!Objects.equals(this.eliminado, eliminado)) {
            this.eliminado = eliminado;
        }
    }
    
    /**
     * Establece el codigo del microchip (obligatorio).
     */
    public void setCodigo(String codigo) {
        validarNotNull(codigo, "Codigo");
        if (!Objects.equals(this.codigo, codigo)) {
            this.codigo = codigo;
        }
    }
    
    /**
     * Establece la fecha de implementacion.
     */
    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        /*
        if (fechaImplementacion == null) {
            throw new IllegalArgumentException("fechaImplementacion no puede ser nulo");
        }
        */
        if (fechaImplementacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        if (!Objects.equals(this.fechaImplementacion, fechaImplementacion)) {
            this.fechaImplementacion = fechaImplementacion;
        }
    }
    
    /**
     * Establece la veterinaria (opcional).
     */
    public void setVeterinaria(String veterinaria) {
        validarNotEmpty(veterinaria, "Veterinaria");
        if (!Objects.equals(this.veterinaria, veterinaria)) {
            this.veterinaria = veterinaria;
        }
    }
    
    /**
     * Establece las observacioness (opcional)
     */
    public void setObservaciones(String observaciones) {
        validarNotEmpty(observaciones, "Observaciones");
        if (!Objects.equals(this.observaciones, observaciones)) {
            this.observaciones = observaciones;
        }
    }
    
    @Override
    public String toString() {
        return "Microchip{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", codigo='" + codigo + '\'' +
                ", fechaImplementacion=" + fechaImplementacion +
                ", veterinaria='" + veterinaria + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}