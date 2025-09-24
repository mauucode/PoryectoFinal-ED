import java.time.LocalDate;

/**
 * Representa una transacción de pago realizada por un residente.
 */
public class Pago {
    double monto;
    LocalDate fecha;
    Residente residenteAsignado; // Residente que realizó el pago

    /**
     * Constructor para pagos reconocidos, asociados a un residente.
     * @param monto El monto del pago.
     * @param fecha La fecha en que se realizó el pago.
     * @param residenteAsignado El residente que paga.
     */
    public Pago(double monto, LocalDate fecha, Residente residenteAsignado) {
        this.monto = monto;
        this.fecha = fecha;
        this.residenteAsignado = residenteAsignado;
    }
    
    /**
     * Constructor alternativo para pagos que podrían no estar asignados inicialmente.
     * @param monto El monto del pago.
     * @param fecha La fecha en que se realizó el pago.
     */
    public Pago(double monto, LocalDate fecha) {
        this.monto = monto;
        this.fecha = fecha;
        this.residenteAsignado = null;
    }

    @Override
    public String toString() {
        String asignado = residenteAsignado != null ? "Asignado a: " + residenteAsignado.nombre : "NO RECONOCIDO";
        return "Monto: $" + String.format("%.2f", monto) + " | Fecha: " + fecha + " | Estado: " + asignado;
    }
}