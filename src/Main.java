import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

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
                case 3: menuGestionReportes(); break;
                case 4: menuPagosYCuotas(); break;
                case 5: menuGestionTareas(); break;
                case 6: menuGestionResidentes(); break;
                case 7: verHistorialActividades(); break;
                case 8: buscarResidentePorIdInterfaz(); break;
                case 9:
                    salir = true;
                    System.out.println("Gracias por usar el Sistema de CONDOMINIOS VITA S.A. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            // La pausa solo se aplica después de una acción del menú principal
            if (!salir && (opcion == 1 || opcion == 2 || opcion == 7 || opcion == 8)) {
                System.out.println("\nPresione Enter para volver al menú principal...");
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
        System.out.println("2. 📝 Generar Nuevo Reporte Financiero");
        System.out.println("3. 📊 Visualizar y Gestionar Reportes");
        System.out.println("4. 💳 Gestión de Pagos y Cuotas");
        System.out.println("5. 🔧 Gestión de Tareas");
        System.out.println("6. 🏠 Gestión de Residentes");
        System.out.println("7. 📚 Ver historial de actividades del sistema");
        System.out.println("8. 🔍 Buscar residente por ID");
        System.out.println("9. 🚪 Salir del sistema");
        System.out.print("Seleccione una opción: ");
    }
    
    // --- SUBMENÚS CON BUCLE (SIN PAUSA) ---

    private static void menuGestionReportes() {
        while (true) {
            System.out.println("\n--- 📊 Menú de Gestión de Reportes ---");
            System.out.println("1. Ver todos los reportes guardados");
            System.out.println("2. Buscar y ver un reporte por ID");
            System.out.println("3. Eliminar un reporte por ID");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = leerOpcion();

            switch(opcion) {
                case 1: verTodosLosReportes(); break;
                case 2: buscarYVerReporte(); break;
                case 3: eliminarReporte(); break;
                case 4: return;
                default: System.out.println("Opción no válida.");
            }
            // Se eliminó la pausa de "Presione Enter"
        }
    }

    private static void menuPagosYCuotas() {
        while (true) {
            System.out.println("\n--- 💳 Menú de Pagos y Cuotas ---");
            System.out.println("1. Registrar pago individual a un residente");
            System.out.println("2. Aplicar cuota de mantenimiento general ($800.00)");
            System.out.println("3. Ver historial de pagos (Transacciones)");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1: registrarPagoIndividual(); break;
                case 2: aplicarCuotaGeneral(); break;
                case 3: verHistorialDePagos(); break;
                case 4: return;
                default: System.out.println("Opción no válida.");
            }
            // Se eliminó la pausa de "Presione Enter"
        }
    }

    private static void menuGestionTareas() {
        while (true) {
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
                case 4: return;
                default: System.out.println("Opción no válida.");
            }
            // Se eliminó la pausa de "Presione Enter"
        }
    }
    
    private static void menuGestionResidentes() {
        while (true) {
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
                case 4: return;
                default: System.out.println("Opción no válida.");
            }
            // Se eliminó la pausa de "Presione Enter"
        }
    }

    // --- MÉTODOS DE ACCIÓN (SIN CAMBIOS) ---

    private static void generarReporteFinanciero() {
        System.out.println("\n--- 📝 Generando Nuevo Reporte Financiero ---");
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
        System.out.println("\n✅ ¡Reporte (ID: " + nuevoReporte.id + ") guardado con éxito!");
        historialActividades.push(new Actividad("Generó el reporte financiero ID " + nuevoReporte.id + ": " + titulo));
    }

    private static void verTodosLosReportes() {
        System.out.println("\n--- Lista de Reportes Financieros Guardados ---");
        if (reportes.size() == 0) {
            System.out.println("No hay reportes guardados todavía. Genere uno desde el menú principal.");
        } else {
            reportes.display();
        }
        historialActividades.push(new Actividad("Consultó la lista de reportes guardados."));
    }

    private static void buscarYVerReporte() {
        System.out.println("\n--- 🔍 Buscar Reporte por ID ---");
        System.out.print("Ingrese el ID del reporte que desea visualizar: ");
        int id = leerOpcion();
        Reporte reporteEncontrado = null;
        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).id == id) {
                reporteEncontrado = reportes.get(i);
                break;
            }
        }
        if (reporteEncontrado != null) {
            reporteEncontrado.display();
            historialActividades.push(new Actividad("Visualizó el reporte con ID: " + id));
        } else {
            System.out.println("❌ No se encontró ningún reporte con el ID " + id + ".");
        }
    }
    
    private static void eliminarReporte() {
        System.out.println("\n--- 🗑️ Eliminar Reporte por ID ---");
        verTodosLosReportes();
        if (reportes.size() == 0) return;
        System.out.print("Ingrese el ID del reporte que desea eliminar: ");
        int id = leerOpcion();
        int indexParaEliminar = -1;
        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).id == id) {
                indexParaEliminar = i;
                break;
            }
        }
        if (indexParaEliminar != -1) {
            String titulo = reportes.get(indexParaEliminar).titulo;
            reportes.removeAt(indexParaEliminar);
            System.out.println("✅ ¡Reporte '" + titulo + "' (ID: " + id + ") eliminado con éxito!");
            historialActividades.push(new Actividad("Eliminó el reporte con ID: " + id));
        } else {
            System.out.println("❌ No se encontró ningún reporte con el ID " + id + ".");
        }
    }

    private static void enviarAvisosDePago() {
        System.out.println("\n--- 🔔 Enviando Avisos de Pago ---");
        boolean hayDeudores = false;
        for (int i = 0; i < residentes.size(); i++) {
            Residente res = residentes.get(i);
            if (res.tieneDeuda()) {
                hayDeudores = true;
                System.out.println("---------------------------------------------");
                System.out.println("AVISO ENVIADO A: " + res.nombre + " (Unidad " + res.unidad + ")");
                System.out.println("Estimado residente, le recordamos que tiene una " + res.toString().substring(res.toString().indexOf("DEUDA")));
                System.out.println("Favor de realizar su pago a la brevedad.");
            }
        }
        if (!hayDeudores) {
            System.out.println("¡Excelente! No hay residentes con deudas pendientes.");
        }
        historialActividades.push(new Actividad("Se enviaron avisos de pago automáticos."));
    }
    
    private static void verHistorialDePagos() {
        System.out.println("\n--- 🧾 Historial de Transacciones de Pago ---");
        if (historialPagos.size() == 0) {
            System.out.println("Aún no se han registrado pagos en el sistema.");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("%-21s | %-30s | %s%n", "FECHA Y HORA", "RESIDENTE", "MONTO");
            System.out.println("-------------------------------------------------------------------------");
            historialPagos.display();
            System.out.println("-------------------------------------------------------------------------");
        }
        historialActividades.push(new Actividad("Consultó el historial de pagos."));
    }

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
            historialPagos.add(new Pago(monto, LocalDateTime.now(), res)); 
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

    private static void verHistorialActividades() {
        System.out.println("\n--- 📚 Historial de Actividades del Sistema (LIFO) ---");
        historialActividades.display();
    }
    
    private static void cargarDatosIniciales() {
        condominios.add(new Condominio("Vita Park", "Av. Principal 123"));
        String[] nombres = {"Ana", "Luis", "Carla", "David", "Elena", "Fernando", "Gloria", "Hugo", "Irene", "Jorge"};
        String[] apellidos = {"Torres", "Morales", "Soto", "Reyes", "Garza", "Cruz", "Peña", "Jimenez", "Castillo", "Luna"};
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            String nombre = nombres[rand.nextInt(nombres.length)] + " " + apellidos[rand.nextInt(apellidos.length)];
            String unidad = (char)('A' + rand.nextInt(3)) + "-" + (101 + i);
            String telefono = "81" + (10000000 + rand.nextInt(90000000));
            double saldo = -800 + rand.nextInt(1000) - rand.nextInt(500);
            residentes.add(new Residente(nombre, unidad, telefono, saldo));
        }
        
        String[] accionesMantenimiento = {"Reparar", "Pintar", "Limpiar", "Revisar", "Cambiar"};
        String[] objetosMantenimiento = {"fuga de agua", "pared del lobby", "alberca", "sistema de riego", "foco del pasillo"};
        String[] accionesSeguridad = {"Vigilar", "Monitorear", "Reportar", "Controlar acceso en", "Revisar"};
        String[] objetosSeguridad = {"entrada principal", "cámaras del Sector B", "vehículo sospechoso", "puerta de servicio", "bitácora"};
        String[] accionesAdmin = {"Elaborar", "Contactar a", "Archivar", "Programar junta", "Realizar pago de"};
        String[] objetosAdmin = {"reporte financiero", "proveedor de limpieza", "facturas de Julio", "con el comité", "servicios públicos"};
        
        for (int i = 0; i < 50; i++) {
            int tipo = rand.nextInt(3);
            Urgencia urg = Urgencia.values()[rand.nextInt(3)];
            Tarea t;
            double costo; 
            String depto;
            String desc;
            if (tipo == 0) {
                depto = "Mantenimiento";
                desc = accionesMantenimiento[rand.nextInt(5)] + " " + objetosMantenimiento[rand.nextInt(5)];
                costo = 1000 + rand.nextInt(4001); 
            } else if (tipo == 1) {
                depto = "Seguridad";
                desc = accionesSeguridad[rand.nextInt(5)] + " " + objetosSeguridad[rand.nextInt(5)];
                costo = 500 + rand.nextInt(1001);
            } else {
                depto = "Administracion";
                desc = accionesAdmin[rand.nextInt(5)] + " " + objetosAdmin[rand.nextInt(5)];
                costo = 100 + rand.nextInt(401);
            }
            t = new Tarea(desc, depto, urg, costo);
            gestorTareas.agregarTarea(t);
        }

        Tarea egresoFijo1 = new Tarea("Reparación mayor de bomba de agua en cisterna", "Mantenimiento", Urgencia.ALTA, 3500.00);
        Tarea egresoFijo2 = new Tarea("Auditoría de seguridad nocturna externa", "Seguridad", Urgencia.MEDIA, 1200.00);
        egresoFijo1.completar();
        egresoFijo2.completar();
        gestorTareas.agregarTarea(egresoFijo1);
        gestorTareas.agregarTarea(egresoFijo2);
        double egresosInicialesFijos = egresoFijo1.getCosto() + egresoFijo2.getCosto();

        historialPagos.add(new Pago(1500, LocalDateTime.now().minusDays(1), residentes.get(0)));
        historialPagos.add(new Pago(800, LocalDateTime.now(), residentes.get(2)));
        double ingresosInicialesFijos = 2300.00;

        reportes.add(new Reporte("Reporte Inicial del Sistema", ingresosInicialesFijos, egresosInicialesFijos));
        historialActividades.push(new Actividad("Sistema iniciado y datos de demostración cargados."));
    }
}