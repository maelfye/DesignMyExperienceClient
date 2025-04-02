/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;
import java.util.Date;
import java.time.LocalTime;

/**
 *
 * @author maelfye
 */
public class Activity extends Offering {
    
    
    private Date startDate;


    private Date endDate;

    // Constructor
    public Activity(Long id, String title, String description, int capacity, String location,
                    OfferingTypes type, byte[] picture, double price, BusinessOwner businessOwner,
                    double duration, Date startDate, Date endDate) {
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
        this.startDate = startDate;
        this.endDate = endDate;
       
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
    return super.getId();
}

public String getTitle() {
    return super.getTitle();
}

public String getDescription() {
    return super.getDescription();
}

public int getCapacity() {
    return super.getCapacity();
}

public String getLocation() {
    return super.getLocation();
}

public OfferingTypes getType() {
    return super.getType();
}

public byte[] getPicture() {
    return super.getPicture();
}

public double getPrice() {
    return super.getPrice();
}

public BusinessOwner getBusinessOwner() {
    return super.getBusinessOwner();
}

public double getDuration() {
    return super.getDuration();
}

}
