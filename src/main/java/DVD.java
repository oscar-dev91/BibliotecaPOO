// Clase DVD que hereda de ElementoBiblioteca
public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String categoria;

    // Constructor
    public DVD(String titulo, String autor, int anioPublicacion, int duracion, String categoria) {
        super(titulo, autor, anioPublicacion);
        this.duracion = duracion;
        this.categoria = categoria;
    }

    // Método específico de DVD
    public void registrarDVD(Biblioteca biblioteca) {
        // Lógica específica para registrar un DVD si es necesario
        agregarABiblioteca(biblioteca);
        System.out.println("DVD registrado con detalles adicionales.");
    }

    // Implementación del método abstracto mostrarDetalles()
    @Override
    public void mostrarDetalles() {
        System.out.println("DVD: " + titulo);
        System.out.println("Director: " + autor);
        System.out.println("Año de Publicación: " + anioPublicacion);
        System.out.println("Duración: " + duracion + " minutos");
        System.out.println("Categoría: " + categoria);
        System.out.println("Estado: " + (prestado ? "Prestado" : "Disponible"));
    }

    // Implementación del método abstracto getTipo()
    @Override
    public String getTipo() {
        return "DVD";
    }

    // Getters específicos de DVD
    public int getDuracion() {
        return duracion;
    }

    public String getCategoria() {
        return categoria;
    }
}