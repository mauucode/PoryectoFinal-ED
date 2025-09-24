/**
 * Implementación de una Pila (Stack) genérica basada en una ListaEnlazada.
 * Sigue el principio LIFO (Last-In, First-Out).
 * @param <T> El tipo de dato que almacenará la pila.
 */
public class Pila<T> {
    private ListaEnlazada<T> list;

    public Pila() {
        list = new ListaEnlazada<>();
    }

    public void push(T data) {
        list.insertAt(0, data);
    }

    public T pop() {
        if (isEmpty()) return null;
        T data = list.head.data;
        list.removeAt(0);
        return data;
    }

    public T peek() {
        if (isEmpty()) return null;
        return list.head.data;
    }

    public boolean isEmpty() {
        return list.head == null;
    }

    public void display() {
        list.display();
    }
}