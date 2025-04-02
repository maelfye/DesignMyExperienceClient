/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author maelfye
 */

public class Service extends Offering {

    private Date opening;

  
    private Date closing;


    private boolean onDemand;

  
    private String serviceArea;

    // Constructor
    public Service(Long id, String title, String description, int capacity, String location,
                   OfferingTypes type, byte[] picture, double price, BusinessOwner businessOwner,
                   double duration, Date opening, Date closing, boolean onDemand, String serviceArea) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setCapacity(capacity);
        setLocation(location);
        setType(type);
        setPicture(picture);
        setPrice(price);
        setBusinessOwner(businessOwner);
        setDuration(duration);
        this.opening = opening;
        this.closing = closing;
        this.onDemand = onDemand;
        this.serviceArea = serviceArea;
    }
    

    public Date getOpening() {
        return opening;
    }

    public void setOpening(Date opening) {
        this.opening = opening;
    }

    public Date getClosing() {
        return closing;
    }

    public void setClosing(Date closing) {
        this.closing = closing;
    }

    public boolean isOnDemand() {
        return onDemand;
    }

    public void setOnDemand(boolean onDemand) {
        this.onDemand = onDemand;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

}