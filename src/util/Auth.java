/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import model.Staff;

/**
 *
 * @author ta2khu75
 */
public class Auth {
    private static Staff staff;
    
    public static void remove(){
        staff=null;
    }
    /**
     * @return the staff
     */
    public static Staff get() {
        return staff;
    }

    /**
     * @param aStaff the staff to set
     */
    public static void set(Staff aStaff) {
        staff = aStaff;
    }
    
}
