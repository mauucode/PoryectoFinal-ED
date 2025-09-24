import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reporte {
    String titulo;
    LocalDate fecha;
    double totalIngresos;
    double totalEgresos;
    double balance;

    public Reporte(String titulo, double totalIngresos, double totalEgresos) {
        this.titulo = titulo;
        this.fecha = LocalDate.now();
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = totalIngresos - totalEgresos;
    }

    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n==================================================");
        System.out.println("      REPORTE FINANCIERO: " + titulo.toUpperCase());
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
        return "Reporte: '" + titulo + "' (Generado el " + fecha + ")";
    }
}