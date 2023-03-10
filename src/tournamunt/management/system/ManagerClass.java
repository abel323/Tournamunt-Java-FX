/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tournamunt.management.system;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Abel
 */
public class ManagerClass {
    StringProperty teamName = new SimpleStringProperty();
    StringProperty fname = new SimpleStringProperty();
    StringProperty mname = new SimpleStringProperty();
    StringProperty lname = new SimpleStringProperty();
    StringProperty dob = new SimpleStringProperty();
    StringProperty hStatus = new SimpleStringProperty();

    public ManagerClass() {
    }

    public StringProperty getTeamName() {
        return teamName;
    }

    public void setTeamName(StringProperty teamName) {
        this.teamName = teamName;
    }

    public StringProperty getFname() {
        return fname;
    }

    public void setFname(StringProperty fname) {
        this.fname = fname;
    }

    public StringProperty getMname() {
        return mname;
    }

    public void setMname(StringProperty mname) {
        this.mname = mname;
    }

    public StringProperty getLname() {
        return lname;
    }

    public void setLname(StringProperty lname) {
        this.lname = lname;
    }

    public StringProperty getDob() {
        return dob;
    }

    public void setDob(StringProperty dob) {
        this.dob = dob;
    }

    public StringProperty gethStatus() {
        return hStatus;
    }

    public void sethStatus(StringProperty hStatus) {
        this.hStatus = hStatus;
    }
    
    
}
