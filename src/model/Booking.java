/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
/**
 *
 * @author phamn
 */
public class Booking {
    private int id;
    private String idGuest;
    private String idRoom;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean type;
    private float totalMoney;
    private boolean  status;

    public Booking(int id, String idGuest, String idRoom, LocalDateTime startDate, LocalDateTime endDate, boolean type, float totalMoney, boolean status) {
        this.id = id;
        this.idGuest = idGuest;
        this.idRoom = idRoom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.totalMoney = totalMoney;
        this.status = status;
    }

    public Booking(String idGuest, String idRoom, LocalDateTime startDate, LocalDateTime endDate, boolean type, float totalMoney, boolean status) {
        this.idGuest = idGuest;
        this.idRoom = idRoom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.totalMoney = totalMoney;
        this.status = status;
    }

    public Booking() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idGuest
     */
    public String getIdGuest() {
        return idGuest;
    }

    /**
     * @param idGuest the idGuest to set
     */
    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    /**
     * @return the idRoom
     */
    public String getIdRoom() {
        return idRoom;
    }

    /**
     * @param idRoom the idRoom to set
     */
    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * @return the startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the type
     */
    public boolean isType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(boolean type) {
        this.type = type;
    }

    /**
     * @return the totalMoney
     */
    public float getTotalMoney() {
        return totalMoney;
    }

    /**
     * @param totalMoney the totalMoney to set
     */
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
