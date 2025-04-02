# GUI MyExperience 🎨🧘‍♂️

**GUI MyExperience** is a Java Swing desktop application that allows clients to explore, book, and pay for experiences or services offered by business owners. It is backed by a PostgreSQL database and includes both frontend (Swing) and backend (REST API) integration.

---

##  Features

- 👤 **User Management**  
  - Register and log in as a Client or Business Owner  
  - Profile handling and validation

- 🎯 **Offerings Management**  
  - Business Owners can create **Activities** and **Services**
  - Set descriptions, prices, dates, locations, and availability
  - Delete his offerings

- 📅 **Booking System**  
  - Clients can book available offerings
  - Support for attendee count and discount codes

- 💳 **Payment Integration**  
  - Confirm bookings using a blockchain transaction hash
  - Validate transactions via backend API
  - Custom copy/paste enabled payment window



## 🧱 Database Structure

- `users` — core user info and login data  
- `clients` / `businessowners` — role-specific tables  
- `offerings` — stores activities and services  
- `activities` / `services` — extended offering details  
- `bookings` — user reservations with date and attendee count  
- `payments` — transaction validation via hash  
- `discounts` — optional promotional codes

---


