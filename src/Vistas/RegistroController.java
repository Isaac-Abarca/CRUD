/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelos.ConeccionSQL;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author abarc
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtContrasena;
    @FXML
    private TextField txtConfirCotra;
    @FXML
    private Label ContraseñaCorrecta;

    boolean ListaContra = false;

    ConeccionSQL conexion = new ConeccionSQL();
    Connection con;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            con = conexion.conexion();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error de conexion " + ex.getMessage());
//        }
    }

    @FXML
    private void compararContra(KeyEvent event) {
        if (txtContrasena.getText().equals(txtConfirCotra.getText())) {
            ContraseñaCorrecta.setText("Coinciden");
            ListaContra = true;
        } else {
            ContraseñaCorrecta.setText("No coinciden");
        }
    }

    @FXML
    private void Registro(MouseEvent event) {
        insertarDatos();
        cambiarScena(event);
    }

    public void cambiarScena(MouseEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Escuela");
            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            ((Node) (event.getSource())).getScene().getWindow().hide();
            stage.show();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void insertarDatos() {
        try {
            if ((txtUser.getText() != null || !"".equals(txtUser.getText())) && ListaContra) {
                String consulta = "insert into usuarios(pasword,id,nombreUsuario) values(?,?,?);";
                PreparedStatement pre = con.prepareStatement(consulta);
                pre.setString(1, txtContrasena.getText());  //--- le pasamos el valor del 1er parámetro en formato String
                pre.setInt(2, (int) (Math.random() * 1000 + (Math.random() * 100)));         //--- le pasamos el valor del 2do parámetro en formato Enteger
                pre.setString(3, txtUser.getText());   //--- le pasamos el valor del 3er parámetro en formato String
                pre.execute();
                JOptionPane.showMessageDialog(null, "Registro correctamente");

            } else {
                JOptionPane.showMessageDialog(null, "Hacen falta datos");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el registro \n" + ex.getMessage());
        }
    }
}
