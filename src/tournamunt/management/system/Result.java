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
import java.sql.Connection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
public class Result extends Application{
    Connection conn;
    private String []teamData = new String[10];
    int teamID1;
    int teamID2;
    Label lblTeam1 = new Label("Team 1 ");
    ComboBox cboTeam1 = new ComboBox();
    Label lblTeam2 = new Label("Team 2 ");
    ComboBox cboTeam2 = new ComboBox();
    Button btnSearch = new Button("Search Match");
    FlowPane fpNorth = new FlowPane();
    BorderPane bp = new BorderPane();
    GridPane gp = new GridPane();
    Label lblFirstTeam = new Label("Team1: ");
    Label lblFirTeam = new Label();
    TextField txtTeam1 = new TextField();
    Label lblSecondTeam = new Label("Team2: ");
    Label lblSecTeam = new Label();
    TextField txtTeam2 = new TextField();
    Label lblGoal = new Label("Goal: ");
    Label lblPossession = new Label("Possession: ");
    TextField txtStatTeam1 = new TextField();
    TextField txtStatTeam2 = new TextField();
    Button btnAddResult = new Button("Add Result");
    Button btnEditResult = new Button("Edit Result");
    Button btnDisplay = new Button("Display");
    Button btnHome = new Button("Home");
    FlowPane fpSouth = new FlowPane();
    @Override
    public void start(Stage ps){
        getTeamData();
        for(int i=0; i<teamData.length; i++){
            cboTeam1.getItems().addAll(teamData[i]);
            cboTeam2.getItems().addAll(teamData[i]);
        }
        fpNorth.getChildren().addAll(lblTeam1, cboTeam1, lblTeam2, cboTeam2, btnSearch);
        fpNorth.setAlignment(Pos.CENTER);
        fpNorth.setHgap(15);
        
        fpSouth.getChildren().addAll(btnAddResult,btnEditResult,btnDisplay,btnHome);
        fpSouth.setHgap(15);
        fpSouth.setAlignment(Pos.CENTER);
        
        gp.add(lblFirstTeam, 0, 0);
        gp.add(lblFirTeam, 1, 0);
        gp.add(lblSecondTeam, 2, 0);
        gp.add(lblSecTeam, 3, 0);
        gp.add(lblGoal, 0, 1);
        gp.add(txtTeam1, 1, 1);
        gp.add(txtTeam2, 2, 1);
        gp.add(lblPossession, 0, 2);
        gp.add(txtStatTeam1, 1, 2);
        gp.add(txtStatTeam2, 2, 2);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(15,15,15,15));
        gp.setHgap(15);
        gp.setVgap(15);
        bp.setTop(fpNorth);
        bp.setCenter(gp);
        bp.setBottom(fpSouth);
        Scene scene = new Scene(bp,900,400);
        ps.setTitle("Result");
        ps.setScene(scene);
        ps.show();
        
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage hp = new HomePage();
                hp.start(ps);
            }
        });
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String sqlTeam1 = "SELECT Team_Name FROM tblteam WHERE Team_Name='" + cboTeam1.getValue()+"'";
                    String sqlTeam2 = "SELECT Team_Name FROM tblteam WHERE Team_Name='" + cboTeam2.getValue()+"'";
                    Statement stmtTeam1 = conn.createStatement();
                    Statement stmtTeam2 = conn.createStatement();
                    ResultSet resultTeam1 = stmtTeam1.executeQuery(sqlTeam1);
                    ResultSet resultTeam2 = stmtTeam2.executeQuery(sqlTeam2);
                    int i=0;
                    while(resultTeam1.next()){
                        lblFirTeam.setText(resultTeam1.getString("Team_Name"));
                        i++;
                    }
                    while(resultTeam2.next()){
                        lblSecTeam.setText(resultTeam2.getString("Team_Name"));
                        i++;
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
        btnAddResult.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    getTeamID1();
                    getTeamID2();
                    String sqlUpdateMatch = "UPDATE tblmatch SET Goal_Team1="+Integer.parseInt(txtTeam1.getText())+", Goal_Team2=" + Integer.parseInt(txtTeam2.getText()) 
                            +", Stat_Team1="+txtStatTeam1.getText() + ", Stat_Team2="+txtStatTeam2.getText()+" WHERE Team1="+teamID1+ " AND Team2="+teamID2;
                    Statement stmtUpdateMatch = conn.createStatement();
                    stmtUpdateMatch.execute(sqlUpdateMatch);
                    JOptionPane.showMessageDialog(null, "Match Update Data", "Success!", INFORMATION_MESSAGE);
                    if(Integer.parseInt(txtTeam1.getText())>Integer.parseInt(txtTeam2.getText())){
                        
                        int teamPoint1=0;
                        int totalGoalScored=0;
                        int totalGoalConced=0;
                        int aggrigate_goal=0;
                        int matchPlay=0;
                        int win=0;
                        int loss=0;
                         String selectSql = "SELECT Match_Played, Wins,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID1;
                         Statement stmtSelect = conn.createStatement();
                         ResultSet resultSelect = stmtSelect.executeQuery(selectSql);
                         while(resultSelect.next()){
                             teamPoint1=resultSelect.getInt("Total_Point");
                             matchPlay=resultSelect.getInt("Match_Played");
                             totalGoalScored=resultSelect.getInt("Goal_scored");
                             totalGoalConced=resultSelect.getInt("Goal_conced");
                             aggrigate_goal=resultSelect.getInt("Aggrigate_Goal");
                             win = resultSelect.getInt("Wins");
                         }
                         teamPoint1 = teamPoint1 + 3;
                         totalGoalScored=totalGoalScored+Integer.parseInt(txtTeam1.getText());
                         totalGoalConced=totalGoalConced+Integer.parseInt(txtTeam2.getText());
                         aggrigate_goal=aggrigate_goal+(totalGoalScored-totalGoalConced);
                         win = win+1;
                         matchPlay=matchPlay+1;
                         String sqlUpdatePoint="UPDATE tblpoint SET Match_Played="+matchPlay+", Wins="+win+", Goal_conced="+totalGoalConced+", Goal_scored="+totalGoalScored+", Aggrigate_goal="+aggrigate_goal+", "
                                 + " Total_Point="+teamPoint1+" WHERE Team_ID="+teamID1;
                         Statement stmtUpdate = conn.createStatement();
                         stmtUpdate.execute(sqlUpdatePoint);
                         JOptionPane.showMessageDialog(null, "Winner Team Data Updated!", "Info Message", INFORMATION_MESSAGE);
                         
                         String selectSqlTeam2 = "SELECT Loss,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID2;
                         Statement stmtSelect2 = conn.createStatement();
                         ResultSet resultSelect2 = stmtSelect2.executeQuery(selectSqlTeam2);
                         while(resultSelect2.next()){
                             teamPoint1=resultSelect2.getInt("Total_Point");
                             totalGoalScored=resultSelect2.getInt("Goal_scored");
                             matchPlay=resultSelect2.getInt("Match_Played");
                             totalGoalConced=resultSelect2.getInt("Goal_conced");
                             aggrigate_goal=resultSelect2.getInt("Aggrigate_Goal");
                             win = resultSelect2.getInt("Loss");
                         }
                         teamPoint1 = teamPoint1 + 0;
                         totalGoalScored=totalGoalScored+Integer.parseInt(txtTeam2.getText());
                         totalGoalConced=totalGoalConced+Integer.parseInt(txtTeam1.getText());
                         aggrigate_goal=aggrigate_goal+(totalGoalScored-totalGoalConced);
                         loss = loss+1;
                         String sqlUpdatePoint1="UPDATE tblpoint SET Match_Played="+matchPlay+", Loss="+loss+", Goal_conced="+totalGoalConced+", Goal_scored="+totalGoalScored+", Aggrigate_goal="+aggrigate_goal+", "
                                 + " Total_Point="+teamPoint1+" WHERE Team_ID="+teamID2;
                         Statement stmtUpdate1 = conn.createStatement();
                         stmtUpdate1.execute(sqlUpdatePoint1);
                         JOptionPane.showMessageDialog(null, "Lost Team Point Data Updated!", "Sucess!", INFORMATION_MESSAGE);
                    }
                    else if(Integer.parseInt(txtTeam2.getText())>Integer.parseInt(txtTeam1.getText())){
                        int teamPoint1=0;
                        int totalGoalScored=0;
                        int totalGoalConced=0;
                        int aggrigate_goal=0;
                        int matchPlay=0;
                        int win=0;
                        int loss=0;
                         String selectSql = "SELECT Match_Played, Wins,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID2;
                         Statement stmtSelect = conn.createStatement();
                         ResultSet resultSelect = stmtSelect.executeQuery(selectSql);
                         while(resultSelect.next()){
                             teamPoint1=resultSelect.getInt("Total_Point");
                             matchPlay=resultSelect.getInt("Match_Played");
                             totalGoalScored=resultSelect.getInt("Goal_scored");
                             totalGoalConced=resultSelect.getInt("Goal_conced");
                             aggrigate_goal=resultSelect.getInt("Aggrigate_Goal");
                             win = resultSelect.getInt("Wins");
                         }
                         teamPoint1 = teamPoint1 + 3;
                         totalGoalScored=totalGoalScored+Integer.parseInt(txtTeam2.getText());
                         totalGoalConced=totalGoalConced+Integer.parseInt(txtTeam1.getText());
                         aggrigate_goal=aggrigate_goal+(totalGoalScored-totalGoalConced);
                         win = win+1;
                         matchPlay=matchPlay+1;
                         String sqlUpdatePoint="UPDATE tblpoint SET Match_Played="+matchPlay+", Wins="+win+", Goal_conced="+totalGoalConced+", Goal_scored="+totalGoalScored+", Aggrigate_goal="+aggrigate_goal+", "
                                 + " Total_Point="+teamPoint1+" WHERE Team_ID="+teamID2;
                         Statement stmtUpdate = conn.createStatement();
                         stmtUpdate.execute(sqlUpdatePoint);
                         JOptionPane.showMessageDialog(null, "Winner Team Data Updated!", "Info Message", INFORMATION_MESSAGE);
                         
                         String selectSqlTeam2 = "SELECT Loss,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID1;
                         Statement stmtSelect2 = conn.createStatement();
                         ResultSet resultSelect2 = stmtSelect2.executeQuery(selectSqlTeam2);
                         while(resultSelect2.next()){
                             teamPoint1=resultSelect2.getInt("Total_Point");
                             totalGoalScored=resultSelect2.getInt("Goal_scored");
                             matchPlay=resultSelect2.getInt("Match_Played");
                             totalGoalConced=resultSelect2.getInt("Goal_conced");
                             aggrigate_goal=resultSelect2.getInt("Aggrigate_Goal");
                             win = resultSelect2.getInt("Loss");
                         }
                         teamPoint1 = teamPoint1 + 0;
                         totalGoalScored=totalGoalScored+Integer.parseInt(txtTeam1.getText());
                         totalGoalConced=totalGoalConced+Integer.parseInt(txtTeam2.getText());
                         aggrigate_goal=aggrigate_goal+(totalGoalScored-totalGoalConced);
                         loss = loss+1;
                         String sqlUpdatePoint1="UPDATE tblpoint SET Match_Played="+matchPlay+", Loss="+loss+", Goal_conced="+totalGoalConced+", Goal_scored="+totalGoalScored+", Aggrigate_goal="+aggrigate_goal+", "
                                 + " Total_Point="+teamPoint1+" WHERE Team_ID="+teamID1;
                         Statement stmtUpdate1 = conn.createStatement();
                         stmtUpdate1.execute(sqlUpdatePoint1);
                         JOptionPane.showMessageDialog(null, "Lost Team Point Data Updated!", "Sucess!", INFORMATION_MESSAGE);
                    }
                    else if(Integer.parseInt(txtTeam1.getText())==Integer.parseInt(txtTeam2.getText())){
                       String selectTeamPoint = "SELECT Draw,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID1;
                       String selectTeamPoint1 = "SELECT Draw,Goal_conced,Goal_scored,Aggrigate_Goal,Total_Point FROM tblpoint WHERE Team_ID="+teamID2;
                       Statement stmtTeam1 = conn.createStatement();
                       Statement stmtTeam2 = conn.createStatement();
                       int draw=0, GoalConced=0, GoalScored=0,Aggrigate_Goal=0,TotalPoint=0;
                       int draw1=0,GoalConced1=0, GoalScored1=0, Aggrigate_Goal1=0,TotalPoint1=0;
                       ResultSet resultTeam1 = stmtTeam1.executeQuery(selectTeamPoint);
                       ResultSet resultTeam2 = stmtTeam2.executeQuery(selectTeamPoint1);
                       while(resultTeam1.next()){
                           draw = resultTeam1.getInt("Draw");
                           GoalConced = resultTeam1.getInt("Goal_conced");
                           GoalScored = resultTeam1.getInt("Goal_scored");
                           Aggrigate_Goal=resultTeam1.getInt("Aggrigate_Goal");
                           TotalPoint = resultTeam1.getInt("Total_Point");
                       }
                       draw=draw+1;
                       GoalConced=GoalConced+Integer.parseInt(txtTeam2.getText());
                       GoalScored=GoalScored+Integer.parseInt(txtTeam1.getText());
                       Aggrigate_Goal = Aggrigate_Goal+(GoalScored-GoalConced);
                       TotalPoint=TotalPoint+1;
                       String updateSqlTeam1 = "UPDATE tblpoint SET Draw="+draw+", Goal_scored="+GoalScored+", GoalConced="+GoalConced+", Aggrigate_Goal="+Aggrigate_Goal+", Total_Point="+TotalPoint+" WHERE Team_ID="+teamID1;
                       Statement stmtUpdate = conn.createStatement();
                       stmtUpdate.execute(updateSqlTeam1);
                       while(resultTeam2.next()){
                           draw1 = resultTeam1.getInt("Draw");
                           GoalConced1 = resultTeam1.getInt("Goal_conced");
                           GoalScored1 = resultTeam1.getInt("Goal_scored");
                           Aggrigate_Goal1=resultTeam1.getInt("Aggrigate_Goal");
                           TotalPoint1 = resultTeam1.getInt("Total_Point");
                       }
                       draw1=draw1+1;
                       GoalConced1=GoalConced1+Integer.parseInt(txtTeam1.getText());
                       GoalScored1=GoalScored1+Integer.parseInt(txtTeam2.getText());
                       Aggrigate_Goal1 = Aggrigate_Goal1+(GoalScored1-GoalConced1);
                       TotalPoint1=TotalPoint1+1;
                       String updateSqlTeam2 = "UPDATE tblpoint SET Draw="+draw1+", Goal_scored="+GoalScored1+", GoalConced="+GoalConced1+", Aggrigate_Goal="+Aggrigate_Goal1+", Total_Point="+TotalPoint1+" WHERE Team_ID="+teamID2;
                       Statement stmtUpdateTeam2 = conn.createStatement();
                       stmtUpdateTeam2.execute(updateSqlTeam2);
                       JOptionPane.showMessageDialog(null, "Team Point Updated Successfully", "Sucess", INFORMATION_MESSAGE);
                       
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
    }
    public void connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");        
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
    
    public int getTeamID1(){
        try{
            connection();
            String sqlTeam = "SELECT ID FROM tblteam WHERE Team_Name='" + cboTeam1.getValue() + "'";
            Statement stmtTeam = conn.createStatement();
            ResultSet resultTeam = stmtTeam.executeQuery(sqlTeam);
            int i=0;
            while(resultTeam.next()){
                teamID1 = resultTeam.getInt("ID");
                i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        return teamID1;
    }
        public int getTeamID2(){
        try{
            connection();
            String sqlTeam = "SELECT ID FROM tblteam WHERE Team_Name='" + cboTeam2.getValue() + "'";
            Statement stmtTeam = conn.createStatement();
            ResultSet resultTeam = stmtTeam.executeQuery(sqlTeam);
            int i=0;
            while(resultTeam.next()){
                teamID2 = resultTeam.getInt("ID");
                i++;
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
        }
        return teamID2;
    }
}
