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
import guimyexperience.model.Client;
import org.json.JSONObject;
import guimyexperience.model.Offering;
/**
 *
 * @author maelfye
 */
public class Payment extends javax.swing.JFrame {
private Offering selectedActivity;
private Client client;
private double finalTotalPrice;
private String bookingDateStr;
private int attendeeCount;
private JSONObject JsonObject;
private javax.swing.JButton btnConfirm;
    /**
     * Creates new form brouillon
     */
    public Payment(Offering selectedActivity, Client client, double finalTotalPrice, String bookingDateStr,int attendeeCount) {
    this.selectedActivity = selectedActivity;
    this.client = client;
    this.finalTotalPrice = finalTotalPrice;
    this.bookingDateStr = bookingDateStr;
    this.attendeeCount = attendeeCount;
   
    initComponents();
    
    txtHash.setEditable(true);
txtHash.setFocusable(true);


javax.swing.InputMap im = txtHash.getInputMap();
javax.swing.ActionMap am = txtHash.getActionMap();

im.put(javax.swing.KeyStroke.getKeyStroke("control V"), "paste");
am.put("paste", new javax.swing.text.DefaultEditorKit.PasteAction());

// RIGHT-CLICK PASTE MENU
javax.swing.JPopupMenu popup = new javax.swing.JPopupMenu();
javax.swing.JMenuItem pasteItem = new javax.swing.JMenuItem("Paste");

// Action to paste clipboard contents
pasteItem.addActionListener(e -> {
    try {
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        String clipboardContent = (String) clipboard.getData(java.awt.datatransfer.DataFlavor.stringFlavor);
        txtHash.setText(clipboardContent);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
popup.add(pasteItem);
txtHash.setComponentPopupMenu(popup);

setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
   

   
}
   


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BtnValidation = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtHash = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel3.setText("HashTransaction");

        BtnValidation.setText("Confirm");
        BtnValidation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnValidationActionPerformed(evt);
            }
        });

        jLabel4.setText("Wallet to send :   0x7Be9992213C89CDc53f086897A20c40d1c0C8C97");

        jLabel2.setText("Payment ");

        txtHash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHashActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel1)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(34, 34, 34)
                                .addComponent(txtHash, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(BtnValidation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel1)))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(txtHash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(BtnValidation)
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnValidationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnValidationActionPerformed
        // TODO add your handling code here:
       try {
        String hash = txtHash.getText();
        if (hash.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a transaction hash!", "Missing Hash", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println(finalTotalPrice);

        JSONObject paymentPayload = new JSONObject();
        paymentPayload.put("transactionHash", hash);
        paymentPayload.put("offeringId", selectedActivity.getId());
        paymentPayload.put("clientId", client.getId());
        paymentPayload.put("price", finalTotalPrice);
        paymentPayload.put("bookingDateTime", bookingDateStr);
        paymentPayload.put("attendeeCount", attendeeCount);

        URL url = new URL("http://localhost:8080/api/payments/validate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.getOutputStream().write(paymentPayload.toString().getBytes("UTF-8"));

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            JOptionPane.showMessageDialog(this, " Payment validated and booking confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JOptionPane.showMessageDialog(this, " Payment failed: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Something went wrong: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }             
    
    }//GEN-LAST:event_BtnValidationActionPerformed

    private void txtHashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHashActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnValidation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtHash;
    // End of variables declaration//GEN-END:variables
}
