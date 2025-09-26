import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // --- ESTRUCTURAS DE DATOS PRINCIPALES ---
    private static ListaEnlazada<Condominio> condominios = new ListaEnlazada<>();
    private static GestorTareas gestorTareas = new GestorTareas();
    private static ListaEnlazada<Reporte> reportes = new ListaEnlazada<>();
    private static Pila<Actividad> historialActividades = new Pila<>();
    private static ListaEnlazada<Pago> historialPagos = new ListaEnlazada<>();
    private static Scanner scanner = new Scanner(System.in);

    // --- ESTRUCTURAS OPTIMIZADAS ---
    private static ListaEnlazada<Residente> residentes = new ListaEnlazada<>();
    private static HashMap<Integer, Residente> residentesPorId = new HashMap<>();
    private static HashMap<Integer, Tarea> tareasPorId = new HashMap<>();
    private static ArbolBinarioBusqueda residentesPorNombre = new ArbolBinarioBusqueda();

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
                case 8:
                    salir = true;
                    System.out.println("Gracias por usar el Sistema de CONDOMINIOS VITA S.A. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
            if (!salir && (opcion == 1 || opcion == 7)) {
                System.out.println("\nPresione Enter para volver al menu principal...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no valida. Por favor, ingrese un numero.");
            scanner.next();
            System.out.print("Seleccione una opcion: ");
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    private static void mostrarBanner() {
        System.out.println("==================================================");
        System.out.println("    CONDOMINIOS VITA S.A.");
        System.out.println("  Sistema Integral de Gestion Condominial");
        System.out.println("==================================================");
    }

    private static void mostrarMenu() {
        System.out.println("\nMenu del sistema:");
        System.out.println("1. Enviar avisos de pago a residentes");
        System.out.println("2. Generar Nuevo Reporte Financiero");
        System.out.println("3. Visualizar y Gestionar Reportes");
        System.out.println("4. Gestion de Pagos y Cuotas");
        System.out.println("5. Gestion de Tareas");
        System.out.println("6. Gestion de Residentes");
        System.out.println("7. Ver historial de actividades del sistema");
        System.out.println("8. Salir del sistema");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static void menuGestionReportes() {
        while (true) {
            System.out.println("\n--- Menu de Gestion de Reportes ---");
            System.out.println("1. Ver todos los reportes guardados");
            System.out.println("2. Buscar y ver un reporte por ID");
            System.out.println("3. Eliminar un reporte por ID");
            System.out.println("4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = leerOpcion();

            switch(opcion) {
                case 1: verTodosLosReportes(); break;
                case 2: buscarYVerReporte(); break;
                case 3: eliminarReporte(); break;
                case 4: return;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void menuPagosYCuotas() {
        while (true) {
            System.out.println("\n--- Menu de Pagos y Cuotas ---");
            System.out.println("1. Registrar pago individual a un residente");
            System.out.println("2. Aplicar cuota de mantenimiento general ($800.00)");
            System.out.println("3. Ver historial de pagos");
            System.out.println("4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1: registrarPagoIndividual(); break;
                case 2: aplicarCuotaGeneral(); break;
                case 3: verHistorialDePagos(); break;
                case 4: return;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void menuGestionTareas() {
        while (true) {
            System.out.println("\n--- Menu de Gestion de Tareas ---");
            System.out.println("1. Ver todas las tareas");
            System.out.println("2. Agregar nueva tarea");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Buscar tarea por costo");
            System.out.println("5. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1: verTodasLasTareas(); break;
                case 2: agregarNuevaTarea(); break;
                case 3: marcarTareaComoCompletada(); break;
                case 4: buscarTareaPorCostoInterfaz(); break;
                case 5: return;
                default: System.out.println("Opcion no valida.");
            }
        }
    }
    
    private static void menuGestionResidentes() {
        while (true) {
            System.out.println("\n--- Menu de Gestion de Residentes ---");
            System.out.println("1. Ver lista de residentes");
            System.out.println("2. Agregar nuevo residente");
            System.out.println("3. Eliminar residente");
            System.out.println("4. Buscar residente por ID");
            System.out.println("5. Buscar residente por nombre");
            System.out.println("6. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1: residentes.display(); break;
                case 2: agregarNuevoResidente(); break;
                case 3: eliminarResidente(); break;
                case 4: buscarResidentePorIdInterfaz(); break;
                case 5: buscarResidentePorNombreInterfaz(); break;
                case 6: return;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void generarReporteFinanciero() {
        System.out.println("\n--- Generando Nuevo Reporte Financiero ---");
        System.out.print("Ingrese un titulo para este reporte (ej. 'Cierre de Septiembre 2025'): ");
        String titulo = scanner.nextLine();
        double totalIngresos = 0;
        for (int i = 0; i < historialPagos.size(); i++) {
            totalIngresos += historialPagos.get(i).monto;
        }
        double totalEgresos = 0;
        for (Tarea t : tareasPorId.values()) {
            if (t.estaCompletada()) {
                totalEgresos += t.getCosto();
            }
        }
        Reporte nuevoReporte = new Reporte(titulo, totalIngresos, totalEgresos);
        reportes.add(nuevoReporte);
        System.out.println("\nReporte (ID: " + nuevoReporte.id + ") guardado con exito!");
        historialActividades.push(new Actividad("Genero el reporte financiero ID " + nuevoReporte.id + ": " + titulo));
    }
    
    // =======================================================================
    // MÉTODO MODIFICADO PARA MOSTRAR LA TABLA GRÁFICA
    // =======================================================================
    private static void verTodosLosReportes() {
        System.out.println("\n--- Vista Grafica de Reportes Financieros ---");
        historialActividades.push(new Actividad("Consulto la lista de reportes guardados."));

        if (reportes.size() == 0) {
            System.out.println("No hay reportes guardados todavia. Genere uno desde el menu principal.");
            return;
        }

        // Se define el formato y separadores de la tabla para una vista limpia
        String headerFormat = "| %-4s | %-35s | %-12s | %-18s | %-18s | %-18s |%n";
        String rowFormat = "| %-4d | %-35s | %-12s | $%,-17.2f | $%,-17.2f | $%,-17.2f |%n";
        String separator = "+------+-------------------------------------+--------------+--------------------+--------------------+--------------------+%n";

        // Imprimir la cabecera de la tabla
        System.out.format(separator);
        System.out.format(headerFormat, "ID", "TITULO DEL REPORTE", "FECHA", "INGRESOS TOTALES", "EGRESOS TOTALES", "BALANCE");
        System.out.format(separator);

        // Se itera sobre la lista de reportes para imprimir cada uno como una fila
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < reportes.size(); i++) {
            Reporte r = reportes.get(i);
            System.out.format(rowFormat,
                    r.id,
                    r.titulo,
                    r.fecha.format(formatter),
                    r.totalIngresos,
                    r.totalEgresos,
                    r.balance);
        }

        // Imprimir el pie de la tabla
        System.out.format(separator);
    }

    private static void buscarYVerReporte() {
        System.out.println("\n--- Buscar Reporte por ID ---");
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
            historialActividades.push(new Actividad("Visualizo el reporte con ID: " + id));
        } else {
            System.out.println("No se encontro ningun reporte con el ID " + id + ".");
        }
    }
    
    private static void eliminarReporte() {
        System.out.println("\n--- Eliminar Reporte por ID ---");
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
            System.out.println("Reporte '" + titulo + "' (ID: " + id + ") eliminado con exito!");
            historialActividades.push(new Actividad("Elimino el reporte con ID: " + id));
        } else {
            System.out.println("No se encontro ningun reporte con el ID " + id + ".");
        }
    }

    private static void enviarAvisosDePago() {
        System.out.println("\n--- Enviando Avisos de Pago ---");
        boolean hayDeudores = false;
        for (int i = 0; i < residentes.size(); i++) {
            Residente res = residentes.get(i);
            if (res.tieneDeuda()) {
                hayDeudores = true;
                System.out.println("---------------------------------------------");
                System.out.println("AVISO ENVIADO A: " + res.nombre + " (Departamento " + res.getDepartamento() + ")");
                System.out.println("Estimado residente, le recordamos que tiene una " + res.toString().substring(res.toString().indexOf("DEUDA")));
                System.out.println("Favor de realizar su pago a la brevedad.");
            }
        }
        if (!hayDeudores) {
            System.out.println("Excelente! No hay residentes con deudas pendientes.");
        }
        historialActividades.push(new Actividad("Se enviaron avisos de pago automaticos."));
    }
    
    private static void verHistorialDePagos() {
        System.out.println("\n--- Historial de Transacciones de Pago ---");
        if (historialPagos.size() == 0) {
            System.out.println("Aun no se han registrado pagos en el sistema.");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("%-21s | %-30s | %s%n", "FECHA Y HORA", "RESIDENTE", "MONTO");
            System.out.println("-------------------------------------------------------------------------");
            historialPagos.display();
            System.out.println("-------------------------------------------------------------------------");
        }
        historialActividades.push(new Actividad("Consulto el historial de pagos."));
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
            System.out.println("Pago registrado! Estado de cuenta actualizado:");
            System.out.println(res);
            historialActividades.push(new Actividad("Registro pago de $" + monto + " para residente ID " + id));
        } else {
            System.out.println("No se encontro un residente con ese ID.");
        }
    }

    private static void aplicarCuotaGeneral() {
        System.out.println("\n--- Aplicando Cuota de Mantenimiento General de $800.00 ---");
        double cuota = 800.00;
        for (int i = 0; i < residentes.size(); i++) {
            residentes.get(i).saldo -= cuota;
        }
        System.out.println("Cuota aplicada a todos los " + residentes.size() + " residentes con exito!");
        historialActividades.push(new Actividad("Aplico cuota general de $" + cuota));
    }

    private static void verTodasLasTareas() {
        System.out.println("\n--- Tareas de Mantenimiento ---");
        List<Tarea> mantenimiento = gestorTareas.obtenerTareasPorDepartamento("Mantenimiento");
        if (mantenimiento.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
        } else {
            mantenimiento.forEach(t -> System.out.println("-> " + t));
        }

        System.out.println("\n--- Tareas de Seguridad ---");
        List<Tarea> seguridad = gestorTareas.obtenerTareasPorDepartamento("Seguridad");
        if (seguridad.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
        } else {
            seguridad.forEach(t -> System.out.println("-> " + t));
        }
        
        System.out.println("\n--- Tareas de Administracion ---");
        List<Tarea> administracion = gestorTareas.obtenerTareasPorDepartamento("Administracion");
        if (administracion.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
        } else {
            administracion.forEach(t -> System.out.println("-> " + t));
        }
    }

    private static void agregarNuevaTarea() {
        System.out.println("\n--- Agregar Nueva Tarea ---");
        System.out.print("Descripcion de la tarea: ");
        String desc = scanner.nextLine();
        System.out.print("Departamento (Mantenimiento, Seguridad, Administracion): ");
        String depto = scanner.nextLine();
        System.out.print("Urgencia (ALTA, MEDIA, BAJA): ");
        Urgencia urgencia = Urgencia.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Costo estimado de la tarea: ");
        double costo = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());

        Tarea nuevaTarea = new Tarea(desc, depto, urgencia, costo, fecha);
        
        while (true) {
            System.out.print("Anadir prerrequisito? Ingrese el ID de la tarea (o 0 para terminar): ");
            int prerrequisitoId = leerOpcion();
            if (prerrequisitoId == 0) {
                break;
            }
            if (tareasPorId.containsKey(prerrequisitoId)) {
                nuevaTarea.agregarPrerrequisito(prerrequisitoId);
                System.out.println("Prerrequisito ID: " + prerrequisitoId + " anadido.");
            } else {
                System.out.println("ID de tarea no valido.");
            }
        }

        gestorTareas.agregarTarea(nuevaTarea);
        tareasPorId.put(nuevaTarea.getId(), nuevaTarea);
        
        System.out.println("Nueva tarea agregada con exito!");
        historialActividades.push(new Actividad("Agrego nueva tarea: " + desc));
    }

    private static void marcarTareaComoCompletada() {
        System.out.println("\n--- Marcar Tarea como Completada ---");
        verTodasLasTareas();
        System.out.print("Ingrese el ID de la tarea a marcar como completada: ");
        int id = leerOpcion();
        
        Tarea tarea = tareasPorId.get(id);

        if (tarea != null) {
            if (tarea.estaCompletada()) {
                System.out.println("Esta tarea ya estaba completada.");
                return;
            }

            boolean prerrequisitosCompletos = true;
            for (int prerrequisitoId : tarea.getPrerrequisitos()) {
                Tarea prerrequisito = tareasPorId.get(prerrequisitoId);
                if (prerrequisito != null && !prerrequisito.estaCompletada()) {
                    System.out.println("\nERROR: No se puede completar esta tarea.");
                    System.out.println("Primero debe finalizar la tarea prerrequisito ID: " + prerrequisitoId + " (" + prerrequisito.descripcion + ")");
                    prerrequisitosCompletos = false;
                    break; 
                }
            }

            if (prerrequisitosCompletos) {
                tarea.completar();
                System.out.println("Tarea ID " + id + " marcada como completada!");
                historialActividades.push(new Actividad("Completo la tarea ID " + id));
            }

        } else {
            System.out.println("No se encontro ninguna tarea con el ID " + id + ".");
        }
    }

    private static void agregarNuevoResidente() {
        System.out.println("\n--- Agregar Nuevo Residente ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Departamento (ej. A-101): ");
        String departamento = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();
        System.out.print("Saldo inicial (negativo para deuda, ej. -800): ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();
        
        Residente nuevoResidente = new Residente(nombre, departamento, tel, saldo);
        residentes.add(nuevoResidente);
        residentesPorId.put(nuevoResidente.id, nuevoResidente);
        residentesPorNombre.insertar(nuevoResidente);
        
        System.out.println("Residente " + nombre + " agregado con exito!");
        historialActividades.push(new Actividad("Agrego nuevo residente: " + nombre));
    }

    private static void eliminarResidente() {
        System.out.println("\n--- Eliminar Residente ---");
        residentes.display();
        System.out.print("Ingrese el ID del residente a eliminar: ");
        int id = leerOpcion();

        Residente residenteAEliminar = residentesPorId.get(id);

        if (residenteAEliminar != null) {
            // Nota: La eliminación de un árbol binario no está implementada en esta versión
            // para mantener la simplicidad. El residente solo se eliminará de la lista y el HashMap.
            int indexParaEliminar = -1;
            for(int i = 0; i < residentes.size(); i++) {
                if(residentes.get(i).id == id) {
                    indexParaEliminar = i;
                    break;
                }
            }
            if(indexParaEliminar != -1) {
                residentes.removeAt(indexParaEliminar);
            }
            
            residentesPorId.remove(id);

            System.out.println("Residente " + residenteAEliminar.nombre + " eliminado con exito!");
            historialActividades.push(new Actividad("Elimino al residente: " + residenteAEliminar.nombre));
        } else {
            System.out.println("No se encontro ningun residente con el ID " + id + ".");
        }
    }

    private static Residente buscarResidentePorId(int id) {
        return residentesPorId.get(id);
    }

    private static void buscarResidentePorIdInterfaz() {
        System.out.println("\n--- Buscar Residente por ID ---");
        System.out.print("Ingrese el ID del residente a buscar: ");
        int id = leerOpcion();
        Residente res = buscarResidentePorId(id);
        if (res != null) {
            System.out.println("Residente encontrado:");
            System.out.println(res);
        } else {
            System.out.println("No se encontro ningun residente con el ID " + id + ".");
        }
        historialActividades.push(new Actividad("Busco residente por ID: " + id));
    }

    private static void buscarResidentePorNombreInterfaz() {
        System.out.println("\n--- Buscar Residente por Nombre ---");
        System.out.print("Ingrese el nombre completo del residente a buscar: ");
        String nombre = scanner.nextLine();
        
        Residente res = residentesPorNombre.buscar(nombre);
        
        if (res != null) {
            System.out.println("Residente encontrado:");
            System.out.println(res);
        } else {
            System.out.println("No se encontro ningun residente con el nombre '" + nombre + "'.");
        }
        historialActividades.push(new Actividad("Busco residente por nombre: " + nombre));
    }

    private static void buscarTareaPorCostoInterfaz() {
        System.out.println("\n--- Buscar Tarea por Costo (Usando Busqueda Binaria Recursiva) ---");
        System.out.print("Ingrese el costo exacto de la tarea a buscar (ej. 1500.0): ");
        double costo = scanner.nextDouble();
        scanner.nextLine();

        List<Tarea> listaDeTareas = new ArrayList<>(tareasPorId.values());
        
        Collections.sort(listaDeTareas, Comparator.comparingDouble(Tarea::getCosto));

        Tarea tareaEncontrada = busquedaBinariaTareas(listaDeTareas, costo, 0, listaDeTareas.size() - 1);
        
        if (tareaEncontrada != null) {
            System.out.println("Tarea encontrada:");
            System.out.println(tareaEncontrada);
        } else {
            System.out.println("No se encontro ninguna tarea con el costo exacto de $" + costo);
        }
        historialActividades.push(new Actividad("Busco tarea por costo: " + costo));
    }

    private static Tarea busquedaBinariaTareas(List<Tarea> tareas, double costoBuscado, int inicio, int fin) {
        if (inicio > fin) {
            return null;
        }

        int medio = inicio + (fin - inicio) / 2;
        Tarea tareaDelMedio = tareas.get(medio);

        if (tareaDelMedio.getCosto() == costoBuscado) {
            return tareaDelMedio;
        }

        if (tareaDelMedio.getCosto() < costoBuscado) {
            return busquedaBinariaTareas(tareas, costoBuscado, medio + 1, fin);
        } else {
            return busquedaBinariaTareas(tareas, costoBuscado, inicio, medio - 1);
        }
    }

    private static void verHistorialActividades() {
        System.out.println("\n--- Historial de Actividades del Sistema (LIFO) ---");
        historialActividades.display();
    }
    
    private static void cargarDatosIniciales() {
        condominios.add(new Condominio("Vita Park", "Av. Principal 123"));
        String[] nombres = {"Ana", "Luis", "Carla", "David", "Elena", "Fernando", "Gloria", "Hugo", "Irene", "Jorge"};
        String[] apellidos = {"Torres", "Morales", "Soto", "Reyes", "Garza", "Cruz", "Pena", "Jimenez", "Castillo", "Luna"};
        Random rand = new Random();
        
        for (int i = 0; i < 50; i++) { 
            String nombre = nombres[rand.nextInt(nombres.length)] + " " + apellidos[rand.nextInt(apellidos.length)];
            String departamento = (char)('A' + rand.nextInt(3)) + "-" + (101 + i);
            String telefono = "81" + (10000000 + rand.nextInt(90000000));
            double saldo = -800 + rand.nextInt(1000) - rand.nextInt(500);
            Residente r = new Residente(nombre, departamento, telefono, saldo);
            residentes.add(r);
            residentesPorId.put(r.id, r);
            residentesPorNombre.insertar(r);
        }
        
        String[] descMantenimiento = {"Reparar fuga de agua", "Pintar pared del lobby", "Limpiar alberca", "Revisar sistema de riego", "Cambiar foco del pasillo"};
        String[] descSeguridad = {"Vigilar entrada principal", "Monitorear camaras", "Reportar vehiculo sospechoso", "Controlar acceso en puerta", "Revisar bitacora"};
        String[] descAdmin = {"Elaborar reporte financiero", "Contactar a proveedor", "Archivar facturas de Julio", "Programar junta con comite", "Realizar pago de servicios"};
        
        for (int i = 0; i < 15; i++) {
            String desc = descMantenimiento[rand.nextInt(descMantenimiento.length)];
            Urgencia urg = Urgencia.values()[rand.nextInt(3)];
            double costo = 1000 + rand.nextInt(4001);
            LocalDate fecha = LocalDate.now().plusDays(rand.nextInt(30));
            Tarea t = new Tarea(desc, "Mantenimiento", urg, costo, fecha);
            gestorTareas.agregarTarea(t);
            tareasPorId.put(t.getId(), t);
        }

        for (int i = 0; i < 15; i++) {
            String desc = descSeguridad[rand.nextInt(descSeguridad.length)];
            Urgencia urg = Urgencia.values()[rand.nextInt(3)];
            double costo = 500 + rand.nextInt(1001);
            LocalDate fecha = LocalDate.now().plusDays(rand.nextInt(30));
            Tarea t = new Tarea(desc, "Seguridad", urg, costo, fecha);
            gestorTareas.agregarTarea(t);
            tareasPorId.put(t.getId(), t);
        }

        for (int i = 0; i < 15; i++) {
            String desc = descAdmin[rand.nextInt(descAdmin.length)];
            Urgencia urg = Urgencia.values()[rand.nextInt(3)];
            double costo = 100 + rand.nextInt(401);
            LocalDate fecha = LocalDate.now().plusDays(rand.nextInt(30));
            Tarea t = new Tarea(desc, "Administracion", urg, costo, fecha);
            gestorTareas.agregarTarea(t);
            tareasPorId.put(t.getId(), t);
        }
        
        if (tareasPorId.containsKey(5) && tareasPorId.containsKey(10)) {
            tareasPorId.get(10).agregarPrerrequisito(5);
        }
        if (tareasPorId.containsKey(16) && tareasPorId.containsKey(20)) {
            tareasPorId.get(20).agregarPrerrequisito(16);
        }
        if (tareasPorId.containsKey(31) && tareasPorId.containsKey(35) && tareasPorId.containsKey(40)) {
            tareasPorId.get(40).agregarPrerrequisito(31);
            tareasPorId.get(40).agregarPrerrequisito(35);
        }

        historialActividades.push(new Actividad("Sistema iniciado y datos de demostracion cargados."));
    }
}