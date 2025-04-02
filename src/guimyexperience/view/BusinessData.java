/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;

import guimyexperience.model.BusinessOwner;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BusinessData extends JFrame {
    private final BusinessOwner owner;
    private JLabel lblTotalOffers;
    private JLabel lblTotalBookings;
    private JLabel lblTotalRevenue;
    private JPanel chartPanel;

    // create the windod and fetches the datas from the API
    public BusinessData(BusinessOwner owner) {
        this.owner = owner;
        setTitle("Business Analytics Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();              
        fetchAndDisplayData(); 
        setVisible(true);
    }

    // Initialises the windows components 
    private void initUI() {
        lblTotalOffers = new JLabel("Total Offers: 0");
        lblTotalBookings = new JLabel("Total Bookings: 0");
        lblTotalRevenue = new JLabel("Total Revenue: £0.0");

        // Styling for the text labels
        Font infoFont = new Font("Arial", Font.BOLD, 16);
        lblTotalOffers.setFont(infoFont);
        lblTotalBookings.setFont(infoFont);
        lblTotalRevenue.setFont(infoFont);

        // Chart panel where bar chart will be drawn
        chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(580, 250));
        chartPanel.setBackground(Color.WHITE);

        // Layout for the stats summary
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        statsPanel.add(lblTotalOffers);
        statsPanel.add(lblTotalBookings);
        statsPanel.add(lblTotalRevenue);

        
        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    // could not dowload the dependence asked, Draws a bar chart showing revenue per offer instead
    private void drawRevenueChart(Map<String, Double> revenueMap) {
        chartPanel.removeAll(); 

        int barWidth = 40;
        int spacing = 30;
        int padding = 60;
        int panelWidth = Math.max(500, revenueMap.size() * (barWidth + spacing) + padding);
        int panelHeight = 220;

        JPanel bars = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 40;
                int maxHeight = 150;
                double maxRevenue = revenueMap.values().stream().max(Double::compareTo).orElse(1.0);

                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.setColor(Color.DARK_GRAY);
                g.drawString("Revenue per Offer", 10, 20);

                for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
                    int height = (int) ((entry.getValue() / maxRevenue) * maxHeight);

                    // Draw bar
                    g.setColor(new Color(0, 102, 204));
                    g.fillRoundRect(x, maxHeight - height + 40, barWidth, height, 10, 10);

                    // Draw label + revenue
                    g.setColor(Color.BLACK);
                    String title = entry.getKey();
                    if (title.length() > 10) title = title.substring(0, 9) + "...";
                    g.drawString(title, x, maxHeight + 60);
                    g.drawString("£" + String.format("%.2f", entry.getValue()), x, maxHeight + 75);

                    x += barWidth + spacing;
                }
            }
        };

        bars.setPreferredSize(new Dimension(panelWidth, panelHeight));
        bars.setBackground(Color.WHITE);

        // Wrap bars panel in a scrollable container
        JScrollPane scrollPane = new JScrollPane(bars);
        scrollPane.setPreferredSize(new Dimension(560, 220));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(scrollPane, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    // Fetches offering + booking data, then updates labels and chart
    private void fetchAndDisplayData() {
        try {
            // Fetch all offerings by the business owner
            String offeringsUrl = "http://localhost:8080/api/offerings/business-owner/" + owner.getId();
            JSONArray offerings = fetchJsonArrayFromUrl(offeringsUrl);

            int totalOffers = offerings.length();
            int totalBookings = 0;
            double totalRevenue = 0.0;
            Map<String, Double> revenuePerOffer = new HashMap<>();

            // For each offer, fetch related bookings and calculate revenue
            for (int i = 0; i < offerings.length(); i++) {
                JSONObject offer = offerings.getJSONObject(i);
                long offerId = offer.getLong("id");
                String title = offer.getString("title");
                double price = offer.getDouble("price");

                String bookingsUrl = "http://localhost:8080/api/bookings/offering/" + offerId;
                JSONArray bookings = fetchJsonArrayFromUrl(bookingsUrl);

                int bookingsCount = bookings.length();
                totalBookings += bookingsCount;
                totalRevenue += bookingsCount * price;
                revenuePerOffer.put(title, bookingsCount * price);
            }

            // Update text labels and draw chart
            lblTotalOffers.setText("Total Offers: " + totalOffers);
            lblTotalBookings.setText("Total Bookings: " + totalBookings);
            lblTotalRevenue.setText("Total Revenue: £" + totalRevenue);
            drawRevenueChart(revenuePerOffer);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // fetches JSON array from an HTTP GET endpoint
    private JSONArray fetchJsonArrayFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) response.append(line);
        br.close();

        return new JSONArray(response.toString());
    }
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
