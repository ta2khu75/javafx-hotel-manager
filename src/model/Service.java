/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phamn
 */
public class Service {
    private int id;
    private String name;
    private float price;
    private String idTypeOfService;

    public Service() {
    }

    public Service(String name, float price, String idTypeOfService) {
        this.name = name;
        this.price = price;
        this.idTypeOfService = idTypeOfService;
    }
    
    public Service(int id, String name, float price, String idTypeOfService) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idTypeOfService = idTypeOfService;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIdTypeOfService() {
        return idTypeOfService;
    }

    public void setIdTypeOfService(String idTypeOfService) {
        this.idTypeOfService = idTypeOfService;
    }
    
}
