// Clase principal para demostrar el uso
public class MainBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Crear elementos
        Libro libro1 = new Libro("Cien Años de Soledad", "Gabriel García Márquez", 1967, 432, "Realismo Mágico");
        Revista revista1 = new Revista("National Geographic", "National Geographic Society", 2023, 245, "Ciencia y Naturaleza");
        DVD dvd1 = new DVD("Inception", "Christopher Nolan", 2010, 148, "Ciencia Ficción");

        // Agregar elementos a la biblioteca usando métodos específicos
        libro1.registrarLibro(biblioteca);
        revista1.registrarRevista(biblioteca);
        dvd1.registrarDVD(biblioteca);

        // Mostrar todos los elementos
        System.out.println("Elementos en la biblioteca:");
        biblioteca.mostrarTodosLosElementos();

        // Prestar un elemento
        libro1.prestar();

        // Mostrar elementos disponibles
        System.out.println("\nElementos disponibles:");
        biblioteca.mostrarElementosDisponibles();
    }
}