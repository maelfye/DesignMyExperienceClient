/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;

import guimyexperience.model.BusinessOwner;
import java.util.Date;

/**
 * Represent an offering that can be booked by clients. It is provided by a business owner.
 */
public class Offering {

    
    private Long id;

    
    private String title;

    
    private String description;

    
    private int capacity;

   
    private String location;

    
    private OfferingTypes type;

    
    private byte[] picture;

    
    private double price;

   
    private BusinessOwner businessOwner;

    
    private double duration;

    /**
     * @return Offering ID
     */
    public Long getOfferingId() {
        return this.id;
    }

    // Optionally add getters and setters if needed for serialization/deserialization
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public OfferingTypes getType() { return type; }
    public void setType(OfferingTypes type) { this.type = type; }

    public byte[] getPicture() { return picture; }
    public void setPicture(byte[] picture) { this.picture = picture; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public BusinessOwner getBusinessOwner() { return businessOwner; }
    public void setBusinessOwner(BusinessOwner businessOwner) { this.businessOwner = businessOwner; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }
}