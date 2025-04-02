/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import guimyexperience.model.Offering;
import guimyexperience.model.User;
import guimyexperience.model.OfferingTypes;
import guimyexperience.model.BusinessOwner;
import guimyexperience.model.Activity;
import guimyexperience.model.Service;
import guimyexperience.model.UserTypes;
import guimyexperience.model.Client;
import java.util.Base64;
/*
 *
 * @author maelfye
 */
public class MainScreen extends javax.swing.JFrame {
        private JTextField searchField;
    private DefaultListModel<String> activityListModel;
    private DefaultListModel<String> serviceListModel;
    private JList<String> activityList;
    private JList<String> serviceList;
    private List<Activity> activityObjects = new ArrayList<>();
    private List<Service> serviceObjects = new ArrayList<>();
    private User loggedInUser; 
    private BusinessOwner OwnerLogged;
    

    public MainScreen(User user) {
        this.loggedInUser = user;
       
        UserTypes userType= loggedInUser.getUserType();
    if (userType == UserTypes.BusinessOwner) {
        fetchBusinessOwnerDetails(user);
    } else {
        this.loggedInUser = new Client(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getEmail(),
            user.getAddress(),
            userType,
            user.getProfilePicture()
        );}
    initialisationPanel();
}
// fetching the buisnessOwnerDetails from the API to pass them to the businessOwner interface
private void fetchBusinessOwnerDetails(User user) {
    try {
        String apiUrl = "http://localhost:8080/api/users/business-owners/" + user.getId();

        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            this.loggedInUser = new BusinessOwner(
                user,
                json.optString("businessname"),
                json.optString("businessaddress"),
                json.optString("businessdescription"),
                null 
            );
     
        } else {
            JOptionPane.showMessageDialog(null, "Could not fetch business details.");
            this.loggedInUser = user;
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error fetching business data: " + e.getMessage());
        this.loggedInUser = user;
    }
SwingUtilities.invokeLater(this::initialisationPanel);
    }
private void fetchOfferingsFromAPI() {
    fetchActivitiesFromAPI();
    fetchServicesFromAPI();
    System.out.println("Activity List Size: " + activityObjects.size());
    System.out.println("Service List Size: " + serviceObjects.size());
}

//creating all the Panel
public void initialisationPanel(){
        //creating a mainPanel on the full window
        setTitle("Search Offerings");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        //creating a header 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));

        // creating the serach field
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterOfferings(searchField.getText());
            }
        });

        // User Profile Button
        JButton userProfileButton = new JButton("Profile");
        userProfileButton.setPreferredSize(new Dimension(150, 40));
        userProfileButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        //waiting fot the button to be clicked
        userProfileButton.addActionListener(e -> openUserProfile());

       
        headerPanel.add(searchField, BorderLayout.CENTER);
        headerPanel.add(userProfileButton, BorderLayout.EAST);

        // LIST PANEL 
        JPanel listPanel = new JPanel(new GridLayout(1, 2, 40, 40));
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50)); 

        // Activity Panel
        JPanel activityPanel = new JPanel(new BorderLayout());
        JLabel activityLabel = new JLabel("Activities", JLabel.CENTER);
        activityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        activityListModel = new DefaultListModel<>();
        activityList = new JList<>(activityListModel);
        activityList.setFixedCellHeight(35);
        activityList.setFont(new Font("Arial", Font.PLAIN, 16));
        activityList.setVisibleRowCount(8);
        activityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handleItemClick(activityList, evt);
            }
        });

        JScrollPane activityScrollPane = new JScrollPane(activityList);
        activityScrollPane.setPreferredSize(new Dimension(300, 300));

        activityPanel.add(activityLabel, BorderLayout.NORTH);
        activityPanel.add(activityScrollPane, BorderLayout.CENTER);

        // Service Panel
        JPanel servicePanel = new JPanel(new BorderLayout());
        JLabel serviceLabel = new JLabel("Services", JLabel.CENTER);
        serviceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        serviceListModel = new DefaultListModel<>();
        serviceList = new JList<>(serviceListModel);
        serviceList.setFixedCellHeight(35);
        serviceList.setFont(new Font("Arial", Font.PLAIN, 16));
        serviceList.setVisibleRowCount(8);
        serviceList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handleItemClick(serviceList, evt);
            }
        });

        JScrollPane serviceScrollPane = new JScrollPane(serviceList);
        serviceScrollPane.setPreferredSize(new Dimension(300, 300));

        servicePanel.add(serviceLabel, BorderLayout.NORTH);
        servicePanel.add(serviceScrollPane, BorderLayout.CENTER);

        // Add lists
        listPanel.add(activityPanel);
        listPanel.add(servicePanel);

        // ADD COMPONENTS TO MAIN PANEL
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Fetch Data from API
        fetchOfferingsFromAPI();
        System.out.println("Activity List Size: " + activityObjects.size());
        System.out.println("Service List Size: " + serviceObjects.size());

        setVisible(true);
    
}
private void fetchActivitiesFromAPI() {
    String apiUrl = "http://localhost:8080/api/offerings/activities/upcoming";

    try {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONArray activitiesArray = new JSONArray(response.toString());
            activityObjects.clear();
            activityListModel.clear();

            for (int i = 0; i < activitiesArray.length(); i++) {
                JSONObject obj = activitiesArray.getJSONObject(i);

                String base64Image = obj.optString("picture", null);
                byte[] imageBytes = null; 

                if (base64Image != null && !base64Image.equals("null")) {
                    imageBytes = Base64.getDecoder().decode(base64Image);
                }
                
                Activity activity = new Activity(
                        obj.getLong("id"),
                        obj.getString("title"),
                        obj.getString("description"),
                        obj.getInt("capacity"),
                        obj.getString("location"),
                        OfferingTypes.Activity,
                        imageBytes,
                        obj.getDouble("price"),
                        null,
                        obj.getDouble("duration"),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("startDate")),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("endDate"))
                );

                activityObjects.add(activity);
                activityListModel.addElement(activity.getTitle());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching activities: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void fetchServicesFromAPI() {
    String apiUrl = "http://localhost:8080/api/offerings/services"; 

    try {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONArray servicesArray = new JSONArray(response.toString());
            serviceObjects.clear();
            serviceListModel.clear();

            for (int i = 0; i < servicesArray.length(); i++) {
                JSONObject obj = servicesArray.getJSONObject(i);

                String base64Image = obj.optString("picture", null);
                byte[] imageBytes = null; 

                if (base64Image != null && !base64Image.equals("null")) {
                    imageBytes = Base64.getDecoder().decode(base64Image);
                }
                
                Service service = new Service(
                        obj.getLong("id"),
                        obj.getString("title"),
                        obj.getString("description"),
                        obj.getInt("capacity"),
                        obj.getString("location"),
                        OfferingTypes.Service,
                        imageBytes,
                        obj.getDouble("price"),
                        null,
                        obj.getDouble("duration"),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("opening")),
                        //new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("opening")),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("closing")),
                        //new SimpleDateFormat("yyyy-MM-dd").parse(obj.getString("closing")),
                        obj.getBoolean("onDemand"),
                        obj.getString("serviceArea")
                );

                serviceObjects.add(service);
                serviceListModel.addElement(service.getTitle());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching services: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    //filtering the activity and service based on the User input
private void filterOfferings(String query) {
    activityListModel.clear();
    serviceListModel.clear();
    List<String> activities = new ArrayList<>();
    List<String> services = new ArrayList<>();

    //  Filter Activities
    for (Activity activity : activityObjects) {
        if (activity.getTitle().toLowerCase().contains(query.toLowerCase())) {
            activities.add(activity.getTitle());
        }
    }

    //  Filter Services
    for (Service service : serviceObjects) {
        if (service.getTitle().toLowerCase().contains(query.toLowerCase())) {
            services.add(service.getTitle());
        }
    }

    // Sort alphabetically
    Collections.sort(activities);
    Collections.sort(services);

    //Update list models
    for (String activity : activities){
        activityListModel.addElement(activity);}
    for (String service : services) {
        serviceListModel.addElement(service);}
}

private void handleItemClick(JList<String> list, MouseEvent evt) {
    if (evt.getClickCount() == 2) { 
        String selectedTitle = list.getSelectedValue();
        if (selectedTitle != null) {
            Offering selectedOffering = null;

            //  Check both lists (Activity and Service)
            for (Activity activity : activityObjects) {
                if (activity.getTitle().equals(selectedTitle)) {
                    selectedOffering = activity;
                    break;
                }
            }
            for (Service service : serviceObjects) {
                if (service.getTitle().equals(selectedTitle)) {
                    selectedOffering = service;
                    break;
                }
            }

            // Open appropriate interface
            if (selectedOffering != null) {
                if (selectedOffering instanceof Activity) { 
                    ActivityInterface AI = new ActivityInterface(loggedInUser, selectedOffering);
                    AI.setVisible(true);
                } else if (selectedOffering instanceof Service) {
                    ServiceInterface SI = new ServiceInterface(loggedInUser, selectedOffering);
                    SI.setVisible(true);
                   
                }
            }
        }
    }
}

  private void openUserProfile() {
    if (loggedInUser instanceof BusinessOwner) {
        System.out.println("I am business owner");
        BuisnessInterface Bi = new BuisnessInterface((BusinessOwner) loggedInUser,this);
        Bi.setVisible(true);
    } else if (loggedInUser instanceof Client) {
        System.out.println("i am client");
        ClientInterface Ci = new ClientInterface(loggedInUser, this);
        Ci.setVisible(true);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel11 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtResearch = new javax.swing.JTextField();
        BAccount = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Research");

        BAccount.setText("Account");
        BAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAccountActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel6);

        jPanel2.add(jPanel5);

        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(336, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtResearch, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(680, 680, 680))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(213, 213, 213))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1283, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(304, 304, 304)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(BAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtResearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(214, 214, 214)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 341, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAccountActionPerformed
    
       // TODO add your handling code here:
    }//GEN-LAST:event_BAccountActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAccount;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField txtResearch;
    // End of variables declaration//GEN-END:variables
}
