import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Actividad {
    String descripcion;
    LocalDateTime timestamp;

    public Actividad(String descripcion) {
        this.descripcion = descripcion;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + timestamp.format(formatter) + "] " + descripcion;
    }
}