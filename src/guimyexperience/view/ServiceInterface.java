/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;
import guimyexperience.model.Client;
import guimyexperience.model.Offering;
import guimyexperience.model.User;
import guimyexperience.model.Service;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
/**
 *
 * @author maelfye
 */
public class ServiceInterface extends javax.swing.JFrame {
private User loggedInUser;
private Offering Offers;
   
    public ServiceInterface(User loggedInUser, Offering Offers) {
        this.Offers = Offers;
        this.loggedInUser = loggedInUser;
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        Service ServiceOffer = (Service) Offers; 
        lblServiceTitle.setText(Offers.getTitle());
        txtDescription.setText(Offers.getDescription());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        lblOpening.setText("Opening hour : " + hourFormat.format(ServiceOffer.getOpening()));
        lblClosing.setText("Closing hour : " + hourFormat.format(ServiceOffer.getClosing()));
        lblArea.setText(Offers.getLocation());
        lblPrice.setText("Price : "+ServiceOffer.getPrice()+"£");
        lblCapacity.setText("Capacity : "+ServiceOffer.getCapacity()+"person max");
        lblDuration.setText("duration : "+Offers.getDuration()+"hours");
        byte[] imageBytes = ServiceOffer.getPicture(); 
        
        if (imageBytes != null) {
            ImageIcon icon = new ImageIcon(imageBytes);
             Image scaledImage = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(scaledImage));
           
        } else {
                    lblImage.setText("No image available");
            }
        
        
        
        
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServiceTitle = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        lblDuration = new javax.swing.JLabel();
        lblCapacity = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblOpening = new javax.swing.JLabel();
        lblClosing = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblServiceTitle.setText("Activity Name");

        lblArea.setText("Area :");

        lblDuration.setText("duration :");

        lblCapacity.setText("Capcity :");

        lblPrice.setText("price");

        lblOpening.setText("opening hour");

        lblClosing.setText("lblClosing");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jLabel1.setText("description :");

        jButton1.setText("Book");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblImage.setText("Image");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(lblServiceTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDuration)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblArea)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                                        .addComponent(jLabel1))
                                    .addComponent(lblCapacity))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPrice)
                                            .addComponent(lblOpening)
                                            .addComponent(lblClosing))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1)
                                        .addGap(50, 50, 50)))
                                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblServiceTitle)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblArea)
                                    .addComponent(jLabel1))
                                .addGap(27, 27, 27)
                                .addComponent(lblDuration)
                                .addGap(26, 26, 26)
                                .addComponent(lblCapacity))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPrice)
                                .addGap(18, 18, 18)
                                .addComponent(lblOpening)
                                .addGap(18, 18, 18)
                                .addComponent(lblClosing))
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Client client = (Client) loggedInUser;
     Service Service = (Service) Offers;
    BookService BS = new BookService(client, Service);
    BS.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCapacity;
    private javax.swing.JLabel lblClosing;
    private javax.swing.JLabel lblDuration;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblOpening;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblServiceTitle;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
}
