import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class GestorTareas {
    // Se reemplazan las 3 colas por una sola Cola de Prioridad
    private PriorityQueue<Tarea> tareasPrioritarias;

    public GestorTareas() {
        this.tareasPrioritarias = new PriorityQueue<>();
    }

    // El metodo ahora es mas simple
    public void agregarTarea(Tarea tarea) {
        tareasPrioritarias.add(tarea);
    }

    // Metodo para obtener la tarea mas importante (sin importar el depto)
    public Tarea obtenerSiguienteTarea() {
        return tareasPrioritarias.poll(); // poll() extrae el elemento con mayor prioridad
    }

    // Metodo para ver todas las tareas ordenadas por prioridad
    public List<Tarea> obtenerTodasLasTareasOrdenadas() {
        // Creamos una copia para no vaciar la cola original
        List<Tarea> listaOrdenada = new ArrayList<>(tareasPrioritarias);
        // La PriorityQueue no garantiza el orden al iterar, asi que la ordenamos para visualizacion
        Collections.sort(listaOrdenada);
        return listaOrdenada;
    }
    
    // Metodo para obtener tareas filtradas por departamento y ordenadas
    public List<Tarea> obtenerTareasPorDepartamento(String depto) {
        return tareasPrioritarias.stream()
            .filter(t -> t.departamento.equalsIgnoreCase(depto))
            .sorted() // El stream se ordena usando el compareTo de Tarea
            .collect(Collectors.toList());
    }
}