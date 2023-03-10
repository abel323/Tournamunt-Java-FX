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
public class ScheduleDetail extends JFrame implements ActionListener{
        JTable table = new JTable();
    JPanel pnlCenter = new JPanel();
    Connection conn;
    String data[][] = new String[20][3];
    String columns[] = {"Team1","Team2","Schedule Date"};
    JComboBox cboFilter = new JComboBox();
    JLabel lblFilter = new JLabel("Filter By: ");
    JTextField txtSearchTerm = new JTextField(50);
    JButton btnSearch = new JButton("Search");
    JPanel pnlNorth = new JPanel();
    JScrollPane pane;
    private String []filter = {"*"};
    
    public ScheduleDetail(){
        for(int i=0; i<filter.length; i++){
            cboFilter.addItem(filter[i]);
        }
        txtSearchTerm.disable();
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
                    String sql = "SELECT * FROM viewschedule";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getInt("Team1")+"";
                        data[i][1] = result.getInt("Team2")+"";
                        data[i][2] = result.getString("Schedule_Date")+"";
                        
                        i++;
                    }
                    DefaultTableModel model = new DefaultTableModel(data,columns);
                    table.setModel(model);
                    table.setShowGrid(true);
                    table.setShowVerticalLines(true);
                    pane = new JScrollPane(table);
                    pnlCenter.add(pane);
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
                catch(ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        }
    }
}
