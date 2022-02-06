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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author abarc
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuName;
    @FXML
    private PasswordField txtPasword;
    @FXML
    private Button btnLogin;
    
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

    }

    public void cambiarScena(MouseEvent event,String escena) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(escena));
            Scene scene = new Scene(root);
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

    @FXML
    private void Action(ActionEvent event) {
        
    }

    @FXML
    private void Entrar(MouseEvent event, String escena) {
        try {
            String usuario = txtUsuName.getText();
            String pasword = txtPasword.getText();
            String SQL = "select COUNT(id) AS I from usuarios where nombreUsuario = '"+usuario+"' and pasword = '"+pasword+"'";
            Statement rt = con.createStatement();
            ResultSet rs = rt.executeQuery(SQL);
            while(rs.next()){
                if (rs.getString("I").equals("1")) {
                    cambiarScena(event,"crudescuelita/FXMLDocument.fxml");
                }else{
                    JOptionPane.showMessageDialog(null, "Error nombre de usuario o contaseña, incorrecto");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejcutar la consutal"+ex.getMessage());
        }
    }

    @FXML
    private void Registrarce(MouseEvent event) {
        
    }

}
