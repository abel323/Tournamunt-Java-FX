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
public class Standing extends JFrame implements ActionListener{
        JTable table = new JTable();
    JPanel pnlCenter = new JPanel();
    Connection conn;
    String data[][] = new String[20][9];
    String columns[] = {"Team_Name", "Match_Played","Wins", "Draw","Loss" ,"Goal Conced", "Goal Scored", "Aggrigate Goal", "Total Point"};
    JComboBox cboFilter = new JComboBox();
    JLabel lblFilter = new JLabel("Filter By: ");
    JTextField txtSearchTerm = new JTextField(50);
    JButton btnSearch = new JButton("Search");
    JPanel pnlNorth = new JPanel();
    JScrollPane pane;
    private String []filter = {"*"};
    
    public Standing(){
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
                    String sql = "SELECT * FROM point_view ORDER BY Total_Point DESC";
                    Statement stmt = conn.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    int i=0;
                    while(result.next()){
                        data[i][0] = result.getString("Team_Name");
                        data[i][1] = result.getInt("Match_Played")+"";
                        data[i][2] = result.getInt("Wins")+"";
                        data[i][3] = result.getInt("Draw")+"";
                        data[i][4] = result.getInt("Loss")+"";
                        data[i][5] = result.getInt("Goal_conced")+"";
                        data[i][6] = result.getInt("Goal_Scored")+"";
                        data[i][7] = result.getInt("Aggrigate_Goal")+"";
                        data[i][8] = result.getInt("Total_Point")+"";
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
