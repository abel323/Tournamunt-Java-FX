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
import javax.swing.JOptionPane;
public class TeamDetail extends JFrame implements ActionListener{
    //DefaultTableModel model;
    JTable table = new JTable();
    JPanel pnlCenter = new JPanel();
    Connection conn;
    String data[][] = new String[20][4];
    String columns[] = {"Department Name", "Team_Name","Team Size","Batch"};
    JComboBox cboFilter = new JComboBox();
    JLabel lblFilter = new JLabel("Filter By: ");
    JTextField txtSearchTerm = new JTextField(50);
    JButton btnSearch = new JButton("Search");
    JPanel pnlNorth = new JPanel();
    private String []filter = {"*","Department Name", "Team Name", "Team Size", "Batch"};
    
    public TeamDetail(){
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
                    String sql = "SELECT * FROM team_detail";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Department_Name");
                        data[i][1] = result.getString("Team_Name");
                        data[i][2] = result.getInt("Team_Size")+"";
                        data[i][3] = result.getString("Batch");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="Department Name"){
                try{
                    connection();
                    String sql = "SELECT * FROM team_detail WHERE Department_Name='" + txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Department Name");
                        data[i][1] = result.getString("Team_Name");
                        data[i][2] = result.getInt("Team_Size")+"";
                        data[i][3] = result.getString("Batch");
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
            else if(cboFilter.getSelectedItem()=="Team Name"){
                try{
                    connection();
                    String sql = "SELECT * FROM team_detail WHERE Team_Name='"+txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Department_Name");
                        data[i][1] = result.getString("Team_Name");
                        data[i][2] = result.getInt("Team_Size")+"";
                        data[i][3] = result.getString("Batch");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="Team Size"){
                try{
                    connection();
                    String sql = "SELECT * FROM team_detail WHERE team_size='" + txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Department_Name");
                        data[i][1] = result.getString("Team_Name");
                        data[i][2] = result.getInt("Team_Size")+"";
                        data[i][3] = result.getString("Batch");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlCenter.add(pane, BorderLayout.CENTER);
            }
            else if(cboFilter.getSelectedItem()=="Batch"){
                try{
                    connection();
                    String sql = "SELECT * FROM team_detail WHERE Batch='" +txtSearchTerm.getText()+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Department_Name");
                        data[i][1] = result.getString("Team_Name");
                        data[i][2] = result.getInt("Team_Size")+"";
                        data[i][3] = result.getString("Batch");
                        i++;
                    }
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
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
