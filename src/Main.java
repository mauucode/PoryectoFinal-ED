import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal que ejecuta el Sistema Integral de Administración Condominial.
 * Contiene el menú principal y la lógica para interactuar con el usuario.
 * Versión 2.0 con mejoras financieras, de gestión y de datos.
 */
public class Main {
    // --- ESTRUCTURAS DE DATOS PRINCIPALES ---
    private static ListaEnlazada<Condominio> condominios = new ListaEnlazada<>();
    private static ListaEnlazada<Residente> residentes = new ListaEnlazada<>();
    private static GestorTareas gestorTareas = new GestorTareas();
    private static ListaEnlazada<Reporte> reportes = new ListaEnlazada<>();
    private static Pila<Actividad> historialActividades = new Pila<>();
    private static ListaEnlazada<Pago> historialPagos = new ListaEnlazada<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosIniciales();
        mostrarBanner();
        
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1: enviarAvisosDePago(); break;
                case 2: generarReporteFinanciero(); break;
                case 3: menuPagosYCuotas(); break;
                case 4: menuGestionTareas(); break;
                case 5: menuGestionResidentes(); break;
                case 6: verHistorialActividades(); break;
                case 7: buscarResidentePorIdInterfaz(); break;
                case 8:
                    salir = true;
                    System.out.println("Gracias por usar el Sistema de CONDOMINIOS VITA S.A. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            if (!salir) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
            scanner.next();
            System.out.print("Seleccione una opción: ");
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    private static void mostrarBanner() {
        System.out.println("==================================================");
        System.out.println("     CONDOMINIOS VITA S.A.");
        System.out.println("   Sistema Integral de Gestión Condominial");
        System.out.println("==================================================");
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú del sistema:");
        System.out.println("1. 🔔 Enviar avisos de pago a residentes");
        System.out.println("2. 📈 Generar Reporte Financiero Actualizado");
        System.out.println("3. 💳 Gestión de Pagos y Cuotas");
        System.out.println("4. 🔧 Gestión de Tareas");
        System.out.println("5. 🏠 Gestión de Residentes");
        System.out.println("6. 📚 Ver historial de actividades del sistema");
        System.out.println("7. 🔍 Buscar residente por ID");
        System.out.println("8. 🚪 Salir del sistema");
        System.out.print("Seleccione una opción: ");
    }
    
    // --- OPCIONES DEL MENÚ PRINCIPAL ---

    private static void enviarAvisosDePago() {
        System.out.println("\n--- 🔔 Enviando Avisos de Pago ---");
        boolean hayDeudores = false;
        for (int i = 0; i < residentes.size(); i++) {
            Residente res = residentes.get(i);
            if (res.tieneDeuda()) {
                hayDeudores = true;
                System.out.println("---------------------------------------------");
                System.out.println("AVISO ENVIADO A: " + res.nombre + " (Unidad " + res.unidad + ")");
                // Extrae dinámicamente el estado de deuda del método toString()
                System.out.println("Estimado residente, le recordamos que tiene una " + res.toString().substring(res.toString().indexOf("DEUDA")));
                System.out.println("Favor de realizar su pago a la brevedad.");
            }
        }
        if (!hayDeudores) {
            System.out.println("¡Excelente! No hay residentes con deudas pendientes.");
        }
        historialActividades.push(new Actividad("Se enviaron avisos de pago automáticos."));
    }

    private static void generarReporteFinanciero() {
        System.out.println("\n--- 📈 Generando Reporte Financiero ---");
        System.out.print("Ingrese un título para este reporte (ej. 'Cierre de Septiembre 2025'): ");
        String titulo = scanner.nextLine();

        double totalIngresos = 0;
        for (int i = 0; i < historialPagos.size(); i++) {
            totalIngresos += historialPagos.get(i).monto;
        }

        double totalEgresos = 0;
        Nodo<Tarea> current = gestorTareas.tareasMantenimiento.getHead();
        while (current != null) { if (current.data.estaCompletada()) totalEgresos += current.data.getCosto(); current = current.next; }
        current = gestorTareas.tareasSeguridad.getHead();
        while (current != null) { if (current.data.estaCompletada()) totalEgresos += current.data.getCosto(); current = current.next; }
        current = gestorTareas.tareasAdministracion.getHead();
        while (current != null) { if (current.data.estaCompletada()) totalEgresos += current.data.getCosto(); current = current.next; }
        
        Reporte nuevoReporte = new Reporte(titulo, totalIngresos, totalEgresos);
        reportes.add(nuevoReporte);
        
        System.out.println("\n¡Reporte generado con éxito!");
        nuevoReporte.display();
        
        historialActividades.push(new Actividad("Generó el reporte financiero: " + titulo));
    }

    private static void menuPagosYCuotas() {
        System.out.println("\n--- 💳 Menú de Pagos y Cuotas ---");
        System.out.println("1. Registrar pago individual a un residente");
        System.out.println("2. Aplicar cuota de mantenimiento general ($800.00)");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int opcion = leerOpcion();

        switch (opcion) {
            case 1: registrarPagoIndividual(); break;
            case 2: aplicarCuotaGeneral(); break;
            case 3: break;
            default: System.out.println("Opción no válida.");
        }
    }

    private static void menuGestionTareas() {
        System.out.println("\n--- 🔧 Menú de Gestión de Tareas ---");
        System.out.println("1. Ver todas las tareas");
        System.out.println("2. Agregar nueva tarea");
        System.out.println("3. Marcar tarea como completada");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int opcion = leerOpcion();

        switch (opcion) {
            case 1: verTodasLasTareas(); break;
            case 2: agregarNuevaTarea(); break;
            case 3: marcarTareaComoCompletada(); break;
            case 4: break;
            default: System.out.println("Opción no válida.");
        }
    }
    
    private static void menuGestionResidentes() {
        System.out.println("\n--- 🏠 Menú de Gestión de Residentes ---");
        System.out.println("1. Ver lista de residentes");
        System.out.println("2. Agregar nuevo residente");
        System.out.println("3. Eliminar residente");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int opcion = leerOpcion();

        switch (opcion) {
            case 1: residentes.display(); break;
            case 2: agregarNuevoResidente(); break;
            case 3: eliminarResidente(); break;
            case 4: break;
            default: System.out.println("Opción no válida.");
        }
    }

    private static void verHistorialActividades() {
        System.out.println("\n--- 📚 Historial de Actividades del Sistema (LIFO) ---");
        historialActividades.display();
    }
    
    private static void buscarResidentePorIdInterfaz() {
        System.out.println("\n--- 🔍 Buscar Residente por ID ---");
        System.out.print("Ingrese el ID del residente a buscar: ");
        int id = leerOpcion();
        Residente res = buscarResidentePorId(id);
        if (res != null) {
            System.out.println("Residente encontrado:");
            System.out.println(res);
        } else {
            System.out.println("No se encontró ningún residente con el ID " + id + ".");
        }
        historialActividades.push(new Actividad("Buscó residente por ID: " + id));
    }

    // --- SUB-MENÚS Y LÓGICA AUXILIAR ---
    
    private static void registrarPagoIndividual() {
        System.out.println("\n--- Registrar Pago Individual ---");
        residentes.display();
        System.out.print("Ingrese el ID del residente para registrar un pago: ");
        int id = leerOpcion();
        Residente res = buscarResidentePorId(id);

        if (res != null) {
            System.out.print("Ingrese el monto del pago para " + res.nombre + ": ");
            double monto = scanner.nextDouble();
            scanner.nextLine();
            
            res.saldo += monto;
            historialPagos.add(new Pago(monto, LocalDate.now(), res));
            
            System.out.println("¡Pago registrado! Estado de cuenta actualizado:");
            System.out.println(res);

            historialActividades.push(new Actividad("Registró pago de $" + monto + " para residente ID " + id));
        } else {
            System.out.println("No se encontró un residente con ese ID.");
        }
    }

    private static void aplicarCuotaGeneral() {
        System.out.println("\n--- Aplicando Cuota de Mantenimiento General de $800.00 ---");
        double cuota = 800.00;
        for (int i = 0; i < residentes.size(); i++) {
            residentes.get(i).saldo -= cuota;
        }
        System.out.println("¡Cuota aplicada a todos los " + residentes.size() + " residentes con éxito!");
        historialActividades.push(new Actividad("Aplicó cuota general de $" + cuota));
    }

    private static void verTodasLasTareas() {
        System.out.println("\n--- Tareas de Mantenimiento ---");
        gestorTareas.tareasMantenimiento.display();
        System.out.println("\n--- Tareas de Seguridad ---");
        gestorTareas.tareasSeguridad.display();
        System.out.println("\n--- Tareas de Administración ---");
        gestorTareas.tareasAdministracion.display();
    }

    private static void agregarNuevaTarea() {
        System.out.println("\n--- Agregar Nueva Tarea ---");
        System.out.print("Descripción de la tarea: ");
        String desc = scanner.nextLine();
        System.out.print("Departamento (Mantenimiento, Seguridad, Administracion): ");
        String depto = scanner.nextLine();
        System.out.print("Urgencia (ALTA, MEDIA, BAJA): ");
        Urgencia urgencia = Urgencia.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Costo estimado de la tarea: ");
        double costo = scanner.nextDouble();
        scanner.nextLine();

        Tarea nuevaTarea = new Tarea(desc, depto, urgencia, costo);
        gestorTareas.agregarTarea(nuevaTarea);
        System.out.println("¡Nueva tarea agregada con éxito!");
        historialActividades.push(new Actividad("Agregó nueva tarea: " + desc));
    }
    
    private static void marcarTareaComoCompletada() {
        System.out.println("\n--- Marcar Tarea como Completada ---");
        verTodasLasTareas();
        System.out.print("Ingrese el ID de la tarea a marcar como completada: ");
        int id = leerOpcion();
        Tarea tarea = null;
        
        // Buscar la tarea en todas las colas
        Nodo<Tarea> current = gestorTareas.tareasMantenimiento.getHead();
        while(current != null) { if(current.data.id == id) { tarea = current.data; break; } current = current.next; }
        if (tarea == null) {
            current = gestorTareas.tareasSeguridad.getHead();
            while(current != null) { if(current.data.id == id) { tarea = current.data; break; } current = current.next; }
        }
        if (tarea == null) {
            current = gestorTareas.tareasAdministracion.getHead();
            while(current != null) { if(current.data.id == id) { tarea = current.data; break; } current = current.next; }
        }

        if (tarea != null) {
            tarea.completar();
            System.out.println("¡Tarea ID " + id + " marcada como completada! Su costo ahora contará como egreso.");
            historialActividades.push(new Actividad("Completó la tarea ID " + id));
        } else {
            System.out.println("No se encontró ninguna tarea con el ID " + id + ".");
        }
    }

    private static void agregarNuevoResidente() {
        System.out.println("\n--- Agregar Nuevo Residente ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Unidad (ej. A-101): ");
        String unidad = scanner.nextLine();
        System.out.print("Teléfono: ");
        String tel = scanner.nextLine();
        System.out.print("Saldo inicial (negativo para deuda, ej. -800): ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        residentes.add(new Residente(nombre, unidad, tel, saldo));
        System.out.println("¡Residente " + nombre + " agregado con éxito!");
        historialActividades.push(new Actividad("Agregó nuevo residente: " + nombre));
    }

    private static void eliminarResidente() {
        System.out.println("\n--- Eliminar Residente ---");
        residentes.display();
        System.out.print("Ingrese el ID del residente a eliminar: ");
        int id = leerOpcion();

        int indexParaEliminar = -1;
        for (int i = 0; i < residentes.size(); i++) {
            if (residentes.get(i).id == id) {
                indexParaEliminar = i;
                break;
            }
        }

        if (indexParaEliminar != -1) {
            String nombre = residentes.get(indexParaEliminar).nombre;
            residentes.removeAt(indexParaEliminar);
            System.out.println("¡Residente " + nombre + " eliminado con éxito!");
            historialActividades.push(new Actividad("Eliminó al residente: " + nombre));
        } else {
            System.out.println("No se encontró ningún residente con el ID " + id + ".");
        }
    }

    private static Residente buscarResidentePorId(int id) {
        for (int i = 0; i < residentes.size(); i++) {
            if (residentes.get(i).id == id) {
                return residentes.get(i);
            }
        }
        return null;
    }

    // --- CARGA DE DATOS MASIVA ---
    private static void cargarDatosIniciales() {
        condominios.add(new Condominio("Vita Park", "Av. Principal 123"));

        // Generar 50 residentes
        String[] nombres = {"Ana", "Luis", "Carla", "David", "Elena", "Fernando", "Gloria", "Hugo", "Irene", "Jorge"};
        String[] apellidos = {"Torres", "Morales", "Soto", "Reyes", "Garza", "Cruz", "Peña", "Jimenez", "Castillo", "Luna"};
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            String nombre = nombres[rand.nextInt(nombres.length)] + " " + apellidos[rand.nextInt(apellidos.length)];
            String unidad = (char)('A' + rand.nextInt(3)) + "-" + (101 + i);
            String telefono = "81" + (10000000 + rand.nextInt(90000000));
            double saldo = -800 + rand.nextInt(1000) - rand.nextInt(500); // Saldos variados
            residentes.add(new Residente(nombre, unidad, telefono, saldo));
        }

        // Generar 50 tareas
        String[] accionesMantenimiento = {"Reparar", "Pintar", "Limpiar", "Revisar", "Cambiar"};
        String[] objetosMantenimiento = {"fuga de agua", "pared del lobby", "alberca", "sistema de riego", "foco del pasillo"};
        String[] accionesSeguridad = {"Vigilar", "Monitorear", "Reportar", "Controlar acceso en", "Revisar"};
        String[] objetosSeguridad = {"entrada principal", "cámaras del Sector B", "vehículo sospechoso", "puerta de servicio", "bitácora"};
        String[] accionesAdmin = {"Elaborar", "Contactar a", "Archivar", "Programar junta", "Realizar pago de"};
        String[] objetosAdmin = {"reporte financiero", "proveedor de limpieza", "facturas de Julio", "con el comité", "servicios públicos"};
        
        for (int i = 0; i < 50; i++) {
            int tipo = rand.nextInt(3);
            Urgencia urg = Urgencia.values()[rand.nextInt(3)];
            double costo = 200 + rand.nextInt(2000);
            Tarea t;
            if (tipo == 0) { // Mantenimiento
                t = new Tarea(accionesMantenimiento[rand.nextInt(5)] + " " + objetosMantenimiento[rand.nextInt(5)], "Mantenimiento", urg, costo);
            } else if (tipo == 1) { // Seguridad
                t = new Tarea(accionesSeguridad[rand.nextInt(5)] + " " + objetosSeguridad[rand.nextInt(5)], "Seguridad", urg, costo);
            } else { // Administración
                t = new Tarea(accionesAdmin[rand.nextInt(5)] + " " + objetosAdmin[rand.nextInt(5)], "Administracion", urg, costo);
            }
            gestorTareas.agregarTarea(t);
        }

        // Marcar algunas tareas como completadas para que haya egresos iniciales
        gestorTareas.tareasMantenimiento.getHead().data.completar();
        if (gestorTareas.tareasSeguridad.getHead().next != null) {
             gestorTareas.tareasSeguridad.getHead().next.data.completar();
        }

        // Registrar algunos pagos iniciales para que haya ingresos
        historialPagos.add(new Pago(1500, LocalDate.now(), residentes.get(0)));
        historialPagos.add(new Pago(800, LocalDate.now(), residentes.get(2)));
        
        double egresosIniciales = gestorTareas.tareasMantenimiento.getHead().data.getCosto();
        if(gestorTareas.tareasSeguridad.getHead().next != null){
            egresosIniciales += gestorTareas.tareasSeguridad.getHead().next.data.getCosto();
        }

        reportes.add(new Reporte("Reporte Inicial del Sistema", 2300, egresosIniciales));

        historialActividades.push(new Actividad("Sistema iniciado y datos de demostración cargados."));
    }
}