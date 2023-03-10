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
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
public class Player extends Application{
    Connection conn;
    String depData[] = new String[10];
    BorderPane bp = new BorderPane();
    FlowPane fpNorth = new FlowPane();
    Label lblID = new Label("Player ID: ");
    TextField txtID = new TextField();
    Button btnSearch = new Button("Search Player");
    String teamData[] = new String[10];
    int teamID,depId;
    String Health_Status[] = {"Normal","Not Normal", "Exceptional"};
    FlowPane fp = new FlowPane();
    GridPane gp = new GridPane();
    Button btnAdd = new Button("Register Player");
    Button btnEdit = new Button("Edit Palyer");
    Button btnDisplay = new Button("Display");
    Button btnDelete = new Button("Delete Player");
    Button btnHome = new Button("Home");
    TextField txtFName = new TextField();
    TextField txtMName = new TextField();
    TextField txtLName = new TextField();
    TextField txtDOB = new TextField();
    ComboBox cboHStatus = new ComboBox();
    ComboBox cboDepartment = new ComboBox();
    ComboBox cboTeam = new ComboBox();
    Label lblFName = new Label("First Name: ");
    Label lblMName = new Label("Middle Name: ");
    Label lblLName = new Label("Last Name: ");
    Label lblDOB = new Label("Date Of Birth: ");
    Label lblHStatus = new Label("Health Status: ");
    Label lblDepartment = new Label("Department: ");
    Label lblTeam = new Label("Team: ");
    @Override
    public void start(Stage ps){
        for(int i=0; i<Health_Status.length; i++){
            cboHStatus.getItems().addAll(Health_Status[i]);
        }
        getTeamData();
        for(int i=0; i<teamData.length; i++){
            cboTeam.getItems().addAll(teamData[i]);
        }
        getDepartment();
        for(int i=0; i<depData.length; i++){
            cboDepartment.getItems().addAll(depData[i]);
        }
        fpNorth.getChildren().addAll(lblID,txtID,btnSearch);
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
        gp.add(lblDepartment, 0, 5);
        gp.add(cboDepartment, 1, 5);
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
        bp.setTop(fpNorth);
        bp.setCenter(gp);
        bp.setBottom(fp);
        Scene scene = new Scene(bp,900,500);
        ps.setTitle("Player Management");
        ps.setScene(scene);
        ps.show();
        //Display event handler
        btnDisplay.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                PlayerDetail pd = new PlayerDetail();
                pd.setTitle("Player List");
                pd.setSize(800,600);
                pd.setVisible(true);
                pd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        //Register button event handler
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    //getTeamID();
                    String sqlInsert = "INSERT INTO tblplayer(FName,MName,LName,DOB,Health_Status,Department,Team) VALUES"
                            + "('"+txtFName.getText()+"','"+txtMName.getText()+"','"+txtLName.getText()+"','"+txtDOB.getText()+"','"+cboHStatus.getValue()+"',"+getDepID()+","+getTeamID()+")";
                    Statement stmtInsert = conn.createStatement();
                    stmtInsert.execute(sqlInsert);
                    JOptionPane.showMessageDialog(null, "Player Registered Successfully!", "Information Message!", INFORMATION_MESSAGE);
                    clearTextFields();
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
                }
                finally{
                    try{
                        conn.close();
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
                    }
                }
            }
        });
        //Search button event handler
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String sqlSearch = "SELECT * FROM player_detail WHERE ID="+ Integer.parseInt(txtID.getText());
                    Statement stmtSearch = conn.createStatement();
                    ResultSet resultSearch = stmtSearch.executeQuery(sqlSearch);
                    int i=0;
                    while(resultSearch.next()){
                        txtFName.setText(resultSearch.getString("FName"));
                        txtLName.setText(resultSearch.getString("LName"));
                        txtMName.setText(resultSearch.getString("MName"));
                        txtDOB.setText(resultSearch.getString("DOB"));
                        cboTeam.setValue(resultSearch.getString("Team_Name"));
                        cboDepartment.setValue(resultSearch.getString("Department_Name"));
                        cboHStatus.setValue(resultSearch.getString("Health_Status"));
                    }
                   
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
                }
                finally{
                    try{
                        conn.close();
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
                    }
                }
            }
        });
        btnEdit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String sqlUpdate = "UPDATE tblPlayer SET FName='" + txtFName.getText() + "', MName='"+txtMName.getText()+"', LName='"+txtLName.getText()+"', DOB='"+txtDOB.getText()+"',"
                            + " Health_Status='"+cboHStatus.getValue()+"', Team=" +getTeamID()+ ", Department=" +getDepID()+" WHERE ID="+Integer.parseInt(txtID.getText());
                    Statement stmtUpdate = conn.createStatement();
                    stmtUpdate.execute(sqlUpdate);
                    JOptionPane.showMessageDialog(null, "Player Data Updated Successfully!", "INFO MESSAGE", INFORMATION_MESSAGE);
                    clearTextFields();
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
                        JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
                    }
                }
            }
        });
        //Delete button event handler
        btnDelete.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                    connection();
                    String sqlDelete = "DELETE FROM tblplayer WHERE ID="+Integer.parseInt(txtID.getText());
                    Statement stmtDelete = conn.createStatement();
                    stmtDelete.execute(sqlDelete);
                    JOptionPane.showMessageDialog(null, "Player Deleted Successfully!", "Information Message", INFORMATION_MESSAGE);
                    clearTextFields();
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "ERROR MESSAGE", ERROR_MESSAGE);
                }
            }
        });
        //Home Button Event Handler
        btnHome.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                HomePage hp = new HomePage();
                hp.start(ps);
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
    public void connection()throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    
    public int getTeamID(){
        try{
            connection();
            String sqlTeam = "SELECT ID FROM tblteam WHERE Team_Name='" + cboTeam.getValue() + "'";
            Statement stmtTeam = conn.createStatement();
            ResultSet resultTeam = stmtTeam.executeQuery(sqlTeam);
            int i=0;
            while(resultTeam.next()){
                teamID = resultTeam.getInt("ID");
                i++;
            }
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
        }
        return teamID;
    }
        public int getDepID(){
        try{
            connection();
            String sqlDep = "SELECT Department_ID FROM tbldepartment WHERE Department_Name='" + cboDepartment.getValue() + "'";
            Statement stmtDep = conn.createStatement();
            ResultSet resultDep = stmtDep.executeQuery(sqlDep);
            int i=0;
            while(resultDep.next()){
                depId = resultDep.getInt("Department_ID");
                i++;
            }
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString(),"Error Message", ERROR_MESSAGE);
        }
        return depId;
    }
    public void clearTextFields(){
        txtFName.setText("");
        txtID.setText("");
        txtLName.setText("");
        txtMName.setText("");
        txtDOB.setText("");
        
    }
}
