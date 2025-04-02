/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;

import java.util.List;

/**
 * Represent a business owner. A business owner provides offerings.
 */
public class BusinessOwner extends User {
    private String businessName;
    private String businessAddress;
    private String businessDescription;
    private List<Offering> offerings;

     public BusinessOwner(User user, String businessName, String businessAddress, String businessDescription, List<Offering> offerings) {
        super(user.getId(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getAddress(),
                user.getUserType(), user.getProfilePicture());
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessDescription = businessDescription;
        this.offerings = offerings;
    }
public void setActivites(List<Offering> offerings) {
        this.offerings = offerings;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }
}

