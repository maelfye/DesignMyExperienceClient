/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guimyexperience.view;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 *
 * @author maelfye
 */
public class OwnerActivityInterface extends javax.swing.JFrame {
private Long OfferId;
    /**
     * Creates new form OwnerActivityInterface
     */
    public OwnerActivityInterface(Long OfferId) {
        this.OfferId = OfferId;
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblActivityName = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblActivityName.setText("Do you want to delete this activity ?");

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addComponent(lblActivityName)
                .addGap(135, 135, 135))
            .addGroup(layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(lblActivityName)
                .addGap(48, 48, 48)
                .addComponent(jButton1)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
            // Confirm with the user first (good UX)
    int confirm = JOptionPane.showConfirmDialog(null,
        "Are you sure you want to delete this activity?",
        "Delete Confirmation",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            
            URL url = new URL("http://localhost:8080/api/offerings/delete_offering/" + OfferId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode == 204 || responseCode == 200) {
                JOptionPane.showMessageDialog(null, "Activity deleted successfully.");
                this.dispose(); 
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder error = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    error.append(line);
                }
                JOptionPane.showMessageDialog(null, "Failed to delete activity:\n" + error.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting activity: " + e.getMessage());
        }
    }

    }//GEN-LAST:event_jButton1ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblActivityName;
    // End of variables declaration//GEN-END:variables
}
