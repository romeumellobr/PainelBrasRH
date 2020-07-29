package org.tensorflow.lite.examples.detection.Threads;



import org.tensorflow.lite.examples.detection.LoginActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Externo {

private String urlGetID="http://192.168.15.6:8080/ProjetoScaS/GerarIdPref";
private String urlChecaID="http://192.168.15.6:8080/ProjetoScaS/ChecaEstado";




    public String geraIdPf(String email, String valor, String titulo) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(urlGetID);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

          //  connection.setRequestProperty("Content-Length",
           //         Integer.toString(urlParameters.getBytes().length));
          //  connection.setRequestProperty("Content-Language", "en-US");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("email="+email+"&valor="+valor+"&titulo="+titulo);
            wr.close();




            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);

            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String checaEstado(String id) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(urlChecaID);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            //  connection.setRequestProperty("Content-Length",
            //         Integer.toString(urlParameters.getBytes().length));
            //  connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("id="+id);
            wr.close();




            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);

            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public String checaEstadoJson(String idJson) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(urlChecaID);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //  connection.setRequestProperty("Content-Length",
            //         Integer.toString(urlParameters.getBytes().length));
            //  connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("idJson="+idJson);
            wr.close();




            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);

            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

 }
