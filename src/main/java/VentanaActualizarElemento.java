import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaActualizarElemento extends JFrame {
    private ElementoBiblioteca elemento;

    public VentanaActualizarElemento(ElementoBiblioteca elemento) {
        this.elemento = elemento;
        setTitle("Actualizar Elemento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Etiquetas y campos para actualizar los datos
        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField(elemento.getTitulo());

        JLabel lblAutor = new JLabel("Autor:");
        JTextField txtAutor = new JTextField(elemento.getAutor());

        JLabel lblAnio = new JLabel("Año de Publicación:");
        JTextField txtAnio = new JTextField(String.valueOf(elemento.getAnoPublicacion()));

        JButton btnActualizar = new JButton("Actualizar");

        // Acción para el botón de actualización
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se actualizarían los atributos del elemento
                elemento.setTitulo(txtTitulo.getText());
                elemento.setAutor(txtAutor.getText());
                elemento.setAnoPublicacion(Integer.parseInt(txtAnio.getText()));
                JOptionPane.showMessageDialog(VentanaActualizarElemento.this, "Elemento actualizado correctamente.");
                dispose();  // Cerrar la ventana de actualización
            }
        });

        // Agregar los componentes a la ventana
        add(lblTitulo);
        add(txtTitulo);
        add(lblAutor);
        add(txtAutor);
        add(lblAnio);
        add(txtAnio);
        add(new JLabel());
        add(btnActualizar);
    }
}
