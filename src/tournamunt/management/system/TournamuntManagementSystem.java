/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamunt.management.system;

import javafx.application.Application;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javax.swing.JOptionPane;
//import java.sql.Driver;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author Abel
 */
public class TournamuntManagementSystem extends Application {
    Connection conn;
     public void connect()throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    @Override
    public void start(Stage primaryStage) {
        //Connection conn;
        String uName;
        //Connection conn;
        final String Password;
        BorderPane bp = new BorderPane();
        StackPane sp = new StackPane();
        GridPane gp = new GridPane();
        GridPane gpButton = new GridPane();
        Label lblAdmin = new Label("Admin Login Window");
        Label lblUser = new Label("User Name: ");
        Label lblPassword = new Label("Password: ");
        TextField txtUser = new TextField();
        Button btnLogIn = new Button("Login");
        Button btnRegister = new Button("Register");
        Button btnExit = new Button("Exit");
        
        PasswordField txtPassword = new PasswordField();
        sp.getChildren().add(lblAdmin);
        bp.setTop(sp);
        gp.add(lblUser, 0, 0);
        gp.add(txtUser, 1, 0);
        gp.add(lblPassword, 0,1);
        gp.add(txtPassword, 1, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10.5,10.5,10.5,10.5));
        gp.setHgap(7);
        gp.setVgap(7);
        gpButton.add(btnLogIn, 0, 0);
        gpButton.add(btnRegister, 1, 0);
        gpButton.add(btnExit, 2,0);
        gpButton.setAlignment(Pos.CENTER);
        gpButton.setHgap(10);
        gpButton.setVgap(10);
        bp.setCenter(gp);
        bp.setBottom(gpButton);
        Scene scene = new Scene(bp, 500,300);
        btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //String userName = txtUser.getText();
                //String password = txtPassword.getText();
                if(txtUser.getText().length()==0 || txtPassword.getText().length()==0){
                    JOptionPane.showMessageDialog(null, "User Name and Pasword can't be empty", "Error Message", ERROR_MESSAGE);
                }
                else{
                    try{
                        //SqlConnection connect = new SqlConnection();
                        String uName="", Password="";
                        connect();
                        String sql = "SELECT User_Name, UPassword FROM tblAdmin WHERE User_Name='"+txtUser.getText()+"' AND UPassword='"+txtPassword.getText()+"'";
                        Statement stmt = conn.createStatement();
                        ResultSet resultSet = stmt.executeQuery(sql);
                        while(resultSet.next()){
                            uName = resultSet.getString("User_Name");
                            Password = resultSet.getString("UPassword");
                        }
                        if(txtUser.getText().equals(uName) && txtPassword.getText().equals(Password)){
                            HomePage hp = new HomePage();
                            hp.start(primaryStage);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Error: Please check your user name and password!", "Error", ERROR_MESSAGE);
                        }
                    }
                    catch(SQLException ex2){
                        JOptionPane.showMessageDialog(null,ex2.toString(), "Error", ERROR_MESSAGE);
                    }
                    catch(ClassNotFoundException ex){
                        JOptionPane.showMessageDialog(null,ex.toString(), "Error", ERROR_MESSAGE);
                    }
                    finally{
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(TournamuntManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        btnRegister.setOnAction((ActionEvent event)->{
            RegisterAdmin ra = new RegisterAdmin();
            ra.start(primaryStage);
        });
        btnExit.setOnAction((ActionEvent event)->{
            System.exit(0);
        });
        primaryStage.setTitle("Admin Login Window");
        primaryStage.setScene(scene);
        primaryStage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
