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
public class Person {
    //protected int id;
    protected String FName;
    protected String LName;
    protected String MName;
    protected String dob;
    protected String dor;
    public Person(){
        
    }
    public Person(String FName, String LName, String MName, String dob, String dor){
        this.FName = FName;
        this.LName = LName;
        this.MName = MName;
        this.dor = dor;
        this.dob = dob;
    }
    
}
