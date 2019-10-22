/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
{
 "vars":{
   "PLU":"116",
   "SUB":"513"
 },
 "error": boolean true o false,
 "errorInfo": "message"
}
*/
package com.sequal.selenium;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

    

public class Log {
    public static DateFormat formatFecha=new SimpleDateFormat("dd-MM-yyyy");
    public static DateFormat formatHora=new SimpleDateFormat("hh:mm:ss");
    public static File file=new File(System.getProperty("user.home") + "/Desktop/log/prueba.html");
    private  static String templateIni="<!DOCTYPE html>\n" +
        "<html>\n" +
        "<head>\n" +
        "<style>\n" +
        "t {\n" +
        "    color: black;\n" +
        "    font-family: verdana;\n" +
        "    font-size: 100%;\n" +
        "}\n" +
        "title {\n" +
        "    color: blue;\n" +
        "    font-family: verdana;\n" +
        "    font-size: 150%;\n" +
        "}\n" +
//        "element.style {\n" +
//        "    border: #5ae34f 3px solid;\n" +
//        "    border-radius: 10px;\n" +
//        "    border-width: 2px;\n" +
//        "    width: 1200px;"+
//        "}\n" +
        "</style>\n" +
        "</head>\n" +
        "<body>\n";
    private  static String templateFin=
        "</body>\n" +
        "</html>";
    private  static String templateExample=
        "<title><p>Log</p></title>\n" +
        "<t><p>This is a paragraph.</p></t>\n" +
        "<div>\n" +
        "    <img src=\"data:image/png;base64,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\">\n" +
        "</div> \n";
    
//    private static File file;
    public static void addFechaActual(){
        addText("Fecha: " + formatFecha.format(Calendar.getInstance().getTime()));
    }
    
    public static void addHoraActual(){
        addText("Hora: " + formatHora.format(Calendar.getInstance().getTime()));
    }
    
    public static void addText(String text){
        text=text.replaceAll("á", "&aacute");
        text=text.replaceAll("é", "&eacute");
        text=text.replaceAll("í", "&iacute");
        text=text.replaceAll("ó", "&oacute");
        text=text.replaceAll("ú", "&uacute");
        try {
//            File file=new File(Config.getValue(Config.EXECUTION_PATH_LOG));
            if(!file.exists()){
                file.createNewFile();
            }
            
            StringBuffer bu=new StringBuffer();
//            System.out.println("File: " + file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                bu.append(st).append("\n");
            }
            br.close();
            
            PrintWriter writer = new PrintWriter(file);
            if(bu.length()==0){
                writer.println(templateIni);
            }else{
                bu=bu.delete(bu.length()-17, bu.length());
                writer.println(bu);
            }
            writer.println("<t>" + text + "</t><br/>");
            writer.print(templateFin);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addTextError(String text){
        text=text.replaceAll("á", "&aacute");
        text=text.replaceAll("é", "&eacute");
        text=text.replaceAll("í", "&iacute");
        text=text.replaceAll("ó", "&oacute");
        text=text.replaceAll("ú", "&uacute");
        try {
//            File file=new File(Config.getValue(Config.EXECUTION_PATH_LOG));
            if(!file.exists()){
                file.createNewFile();
            }
            
            StringBuffer bu=new StringBuffer();
//            System.out.println("File: " + file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                bu.append(st).append("\n");
            }
            br.close();
            
            PrintWriter writer = new PrintWriter(file);
            if(bu.length()==0){
                writer.println(templateIni);
            }else{
                bu=bu.delete(bu.length()-17, bu.length());
                writer.println(bu);
            }
            writer.println("<t style=\"color: #bb0000;\">" + text + "</t><br/>");
            writer.print(templateFin);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addScreen(String imageBase){
        try {
//            File file=new File(Config.getValue(Config.EXECUTION_PATH_LOG));
            if(!file.exists()){
                file.createNewFile();
            }
            StringBuffer bu=new StringBuffer();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                bu.append(st).append("\n");
            }
            br.close();
            
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            if(bu.length()==0){
                writer.println(templateIni);
            }else{
                bu=bu.delete(bu.length()-17, bu.length());
                writer.println(bu);
            }
            writer.println("<img style=\"border: #5ae34f 3px solid;border-radius: 10px;border-width: 2px;width: 1200px;\"src=\"data:image/png;base64," + imageBase + "\"><br/><br/>");
            writer.print(templateFin);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addScreenError(String imageBase){
        try {
//            File file=new File(Config.getValue(Config.EXECUTION_PATH_LOG));
            if(!file.exists()){
                file.createNewFile();
            }
            StringBuffer bu=new StringBuffer();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                bu.append(st).append("\n");
            }
            br.close();
            
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            if(bu.length()==0){
                writer.println(templateIni);
            }else{
                bu=bu.delete(bu.length()-17, bu.length());
                writer.println(bu);
            }
            writer.println("<img style=\"border: #ff0000 3px solid;border-radius: 10px;border-width: 2px;width: 1200px;\"src=\"data:image/png;base64," + imageBase + "\"><br/><br/>");
            writer.print(templateFin);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String setToBase64(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    public static void saveCapture(WebDriver driver){
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage  fullImg = ImageIO.read(screenshot);
            String base64 = setToBase64(fullImg);
            addScreen(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void saveCaptureError(WebDriver driver){
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage  fullImg = ImageIO.read(screenshot);
            String base64 = setToBase64(fullImg);
            addScreenError(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void setFileName(String fileName){
        file=new File(System.getProperty("user.home") + "/Desktop/log/"+fileName+".html");
    }
    
    public static void main(String[] args) {
        Log.addText("Prueba " + formatFecha.format(Calendar.getInstance().getTime()) + " "+ formatHora.format(Calendar.getInstance().getTime()));
        Log.addScreen("data:image/jpeg;base64,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");
        
    }
}
