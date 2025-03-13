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
    private Long id;
    private String businessName;
    private String businessAddress;
    private String businessDescription;
    private List<Offering> offerings;

    public BusinessOwner(Long id, String userName, String firstName, String lastName, String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        super(id, userName, firstName, lastName, phone, email, address, userType, profilePicture);
    }
}
