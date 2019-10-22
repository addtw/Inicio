/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sequal.selenium;

import com.mongodb.BasicDBObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author rent-com
 */
public class Automation {

    static boolean error = false;
    static String result = "";
    static int timeout = 30;
    static BasicDBObject json = null;
    static BasicDBObject vars = null;

    public static void main(String[] args) {
        if (args.length == 0) {
            json = new BasicDBObject();
            vars = new BasicDBObject();
            json.append("vars", vars);
            vars.append("SHIPTO", "0029");
            vars.append("SEQ", "2");
        } else {
            json = BasicDBObject.parse(args[0]);
            vars = (BasicDBObject) json.get("vars");
        }
        try {
            if (vars != null && vars.getString("SHIPTO") != null && vars.getString("SEQ") != null) {
//                JOptionPane.showConfirmDialog(null, vars.getString("SEQ"));
                runAutomationCedi(getDriver(), vars.getString("SHIPTO"), vars.getString("SEQ"));
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error en JAR: " + e.getLocalizedMessage());
        }

    }

    private static ChromeDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        if (vars != null && vars.getString("HEADLESS") != null && vars.getString("HEADLESS").equals("TRUE")) {
            options.addArguments("--headless");
        }
//        options.addArguments("--disable-gpu");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    private static void sleep(long mil) {
        try {
            Thread.sleep(mil);
        } catch (Exception e) {
        }
    }

    private static void runAutomationCedi(ChromeDriver driver, String SHIPTO, String SEQ) {
        driver.get("http://wmsappqa1.grupo-exito.com/scqa/sce/sceapp.ctrl");

        findElementByXPath(driver, "//*[@id=\"Iujgbo2\"]").sendKeys("t-zvargas");

        findElementByXPath(driver, "//*[@id=\"I604i25\"]").sendKeys("zulavasi555+");

        clickByXpath(driver, "//*[@id=\"$l6fjoa\"]");
        
        clickByXpath(driver, "//*[@id=\"$cw862c_image\"]");

        clickByXpath(driver, "/html/body/form/div[2]/span[2]/div/div/table/tbody/tr/td/div[9]/table/tbody/tr/td[1]/div/div");

        clickByXpath(driver, "//*[@id=\"$jniyz2_arrow\"]");

        clickByXpath(driver, "//*[@id=\"toggle_$moj83l_image\"]");

        clickByXpath(driver, "//*[@id=\"$ohi6s9\"]/div/table/tbody/tr/td/div/ul/li");
        
        clickByXpath(driver, "//*[@id=\"$sdwcs9_filterbutton\"]");
        
//        clickByXpath(driver, "//*[@id=\"$sdwcs9_cell_1_0_Img\"]");//TEXT1
        clickByXpath(driver, "//*[@id=\"$sdwcs9_cell_2_0_Img\"]");//camiar este paso solamente
        
        clickByXpath(driver, "//*[@id=\"As3jlst_label\"]");
        
        sendKEYSByXpath(driver, "//*[@id=\"Iti99ft\"]", SHIPTO);
//        findElementByXPath(driver, "//*[@id=\"Iti99ft\"]").sendKeys(SHIPTO);
        sendKEYSByXpath(driver, "//*[@id=\"Ibvdx9h\"]", SHIPTO);
//        findElementByXPath(driver, "//*[@id=\"Ibvdx9h\"]").sendKeys(SHIPTO);
        sleep(2000);
        sendKEYSByXpath(driver, "//*[@id=\"Io4hrs5\"]", SEQ);
//        findElementByXPath(driver, "//*[@id=\"Io4hrs5\"]").sendKeys(SEQ);
        
        clickByXpath(driver, "//*[@id=\"A4qglj7_label\"]");
//        sleep(1000);
        findElementByXPath(driver, "//*[@id=\"$tykn4z_label\"]");
        close(driver, false, "");

        
    }

    public static WebElement findElementByXPath(ChromeDriver driver, String xpath) {
        long timeCurrent = System.currentTimeMillis();
        String er = "";
        while (true) {
            try {
                WebElement element = driver.findElementByXPath(xpath);
                return element;
            } catch (Exception e) {
//                e.printStackTrace();
                er = e.getMessage();
            }
            sleep(200);
            if (System.currentTimeMillis() - timeCurrent > timeout * 1000) {
                close(driver, true, "\nElement no found: " + er);
            }
        }
    }

    public static void clickByXpath(ChromeDriver driver, String XPath) {
        long timeCurrent = System.currentTimeMillis();
        String er = "";
        while (true) {
            try {
                findElementByXPath(driver, XPath).click();
                return;
            } catch (Exception e) {
                er = e.getMessage();
            }
            sleep(200);
            if (System.currentTimeMillis() - timeCurrent > timeout * 1000) {
                close(driver, true, "\nElement no clickeable: " + er);
            }
        }
    }
    
    public static void sendKEYSByXpath(ChromeDriver driver, String XPath, String text) {
        long timeCurrent = System.currentTimeMillis();
        String er = "";
        while (true) {
            try {
                WebElement element = findElementByXPath(driver, XPath);
                element.clear();
                element.sendKeys(text);
                if(!element.getAttribute("value").equals(text)){
                    throw new Exception();
                }
                return;
            } catch (Exception e) {
                er = e.getMessage();
            }
            sleep(200);
            if (System.currentTimeMillis() - timeCurrent > timeout * 1000) {
                close(driver, true, "\nElement no clickeable: " + er);
            }
        }
    }

    private static void close(ChromeDriver driver, boolean hasError, String result) {
        driver.quit();
        if (json != null) {
            json.append("error", hasError);
            json.append("result", result);
            if(!hasError){
                vars.append("STATE", "OK");
            }
            System.out.println(json.toString());
        }
        System.exit(0);
    }
}
