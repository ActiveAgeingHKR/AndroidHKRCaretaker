package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.util.Base64;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by panos on 12/16/2016.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private Context applicationContext;
    private SSLContext sslContext;
    private final String url = "https://192.168.1.99:8181/MainServerREST/api/";
    private static char[] KEYSTORE_PASSWORD = "changeit".toCharArray();
    private SSLSocketFactory socketFactory;
    private  final String REST_SERVER_USERNAME = "EMPLOYEE";
    private  final String REST_SERVER_PASSWORD = "password";
    public static String employeeID;
    public static LinkedList<EmployeeSchedule> employeeSchedule = new LinkedList<>();
    public static LinkedList<Tasks> employeeTasks = new LinkedList<>();


    private String endoding;

    private MySingleton(Context context) {
        applicationContext = context;

        try {
            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore trusted = KeyStore.getInstance("BKS");
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)
            InputStream in = applicationContext.getApplicationContext().getResources().openRawResource(R.raw.hkrssl);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Provide the password of the keystore
                trusted.load(in, KEYSTORE_PASSWORD);
            } finally {
                in.close();
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trusted);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            socketFactory = sslContext.getSocketFactory();



        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }



    public String doGetJsonString(String methodAddress) throws Exception {

        String usernamePassword = REST_SERVER_USERNAME+":"+REST_SERVER_PASSWORD;
        endoding= android.util.Base64.encodeToString(usernamePassword.getBytes(), Base64.DEFAULT);
        
        URL obj = new URL(url + methodAddress);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());

        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization","Basic "+endoding);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

    public String doGetPlainText(String methodAddress) throws Exception {

        String usernamePassword = REST_SERVER_USERNAME+":"+REST_SERVER_PASSWORD;
        endoding= android.util.Base64.encodeToString(usernamePassword.getBytes(), Base64.DEFAULT);

        URL obj = new URL(url + methodAddress);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());

        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization","Basic "+endoding);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }


    /** HTTP POST request
     * Sends a HHTP POST taking a JSONstring as a parameter
     * @param methodAddress
     * @param paramaeter
     * @return
     * @throws Exception
     * Suitable for calling POST methods on the server that create new objects and Consume JSON strings
     */

    public String sendPostJsonString(String methodAddress, String paramaeter) throws Exception {


        String usernamePassword = REST_SERVER_USERNAME+":"+REST_SERVER_PASSWORD;
        endoding= android.util.Base64.encodeToString(usernamePassword.getBytes(), Base64.DEFAULT);

        URL obj = new URL(url + methodAddress);
        System.out.println("URL to string: "+obj.toString());
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization","Basic "+endoding);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(paramaeter.getBytes());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url+methodAddress+paramaeter);
        System.out.println("Post parameters : " + paramaeter);
        System.out.println("Response Code : " + responseCode);


        return String.valueOf(responseCode);
    }

    /** HTTP POST request
     * Sends a HHTP POST taking a String as a parameter
     * @param methodAddress
     * @param paramaeter
     * @return
     * @throws Exception
     * Suitable for POST methods that dont consume JSON strings, just plain old text strings appened on the URL
     */
    public String sendPost(String methodAddress, String paramaeter) throws Exception {


        String usernamePassword = REST_SERVER_USERNAME+":"+REST_SERVER_PASSWORD;
        endoding= android.util.Base64.encodeToString(usernamePassword.getBytes(), Base64.DEFAULT);

        URL obj = new URL(url + methodAddress+paramaeter);
        System.out.println("URL to string: "+obj.toString());
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization","Basic "+endoding);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url+methodAddress+paramaeter);
        System.out.println("Post parameters : " + paramaeter);
        System.out.println("Response Code : " + responseCode);

        return String.valueOf(responseCode);
    }

    public String sendPut(String methodAddress) throws Exception {

        String usernamePassword = REST_SERVER_USERNAME+":"+REST_SERVER_PASSWORD;
        endoding= android.util.Base64.encodeToString(usernamePassword.getBytes(), Base64.DEFAULT);

        URL obj = new URL(url + methodAddress);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        //add reuqest header
        con.setRequestMethod("PUT");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'PUT' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

    public String sendDelete(String methodAddress) throws Exception {

        URL obj = new URL(url + methodAddress);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        //add reuqest header
        con.setRequestMethod("DELETE");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

}
