public class Solicitud {
    public int id;
    public String legajo;
    public String tipo;
    public int prioridad;
    public String estado;
    public String descripcion;
    public String fecha;
    public int idResponsable;
    public String observaciones;

    public Solicitud(int id, String legajo, String tipo, int prioridad, String estado,
                     String descripcion, String fecha, int idResponsable, String observaciones) {
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

    public static Solicitud crearSolicitud(DatosSolicitud datos) {
        return new Solicitud(
            datos.id,
            datos.legajo,
            datos.tipo,
            datos.prioridad,
            datos.estado,
            datos.descripcion,
            datos.fecha,
            datos.idResponsable,
            datos.observaciones
        );
    }

}
