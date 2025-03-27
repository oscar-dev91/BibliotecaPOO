import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Clase Biblioteca para gestionar los elementos´
public class Biblioteca {
    private List<ElementoBiblioteca> elementos;

    // Constructor
    public Biblioteca() {
        elementos = new ArrayList<>();
    }

    // Método para registrar un elemento en la biblioteca
    public void registrarElemento(ElementoBiblioteca elemento) {
        elementos.add(elemento);
        System.out.println(elemento.getTipo() + " registrado en la biblioteca.");
    }

    // Método para buscar elementos por título
    public List<ElementoBiblioteca> buscarPorTitulo(String titulo) {
        return elementos.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Método para mostrar todos los elementos
    public void mostrarTodosLosElementos() {
        for (ElementoBiblioteca elemento : elementos) {
            elemento.mostrarDetalles();
            System.out.println("-------------------");
        }
    }

    // Método para mostrar elementos disponibles
    public void mostrarElementosDisponibles() {
        elementos.stream()
                .filter(e -> !e.estaPrestado())
                .forEach(ElementoBiblioteca::mostrarDetalles);
    }
}
