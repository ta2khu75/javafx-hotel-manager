/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phamn
 */
public class TypeOfRoom {
    private int id;
    private String name;
    private float hourlyPrice;
    private float pricePerDay;

    public TypeOfRoom() {
    }

    public TypeOfRoom(String name, float hourlyPrice, float pricePerDay) {
        this.name = name;
        this.hourlyPrice = hourlyPrice;
        this.pricePerDay = pricePerDay;
    }

    public TypeOfRoom(int id, String name, float hourlyPrice, float pricePerDay) {
        this.id = id;
        this.name = name;
        this.hourlyPrice = hourlyPrice;
        this.pricePerDay = pricePerDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(float hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    
    
}
