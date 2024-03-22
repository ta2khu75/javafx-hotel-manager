/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phamn
 */
public class Room {
    private String id;
    private int status;
    private int idTypeofRoom;

    public Room() {
    }

    public Room(String id, int status, int idTypeofRoom) {
        this.id = id;
        this.status = status;
        this.idTypeofRoom = idTypeofRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdTypeofRoom() {
        return idTypeofRoom;
    }

    public void setIdTypeofRoom(int idTypeofRoom) {
        this.idTypeofRoom = idTypeofRoom;
    }
    
}
