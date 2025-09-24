enum Urgencia { ALTA, MEDIA, BAJA }

public class Tarea {
    private static int nextId = 1;
    int id;
    String descripcion;
    String departamento; // Mantenimiento, Seguridad, Administración
    Urgencia urgencia;
    double costo; // Costo asociado a la realización de la tarea
    boolean completada; // Estado de la tarea

    public Tarea(String descripcion, String departamento, Urgencia urgencia, double costo) {
        this.id = nextId++;
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.urgencia = urgencia;
        this.costo = costo;
        this.completada = false; // Las tareas nuevas no están completadas
    }

    public void completar() {
        this.completada = true;
    }

    public boolean estaCompletada() {
        return completada;
    }

    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        String estado = completada ? "COMPLETADA" : "PENDIENTE";
        return "ID: " + id + " | Depto: " + departamento + " | Urgencia: " + urgencia +
               " | Costo: $" + String.format("%.2f", costo) + " | Estado: " + estado +
               " | Desc: " + descripcion;
    }
}