/// Clase abstracta base para elementos de la biblioteca
public abstract class ElementoBiblioteca {
    // Atributos protegidos para que sean accesibles por las clases hijas
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;
    protected boolean prestado;
    protected Biblioteca biblioteca; // Referencia a la biblioteca

    // Constructor
    public ElementoBiblioteca(String titulo, String autor, int anioPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.prestado = false;
    }

    // Método para agregar el elemento a una biblioteca
    public void agregarABiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        biblioteca.registrarElemento(this);
    }

    // Métodos abstractos que serán implementados por las clases hijas
    public abstract void mostrarDetalles();
    public abstract String getTipo();

    // Métodos comunes para todos los elementos
    public void prestar() {
        if (!prestado) {
            prestado = true;
            System.out.println(titulo + " ha sido prestado.");
        } else {
            System.out.println(titulo + " ya está prestado.");
        }
    }

    public void devolver() {
        if (prestado) {
            prestado = false;
            System.out.println(titulo + " ha sido devuelto.");
        } else {
            System.out.println(titulo + " no estaba prestado.");
        }
    }

    // Getters para acceder a la información del elemento
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacion() {
        return anioPublicacion;
    }

    public boolean estaPrestado() {
        return prestado;
    }
}
