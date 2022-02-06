/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;



import Modelos.ConeccionSQL;
import Modelos.ModelTableView;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author abarc
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private ComboBox cBMateria;
    @FXML
    private ComboBox cBEstatus;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCalificacion;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;

    ConeccionSQL conexion = new ConeccionSQL();
    Connection con;

    ObservableList<String> list1;
    ObservableList<String> list2;
    @FXML
    private TableColumn<ModelTableView, String> tCAlumnos;
    @FXML
    private TableColumn<ModelTableView, String> tCNombre;
    @FXML
    private TableColumn<ModelTableView, String> tCApellidos;
    @FXML
    private TableColumn<ModelTableView, String> tCmateria;
    @FXML
    private TableColumn<ModelTableView, Double> tCCalificacion;
    @FXML
    private TableColumn<ModelTableView, String> tCEstatus;

    @FXML
    private TableView<ModelTableView> tableView;

    ObservableList<ModelTableView> obs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            con = conexion.conexion();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error de conexion " + ex.getMessage());
//        }
        mostrarRegistros();
        list1 = FXCollections.observableArrayList("Matematica", "Español", "Ingles", "Geografia");
        cBMateria.setItems(list1);
        list2 = FXCollections.observableArrayList("Aprovado", "Reprobado");
        cBEstatus.setItems(list2);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    public void mostrarRegistros() {

        String SQL = "select * from alumnos";
        try {
            Statement rt = con.createStatement();
            ResultSet rs = rt.executeQuery(SQL);
            while (rs.next()) {
//                obs.add(new ModelTableView(rs.getString("idAlumnos"), rs.getString("nombre"), rs.getString("Apellidos"),
//                        rs.getString("materia"), rs.getDouble("calificacion"), rs.getString("estatus")));
            }

            tCAlumnos.setCellValueFactory(new PropertyValueFactory<>("idAlumnos"));
            tCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tCApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            tCmateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
            tCCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
            tCEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

            tableView.setItems(obs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros xistentes  \n" + ex.getMessage());
        }
    }

    @FXML
    private void BotonGuardar(MouseEvent event) {
        insertarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }

    public void limpiarCeldas() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCalificacion.setText("");
        cBMateria.setValue(null);
        cBEstatus.setValue(null);

    }

    private void insertarDatos() {
        try {
            String SQL = "insert into alumnos(nombre,Apellidos,materia,calificacion,estatus)"
                    + "values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(SQL);
            
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellidos.getText());
            String cb = (String) cBMateria.getValue();
            pst.setString(3, cb);
            pst.setDouble(4, Double.parseDouble(txtCalificacion.getText()));
            pst.setString(5, (String) cBEstatus.getValue());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el registro \n" + ex.getMessage());
        }
    }

    @FXML
    private void clickTabla(MouseEvent event) {
//        int filaSeleconada = tableView.getSelectionModel().getSelectedIndex(); // Haci se jala el indiceque se esta selecionando
//        tableView.getItems().get(filaSeleconada).getNombre(); // De esta forma se tiene acceso a los valores almacenados en la fila
//        txtNombre.setText(tableView.getItems().get(filaSeleconada).getNombre());
//        txtApellidos.setText(tableView.getItems().get(filaSeleconada).getApellidos());
//        txtCalificacion.setText("" + tableView.getItems().get(filaSeleconada).getCalificacion());
//        cBEstatus.setValue(tableView.getItems().get(filaSeleconada).getEstatus());
//        cBMateria.setValue(tableView.getItems().get(filaSeleconada).getMateria());
    }

    @FXML
    private void clickBtnActualizar(MouseEvent event) {
        acualizarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }
   
    private void acualizarDatos() {
        try {
            String SQL = "update alumnos set nombre=?,Apellidos=?,materia=?,calificacion=?,estatus=?"
                    + "where idAlumnos=?";

            int filaSeleconada = tableView.getSelectionModel().getSelectedIndex();

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellidos.getText());
            String cb = (String) cBMateria.getValue();
            pst.setString(3, cb);
            pst.setDouble(4, Double.parseDouble(txtCalificacion.getText()));
            pst.setString(5, (String) cBEstatus.getValue());
//            pst.setString(6, (String) tableView.getItems().get(filaSeleconada).getIdAlumnos());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Registro actualizado exitoso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la actualizacion \n" + ex.getMessage());
        }
    }

    @FXML
    private void clickBtnEliminar(MouseEvent event) {
        eliminarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }

    private void eliminarDatos(){
        int filaSeleconada = tableView.getSelectionModel().getSelectedIndex();
//        try {
//        String SQL = "delete from alumnos "
//                + "where idAlumnos="+(String) tableView.getItems().get(filaSeleconada).getIdAlumnos();
//        PreparedStatement pst = con.prepareStatement(SQL);
//         pst.execute();
//         JOptionPane.showMessageDialog(null, "Datos correctamente eliminados");
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error al eliminar \n" + ex.getMessage());
//        }
    }
    
    public void busquedaEnRegistros(String valor) {

        String SQL = "select * from alumnos where nombre like '%"+valor+"%'";
        try {
            Statement rt = con.createStatement();
            ResultSet rs = rt.executeQuery(SQL);
            while (rs.next()) {
//                obs.add(new ModelTableView(rs.getString("idAlumnos"), rs.getString("nombre"), rs.getString("Apellidos"),
//                        rs.getString("materia"), rs.getDouble("calificacion"), rs.getString("estatus")));
            }

            tCAlumnos.setCellValueFactory(new PropertyValueFactory<>("idAlumnos"));
            tCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tCApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            tCmateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
            tCCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
            tCEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

            tableView.setItems(obs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros xistentes  \n" + ex.getMessage());
        }
    }

    @FXML
    private void funcionTFieldBuscar(KeyEvent event) {
        obs.clear();
        busquedaEnRegistros(txtBusqueda.getText());
        
    }
    

}
