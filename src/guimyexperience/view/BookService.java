/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;
import guimyexperience.model.Client;
import guimyexperience.model.Service;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;




public class BookService extends JFrame {
    
private JComboBox<String> comboMonth;
    private JComboBox<String> comboDay;
    private JComboBox<String> comboHour;
    private JSpinner spinnerPeople;
    private JLabel lblTotalPrice;
    private JLabel lblDiscount;
    private JTextField txtDiscountCode;

    private int openingHour;
    private int closingHour;
    private int maxCapacity;
    private double pricePerHour;
    
private double finalPricePerPerson;
    private Client client;
    private Service selectedService;

    public BookService(Client client, Service selectedService) {
        this.client = client;
        this.selectedService = selectedService;
        this.maxCapacity = selectedService.getCapacity();
        this.pricePerHour = selectedService.getPrice();
        
        this.openingHour = selectedService.getOpening().toInstant().atZone(java.time.ZoneId.systemDefault()).getHour();
        this.closingHour = selectedService.getClosing().toInstant().atZone(java.time.ZoneId.systemDefault()).getHour();

        setTitle("Book Service - " + selectedService.getTitle());
        setSize(500, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialise dropdowns
        int currentYear = java.time.LocalDate.now().getYear();
        int currentMonth = java.time.LocalDate.now().getMonthValue();
        comboDay = new JComboBox<>(generateDays(currentYear, currentMonth));
        comboMonth = new JComboBox<>(generateMonths());
        comboHour = new JComboBox<>(generateHours());
        comboMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            int selectedMonth = Integer.parseInt((String) comboMonth.getSelectedItem());
            int year = java.time.LocalDate.now().getYear(); 
            String[] updatedDays = generateDays(year, selectedMonth);

            comboDay.removeAllItems();
            for (String day : updatedDays) {
            comboDay.addItem(day);
        }}});
        

        // Main panel layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Title
        JLabel lblTitle = new JLabel("Book: " + selectedService.getTitle());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Date range
        
        SimpleDateFormat hourOnly = new SimpleDateFormat("HH:mm");
        gbc.gridwidth = 2;
        gbc.gridy = y++;
        panel.add(new JLabel("Opening: " +hourOnly.format(selectedService.getOpening())), gbc);
        gbc.gridy = y++;
        panel.add(new JLabel("Closing: " +hourOnly.format(selectedService.getClosing())), gbc);
       
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
        panel.add(new JLabel("People (Max " + maxCapacity + "):"), gbc);
        spinnerPeople = new JSpinner(new SpinnerNumberModel(1, 1, maxCapacity, 1));
        spinnerPeople.addChangeListener(e -> updatePrice());
        gbc.gridx = 1;
        panel.add(spinnerPeople, gbc);
        
         // Discount Code Input
        gbc.gridy = ++y; gbc.gridx = 0;
        panel.add(new JLabel("Discount Code:"), gbc);
        txtDiscountCode = new JTextField();
        gbc.gridx = 1;
        panel.add(txtDiscountCode, gbc);
        
        //Discount code
        JButton btnApplyDiscount = new JButton("Apply");
        btnApplyDiscount.addActionListener(e -> updatePrice()); 
        gbc.gridx = 2;
        panel.add(btnApplyDiscount, gbc);

        // Total Price
        lblTotalPrice = new JLabel("Total Price: £0");
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = ++y; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(lblTotalPrice, gbc);

        // Confirm button
        JButton btnConfirm = new JButton("Confirm Booking");
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirm.setBackground(new Color(0, 153, 76));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.addActionListener(new ConfirmBookingListener());
        gbc.gridy = ++y;
        panel.add(btnConfirm, gbc);

        add(panel);
        
        setVisible(true);}
   //computing the price depending on the amount of person choosen
    private void updatePrice() {
    int people = (int) spinnerPeople.getValue();
    String discountCode = txtDiscountCode.getText().trim();

    double discount = 0.0;
    if (!discountCode.isEmpty()) {
        discount = fetchDiscount(discountCode, selectedService.getId());
    }

    double temp = pricePerHour - (pricePerHour * discount / 100.0);
    this.finalPricePerPerson = temp * people;

    lblTotalPrice.setText("Total Price: £" + String.format("%.2f", finalPricePerPerson));

}

    private String[] generateDays(int year, int month) {
    java.time.YearMonth yearMonth = java.time.YearMonth.of(year, month);
    int daysInMonth = yearMonth.lengthOfMonth();
    String[] days = new String[daysInMonth];
    for (int i = 1; i <= daysInMonth; i++) {
        days[i - 1] = String.format("%02d", i);
    }
    return days;
}

    private String[] generateMonths() {
    int currentMonth = java.time.LocalDate.now().getMonthValue();
    int totalMonths = 12 - currentMonth + 1;
    String[] months = new String[totalMonths];
    for (int i = 0; i < totalMonths; i++) {
        months[i] = String.format("%02d", currentMonth + i);
    }
    return months;
}

    private String[] generateHours() {
    String[] hours = new String[closingHour - openingHour + 1];
    for (int i = openingHour; i <= closingHour; i++) {
        hours[i - openingHour] = String.format("%02d", i);
    }
    return hours;
}
    
    // Listener for Confirm Button
    private class ConfirmBookingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           // try {
                String selectedMonth = (String) comboMonth.getSelectedItem();
                String selectedDay = (String) comboDay.getSelectedItem();
                String selectedHour = (String) comboHour.getSelectedItem();
                int attendeeCount = (int) spinnerPeople.getValue();

                int year = LocalDate.now().getYear();
                String bookingDateStr = year + "-" + selectedMonth + "-" + selectedDay + "T" + selectedHour + ":00";
                LocalDateTime bookingDateTime = LocalDateTime.parse(bookingDateStr);

            // Compare with current date-time
            if (bookingDateTime.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(null, "You can't book for a past time!", "Invalid Booking", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Payment P = new Payment(selectedService,  client, finalPricePerPerson,  bookingDateStr,attendeeCount);
            P.setVisible(true);
            
           }}
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
    return 0.0; // no discount
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
