public class DatosSolicitud {
    public final int id;
    public final String legajo;
    public final String tipo;
    public final int prioridad;
    public final String estado;
    public final String descripcion;
    public final String fecha;
    public final int idResponsable;
    public final String observaciones;

    public DatosSolicitud(
            int id, String legajo, String tipo,
            int prioridad, String estado, String descripcion,
            String fecha, int idResponsable, String observaciones) {

        this.id = id;
        this.legajo = legajo;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idResponsable = idResponsable;
        this.observaciones = observaciones;
    }
}