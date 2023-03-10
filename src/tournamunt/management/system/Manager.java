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
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
public class Manager extends Application{
    Connection conn;
    String teamData[] = new String[10];
    int teamID;
    String health_status[] = {"Normal","Not Normal", "Exceptional"};
    BorderPane bp = new BorderPane();
    FlowPane fp = new FlowPane();
    FlowPane fpNorth = new FlowPane();
    Label lblTeamSearch = new Label("Team Name: ");
    ComboBox cboSearch = new ComboBox();
    Button btnSearch = new Button("Search Manager");
    GridPane gp = new GridPane();
    Button btnAdd = new Button("Register Manager");
    Button btnEdit = new Button("Edit Manager");
    Button btnDisplay = new Button("Display Manager");
    Button btnDelete = new Button("Delete Manager");
    Button btnHome = new Button("Home");
    TextField txtFName = new TextField();
    TextField txtMName = new TextField();
    TextField txtLName = new TextField();
    TextField txtDOB = new TextField("yyyy/mm/dd");
    ComboBox cboHStatus = new ComboBox();
    ComboBox cboTeam = new ComboBox();
    Label lblFName = new Label("First Name: ");
    Label lblMName = new Label("Middle Name: ");
    Label lblLName = new Label("Last Name: ");
    Label lblDOB = new Label("Date Of Birth: ");
    Label lblHStatus = new Label("Health Status: ");
    Label lblTeam = new Label("Team: ");
    @Override
    public void start(Stage ps){
        getTeamData();
        for(int i=0; i<teamData.length; i++){
            cboTeam.getItems().addAll(teamData[i]);
        }
        for(int i=0; i<teamData.length; i++){
            cboSearch.getItems().addAll(teamData[i]);
        }
        for(int i=0; i<health_status.length; i++){
            cboHStatus.getItems().addAll(health_status[i]);
        }
        fpNorth.getChildren().addAll(lblTeamSearch,cboSearch,btnSearch);
        fpNorth.setHgap(15);
        fpNorth.setAlignment(Pos.CENTER);
        
        gp.add(lblFName, 0,0);
        gp.add(txtFName, 1,0);
        gp.add(lblMName, 0, 1);
        gp.add(txtMName, 1, 1);
        gp.add(lblLName, 0, 2);
        gp.add(txtLName, 1, 2);
        gp.add(lblDOB, 0, 3);
        gp.add(txtDOB, 1, 3);
        gp.add(lblHStatus, 0, 4);
        gp.add(cboHStatus, 1, 4);
        gp.add(lblTeam, 0, 6);
        gp.add(cboTeam, 1, 6);
        
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(15,15,15,15));
        
        fp.getChildren().addAll(btnAdd,btnEdit,btnDisplay,btnDelete,btnHome);
        
        fp.setHgap(15);
        fp.setAlignment(Pos.CENTER);
        fp.setPadding(new Insets(15,15,15,15));
        bp.setTop(fpNorth);;
        bp.setCenter(gp);
        bp.setBottom(fp);
        Scene scene = new Scene(bp,800,400);
        ps.setTitle("Manager Management");
        ps.setScene(scene);
        ps.show();
        //home button event handler
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage hp = new HomePage();
                hp.start(ps);
            }
        });
        btnDisplay.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                ManagerDetail md = new ManagerDetail();
                md.setTitle("Manager List");
                md.setSize(700,400);
                md.setVisible(true);
                md.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        //register button event handler
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    //Get team ID
                    String sqlTeam = "SELECT ID FROM tblteam WHERE Team_Name='"+cboTeam.getValue()+"'";
                    Statement stmtTeam = conn.createStatement();
                    ResultSet resultTeam = stmtTeam.executeQuery(sqlTeam);
                    int i=0;
                    while(resultTeam.next()){
                        teamID = resultTeam.getInt("ID");
                        i++;
                    }
                    String sqlInsert="INSERT INTO tblManager(FName,MName,LName,DOB,Health_Status,Team) VALUES('"+txtFName.getText()+"','"+txtMName.getText()+"','"+txtLName.getText()+"','"+txtDOB.getText()+"','"+cboHStatus.getValue()+"',"+teamID+")";
                    Statement stmtInsert = conn.createStatement();
                    stmtInsert.execute(sqlInsert);
                    JOptionPane.showMessageDialog(null, "Manager Registered Successfully!", "Success!", INFORMATION_MESSAGE);
                    txtFName.setText("");
                    txtLName.setText("");
                    txtMName.setText("");
                    txtDOB.setText("yyyy/mm/dd");
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
            }
        });
        //Edit Button Event Handler
        btnEdit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String sqlUpdate = "UPDATE tblmanager SET FName='" +txtFName.getText() + "', MName='"+txtMName.getText()+"', LName='"+txtLName.getText()+"', DOB='"+txtDOB.getText()+"', Health_Status='" + cboHStatus.getValue()+ "', Team="+teamID +" WHERE Team="+teamID;
                    Statement stmtUpdate = conn.createStatement();
                    stmtUpdate.execute(sqlUpdate);
                    JOptionPane.showMessageDialog(null, "Manager Data Updaed Successfully!", "Update Massage", INFORMATION_MESSAGE);
                    txtFName.setText("");
                    txtLName.setText("");
                    txtMName.setText("");
                    txtDOB.setText("yyyy/mm/dd");
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
            }
        });
        //Search Manager Button Event Handler
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                        if(cboSearch.getValue()==""){
                        JOptionPane.showMessageDialog(null,"Please Coose Team To Edit Team Manager!");
                    }
                    else if(cboTeam.getValue()!=""){
                        String sqlTeam = "SELECT ID FROM tblteam WHERE Team_Name='" + cboSearch.getValue()+"'";
                        Statement stmtTeam = conn.createStatement();
                        ResultSet resultTeam = stmtTeam.executeQuery(sqlTeam);
                        int i=0;
                        while(resultTeam.next()){
                            teamID = resultTeam.getInt("ID");
                            i++;
                        }
                        String sqlSearch = "SELECT FName, MName, LName, DOB, Health_Status, Team FROM tblmanager WHERE Team=" +teamID;
                        Statement stmtSearch = conn.createStatement();
                        ResultSet resultSearch = stmtSearch.executeQuery(sqlSearch);
                        
                        while(resultSearch.next()){
                            txtFName.setText(resultSearch.getString("FName"));
                            txtLName.setText(resultSearch.getString("LName"));
                            txtMName.setText(resultSearch.getString("MName"));
                            txtDOB.setText(resultSearch.getString("DOB"));
                            i++;
                        }
                    }
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
            }
        });
        //Delete button event handler
        btnDelete.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String deleteSql = "DELETE FROM tblmanager WHERE Team=" +teamID;
                    Statement deleteStatement = conn.createStatement();
                    deleteStatement.execute(deleteSql);
                    JOptionPane.showMessageDialog(null, "Manager Deleted Successfully!", "Delete Message!", INFORMATION_MESSAGE);
                    txtFName.setText("");
                    txtLName.setText("");
                    txtMName.setText("");
                    txtDOB.setText("yyyy/mm/dd");
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
            }
        });
    }
    public void getTeamData(){
        try{
            connection();
            String sql = "SELECT Team_Name FROM tblTeam";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            int i=0;
            while(result.next()){
              teamData[i] = result.getString("Team_Name");
              i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString(),"Error Message", ERROR_MESSAGE);
        }
    }
    
     public void connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
}
