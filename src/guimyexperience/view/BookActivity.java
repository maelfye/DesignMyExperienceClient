/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template

/**
 *
 * @author maelfye
 */

package guimyexperience.view;

import guimyexperience.model.Activity;
import guimyexperience.model.Booking;
import guimyexperience.model.Client;
import guimyexperience.model.BusinessOwner;
import guimyexperience.model.OfferingTypes;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import guimyexperience.view.Payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends JFrame {
   
    // GUI components
    private JComboBox<String> comboMonth;
    private JComboBox<String> comboDay;
    private JComboBox<String> comboHour;
    private JSpinner spinnerPeople;
    private JLabel lblTotalPrice;
    private JButton btnConfirm;
    private JLabel lblDiscount;
    private JTextField txtDiscountCode;
    private double finalPricePerPerson;
    
    private JSONObject bookingJson;
    private double discountAmount = 0.0;

    // Business data
    private final Activity selectedActivity;
    private final Client client;
    private final double price;
    private final int Capacity;

    public BookActivity(Client client, Activity selectedActivity) {
        this.client = client;
        this.selectedActivity = selectedActivity;
        this.price = selectedActivity.getPrice();
        this.Capacity = selectedActivity.getCapacity();
        
        // Initialise components
        comboMonth = new JComboBox<>(generateMonths());
        comboDay = new JComboBox<>(generateDays());
        comboHour = new JComboBox<>(generateHours());

        setTitle("Activity Booking - " + selectedActivity.getTitle());
        setSize(500, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Title
        JLabel lblTitle = new JLabel("Book: " + selectedActivity.getTitle());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Dates
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        gbc.gridwidth = 2;
        gbc.gridy = y++;
        panel.add(new JLabel("Start Date: " + df.format(selectedActivity.getStartDate())), gbc);
        gbc.gridy = y++;
        panel.add(new JLabel("End Date: " + df.format(selectedActivity.getEndDate())), gbc);

        // Month
        gbc.gridwidth = 1;
        gbc.gridy = y; gbc.gridx = 0;
        panel.add(new JLabel("Month:"), gbc);
        gbc.gridx = 1;
        panel.add(comboMonth, gbc);

        // Day
        gbc.gridy = ++y; gbc.gridx = 0;
        panel.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        panel.add(comboDay, gbc);

        // Hour
        gbc.gridy = ++y; gbc.gridx = 0;
        panel.add(new JLabel("Hour:"), gbc);
        gbc.gridx = 1;
        panel.add(comboHour, gbc);

        // People
        gbc.gridy = ++y; gbc.gridx = 0;
        panel.add(new JLabel("People (Max " + Capacity + "):"), gbc);
        spinnerPeople = new JSpinner(new SpinnerNumberModel(1, 1, Capacity, 1));
        spinnerPeople.addChangeListener(e -> updatePrice());
        gbc.gridx = 1;
        panel.add(spinnerPeople, gbc);
        
        // Discount Code Input
        gbc.gridy = ++y; gbc.gridx = 0;
        panel.add(new JLabel("Discount Code:"), gbc);
        txtDiscountCode = new JTextField();
        gbc.gridx = 1;
        panel.add(txtDiscountCode, gbc);

        // apply the discount
        JButton btnApplyDiscount = new JButton("Apply");
        btnApplyDiscount.addActionListener(e -> updatePrice()); 
        gbc.gridx = 2;
        panel.add(btnApplyDiscount, gbc);

        // Total Price
        lblTotalPrice = new JLabel("Total Price: £0");
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = ++y; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(lblTotalPrice, gbc);
        

        // Button
        btnConfirm = new JButton("Confirm Booking");
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirm.setBackground(new Color(0, 153, 76));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.addActionListener(new ConfirmSelectionListener());
        gbc.gridy = ++y;
        panel.add(btnConfirm, gbc);

        add(panel);
        setVisible(true);
    }
    
    //computing the price depending on the amount of person choosen
    private void updatePrice() {
    int people = (int) spinnerPeople.getValue();
    String discountCode = txtDiscountCode.getText().trim();

    double discount = 0.0;
    if (!discountCode.isEmpty()) {
        discount = fetchDiscount(discountCode, selectedActivity.getId());
    }

    double temp = price - (price * discount / 100.0);
    this.finalPricePerPerson = temp * people;

    lblTotalPrice.setText("Total Price: £" + String.format("%.2f", finalPricePerPerson));

}
    
    //creating a list only with the possible month
    private String[] generateMonths() {
    LocalDateTime start = toLocalDateTime(selectedActivity.getStartDate());
    LocalDateTime end = toLocalDateTime(selectedActivity.getEndDate());

    List<String> months = new ArrayList<>();
    for (int m = start.getMonthValue(); m <= end.getMonthValue(); m++) {
        months.add(String.format("%02d", m));
    }
    return months.toArray(new String[0]);
    }

    //creating a list of days with the only possible days
    private String[] generateDays() {
    LocalDateTime start = toLocalDateTime(selectedActivity.getStartDate());
    LocalDateTime end = toLocalDateTime(selectedActivity.getEndDate());

    List<String> days = new ArrayList<>();
    for (int d = start.getDayOfMonth(); d <= end.getDayOfMonth(); d++) {
        days.add(String.format("%02d", d));
    }
    return days.toArray(new String[0]);
    }
    
    //creating a list with all the hours possible
    private String[] generateHours() {
    LocalDateTime start = toLocalDateTime(selectedActivity.getStartDate());
    LocalDateTime end = toLocalDateTime(selectedActivity.getEndDate());

    List<String> hours = new ArrayList<>();
    for (int h = start.getHour(); h <= end.getHour(); h++) {
        hours.add(String.format("%02d", h));
    }
    return hours.toArray(new String[0]);
    }

    //finding the local time of the User
    private LocalDateTime toLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    private double fetchDiscount(String code, long offeringId) {
    try {
        URL url = new URL("http://localhost:8080/api/bookings/check_discount?code=" + code + "&offeringId=" + offeringId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getDouble("discount");  
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Failed to validate discount code: " + ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
    }
    return 0.0; 
}

    // Handles booking confirmation
    private class ConfirmSelectionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // try {
        // Get user selections
        String selectedMonth = (String) comboMonth.getSelectedItem(); 
        String selectedDay = (String) comboDay.getSelectedItem();     
        String selectedHour = (String) comboHour.getSelectedItem();  
        int attendeeCount = (int) spinnerPeople.getValue();
        
        // Parse ISO date string
        int year = java.time.LocalDate.now().getYear(); 
        String bookingDateStr = year + "-" + selectedMonth + "-" + selectedDay + "T" + selectedHour + ":00";
        LocalDateTime selectedDateTime = LocalDateTime.parse(bookingDateStr);
        LocalDateTime activityStart = toLocalDateTime(selectedActivity.getStartDate());
        LocalDateTime activityEnd = toLocalDateTime(selectedActivity.getEndDate());

        if (selectedDateTime.isBefore(activityStart) || selectedDateTime.isAfter(activityEnd)) {
        JOptionPane.showMessageDialog(null, "Please select a date and time within the activity range!", "Invalid Date", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
          
        Payment P = new Payment(selectedActivity,  client, finalPricePerPerson,  bookingDateStr,attendeeCount);
        P.setVisible(true);
        }}

    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblBookingTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboMonth = new javax.swing.JComboBox<>();
        ComboDay = new javax.swing.JComboBox<>();
        ComboHour = new javax.swing.JComboBox<>();
        BtnConfirm = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Booking Area");

        lblBookingTitle.setText("t");

        jLabel2.setText("when ?");

        jLabel3.setText("MM");

        jLabel4.setText("DD");

        jLabel5.setText("HH");

        ComboMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboMonthActionPerformed(evt);
            }
        });

        ComboDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboHour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboHourActionPerformed(evt);
            }
        });

        BtnConfirm.setText("Confirm");
        BtnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(63, 63, 63)
                                        .addComponent(jLabel3)
                                        .addGap(83, 83, 83)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(ComboDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                        .addComponent(ComboHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(BtnConfirm)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBookingTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblBookingTitle))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addComponent(BtnConfirm)
                .addGap(123, 123, 123))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboMonthActionPerformed

    private void ComboHourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboHourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboHourActionPerformed

    private void BtnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmActionPerformed
    // TODO add your handling code here:
     
    }//GEN-LAST:event_BtnConfirmActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnConfirm;
    private javax.swing.JComboBox<String> ComboDay;
    private javax.swing.JComboBox<String> ComboHour;
    private javax.swing.JComboBox<String> ComboMonth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBookingTitle;
    // End of variables declaration//GEN-END:variables
}