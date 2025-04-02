/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.model;
import java.util.Date;
/**
 *
 * @author maelfye
 */
public class Booking {
    private Long id;
    private Client client;
    private Offering offering;
    private String status;
    private Date bookingDate;
    private int attendeeCount;
    // Constructor
    public Booking(Long id, Client client, Offering offering, String status, Date bookingDate,int attendeeCount) {
        this.id = id;
        this.client = client;
        this.offering = offering;
        this.status = status;
        this.bookingDate = bookingDate;
        this.attendeeCount = attendeeCount;
    }

    // Getters and Setters
    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }
    public Client getClient() { return client; }
    public Date getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public Offering getOffering() { return offering; }
    
    public void setClient(Client client) { this.client = client; }
    public void setOffering(Offering offering) { this.offering = offering; }   
    public void setStatus(String status) { this.status = status; }   
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public  int getAttendeeCount(){return this.attendeeCount; }
    public void setAttendeeCount(int id){this.attendeeCount = attendeeCount;
     } }
