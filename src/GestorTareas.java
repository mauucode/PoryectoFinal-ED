public class GestorTareas {
    public Cola<Tarea> tareasMantenimiento;
    public Cola<Tarea> tareasSeguridad;
    public Cola<Tarea> tareasAdministracion;

    public GestorTareas() {
        this.tareasMantenimiento = new Cola<>();
        this.tareasSeguridad = new Cola<>();
        this.tareasAdministracion = new Cola<>();
    }

    public void agregarTarea(Tarea tarea) {
        switch (tarea.departamento.toLowerCase()) {
            case "mantenimiento":
                tareasMantenimiento.enqueue(tarea);
                break;
            case "seguridad":
                tareasSeguridad.enqueue(tarea);
                break;
            case "administracion":
                tareasAdministracion.enqueue(tarea);
                break;
            default:
                System.out.println("Departamento no válido.");
        }
    }
}
