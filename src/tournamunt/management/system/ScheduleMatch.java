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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
public class ScheduleMatch extends Application{
    Connection conn;
    String teamData[] = new String[20];
    String fieldData[] = new String[5];
    String refreeData[] = new String[20];
    int team1, team2,refree,field;
    int matchID;
    String sql;
    //declare control variables
    Button btnAdd = new Button("Add Schedule");
    Button btnEdit = new Button("Edit Schedule");
    Button btnDelete = new Button("Delete Schedule");
    Button btnDisplay = new Button("Display Schedule");
    Button btnHome = new Button("Home");
    Label lblTeam1 = new Label("Team 1: ");
    Label lblTeam2 = new Label("Team 2: ");
    Label lblRefree = new Label("Refree: ");
    Label lblScheduleDate = new Label("Schedule Date: ");
    Label lblField = new Label("Field: ");
    TextField txtScheduleDate = new TextField("yyyy/mm/dd");
    ComboBox<String> cboTeam1 = new ComboBox();
    ComboBox<String> cboTeam2 = new ComboBox();
    ComboBox<String> cboRefree = new ComboBox();
    ComboBox<String> cboField = new ComboBox();
    BorderPane bp = new BorderPane();
    GridPane gp = new GridPane();
    HBox hBox = new HBox();
    @Override
    public void start(Stage ps){
        getTeamData();
        for(int i=0; i<teamData.length; i++){
            cboTeam1.getItems().addAll(teamData[i]);
            cboTeam2.getItems().addAll(teamData[i]);
        }
        getRefreeData();
        for(int i=0; i<refreeData.length; i++){
            cboRefree.getItems().addAll(refreeData[i]);
        }
        getFieldData();
        for(int i=0; i<fieldData.length; i++){
            cboField.getItems().addAll(fieldData[i]);
        }
        gp.add(lblTeam1, 0, 0);
        gp.add(cboTeam1, 1, 0);
        gp.add(lblTeam2, 0, 1);
        gp.add(cboTeam2, 1,1);
        gp.add(lblRefree, 0, 2);
        gp.add(cboRefree, 1, 2);
        gp.add(lblScheduleDate, 0,3);
        gp.add(txtScheduleDate, 1, 3);
        gp.add(lblField, 0, 4);
        gp.add(cboField, 1, 4);
        gp.setPadding(new Insets(15,15,15,15));
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setAlignment(Pos.CENTER);
        
        hBox = getHBox();
        bp.setBottom(hBox);
        bp.setCenter(gp);
        Scene scene = new Scene(bp,700,400);
        ps.setTitle("Schedule Management");
        ps.setScene(scene);
        ps.show();
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage hp = new HomePage();
                hp.start(ps);
            }
        });
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    sql = "SELECT ID FROM tblTeam WHERE Team_Name='" + cboTeam1.getValue()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet resultTeam1 = stmt.executeQuery(sql);
                    int i=0;
                    while(resultTeam1.next()){
                        team1 = resultTeam1.getInt("ID");
                        i++;
                    }
                    sql = "SELECT ID FROM tblTeam WHERE Team_Name='"+ cboTeam2.getValue()+"'";
                    Statement stmt1 = conn.createStatement();
                    ResultSet resultTeam2 = stmt1.executeQuery(sql);
                    while(resultTeam2.next()){
                        team2 = resultTeam2.getInt("ID");
                        i++;
                    }
                    sql = "SELECT ID FROM tblRefree WHERE Health_Status='Normal' AND ID="+cboRefree.getValue();
                    Statement stmt2 = conn.createStatement();
                    ResultSet resultRefree = stmt2.executeQuery(sql);
                    while(resultRefree.next()){
                        refree = resultRefree.getInt("ID");
                        i++;
                    }
                    
                    if(team1 == team2){
                        JOptionPane.showMessageDialog(null, "You can't match with the same team!", "Error Message", ERROR_MESSAGE);
                    }
                    else if(team1 != team2){
                        sql="INSERT INTO tblMatch(Team1,Team2,Refree) VALUES(" + team1 + "," + team2 + "," + refree + ")";
                        Statement stmtInsert = conn.createStatement();
                        stmtInsert.execute(sql);
                        JOptionPane.showMessageDialog(null, "Match added successfully!");
                        sql = "SELECT Match_ID FROM tblMatch WHERE Match_ID=(SELECT LAST_INSERT_ID())";
                        
                        
                        Statement stmtMatchID = conn.createStatement();
                        ResultSet resultMatch = stmtMatchID.executeQuery(sql);
                        while(resultMatch.next()){
                            matchID = resultMatch.getInt("Match_ID");
                            i++;
                        }
                        sql = "SELECT ID FROM tblField WHERE Place='" +cboField.getValue()+"'";
                        Statement stmtFieldID = conn.createStatement();
                        ResultSet resultField = stmtFieldID.executeQuery(sql);
                        
                        while(resultField.next()){
                            field = resultField.getInt("ID");
                            i++;
                        }
                        sql = "INSERT INTO tblSchedule(Match_ID, Field_ID,Schedule_Date) VALUES("+matchID+ ","+field+",'"+txtScheduleDate.getText()+"')";
                        Statement stmtInsertSchedule = conn.createStatement();
                        stmtInsertSchedule.execute(sql);
                        JOptionPane.showMessageDialog(null, "Schedule inserted successfully");
                    }
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message!",ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error Message", ERROR_MESSAGE);
                }
            }
        });
    }
    public HBox getHBox(){
        hBox.getChildren().addAll(btnAdd,btnEdit,btnDelete,btnDisplay,btnHome);
        hBox.setPadding(new Insets(15,15,15,15));
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    
    public void connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    private void getTeamData(){
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
                JOptionPane.showMessageDialog(null,ex.toString());
            }
        }
    }
    private void getRefreeData(){
        try{
           connection();
           String sql = "SELECT * FROM tblRefree";
           Statement stmt = conn.createStatement();
           ResultSet result = stmt.executeQuery(sql);
           int i=0;
           while(result.next()){
               refreeData[i] = result.getInt("ID")+"";
               i++;
           }
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
    private void getFieldData(){
        try{
            connection();
            String sql = "SELECT Place FROM tblfield";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            int i=0;
            while(result.next()){
                fieldData[i] = result.getString("Place");
                i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        finally{
            try{
                conn.close();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex.toString(), "Error Mesaage", ERROR_MESSAGE);
            }
        }
    }
}
