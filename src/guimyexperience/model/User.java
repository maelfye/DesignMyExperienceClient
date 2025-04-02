/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;

import guimyexperience.model.UserTypes;

public class User {
    //@JsonProperty("id")
    private Long id;

    //@JsonProperty("userName")
    private String userName;

    //@JsonProperty("firstName")
    private String firstName;

   // @JsonProperty("lastName")
    private String lastName;

    //@JsonProperty("phone")
    private String phone;

    //@JsonProperty("email")
    private String email;

    //@JsonProperty("address")
    private String address;

    //@JsonProperty("userType")
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserTypes userType;

    //@JsonProperty("profilePicture")
    private byte[] profilePicture;

    public User(Long id, String firstName, String lastName,
                String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        this.id = id;
        //this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.userType = userType;
        //this.profilePicture = profilePicture;
    }

    // Getters and Setters (Recommandé pour la sérialisation)
    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public UserTypes getUserType() { return userType; }
    public byte[] getProfilePicture() { return profilePicture; }

}