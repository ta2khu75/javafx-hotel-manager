/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phamn
 */
public class ServiceRoom {
    private int id;
    private int idBooking;
    private int idService;
    private int quantity;

    public ServiceRoom() {
    }

    public ServiceRoom(int id, int idBooking, int idService, int quantity) {
        this.id = id;
        this.idBooking = idBooking;
        this.idService = idService;
        this.quantity = quantity;
    }
    public ServiceRoom(int idBooking, int idService, int quantity) {
        this.id = id;
        this.idBooking = idBooking;
        this.idService = idService;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
