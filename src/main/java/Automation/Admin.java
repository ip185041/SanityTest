package Automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Admin {
	
	final static Logger log = Logger.getLogger(Admin.class);
	private static ChromeDriver driver;
	WebElement element;
	
	
	
	@BeforeClass
	 public static void openBrowser(){
		 System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        
		}
	
	@Test
    public void Admin(){
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/Admin.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		ExtentTest logger=extent.createTest("AdminTest");
	    try{

	    	 String fileName = "../logininfo.properties";
			 File file = new File(fileName);
			 FileInputStream fileInput = null;

			 try {
					fileInput = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Properties prop = new Properties();
				
				//load properties file
				try {
					prop.load(fileInput);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 
			 try {
	             logger.log(Status.INFO, "Checking if Cx Banking service is available");
	    		 driver.get(prop.getProperty("URL"));		
	             logger.log(Status.PASS, "Cx Banking service is available");
	    	 }catch (Exception e){
	    		 logger.log(Status.FAIL, "The CxBanking service is not running.");
	    	 }
			 Thread.sleep(1000);
		     driver.findElement(By.name("username")).sendKeys(prop.getProperty("username")); 
	         logger.log(Status.INFO, "Inserted the username");
		     Thread.sleep(1000);
			 driver.findElement(By.name("j_password")).sendKeys(prop.getProperty("password")); 
	         logger.log(Status.INFO, "Inserted the password");
		     Thread.sleep(1000);
			 driver.findElement(By.id("gwt-debug-loginButton")).click();
			 try {
				 Thread.sleep(6000);
				 driver.findElement(By.id("sidebar-nav"));
		         logger.log(Status.PASS, "Login Successful!");
	    	 }catch (Exception e){
		         logger.log(Status.FAIL, "Login not Successful!");
	        	 log.error(e);
	        	 driver.close();
	        	 Assert.fail("Login credentials were wrong");
	    	 }
		     extent.flush();

	         
			 driver.findElement(By.id("sidebar-nav")).click();
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     
		     
//		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking Security Groups");
		     driver.findElement(By.id("submenuItem_securitygroups")).click();
		     Thread.sleep(2000);
		     String sg = driver.findElement(By.tagName("body")).getText();
		     if(sg.contains("Error") || sg.contains("ERROR")) {
	    		 logger.log(Status.ERROR, "The page contains an error");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the security groups section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on Security Groups ");

		     }
		     extent.flush();

		     
		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking User Management Groups");
		     driver.findElement(By.id("submenuItem_usermanagement")).click();
		     Thread.sleep(2000);
		     String um = driver.findElement(By.tagName("body")).getText();
		     if(um.contains("Error") || um.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "the User Management groups section is not available");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the User Management groups section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on User Management Groups ");

		     }
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking Terminal Management Groups");
		     driver.findElement(By.id("submenuItem_terminalmanagement")).click();
		     Thread.sleep(2000);
		     String tm = driver.findElement(By.tagName("body")).getText();
		     if(tm.contains("Error") || tm.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "The Terminal Management Groups section contains an \"Error\"");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the Terminal Management groups section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on Terminal Management Groups ");

		     }
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking Audit Logs");
		     driver.findElement(By.id("submenuItem_auditlog")).click();
		     Thread.sleep(2000);
		     String al = driver.findElement(By.tagName("body")).getText();
		     if(al.contains("Error") || al.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "The Audit Logs section contains an \"Error\"");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the Audit Logs section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on Audit Logs ");

		     }
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking User Preferences");
		     driver.findElement(By.id("submenuItem_userpreferencesadmin")).click();
		     Thread.sleep(2000);
		     String up = driver.findElement(By.tagName("body")).getText();
		     if(up.contains("Error") || up.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "The User Preferences section contains an \"Error\"");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the User Preferences section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on User Preferences ");

		     }
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO, "Cheking License");
		     driver.findElement(By.id("submenuItem_license")).click();
		     Thread.sleep(2000);
		     String l = driver.findElement(By.tagName("body")).getText();
		     if(l.contains("Error") || l.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "The License section contains an \"Error\"");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the License section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on License ");

		     }
		     
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_admin")).click();
		     logger.log(Status.INFO,"Cheking Data Admin");
		     driver.findElement(By.id("submenuItem_dataadmin")).click();
		     Thread.sleep(2000);
		     String da = driver.findElement(By.tagName("body")).getText();
		     if(da.contains("Error") || da.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "The Data Admin section contains an \"Error\"");
		     }else if (sg.contains("Unable to load portlet")) {
		    	 logger.log(Status.ERROR, "the Data Admin groups section is not available");
		     }else {
		    	 logger.log(Status.PASS, "No errors on Data Admin ");

		     }
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_user")).click();
			 driver.findElement(By.id("submenuItem_logout")).click();
	    	 logger.log(Status.PASS, "Logout Successfully");
			 
		     
		     extent.flush();
  
		     
	     }catch (Exception e){
	    	 logger.log(Status.FAIL, e);
		     extent.flush();
	    	 log.error("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
		     driver.close();
		  }
	     
	     
	  }
	 
	 @AfterClass
	 public static void closeBrowser(){
		 driver.close();
	 }    	

	 public static void Logout(){		 
		 driver.findElement(By.id("navigationCategory_user")).click();
		 driver.findElement(By.id("submenuItem_logout")).click();
		 driver.close();
		 
	 }
}