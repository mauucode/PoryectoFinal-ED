import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tarea implements Comparable<Tarea> {
    private static int nextId = 1;
    int id;
    String descripcion;
    String departamento;
    Urgencia urgencia;
    double costo;
    boolean completada;
    LocalDate fecha;
    private List<Integer> idPrerrequisitos; // <-- NUEVO CAMPO

    public Tarea(String descripcion, String departamento, Urgencia urgencia, double costo, LocalDate fecha) {
        this.id = nextId++;
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.urgencia = urgencia;
        this.costo = costo;
        this.completada = false;
        this.fecha = fecha;
        this.idPrerrequisitos = new ArrayList<>(); // Se inicializa la lista
    }

    // --- Métodos de la clase ---
    public void completar() { this.completada = true; }
    public boolean estaCompletada() { return completada; }
    public double getCosto() { return costo; }
    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }

    // --- Nuevos métodos para gestionar las dependencias ---
    public void agregarPrerrequisito(int idTarea) {
        this.idPrerrequisitos.add(idTarea);
    }

    public List<Integer> getPrerrequisitos() {
        return this.idPrerrequisitos;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fecha.format(formatter);
        String estado = completada ? "COMPLETADA" : "PENDIENTE";
        String dependencias = idPrerrequisitos.isEmpty() ? "" : " | Prerrequisitos: " + idPrerrequisitos;
        
        return "ID: " + id + " | Depto: " + departamento + " | Urgencia: " + urgencia +
               " | Fecha: " + fechaFormateada + " | Costo: $" + String.format("%.2f", costo) +
               " | Estado: " + estado + dependencias + " | Desc: " + descripcion;
    }

    @Override
    public int compareTo(Tarea otra) {
        int comparacionUrgencia = this.urgencia.compareTo(otra.urgencia);
        if (comparacionUrgencia != 0) {
            return comparacionUrgencia;
        }
        return this.fecha.compareTo(otra.fecha);
    }
}