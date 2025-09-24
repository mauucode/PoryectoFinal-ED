import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reporte {
    private static int nextId = 1; // Contador estático para IDs únicos
    public int id; // ID único para cada reporte
    String titulo;
    LocalDate fecha;
    double totalIngresos;
    double totalEgresos;
    double balance;

    public Reporte(String titulo, double totalIngresos, double totalEgresos) {
        this.id = nextId++; // Asigna un ID único y lo incrementa
        this.titulo = titulo;
        this.fecha = LocalDate.now();
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = totalIngresos - totalEgresos;
    }

    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n==================================================");
        System.out.println("      REPORTE FINANCIERO (ID: " + this.id + ")");
        System.out.println("      TÍTULO: " + titulo.toUpperCase());
        System.out.println("==================================================");
        System.out.println("Fecha de Generación: " + fecha.format(formatter));
        System.out.println("--------------------------------------------------");
        System.out.println("  (+) INGRESOS TOTALES: $" + String.format("%,.2f", totalIngresos));
        System.out.println("  (-) EGRESOS TOTALES:  $" + String.format("%,.2f", totalEgresos));
        System.out.println("--------------------------------------------------");
        System.out.println("  (=) BALANCE GENERAL:  $" + String.format("%,.2f", balance));
        System.out.println("==================================================");
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + id + " | Título: '" + titulo + "' (Generado el " + fecha.format(formatter) + ")";
    }
}