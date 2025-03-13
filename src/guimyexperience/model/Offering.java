/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;

import guimyexperience.model.BusinessOwner;

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

    /**
     *
     * @return Offering ID
     */
    public Long getOfferingId(){
        return this.id;
    }
}