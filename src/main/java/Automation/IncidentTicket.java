package Automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class IncidentTicket {
	
	final static Logger log = Logger.getLogger(IncidentTicket.class);
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
    public void IncidentTicket(){

   	 	ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/IncidentTicket.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("IncidentTicketTest");
	     try{


			 File file = new File("../logininfo.properties");
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
			     extent.flush();

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
		     extent.flush();
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
			 
	         
			 Thread.sleep(1500);
			 driver.findElement(By.id("sidebar-nav")).click();
		     Thread.sleep(3000);
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     extent.flush();

		     driver.findElement(By.id("navigationCategory_mynetwork")).click();
		     driver.findElement(By.id("submenuItem_incidentdetails")).click();
		     
		     String error = driver.findElement(By.tagName("body")).getText();
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "This section contains an \"Error\"");
		     }else if (error.contains("Unable to load portlet")) {
		    	 logger.log(Status.FAIL, "Ending test, the Incident serivce is not available");
		    	 Logout();
		    	 Assert.fail("Test Failed");
		     }	
		     
		     Thread.sleep(3000);
		     driver.findElement(By.id("searchTypeText")).sendKeys("NCRTEST");
		     driver.findElement(By.id("searchTypeText")).sendKeys(Keys.ENTER);
		     extent.flush();

		     driver.findElement(By.id("accrdGroup_systemDetails")).click();
		     driver.findElement(By.id("incident-panel")).click();
		     Thread.sleep(3000);
		     driver.findElement(By.id("addIncidentBtn")).click();
		     Thread.sleep(3000);
		     driver.findElement(By.id("statusCodeEditable")).sendKeys("Cash Out");
		     Thread.sleep(5000);
		     driver.findElement(By.id("statusCodeEditable")).sendKeys(Keys.ENTER);
		     Thread.sleep(5000);
		     driver.findElement(By.id("summarySaveBtn")).click();
		     Thread.sleep(700);
		     
		     driver.findElement(By.id("breadcrumb-mc")).click();
		     extent.flush();

		     String TicketStatus = driver.findElement(By.id("inc-dispatch-status-0")).getText();
		     if(TicketStatus.contains("Open")) {
		    	 logger.log(Status.PASS, "Incident Ticket created Successfully!");
			     Thread.sleep(1000);
		     }
	    	 logger.log(Status.PASS, "Incident Ticket created Successfully!");

		     driver.findElement(By.id("searchTypeText")).sendKeys(Keys.ENTER);
		     extent.flush();

		     int i = 15;
		     String oStatus = driver.findElement(By.id("inc-dispatch-status-0")).getText();
		     while((!(oStatus.contains("Dispatched"))) && i <= 15) {
		    	 driver.findElement(By.id("searchTypeText")).sendKeys(Keys.ENTER);
			     Thread.sleep(3000);
			     i++;
		     }
		     
		     String dStatus = driver.findElement(By.id("inc-dispatch-status-0")).getText();
		     if(dStatus.contains("Dispatched")){
		    	 logger.log(Status.PASS, "Incident Ticket Dispatched!");
		    	 Thread.sleep(1000);
		     }
		     extent.flush();

		     driver.findElement(By.id("incident-0")).click();
		     
		     Thread.sleep(10000);
		     
		     String comments = driver.findElement(By.id("comments")).getText();
		     if(comments.contains("No comments found")) {
		    	 logger.log(Status.PASS, "The comments are not shown!"); 
		    	 Thread.sleep(2000);
		     }
		     extent.flush();

		     
		     driver.findElement(By.id("summaryEditBtn")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("endTimeCalendar")).click();
		     Thread.sleep(1000);
		     
		     driver.findElement(By.id("summarySaveBtn")).click();
		     Thread.sleep(2000);

		     driver.findElement(By.id("breadcrumb-mc")).click();
		     
		     String tStatus = driver.findElement(By.id("inc-dispatch-status-0")).getText();
		     if(tStatus.contains("Closed")) {
		    	 logger.log(Status.PASS, "Incident Ticket Closed");
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