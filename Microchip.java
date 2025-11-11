package trabajointegrador;
import java.time.LocalDate;
import java.util.Objects;

public class Microchip {
    private Long id;
    private Boolean eliminado;
    private String codigo;
    private java.time.LocalDate fechaImplementacion;
    private String veterinaria;
    private String observaciones;
    
    public Microchip() {}
    
    public Microchip(Long id, String codigo, String veterinaria, String observaciones) {
        this.id = id;
        this.codigo = codigo;
        this.veterinaria = veterinaria;
        this.observaciones = observaciones;
        
        fechaImplementacion = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Boolean getEliminado() {
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

    public void isEliminado(Boolean eliminado) {
        if(!Objects.equals(this.eliminado, eliminado)) {
            this.eliminado = eliminado;
        }
    }

    public void setCodigo(String codigo) {
        if(codigo != null && !this.codigo.equals(codigo)) {
            this.codigo = codigo;
        }   
    }

    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        if(fechaImplementacion != null && this.fechaImplementacion != fechaImplementacion) {
            this.fechaImplementacion = fechaImplementacion;
        }
    }

    public void setVeterinaria(String veterinaria) {
        if(veterinaria != null && !this.veterinaria.equals(veterinaria)) {
            this.veterinaria = veterinaria;
        }
    }

    public void setObservaciones(String observaciones) {
        if(observaciones != null && !this.observaciones.equals(observaciones)) {
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
