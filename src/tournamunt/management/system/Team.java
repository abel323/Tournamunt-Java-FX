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
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;
import javafx.scene.layout.FlowPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
public class Team extends Application{
    int depID=0;
    Connection conn;
    GridPane gp = new GridPane();
    BorderPane bp = new BorderPane();
    Label lblTeamName = new Label("Team Name: ");
    Label lblTeamSize = new Label("Team Size: ");
    Label lblTeamBatch = new Label("Team Batch: ");
    Label lblDepartment = new Label("Department: ");
    TextField txtName = new TextField();
    TextField txtSize = new TextField();
    TextField txtBatch = new TextField();
    ComboBox<String> department = new ComboBox<>();
    String depData[] = new String[10];
    Button btnAdd = new Button("Register Team");
    Button btnEdit = new Button("Edit Team");
    Button btnDelete = new Button("Delete Team");
    Button btnDisplay = new Button("Display");
    Button btnHome = new Button("Home");
    FlowPane fp = new FlowPane();
    int lastTeamID;
    public void start(Stage ps){
        getDepartment();
        for(int i=0; i<depData.length; i++){
            department.getItems().addAll(depData[i]);
        }
        btnAdd.setStyle("-fx-background-color: green; -fx-height: 30; -fx-width: 90; -fx-border-radius: 15; -fx-color: white;");
        btnEdit.setStyle("-fx-background-color: green; -fx-height: 30; -fx-width: 90; -fx-border-radius: 15; -fx-color: white;");
        btnDelete.setStyle("-fx-background-color: green; -fx-height: 30; -fx-width: 90; -fx-border-radius: 15; -fx-color: white;");
        btnDisplay.setStyle("-fx-background-color: green; -fx-height: 30; -fx-width: 90; -fx-border-radius: 15; -fx-color: white;");
        btnHome.setStyle("-fx-background-color: green; -fx-height: 30; -fx-width: 90; -fx-border-radius: 15; -fx-color: white;");
        //btnAdd.setStyle("-fx-background-color: green; -fx-height: 30px; -fx-width: 90px; -fx-border-radius: 15px;");
        gp.add(lblTeamName, 0, 0);
        gp.add(txtName, 1, 0);
        gp.add(lblTeamSize, 0, 1);
        gp.add(txtSize, 1, 1);
        gp.add(lblTeamBatch, 0, 2);
        gp.add(txtBatch, 1,2);
        gp.add(lblDepartment, 0,3);
        gp.add(department, 1, 3);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(15);
        gp.setHgap(15);
        fp.getChildren().addAll(btnAdd,btnEdit,btnDisplay,btnDelete,btnHome);
        fp.setHgap(15);
        fp.setAlignment(Pos.CENTER);
        bp.setCenter(gp);
        bp.setBottom(fp);
        //Display button event handler
        btnDisplay.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                TeamDetail td = new TeamDetail();
                td.setTitle("Team List");
                td.setSize(700,400);
                td.setVisible(true);
                td.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                
                String teamName = txtName.getText();
                String teamSize = txtSize.getText();
                String teamBatch = txtBatch.getText();
                try{
                    connection();
                    String sql = "SELECT Department_ID FROM tbldepartment WHERE Department_Name='" + department.getValue()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        depID = result.getInt("Department_ID");
                        i++;
                    }
                    
                    String insertSql = "INSERT INTO tblteam(Team_Name, Team_Size, Batch, Department) VALUES('" + teamName + "','" + teamSize + "','" + teamBatch + "'," + depID + ")";
                    
                    Statement insertStmt = conn.createStatement();
                    insertStmt.execute(insertSql);
                    JOptionPane.showMessageDialog(null, "Team Inserted Successfully!");
                    String sqlSelectLastTeam = "SELECT ID FROM tblteam WHERE ID=(SELECT LAST_INSERT_ID())";
                    Statement getStatment = conn.createStatement();
                    ResultSet resultTeam = getStatment.executeQuery(sqlSelectLastTeam);
                    
                    while(resultTeam.next()){
                        lastTeamID = resultTeam.getInt("ID");
                    }
                    String insertPoint = "INSERT INTO tblpoint(Team_ID) VALUES("+lastTeamID+")";
                    Statement stmtInsertPoint = conn.createStatement();
                    stmtInsertPoint.execute(insertPoint);
                    JOptionPane.showMessageDialog(null,"Team inserted to point table successfully!","Success!",INFORMATION_MESSAGE);
                    txtName.setText("");
                    txtBatch.setText("");
                    txtSize.setText("");
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null,ex.toString());
                }
                finally{
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        btnDelete.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String teamName = JOptionPane.showInputDialog("Enter Team Name To Be Deleted!");
                    String SQL = "DELETE FROM tblteam WHERE Team_Name='" +teamName+"'";
                    Statement stmt = conn.createStatement();
                    stmt.execute(SQL);
                    JOptionPane.showMessageDialog(null, "Team Deleted Successfully!");
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        });
        btnEdit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String teamName = JOptionPane.showInputDialog(null, "Enter Team Name To Be Edited");
                    String sql = "SELECT * FROM tblteam WHERE Team_Name='"+ teamName + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0; 
                    while(result.next()){
                        txtName.setText(result.getString("Team_Name"));
                        txtSize.setText(result.getString("Team_Size"));
                        txtBatch.setText(result.getInt("Batch")+"");
                        i++;
                    }
                    sql = "UPDATE tblTeam SET Team_Size='"+ txtSize.getText()+"', Batch='" +txtBatch.getText()+"'";
                    stmt.execute(sql);
                    JOptionPane.showMessageDialog(null, "Team Updated Successfully!");
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                finally{
                    try{
                        conn.close();
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                }
            }
        });
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage hp = new HomePage();
                hp.start(ps);
            }
        });
        Scene scene = new Scene(bp,600,300);
        ps.setTitle("Team Management");
        ps.setScene(scene);
        ps.show();
    }
    public void getDepartment(){
        try{
            connection();
            String sql = "SELECT Department_Name FROM tbldepartment";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            int i=0;
            while(result.next()){
                depData[i] = result.getString("Department_Name")+"";
                i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    public void getDepartmentID(){
        try{
            connection();
            String sql = "SELECT Department_ID FROM tbldepartment";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            int i=0;
            while(result.next()){
                depData[i] = result.getString("Department_ID");
                i++;
            }
        }
        catch(SQLException ex){
            
        }
        catch(ClassNotFoundException ex){
            
        }
        finally{
            try{
                conn.close();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }
    public void connection()throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
}
