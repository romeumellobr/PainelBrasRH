package org.tensorflow.lite.examples.detection.Biblis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class DataeHora{
    public String data(){
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    } public String diames(){
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String hora(){
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String horaeminuto(){
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }    

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    } }