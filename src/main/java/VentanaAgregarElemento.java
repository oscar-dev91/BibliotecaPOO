import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAgregarElemento extends JFrame {
    private Biblioteca biblioteca;
    private JComboBox<String> comboTipoElemento;
    private JPanel panelFormulario;
    private JButton btnAgregar;

    public VentanaAgregarElemento(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Agregar Elemento a la Biblioteca");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear combo box para seleccionar el tipo de elemento
        String[] tipos = {"Libro", "Revista", "DVD"};
        comboTipoElemento = new JComboBox<>(tipos);
        comboTipoElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFormulario();
            }
        });

        // Panel para el formulario de los elementos
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(0, 2));

        // Botón para agregar el elemento
        btnAgregar = new JButton("Agregar Elemento");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarElemento();
            }
        });

        // Agregar los componentes a la ventana
        add(comboTipoElemento, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(btnAgregar, BorderLayout.SOUTH);

        actualizarFormulario();
    }

    private void actualizarFormulario() {
        // Limpiar el formulario
        panelFormulario.removeAll();

        String tipo = (String) comboTipoElemento.getSelectedItem();

        // Dependiendo del tipo, mostrar los campos correspondientes
        if ("Libro".equals(tipo)) {
            panelFormulario.add(new JLabel("Título:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Autor:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Año de publicación:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Número de páginas:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Género:"));
            panelFormulario.add(new JTextField());
        } else if ("Revista".equals(tipo)) {
            panelFormulario.add(new JLabel("Título:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Autor:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Año de publicación:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Número:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Tema:"));
            panelFormulario.add(new JTextField());
        } else if ("DVD".equals(tipo)) {
            panelFormulario.add(new JLabel("Título:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Director:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Año de publicación:"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Duración (minutos):"));
            panelFormulario.add(new JTextField());
            panelFormulario.add(new JLabel("Categoría:"));
            panelFormulario.add(new JTextField());
        }

        // Actualizar la ventana
        revalidate();
        repaint();
    }

    private void agregarElemento() {
        String tipo = (String) comboTipoElemento.getSelectedItem();
        JTextField[] campos = (JTextField[]) panelFormulario.getComponents();

        // Obtener los datos del formulario
        String titulo = campos[0].getText();
        String autor = campos[1].getText();
        int anioPublicacion = Integer.parseInt(campos[2].getText());

        // Crear el objeto dependiendo del tipo
        if ("Libro".equals(tipo)) {
            int numeroPaginas = Integer.parseInt(campos[3].getText());
            String genero = campos[4].getText();
            Libro libro = new Libro(titulo, autor, anioPublicacion, numeroPaginas, genero);
            libro.agregarABiblioteca(biblioteca);
        } else if ("Revista".equals(tipo)) {
            int numero = Integer.parseInt(campos[3].getText());
            String tema = campos[4].getText();
            Revista revista = new Revista(titulo, autor, anioPublicacion, numero, tema);
            revista.agregarABiblioteca(biblioteca);
        } else if ("DVD".equals(tipo)) {
            int duracion = Integer.parseInt(campos[3].getText());
            String categoria = campos[4].getText();
            DVD dvd = new DVD(titulo, autor, anioPublicacion, duracion, categoria);
            dvd.agregarABiblioteca(biblioteca);
        }

        JOptionPane.showMessageDialog(this, tipo + " agregado correctamente a la biblioteca.");
        dispose();  // Cerrar la ventana
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        VentanaAgregarElemento ventana = new VentanaAgregarElemento(biblioteca);
        ventana.setVisible(true);
    }
}
