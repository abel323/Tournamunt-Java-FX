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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class Refree extends JFrame implements ActionListener{
    private Connection conn;
    private String URL="jdbc:mysql://localhost:3307/tournamunt_managment_system_demo";
    private String user="root";
    private String password="16761200";
    //Declare componenets of frame
    JLabel lblFName = new JLabel("First Name: ");
    JLabel lblLName = new JLabel("Last Name: ");
    JLabel lblMName = new JLabel("Middle Name: ");
    JLabel lblDOB = new JLabel("Date Of Birth");
    JLabel lblHStatus = new JLabel("Health Status: ");
    JTextField txtFName = new JTextField();
    JTextField txtMName = new JTextField();
    JTextField txtLName = new JTextField();
    JTextField txtDOB = new JTextField();
    JComboBox cboHStatus = new JComboBox();
    JButton btnRegister = new JButton("Register Refree");
    JButton btnDelete = new JButton("Delete");
    JButton btnDisplay = new JButton("Display");
    JButton btnEdit = new JButton("Edit");
    JButton btnLogOut = new JButton("LogOut");
    JButton btnHome = new JButton("Home");
    JPanel pnlFormContainer = new JPanel();
    JPanel pnlTablecontiner = new JPanel();
    JPanel pnlButtonContainer = new JPanel();
    JPanel pnlTableContainer = new JPanel();
    JTable table = new JTable();
    String columns[] = {"ID", "First Name", "Middle Name", "Last Name", "Date Of Birth", "Health Status"};
    String data[][] = new String[10][6];
    private final String HealthStatus[] = {"Normal", "Exceptional Normal", "Not Healthy"};
    //A function that connect with mysql
    public void connection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(URL, user, password);
    }
    //constructor
    public Refree(){
        //Set frame layout
        setLayout(new BorderLayout());
        //Add items of combobox from the array
        for(int i=0; i<HealthStatus.length; i++){
            cboHStatus.addItem(HealthStatus[i]);
            
        }
        pnlFormContainer.setLayout(new GridLayout(6,2,10,5));
        pnlFormContainer.setSize(500,300);
        pnlFormContainer.add(lblFName, 0, 0);
        pnlFormContainer.add(txtFName, 0,1);
        pnlFormContainer.add(lblMName, 1,0);
        pnlFormContainer.add(txtMName, 1,1);
        pnlFormContainer.add(lblLName, 2,0);
        pnlFormContainer.add(txtLName, 2,1);
        pnlFormContainer.add(lblDOB, 3,0);
        pnlFormContainer.add(txtDOB, 3,1);
        pnlFormContainer.add(lblHStatus, 4,0);
        pnlFormContainer.add(cboHStatus, 4,1);
        pnlButtonContainer.setLayout(new FlowLayout());
        pnlButtonContainer.add(btnRegister);
        pnlButtonContainer.add(btnDisplay);
        pnlButtonContainer.add(btnEdit);
        pnlButtonContainer.add(btnHome);
        pnlButtonContainer.add(btnLogOut);
        //register event listeeners
        btnRegister.addActionListener(this);
        btnDisplay.addActionListener(this);
        btnEdit.addActionListener(this);
        btnHome.addActionListener(this);
        btnLogOut.addActionListener(this);
        
        add(pnlFormContainer, BorderLayout.NORTH);
        add(pnlButtonContainer, BorderLayout.SOUTH);
        add(pnlTableContainer, BorderLayout.CENTER);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnDisplay){
            try{
                connection();
                String sql="SELECT * FROM tblrefree";
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(sql);
                int i=0;
                while(result.next()){
                    data[i][0] = result.getInt("ID")+"";
                    data[i][1] = result.getString("FName");
                    data[i][2] = result.getString("MName");
                    data[i][3] = result.getString("LName");
                    data[i][4] = result.getString("DOB");
                    data[i][5] = result.getString("Health_Status");
                    i++;
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlTableContainer.add(pane);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
            catch(ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
        }
        else if(e.getSource()== btnRegister){
            try{
                connection();
                String sql = "INSERT INTO tblrefree(FName,MName,LName,DOB,Health_Status) VALUES('" + txtFName.getText() + "','" + txtMName.getText() + "','" + txtLName.getText()+ "','" + txtDOB.getText() + "','" + cboHStatus.getSelectedItem() + "')";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                JOptionPane.showMessageDialog(null, "Refree Registered Successfully!");
                String sql1 = "SELECT * FROM tblrefree";
                Statement stmt1 = conn.createStatement();
                ResultSet result = stmt1.executeQuery(sql1);
                int i=0;
                while(result.next()){
                    data[i][0] = result.getInt("ID")+"";
                    data[i][1] = result.getString("FName");
                    data[i][2] = result.getString("MName");
                    data[i][3] = result.getString("LName");
                    data[i][4] = result.getString("DOB");
                    data[i][5] = result.getString("Health_Status");
                    i++;
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlTableContainer.add(pane);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
            catch(ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
        }
        else if(e.getSource()==btnHome){
            this.dispose();
        }
        else if(e.getSource()==btnEdit){
            String fname = txtFName.getText();
            String mname = txtMName.getText();
            String lname = txtLName.getText();
            String dob = txtDOB.getText();
            String hStatus = (String) cboHStatus.getSelectedItem();
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Refree ID To Be Edited: "));
            try{
                connection();
                String sql = "UPDATE tblrefree SET FName='" + fname + "', MName='" + mname + "', LName='" + lname + "', DOB='" + dob + "', Health_Status='"+hStatus+"' WHERE ID="+id;
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                JOptionPane.showMessageDialog(null, "Refree Data Updated Successfully!");
                String sql1 = "SELECT * FROM tblrefree";
                ResultSet result = stmt.executeQuery(sql1);
                int i=0;
                while(result.next()){
                    data[i][0] = result.getInt("ID")+"";
                    data[i][1] = result.getString("FName");
                    data[i][2] = result.getString("MName");
                    data[i][3] = result.getString("LName");
                    data[i][4] = result.getString("DOB");
                    data[i][5] = result.getString("Health_Status");
                    i++;
                }
                DefaultTableModel model = new DefaultTableModel(data,columns);
                table.setModel(model);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                pnlTableContainer.add(pane);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
            catch(ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }
}
