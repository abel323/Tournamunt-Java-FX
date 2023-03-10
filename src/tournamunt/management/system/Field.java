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
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;
public class Field extends Application{
    private Connection conn;
    Button btnAdd = new Button("Register Field");
    Button btnDelete = new Button("Delete Field");
    Button btnEdit = new Button("Edit Field");
    Button btnDisplay = new Button("Display Fields");
    Button btnHome = new Button("Home");
    TextField txtFieldName = new TextField();
    TextField txtCapacity = new TextField();
    Label lblField = new Label("Field Name: ");
    Label lblCapacity = new Label("Capacity: ");
    GridPane gp = new GridPane();
    StackPane sp = new StackPane();
    BorderPane bp = new BorderPane();
    FlowPane fp = new FlowPane();
    @Override
    public void start(Stage ps){
        
        gp.add(lblField, 0, 0);
        gp.add(txtFieldName, 1, 0);
        gp.add(lblCapacity, 0, 1);
        gp.add(txtCapacity, 1, 1);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10,10,10,10));
        gp.setVgap(15);
        gp.setHgap(15);
        fp.getChildren().add(btnAdd);
        fp.getChildren().add(btnDelete);
        fp.getChildren().add(btnDisplay);
        fp.getChildren().add(btnEdit);
        fp.getChildren().add(btnHome);
        fp.setAlignment(Pos.CENTER);
        fp.setHgap(15);
        //btnAdd event handler
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String name = txtFieldName.getText();
                int capacity = Integer.parseInt(txtCapacity.getText());
                insert(name,capacity);
            }
        });
        //btnEdit event handelr
        btnEdit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String name=txtFieldName.getText();
                int Capacity = Integer.parseInt(txtCapacity.getText());
                update(name,Capacity);
            }
        });
        //btnDelete event Handler
        btnDelete.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String name = JOptionPane.showInputDialog("Enter Field Name To Be Deleted!");
                delete(name);
            }
        });
        //home button event handlre
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage home = new HomePage();
                home.start(ps);
            }
        });
        bp.setCenter(gp);
        bp.setBottom(fp);
        Scene scene = new Scene(bp, 500,300);
        ps.setTitle("Field Management");
        ps.setScene(scene);
        ps.show();
    }
    public void insert(String name, int Capacity){
        try{
            connection();
            String sql = "INSERT INTO tblfield(Place, Capacity) VALUES('"+name+"',"+Capacity+")";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Field Added Successfully!");
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    //UPdate function to update filed data
    public void update(String name, int capacity){
        try{
            connection();
            String sql = "UPDATE tblfield SET Place='" +name+ "', Capacity="+capacity+" WHERE Place='" + name +"'";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Field Updated Successfully!");
            
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.toString());
        }
    }
    //Delete Function to delete field data
    public void delete(String name){
        try{
            connection();
            String sql = "DELETE FROM tblfield WHERE Place='" + name + "'";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Field Data Deleted Successfully!");
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    //Connection function
    public void connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
}
