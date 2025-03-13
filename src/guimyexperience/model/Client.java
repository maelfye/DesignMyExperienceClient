/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;

/**
 * Represent a client in the system. A client can book offerings.
 */
public class Client extends User {

    public Client(Long id, String userName, String firstName, String lastName, String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        super(id, userName, firstName, lastName, phone, email, address, userType, profilePicture);
    }
}
