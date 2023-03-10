/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamunt.management.system;

/**
 *
 * @author Abel
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
public class RegisterAdmin extends Application{
    Connection conn;
    
    public void connect()throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    @Override
    public void start(Stage ps){
        final Label lblFName = new Label("First Name: ");
        final Label lblMName = new Label("Middle Name: ");
        final Label lblLName = new Label("Last Name: ");
        final Label lblUser = new Label("User Name: ");
        final Label lblPassword = new Label("Password: ");
        final Label lblConfPassword = new Label("Confirm Password: ");
        final Label lblPhone = new Label("Phone Number: ");
        final TextField txtFName = new TextField();
        final TextField txtMName = new TextField();
        final TextField txtLName = new TextField();
        final TextField txtUser = new TextField();
        final PasswordField txtPassword = new PasswordField();
        final PasswordField txtConfPassword = new PasswordField();
        final TextField txtPhone = new TextField();
        final Button btnRegister = new Button("Register");
        final Button btnCancel = new Button("Cancel");
        final Button btnExit = new Button("Exit");
        //Declare GridPane for adding form objects
        GridPane gpForm = new GridPane();
        gpForm.add(lblFName, 0, 0);
        gpForm.add(txtFName, 1, 0);
        gpForm.add(lblMName, 0, 1);
        gpForm.add(txtMName, 1, 1);
        gpForm.add(lblLName, 0, 2);
        gpForm.add(txtLName, 1, 2);
        gpForm.add(lblUser, 0, 3);
        gpForm.add(txtUser, 1, 3);
        gpForm.add(lblPassword, 0, 4);
        gpForm.add(txtPassword, 1, 4);
        gpForm.add(lblConfPassword, 0, 5);
        gpForm.add(txtConfPassword, 1, 5);
        gpForm.add(lblPhone, 0, 6);
        gpForm.add(txtPhone, 1, 6);
        gpForm.setAlignment(Pos.CENTER);
        gpForm.setHgap(10);
        gpForm.setVgap(10);
        //Declare GridPane for buttons
        GridPane gpButton = new GridPane();
        gpButton.add(btnRegister, 0, 0);
        gpButton.add(btnCancel, 1,0);
        gpButton.add(btnExit,2,0);
        gpButton.setAlignment(Pos.CENTER);
        gpButton.setHgap(10);
        gpButton.setVgap(10);
        //Declare borderpane object to add grid pane to the stage
        BorderPane bp = new BorderPane();
        bp.setCenter(gpForm);
        bp.setBottom(gpButton);
        //Set action listeners for button click event
        btnCancel.setOnAction((ActionEvent event)->{
            txtFName.setText("");
            txtLName.setText("");
            txtMName.setText("");
            txtPhone.setText("");
            txtPassword.setText("");
            txtConfPassword.setText("");
            txtPassword.setText("");
            txtPhone.setText("");
            txtUser.setText("");
        });
        //Set ActionEvent for Exit Button click
        btnExit.setOnAction((ActionEvent event)->{
            System.exit(0);
        });
        //Set ActionEvent for Register Button Clik
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!txtPassword.getText().equals(txtConfPassword.getText())){
                  JOptionPane.showMessageDialog(null, "ERROR: Password and confirm password doesnt match!", "ERROR", ERROR_MESSAGE);
                }
                else if(txtPassword.getText().length()<8){
                    JOptionPane.showMessageDialog(null, "ERROR: Password length shuld be greater than or equal to 8 charcters!", "ERROR", ERROR_MESSAGE);
                }
                else if(txtUser.getText().length()<3){
                    JOptionPane.showMessageDialog(null, "ERROR: User Name length shuld be greater than or equal to 3 charcters!", "ERROR", ERROR_MESSAGE);
                }
                else if(txtFName.getText().length()<3){
                    JOptionPane.showMessageDialog(null, "ERROR: First Name length shuld be greater than or equal to 3 charcters!", "ERROR", ERROR_MESSAGE);
                }
                else if(txtMName.getText().length()<3){
                    JOptionPane.showMessageDialog(null, "ERROR: Middle Name length shuld be greater than or equal to 3 charcters!", "ERROR", ERROR_MESSAGE);
                }
                else if(txtLName.getText().length()<3){
                    JOptionPane.showMessageDialog(null, "ERROR: Last Name length shuld be greater than or equal to 3 charcters!", "ERROR", ERROR_MESSAGE);
                }
                else{
                                   
                    try {
                        connect();
                        String sql;
                        sql = "INSERT INTO tblAdmin VALUES('"+txtUser.getText()+"','"+txtFName.getText()+"','"+txtMName.getText()+"','"+txtLName.getText()+"','"+txtPassword.getText()
                            +"','"+txtPhone.getText()+"')";
                        
                        Statement stmt = conn.createStatement();
                        stmt.execute(sql);
                        JOptionPane.showMessageDialog(null, "Admin Created Successfully!", "Success", INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        Logger.getLogger(RegisterAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RegisterAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        Scene scene = new Scene(bp, 700,500);
        ps.setScene(scene);
        ps.setTitle("Register New Admin");
        ps.show();
    }
}
