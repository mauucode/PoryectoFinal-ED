import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una transacción de pago realizada por un residente.
 * Ahora incluye fecha y hora para un registro preciso.
 */
public class Pago {
    double monto;
    LocalDateTime timestamp; // Cambiado de LocalDate a LocalDateTime para incluir la hora
    Residente residenteAsignado;

    /**
     * Constructor para pagos reconocidos, asociados a un residente.
     * @param monto El monto del pago.
     * @param timestamp La fecha y hora exactas en que se realizó el pago.
     * @param residenteAsignado El residente que paga.
     */
    public Pago(double monto, LocalDateTime timestamp, Residente residenteAsignado) {
        this.monto = monto;
        this.timestamp = timestamp;
        this.residenteAsignado = residenteAsignado;
    }

    @Override
    public String toString() {
        // Formateador para una presentación profesional de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = timestamp.format(formatter);

        // Se asegura de que el residente no sea nulo antes de acceder a sus datos
        String infoResidente = "No asignado";
        if (residenteAsignado != null) {
            infoResidente = "ID: " + residenteAsignado.id + " (" + residenteAsignado.nombre + ")";
        }

        return String.format("[%s] | %-30s | Monto: $%,.2f",
                fechaHoraFormateada,
                infoResidente,
                monto);
    }
}