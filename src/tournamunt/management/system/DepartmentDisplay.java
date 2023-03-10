/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamunt.management.system;

import javafx.beans.property.SimpleIntegerProperty;



/**
 *
 * @author Abel
 */
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JScrollPane;
public class DepartmentDisplay extends JFrame implements ActionListener{
    private String columns[] = {"Department ID", "Department Name"};
    private String data[][] = new String[10][2];
    JButton btnSearch = new JButton("Search");
    JLabel lblSearchBy = new JLabel("Search By: ");
    JComboBox cboSearchBy = new JComboBox();
    JLabel lblSearchTerm = new JLabel("Search: ");
    JTextField txtSearch = new JTextField(50);
    JPanel pnlNorthContiner = new JPanel();
    JPanel pnlCenter = new JPanel();
    
    private String searchByVale;
    private final String searchBy[] = {"Department ID","Department Name"}; 
    //DefaultTableModel model = new DefaultTableModel(data,columns);
    JTable table = new JTable();
    private Connection conn;
    public void connection() throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/tournamunt_managment_system_demo", "root", "16761200");
    }
    public DepartmentDisplay(){
        setLayout(new BorderLayout());
        for(int i=0; i<searchBy.length; i++){
            cboSearchBy.addItem(searchBy[i]);
        }
        pnlNorthContiner.setLayout(new FlowLayout());
        pnlNorthContiner.setBackground(Color.YELLOW);
        pnlNorthContiner.add(lblSearchBy);
        pnlNorthContiner.add(cboSearchBy);
        pnlNorthContiner.add(lblSearchTerm);
        pnlNorthContiner.add(txtSearch);
        pnlNorthContiner.add(btnSearch);
        add(pnlNorthContiner, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        //Regiser event listeners
        btnSearch.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnSearch){
            if(cboSearchBy.getSelectedItem()=="Department ID"){
                try{
                   connection();
                   String sql = "SELECT * FROM tbldepartment WHERE Department_ID=" + Integer.parseInt(txtSearch.getText());
                   Statement stmt = conn.createStatement();
                   ResultSet resultSet = stmt.executeQuery(sql);
                   int i=0;
                   while(resultSet.next()){
                       data[i][0] = resultSet.getInt("Department_ID")+"";
                       data[i][1] = resultSet.getString("Department_Name");
                       i++;
                   }
                   
                   DefaultTableModel model = new DefaultTableModel(data,columns);
                   table.setModel(model);
                   table.setShowGrid(true);
                   table.setShowVerticalLines(true);
                   JScrollPane pane = new JScrollPane(table);
                   pnlCenter.add(pane);
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Message!", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Message!", ERROR_MESSAGE);
                }
                finally{
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DepartmentDisplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else if(cboSearchBy.getSelectedItem()=="Department Name"){
                try{
                   connection();
                   String sql = "SELECT * FROM tbldepartment WHERE Department_Name='" + txtSearch.getText()+"'";
                   Statement stmt = conn.createStatement();
                   ResultSet resultSet = stmt.executeQuery(sql);
                   int i=0;
                   while(resultSet.next()){
                       data[i][0] = resultSet.getInt("Department_ID")+"";
                       data[i][1] = resultSet.getString("Department_Name");
                       i++;
                   }
                   
                   DefaultTableModel model = new DefaultTableModel(data,columns);
                   table.setModel(model);
                   table.setShowGrid(true);
                   table.setShowVerticalLines(true);
                   JScrollPane pane = new JScrollPane(table);
                   pnlCenter.add(pane);
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Message!", ERROR_MESSAGE);
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString(), "Message!", ERROR_MESSAGE);
                }
                finally{
                    try{
                        conn.close();
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null,ex.toString(),"MESSAGE", ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
