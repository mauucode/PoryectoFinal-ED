public class Condominio {
    String nombre;
    String direccion;

    public Condominio(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Condominio: " + nombre + " (" + direccion + ")";
    }
}
