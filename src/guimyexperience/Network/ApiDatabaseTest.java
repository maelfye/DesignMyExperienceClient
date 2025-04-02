/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guimyexperience.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiDatabaseTest {
    public static void main(String[] args) {
        String apiUrl = "http://localhost:8080/api/users"; 

        try {
            // Step 1 I create the connexion with the method
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Step 2 I check if i have a reponse and read it
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Step 3 I stock the data 
                JSONArray usersArray = new JSONArray(response.toString());

                System.out.println( usersArray.length() + " users from the database.");

                // Step 4 Loop through all users and display their information
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);
                    System.out.println("\n----- User " + (i + 1) + " -----");
                    System.out.println("ID: " + user.getInt("id"));
                    System.out.println("First Name: " + user.getString("firstName"));
                    System.out.println("Last Name: " + user.getString("lastName"));
                    System.out.println("Email: " + user.getString("email"));
                    System.out.println("Phone: " + user.getString("phone"));
                    System.out.println("Address: " + user.getString("address"));
                    System.out.println("User Type: " + user.getString("userType"));
                    System.out.println("Username: " + user.optString("userName", "No Username Found"));
                }

            } else {
                System.out.println("API Test Failed" + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Could not connect to API.");
            e.printStackTrace();
        }
    }
}