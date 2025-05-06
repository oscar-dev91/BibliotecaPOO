import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazBiblioteca extends JFrame {
    private Biblioteca biblioteca;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaElementos;

    public InterfazBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Interfaz de la Biblioteca");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear un panel para los botones y otras acciones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        // Botón para agregar un nuevo elemento
        JButton btnAgregarElemento = new JButton("Agregar Elemento");
        btnAgregarElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana emergente para agregar un nuevo elemento
                VentanaAgregarElemento ventanaAgregar = new VentanaAgregarElemento(biblioteca);
                ventanaAgregar.setVisible(true);
            }
        });

        // Botón para mostrar todos los elementos de la biblioteca
        JButton btnMostrarElementos = new JButton("Mostrar Elementos");
        btnMostrarElementos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar todos los elementos de la biblioteca en la lista
                mostrarElementosEnLista();
            }
        });

        // Botón para eliminar un elemento seleccionado
        JButton btnEliminarElemento = new JButton("Eliminar Elemento");
        btnEliminarElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarElementoSeleccionado();
            }
        });

        // Botón para actualizar un elemento seleccionado
        JButton btnActualizarElemento = new JButton("Actualizar Elemento");
        btnActualizarElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarElementoSeleccionado();
            }
        });

        // Agregar los botones al panel
        panelBotones.add(btnAgregarElemento);
        panelBotones.add(btnMostrarElementos);
        panelBotones.add(btnEliminarElemento);
        panelBotones.add(btnActualizarElemento);

        // Crear un modelo de lista para mostrar los elementos
        modeloLista = new DefaultListModel<>();
        listaElementos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaElementos);

        // Agregar el panel de botones y la lista a la ventana
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Método para mostrar los elementos en la lista
    private void mostrarElementosEnLista() {
        modeloLista.clear();
        // Obtener todos los elementos y agregarlos a la lista en la interfaz
        biblioteca.mostrarTodosLosElementos();  // Muestra los elementos en la consola
        List<ElementoBiblioteca> elementos = biblioteca.buscarPorTitulo("");  // Busca todos los elementos (vacío para mostrar todos)
        for (ElementoBiblioteca elemento : elementos) {
            modeloLista.addElement(elemento.getTitulo() + " (" + elemento.getTipo() + ")");
        }
    }

    // Método para eliminar un elemento seleccionado
    private void eliminarElementoSeleccionado() {
        int index = listaElementos.getSelectedIndex();
        if (index != -1) {
            String elementoSeleccionado = modeloLista.getElementAt(index);
            String titulo = elementoSeleccionado.split(" \\(")[0];

            // Buscar el elemento por título y eliminarlo
            List<ElementoBiblioteca> elementosEncontrados = biblioteca.buscarPorTitulo(titulo);
            if (!elementosEncontrados.isEmpty()) {
                ElementoBiblioteca elemento = elementosEncontrados.get(0);
                biblioteca.registrarElemento(elemento); // Eliminamos el elemento de la lista de la biblioteca
                modeloLista.remove(index); // Lo eliminamos de la lista gráfica
                JOptionPane.showMessageDialog(this, "Elemento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un elemento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar un elemento seleccionado
    private void actualizarElementoSeleccionado() {
        int index = listaElementos.getSelectedIndex();
        if (index != -1) {
            String elementoSeleccionado = modeloLista.getElementAt(index);
            String titulo = elementoSeleccionado.split(" \\(")[0];

            // Buscar el elemento por título
            List<ElementoBiblioteca> elementosEncontrados = biblioteca.buscarPorTitulo(titulo);
            if (!elementosEncontrados.isEmpty()) {
                ElementoBiblioteca elemento = elementosEncontrados.get(0);
                // Abrir ventana para actualizar los detalles del elemento
                VentanaActualizarElemento ventanaActualizar = new VentanaActualizarElemento(elemento);
                ventanaActualizar.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un elemento para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Crear la biblioteca y la interfaz de la biblioteca
        Biblioteca biblioteca = new Biblioteca();
        InterfazBiblioteca interfaz = new InterfazBiblioteca(biblioteca);
        interfaz.setVisible(true);
    }
}
