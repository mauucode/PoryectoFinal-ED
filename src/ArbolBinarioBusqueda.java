// Esta clase representa un nodo individual en el árbol
class NodoArbol {
    Residente residente;
    NodoArbol izquierdo, derecho;

    public NodoArbol(Residente item) {
        residente = item;
        izquierdo = derecho = null;
    }
}

// Esta es la clase principal que gestiona el árbol
public class ArbolBinarioBusqueda {
    NodoArbol raiz;

    public ArbolBinarioBusqueda() {
        raiz = null;
    }

    // --- Inserción ---
    // Método público para iniciar la inserción
    public void insertar(Residente residente) {
        raiz = insertarRecursivo(raiz, residente);
    }

    // Método recursivo privado para insertar un nuevo residente
    private NodoArbol insertarRecursivo(NodoArbol nodoActual, Residente residenteNuevo) {
        // Si el árbol está vacío, el nuevo residente es la raíz
        if (nodoActual == null) {
            nodoActual = new NodoArbol(residenteNuevo);
            return nodoActual;
        }

        // Si no, recorremos el árbol recursivamente
        // Comparamos por nombre para decidir si va a la izquierda o a la derecha
        if (residenteNuevo.nombre.compareTo(nodoActual.residente.nombre) < 0) {
            nodoActual.izquierdo = insertarRecursivo(nodoActual.izquierdo, residenteNuevo);
        } else if (residenteNuevo.nombre.compareTo(nodoActual.residente.nombre) > 0) {
            nodoActual.derecho = insertarRecursivo(nodoActual.derecho, residenteNuevo);
        }

        return nodoActual;
    }

    // --- Búsqueda ---
    // Método público para iniciar la búsqueda por nombre
    public Residente buscar(String nombre) {
        NodoArbol resultado = buscarRecursivo(raiz, nombre);
        return (resultado != null) ? resultado.residente : null;
    }

    // Método recursivo privado para buscar un residente
    private NodoArbol buscarRecursivo(NodoArbol nodoActual, String nombreBuscado) {
        // Caso Base 1: El nodo no existe o encontramos el nombre
        if (nodoActual == null || nodoActual.residente.nombre.equalsIgnoreCase(nombreBuscado)) {
            return nodoActual;
        }

        // Paso Recursivo: Decidimos si buscar en el sub-árbol izquierdo o derecho
        if (nombreBuscado.compareTo(nodoActual.residente.nombre) < 0) {
            return buscarRecursivo(nodoActual.izquierdo, nombreBuscado);
        } else {
            return buscarRecursivo(nodoActual.derecho, nombreBuscado);
        }
    }
}