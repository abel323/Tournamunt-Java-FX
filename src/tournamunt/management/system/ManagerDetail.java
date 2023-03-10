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

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.ActiveEvent.*;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ManagerDetail extends JFrame implements ActionListener{
    //DefaultTableModel model=  new DefaultTableModel();
    JTable table = new JTable();
    JPanel pnlCenter = new JPanel();
    Connection conn;
    String data[][] = new String[20][6];
    String columns[] = {"First Name", "Middle Name", "Last Name", "Date Of Birth", "Health Status", "Team Name"};
    JComboBox cboFilter = new JComboBox();
    JLabel lblFilter = new JLabel("Filter By: ");
    JTextField txtSearchTerm = new JTextField(50);
    JButton btnSearch = new JButton("Search");
    JPanel pnlNorth = new JPanel();
    private String []filter = {"*","First Name", "Last Name", "Middle Name", "Date Of Birth", "Health Status", "Team_Name"};
    
    public ManagerDetail(){
        for(int i=0; i<filter.length; i++){
            cboFilter.addItem(filter[i]);
        }
        setLayout(new BorderLayout());
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblFilter);
        pnlNorth.add(cboFilter);
        pnlNorth.add(txtSearchTerm);
        pnlNorth.add(btnSearch);
        pnlCenter.setLayout(new BorderLayout());
        add(pnlNorth, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        btnSearch.addActionListener(this);
    }
    
    public void connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnSearch){
            if(cboFilter.getSelectedItem()=="*"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="First Name"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail WHERE FName='" + txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="MName"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail WHERE MName='"+txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="LName"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail WHERE LMame='" + txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="DOB"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail WHERE DOB='" +txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="Team_Name"){
                try{
                    connection();
                    String sql = "SELECT * FROM manager_detail WHERE Team_Name='" + txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("FName");
                        data[i][1] = result.getString("MName");
                        data[i][2] = result.getString("LName");
                        data[i][3] = result.getString("DOB");
                        data[i][4] = result.getString("Health_Status");
                        data[i][5] = result.getString("Team_Name");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    
                }
                catch(ClassNotFoundException ex){
                    
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
        }
    }
}
