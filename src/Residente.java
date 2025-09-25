public class Residente {
    private static int nextId = 1;
    int id;
    String nombre;
    String departamento; // <-- CAMBIO AQUI
    String telefono;
    double saldo;

    public Residente(String nombre, String departamento, String telefono, double saldo) { // <-- Y AQUI
        this.id = nextId++;
        this.nombre = nombre;
        this.departamento = departamento; // <-- Y AQUI
        this.telefono = telefono;
        this.saldo = saldo;
    }
    
    public boolean tieneDeuda() {
        return this.saldo < 0;
    }

    // Getter para el nuevo campo
    public String getDepartamento() {
        return departamento;
    }

    @Override
    public String toString() {
        String estado;
        if (saldo < 0) {
            estado = "DEUDA DE $" + String.format("%.2f", Math.abs(saldo));
        } else if (saldo > 0) {
            estado = "SALDO A FAVOR de $" + String.format("%.2f", saldo);
        } else {
            estado = "AL CORRIENTE";
        }
        
        // Se actualiza "Unidad" por "Departamento"
        return "ID: " + id + " | Residente: " + nombre + " | Departamento: " + departamento + " | Tel: " + telefono + " | Estado: " + estado;
    }
}