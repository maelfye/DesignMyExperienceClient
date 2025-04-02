/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import guimyexperience.model.User;
import guimyexperience.model.Booking;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author maelfye
 */
public class ClientInterface extends javax.swing.JFrame {
    private User loggedInUser;
    private DefaultListModel<String> bookingListModel;
    private JList<String> bookingList;
    private JLabel lblTotalPrice;
    private JFrame mainScreen;
    public ClientInterface(User user, JFrame mainScreen) {
        this.loggedInUser = user;
        this.mainScreen = mainScreen;

        setTitle("Your Bookings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

       
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Your Bookings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Bookings List
        bookingListModel = new DefaultListModel<>();
        bookingList = new JList<>(bookingListModel);
        bookingList.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingList.setFixedCellHeight(30);

        JScrollPane listScrollPane = new JScrollPane(bookingList);
        listScrollPane.setPreferredSize(new Dimension(300, 200));

        //Total Price Panel
        JPanel bottomPanel = new JPanel();
        JLabel lblPriceText = new JLabel("Total Price: ");
        lblTotalPrice = new JLabel("0.00 €");
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(lblPriceText);
        bottomPanel.add(lblTotalPrice);

        //logout button
        JButton btnLogout = new JButton("logout");
        btnLogout.setBackground(new Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogout.addActionListener(e -> {
        dispose();
        if (mainScreen != null) mainScreen.dispose();
        new Connexion().setVisible(true);
        });

        // New bottom layout with logout on right
        bottomPanel.setLayout(new BorderLayout());
        JPanel leftPart = new JPanel();
        leftPart.add(lblPriceText);
        leftPart.add(lblTotalPrice);
        bottomPanel.add(leftPart, BorderLayout.WEST);
        bottomPanel.add(btnLogout, BorderLayout.EAST);

        // Add everything to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        //Fetch bookings
        fetchUserBookings();

        //Move frame to top-right corner
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 10;
        int y = 10;
        setLocation(x, y);
        
        
        setVisible(true);
    }

   private void fetchUserBookings() {

       //API path
    String apiUrl = "http://localhost:8080/api/bookings/client/" + loggedInUser.getId();

    try {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONArray bookingsArray = new JSONArray(response.toString());
            bookingListModel.clear();
            double totalPrice = 0;

            //Loop through bookings
            for (int i = 0; i < bookingsArray.length(); i++) {
                JSONObject obj = bookingsArray.getJSONObject(i);
                String title = obj.getJSONObject("offering").getString("title");
                double price = obj.getJSONObject("offering").getDouble("price");
                String date = obj.getString("bookingDate");

                // Format the date correctly
                date = formatDate(date); 

                //Add to List
                String bookingInfo = title + " | " + price + "€ | " + date;
                bookingListModel.addElement(bookingInfo);
                
                // Add price to total
                totalPrice += price;
            }

            lblTotalPrice.setText(String.format("%.2f €", totalPrice));
        } else {
            JOptionPane.showMessageDialog(this, "Failed to fetch bookings. Response Code: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error fetching bookings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
   }
    
    private String formatDate(String dateTime) {
    try {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy"); // Adjust as needed
        Date date = inputFormat.parse(dateTime);
        return outputFormat.format(date);
    } catch (Exception e) {
        return dateTime; // Return as is if parsing fails
    }


}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LBooking = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblLastname = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Bookings : ");

        LBooking.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(LBooking);

        jLabel2.setText("Total Price : ");

        lblPrice.setText("price");

        lblLastname.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPrice))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(lblLastname)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLastname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblPrice))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> LBooking;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLastname;
    private javax.swing.JLabel lblPrice;
    // End of variables declaration//GEN-END:variables
}
