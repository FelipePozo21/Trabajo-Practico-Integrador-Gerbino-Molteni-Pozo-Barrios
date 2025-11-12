package model;

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
     * @param id Identificador único
     * @param codigo Código del microchip
     * @param veterinaria Nombre de la veterinaria
     * @param observaciones Estado o comentarios
     * @throws IllegalArgumentException si algún parámetro obligatorio es nulo o vacío
     */
    public Microchip(Long id, String codigo, String veterinaria, String observaciones) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        validarParametroString(codigo, "Codigo");
        validarParametroString(veterinaria, "Veterinaria");
        validarParametroString(observaciones, "Observaciones");
        
        this.id = id;
        this.eliminado = false;
        this.codigo = codigo;
        this.veterinaria = veterinaria;
        this.observaciones = observaciones;
        this.fechaImplementacion = LocalDate.now();
    }

    public Microchip() {}
    
    private void validarParametroString(String valor, String nombre) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo o vacío");
        }
    }
    
    // Getters
    public Long getId() { return id; }
    public Boolean isEliminado() { return eliminado; }
    public String getCodigo() { return codigo; }
    public LocalDate getFechaImplementacion() { return fechaImplementacion; }
    public String getVeterinaria() { return veterinaria; }
    public String getObservaciones() { return observaciones; }

    // Setters
    public void setEliminado(Boolean eliminado) {
        if (eliminado == null) throw new IllegalArgumentException("Eliminado no puede ser null");
        if (!Objects.equals(this.eliminado, eliminado)) this.eliminado = eliminado;
    }

    public void setCodigo(String codigo) {
        validarParametroString(codigo, "Codigo");
        if (!Objects.equals(this.codigo, codigo)) this.codigo = codigo;
    }

    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        if (fechaImplementacion == null)
            throw new IllegalArgumentException("fechaImplementacion no puede ser nulo");
        if (fechaImplementacion.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha no puede ser futura");
        if (this.fechaImplementacion != fechaImplementacion)
            this.fechaImplementacion = fechaImplementacion;
    }

    public void setVeterinaria(String veterinaria) {
        validarParametroString(veterinaria, "Veterinaria");
        if (!this.veterinaria.equals(veterinaria)) this.veterinaria = veterinaria;
    }

    public void setObservaciones(String observaciones) {
        validarParametroString(observaciones, "Observaciones");
        if (!this.observaciones.equals(observaciones)) this.observaciones = observaciones;
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
