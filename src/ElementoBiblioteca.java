public abstract class ElementoBiblioteca {
    // Atributos protegidos para que sean accesibles por las clases hijas
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;
    protected boolean prestado;

    // Constructor
    public ElementoBiblioteca(String titulo, String autor, int anioPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.prestado = false;
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
}









// Clase Libro para representar la entidad
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    // Constructor completo
    public Libro(int id, String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Constructor sin ID (para nuevos libros)
    public Libro(String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y Setters
    // (Se omiten por brevedad, pero deberían incluirse todos)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    // ... otros getters y setters
}

// Clase DAO (Data Access Object) para Libro
public class LibroDAO {
    // Método para crear (Insertar) un nuevo libro
    public void crearLibro(Libro libro) {
        String sqlElemento = "INSERT INTO elementos (titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, 'Libro')";
        String sqlLibro = "INSERT INTO libros (elemento_id, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion()) {
            // Desactivar autocommit para manejar transacción
            conexion.setAutoCommit(false);

            try (PreparedStatement pstElemento = conexion.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstLibro = conexion.prepareStatement(sqlLibro)) {

                // Insertar en tabla elementos
                pstElemento.setString(1, libro.getTitulo());
                pstElemento.setString(2, libro.getAutor());
                pstElemento.setInt(3, libro.getAnoPublicacion());
                pstElemento.executeUpdate();

                // Obtener el ID generado
                try (ResultSet generatedKeys = pstElemento.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int elementoId = generatedKeys.getInt(1);

                        // Insertar en tabla libros
                        pstLibro.setInt(1, elementoId);
                        pstLibro.setString(2, libro.getIsbn());
                        pstLibro.setInt(3, libro.getNumeroPaginas());
                        pstLibro.setString(4, libro.getGenero());
                        pstLibro.setString(5, libro.getEditorial());
                        pstLibro.executeUpdate();

                        // Confirmar transacción
                        conexion.commit();
                    } else {
                        conexion.rollback();
                        throw new SQLException("Creación de libro fallida, no se generó ID");
                    }
                }
            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear libro", e);
        }
    }

    // Método para leer (Buscar) libro por ID
    public Libro buscarLibroPorId(int id) {
        String sql = "SELECT e.*, l.* FROM elementos e " +
                "JOIN libros l ON e.id = l.elemento_id " +
                "WHERE e.id = ?";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion();
             PreparedStatement pst = conexion.prepareStatement(sql)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano_publicacion"),
                            rs.getString("isbn"),
                            rs.getInt("numero_paginas"),
                            rs.getString("genero"),
                            rs.getString("editorial")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar libro", e);
        }
        return null;
    }

    // Método para actualizar libro
    public void actualizarLibro(Libro libro) {
        String sqlElemento = "UPDATE elementos SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ?";
        String sqlLibro = "UPDATE libros SET isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE elemento_id = ?";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement pstElemento = conexion.prepareStatement(sqlElemento);
                 PreparedStatement pstLibro = conexion.prepareStatement(sqlLibro)) {

                // Actualizar elementos
                pstElemento.setString(1, libro.getTitulo());
                pstElemento.setString(2, libro.getAutor());
                pstElemento.setInt(3, libro.getAnoPublicacion());
                pstElemento.setInt(4, libro.getId());
                pstElemento.executeUpdate();

                // Actualizar libros
                pstLibro.setString(1, libro.getIsbn());
                pstLibro.setInt(2, libro.getNumeroPaginas());
                pstLibro.setString(3, libro.getGenero());
                pstLibro.setString(4, libro.getEditorial());
                pstLibro.setInt(5, libro.getId());
                pstLibro.executeUpdate();

                conexion.commit();
            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar libro", e);
        }
    }

    // Método para eliminar libro
    public void eliminarLibro(int id) {
        String sql = "DELETE FROM elementos WHERE id = ?";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion();
             PreparedStatement pst = conexion.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar libro", e);
        }
    }

    // Método para listar todos los libros
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.* FROM elementos e " +
                "JOIN libros l ON e.id = l.elemento_id " +
                "WHERE e.tipo = 'Libro'";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacion"),
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar libros", e);
        }

        return libros;
    }

    // Método para buscar libros por título
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.* FROM elementos e " +
                "JOIN libros l ON e.id = l.elemento_id " +
                "WHERE e.tipo = 'Libro' AND e.titulo LIKE ?";

        try (Connection conexion = ConexionBiblioteca.obtenerConexion();
             PreparedStatement pst = conexion.prepareStatement(sql)) {

            pst.setString(1, "%" + titulo + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano_publicacion"),
                            rs.getString("isbn"),
                            rs.getInt("numero_paginas"),
                            rs.getString("genero"),
                            rs.getString("editorial")
                    );
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar libros por título", e);
        }

        return libros;
    }
}

// Clase de ejemplo para demostrar el uso
public class MainBiblioteca {
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAO();

        // Crear un nuevo libro
        Libro nuevoLibro = new Libro(
                "Cien Años de Soledad",
                "Gabriel García Márquez",
                1967,
                "978-0060883287",
                417,
                "Realismo Mágico",
                "Editorial Sudamericana"
        );
        libroDAO.crearLibro(nuevoLibro);

        // Buscar libro por ID
        Libro libroBuscado = libroDAO.buscarLibroPorId(1);
        System.out.println("Libro encontrado: " + libroBuscado.getTitulo());

        // Actualizar libro
        libroBuscado.setTitulo("Nuevo Título");
        libroDAO.actualizarLibro(libroBuscado);

        // Listar todos los libros
        List<Libro> todosLosLibros = libroDAO.listarLibros();
        todosLosLibros.forEach(libro -> System.out.println(libro.getTitulo()));

        // Buscar libros por título
        List<Libro> librosPorTitulo = libroDAO.buscarLibrosPorTitulo("Cien");
        librosPorTitulo.forEach(libro -> System.out.println(libro.getTitulo()));

        // Eliminar libro
        libroDAO.eliminarLibro(1);
    }
}