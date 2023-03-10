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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javax.swing.table.DefaultTableModel;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.awt.BorderLayout;
import static java.rmi.Naming.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class Department extends Application{
   protected int depID;
   protected String depName;
   private Connection conn;
   public Department(){
       
   }
   public Department(int depID, String depName){
       this.depID = depID;
       this.depName = depName;
   }
   public void start(Stage ps){
       //Decalre control objects
       Button btnDisplay = new Button("Display Departments");
       Button btnAdd = new Button("Add New Department");
       Button btnDelete = new Button("Delete Department");
       Button btnHome = new Button("Go Back To Menu Window");
       Button btnLogOut = new Button("Logout");
       Label lblDepID = new Label("Department ID: ");
       Label lblDepName = new Label("Department Name: ");
       TextField txtDepID = new TextField();
       TextField txtDepName = new TextField();
       JScrollPane pane;
       String sql;
       //Add new Border Pane
       BorderPane bp = new BorderPane();
       //Add new HBox
       HBox hBox = new HBox(20);
       hBox.setPadding(new Insets(15,15,15,15));
       hBox.setStyle("-fx-background-color: gold");
       hBox.getChildren().addAll(lblDepID, txtDepID, lblDepName, txtDepName, btnDisplay, btnAdd, btnDelete, btnHome, btnLogOut);//Add all the conetens of hbox
       bp.setTop(hBox);//add hbox on the top of the stage
       //Add event handler and set action on display button
       btnDisplay.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               DepartmentDisplay dp = new DepartmentDisplay();
               dp.setTitle("Display Department Information");
               dp.setSize(800,500);
               dp.setVisible(true);
               dp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
              /*String data[][] = new String[10][2];
              String columns[] = {"Department ID", "Department Name"};
              String id, depName;
              TableView tableView = new TableView();
                              
                
                TableColumn colDepID = new TableColumn(columns[0]);
                TableColumn colDepName = new TableColumn(columns[1]);
                //tableView.setItems(data);
                
                tableView.getColumns().addAll(colDepID,colDepName);
                 ObservableList<list> depList  = FXCollections.observableArrayList(new list("0","0"));
                // ArrayList<String>department = new ArrayList<String>();
                 list lst[] = new list[50];
              try{
                connection();
                String sql = "SELECT * FROM tblDepartment";
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(sql);
                int i=0;
                while(result.next()){
                    id = result.getInt("Department_ID")+"";
                    depName = result.getString("Department_Name");
                    depList = FXCollections.observableArrayList(lst[i] = new list(id,depName));
                    //department.set(0, id);
                   // department.set(1, depName);
                    
                    i++;                    
                }
                
                    tableView.setItems(depList);
                bp.setCenter(tableView);
              }
              catch(Exception ex1){
                  JOptionPane.showMessageDialog(null, ex1.toString());
              }
              finally{
                  try {
                      conn.close();
                  } catch (SQLException ex) {
                      Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }*/
           }
       });
       btnAdd.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               try{
                  connection(); 
                  String sql="INSERT INTO tbldepartment(Department_Name) VALUES('" + txtDepName.getText() + "')";
                  Statement stmt = conn.createStatement();
                  stmt.execute(sql);
                  JOptionPane.showMessageDialog(null, "Department added sucessfully!", "Result Message", INFORMATION_MESSAGE);
                  conn.close();
               }
               catch(SQLException ex){
                   JOptionPane.showMessageDialog(null,ex.toString(), "ERROR", ERROR_MESSAGE);
               }
               catch(ClassNotFoundException ex){
                   JOptionPane.showMessageDialog(null, ex.toString(),"ERROR", ERROR_MESSAGE);
               }
               
           }
       });
       btnDelete.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               try{
                  connection(); 
                  int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Department ID To Be Deleted"));
                  String sql="DELETE FROM tbldepartment WHERE Department_ID="+id;
                  Statement stmt = conn.createStatement();
                  stmt.execute(sql);
                  JOptionPane.showMessageDialog(null,"Departement Deleted Successfully!", "Message", INFORMATION_MESSAGE);
                  conn.close();
               }
               catch(SQLException ex){
                   JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", ERROR_MESSAGE);
               }
               catch(ClassNotFoundException ex){
                   JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", ERROR_MESSAGE);
               }
           }
       });
       btnHome.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               HomePage hp = new HomePage();
               hp.start(ps);
           }
       });
       btnLogOut.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               TournamuntManagementSystem tms = new TournamuntManagementSystem();
               tms.start(ps);
           }
       });
       //Create scene
       Scene scene = new Scene(bp,500,300);
       ps.setTitle("Department");
       ps.setScene(scene);
       ps.show();
   }
   private void connection() throws ClassNotFoundException, SQLException{
       Class.forName("com.mysql.jdbc.Driver");
       conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
   }
}
