// Clase Revista que hereda de ElementoBiblioteca
public class Revista extends ElementoBiblioteca {
    private int numero;
    private String tema;

    // Constructor
    public Revista(String titulo, String autor, int anioPublicacion, int numero, String tema) {
        super(titulo, autor, anioPublicacion);
        this.numero = numero;
        this.tema = tema;
    }

    // Método específico de Revista
    public void registrarRevista(Biblioteca biblioteca) {
        // Lógica específica para registrar una revista si es necesario
        agregarABiblioteca(biblioteca);
        System.out.println("Revista registrada con detalles adicionales.");
    }

    // Implementación del método abstracto mostrarDetalles()
    @Override
    public void mostrarDetalles() {
        System.out.println("Revista: " + titulo);
        System.out.println("Editorial: " + autor);
        System.out.println("Año de Publicación: " + anioPublicacion);
        System.out.println("Número: " + numero);
        System.out.println("Tema: " + tema);
        System.out.println("Estado: " + (prestado ? "Prestado" : "Disponible"));
    }

    // Implementación del método abstracto getTipo()
    @Override
    public String getTipo() {
        return "Revista";
    }

    // Getters específicos de Revista
    public int getNumero() {
        return numero;
    }

    public String getTema() {
        return tema;
    }
}



