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
import org.json.JSONArray;
import org.json.JSONObject;
import guimyexperience.model.User;
import guimyexperience.model.BusinessOwner;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class BuisnessInterface extends JFrame {
    private User loggedInUser;
    private DefaultListModel<String> offerListModel;
    private JList<String> offerList;
    private JLabel lblTotalOffers;
    private JButton btnAddActivity;
    private JButton btnAddService;
    private JButton btnViewStats;
    private JButton btnLogout;
    private ArrayList<Long> offerIdList;
    private JFrame Main;

    public BuisnessInterface(User user, JFrame mainScreen) {
        this.loggedInUser = user;
        this.offerIdList = new ArrayList<>();
        this.Main = mainScreen;
        setTitle("Your Offers");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Offers", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        offerListModel = new DefaultListModel<>();
        offerList = new JList<>(offerListModel);
        offerList.setFont(new Font("Arial", Font.PLAIN, 14));
        offerList.setFixedCellHeight(30);

        JScrollPane scrollPane = new JScrollPane(offerList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JPanel bottomPanel = new JPanel();
        JLabel lblCountText = new JLabel("Total Offers: ");
        lblTotalOffers = new JLabel("0");
        lblTotalOffers.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(lblCountText);
        bottomPanel.add(lblTotalOffers);
      
        btnAddActivity = new JButton("Add Activity");
        btnAddService = new JButton("Add Service");
        btnViewStats = new JButton("View Stats");
        btnViewStats.setFocusPainted(false);
        btnViewStats.addActionListener(e -> {
        BusinessData BD = new BusinessData((BusinessOwner) loggedInUser);
        BD.setVisible(true);
        });
        
        //creating the logout button
        btnLogout = new JButton("logout");
        btnLogout.setBackground(new Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
        dispose();
        if (Main != null) Main.dispose();
        new Connexion().setVisible(true);
        });

        btnAddActivity.setFocusPainted(false);
        btnAddService.setFocusPainted(false);

        bottomPanel.add(btnAddActivity);
        bottomPanel.add(btnAddService);
        bottomPanel.add(btnViewStats);
        bottomPanel.add(btnLogout);
        btnAddActivity.addActionListener(e -> {
        addActivity A = new addActivity(loggedInUser);
        A.setVisible(true);
        
        });

        btnAddService.addActionListener(e -> {
        addServices A = new addServices(loggedInUser);
        A.setVisible(true);
       
        });


        // Create a header panel that holds both the title and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(btnLogout, BorderLayout.EAST);

        // Add everything to mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);     
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);    

        add(mainPanel);

   

        // Fetch offers
        fetchOffers();

        // Move to top-right corner
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 10;
        int y = 10;
        setLocation(x, y);
        //Open an interface to delete an Offer when the User double click on it
        offerList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
              if ( e.getClickCount()==2){
                  int index = offerList.locationToIndex(e.getPoint());
                  long selected_Id = offerIdList.get(index);
                  OwnerActivityInterface OI = new OwnerActivityInterface(selected_Id);
                  OI.setVisible(true);
                  
              }  
            }});
        setVisible(true);
    }

    private void fetchOffers() {
        String apiUrl = "http://localhost:8080/api/offerings/business-owner/" + loggedInUser.getId();

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

                JSONArray offersArray = new JSONArray(response.toString());
                offerListModel.clear();

                for (int i = 0; i < offersArray.length(); i++) {
                    JSONObject obj = offersArray.getJSONObject(i);
                    String title = obj.getString("title");
                    double price = obj.getDouble("price");
                    String description = obj.optString("description", "");
                    Long Id = obj.getLong("id");

                    String offerInfo = title + " | " + price + "€" + (description.isEmpty() ? "" : " | " + description);
                    offerListModel.addElement(offerInfo);
                    offerIdList.add(Id);
                }

                lblTotalOffers.setText(String.valueOf(offersArray.length()));
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch offers. Response Code: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching offers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
}
  
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBusinessName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBusinessName.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(lblBusinessName)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblBusinessName)
                .addContainerGap(242, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBusinessName;
    // End of variables declaration//GEN-END:variables
}
