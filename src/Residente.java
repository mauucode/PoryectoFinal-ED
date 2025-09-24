public class Residente {
    private static int nextId = 1;
    int id;
    String nombre;
    String unidad; // Ej. "A-101"
    String telefono;
    double saldo; // Positivo es saldo a favor, negativo es deuda

    public Residente(String nombre, String unidad, String telefono, double saldo) {
        this.id = nextId++;
        this.nombre = nombre;
        this.unidad = unidad;
        this.telefono = telefono;
        this.saldo = saldo;
    }
    
    public boolean tieneDeuda() {
        return this.saldo < 0;
    }

    @Override
    public String toString() {
        String estado;
        if (saldo < 0) {
            // Usa Math.abs para mostrar la deuda como un número positivo
            estado = "DEUDA DE $" + String.format("%.2f", Math.abs(saldo));
        } else if (saldo > 0) {
            estado = "SALDO A FAVOR de $" + String.format("%.2f", saldo);
        } else {
            estado = "AL CORRIENTE";
        }
        
        return "ID: " + id + " | Residente: " + nombre + " | Unidad: " + unidad + " | Tel: " + telefono + " | Estado: " + estado;
    }
}