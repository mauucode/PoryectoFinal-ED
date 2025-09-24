/**
 * Representa un nodo genérico para ser utilizado en estructuras de datos enlazadas.
 * Contiene un dato y referencias a los nodos siguiente y previo.
 * @param <T> El tipo de dato que almacenará el nodo.
 */
public class Nodo<T> {
    public T data;
    public Nodo<T> next;
    public Nodo<T> prev; // No se usa en esta implementación simple, pero está por completitud

    public Nodo(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}