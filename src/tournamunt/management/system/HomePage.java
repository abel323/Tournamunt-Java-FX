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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class HomePage extends Application{
    
    public void start(Stage ps){
        GridPane gp = new GridPane();
        Button btnTeam = new Button("Team");
        Button btnDepartment = new Button("Department");
        Button btnPlayer = new Button("Player");
        Button btnSchedule = new Button("Schedule");
        Button btnStanding = new Button("Standing");
        Button btnField = new Button("Field Management");
        Button btnRefree = new Button("Refree");
        Button btnManager = new Button("Manager");
        Button btnResult = new Button("Result");
        gp.add(btnTeam, 0,0);
        gp.add(btnDepartment, 0, 1);
        gp.add(btnPlayer, 0,2);
        gp.add(btnSchedule, 0,3);
        gp.add(btnStanding, 1,0);
        gp.add(btnField, 1,1);
        gp.add(btnRefree,1,2);
        gp.add(btnManager, 1, 3);
        gp.add(btnResult, 2, 0);
        gp.setHgap(10);
        gp.setVgap(10);
        btnStanding.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Standing standing = new Standing();
                standing.setSize(1000,500);
                standing.setVisible(true);
                standing.setTitle("Standing");
                standing.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        btnResult.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Result result = new Result();
                result.start(ps);
            }
        });
        btnSchedule.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                ScheduleMatch sm = new ScheduleMatch();
                sm.start(ps);
            }
        });
        btnManager.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Manager manager = new Manager();
                manager.start(ps);
            }
        });
        btnPlayer.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Player player = new Player();
                player.start(ps);
            }
        });
        btnTeam.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Team team = new Team();
                team.start(ps);
            }
        });
        btnDepartment.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Department dep = new Department();
                dep.start(ps);
            }
        });
        btnField.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Field field = new Field();
                field.start(ps);
            }
        });
        btnRefree.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Refree refree = new Refree();
                refree.setTitle("Refree Management Windwo");
                refree.setSize(800,600);
                refree.setVisible(true);
                refree.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        Scene scene = new Scene(gp, 500,400);
        ps.setScene(scene);
        ps.setTitle("Main Page");
        ps.show();
    }
}
