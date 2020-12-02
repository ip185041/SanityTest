package Automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

public class SWD {
	
	final static Logger log = Logger.getLogger(SWD.class);
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
    public void SWD(){

		ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/SWD.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		ExtentTest logger=extent.createTest("SWDTest");
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

			 Thread.sleep(6000);
			 driver.findElement(By.id("sidebar-nav")).click();
		     Thread.sleep(3000);
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     
		     
		     driver.findElement(By.id("navigationCategory_softwaredistribution")).click();
		     driver.findElement(By.id("submenuItem_softwaredistribution")).click();
		     extent.flush();

		     String parentHandle = driver.getWindowHandle();
		     log.info(parentHandle);
		     Thread.sleep(2000);
		     for (String winHandle : driver.getWindowHandles()) {
		    	 log.info(winHandle);
		         driver.switchTo().window(winHandle); 
		     }
	    	 String error = driver.findElement(By.tagName("body")).getText();
		     extent.flush();
		     		     
		     driver.findElement(By.id("li_0")).click();
		     logger.log(Status.INFO, "Cheking Home Page");
		     driver.findElement(By.id("a_li_sub_manu_item-0")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Home page contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on Home Page \n");
		     }
		     
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking Certificates Page");
		     driver.findElement(By.id("li_26")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Certificates Page contains an \"Error\"");
			     
		     }else {
			     logger.log(Status.PASS, "No errors on Certificates Page \n");
		     }
  
		     extent.flush();

		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking RC Cmd Manag Page");
		     driver.findElement(By.id("li_82")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "RC Cmd Manag Page contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on RC Cmd Manag Page \n");
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking Server Configurations Page");
		     driver.findElement(By.id("li_88")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Server Configurations Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Server Configurations Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking Files Store Page");
		     driver.findElement(By.id("li_89")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Files Store Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Files Store Page \n");		
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking System Terminal Logs Page");
		     driver.findElement(By.id("li_109")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "System Terminal Logs contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on System Terminal Logs Page \n");		
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_1")).click();
		     logger.log(Status.INFO, "Cheking Folder Synchronization Page");
		     driver.findElement(By.id("li_111")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Folder Synchronization Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Folder Synchronization Page \n");		
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_3")).click();
		     logger.log(Status.INFO, "Cheking Agent Monitoring Page");
		     driver.findElement(By.id("li_87")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Agent Monitoring Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Agent Monitoring Page \n");		
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_4")).click();
		     logger.log(Status.INFO, "Cheking Create Job Page");
		     driver.findElement(By.id("li_93")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Create Job Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Create Job Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_4")).click();
		     logger.log(Status.INFO, "Cheking Job Search Page");
		     driver.findElement(By.id("li_101")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Job Search Page contains an \"Error\"");
			     
		     }else {
			     logger.log(Status.PASS, "No errors on Job Search Page \n");	
		     }
 
		     
		     extent.flush();
		     
		     driver.findElement(By.id("li_4")).click();
		     logger.log(Status.INFO, "Cheking Job Calendar Page");
		     driver.findElement(By.id("li_102")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Job Calendar Page contains an \"Error\"");
			     
		     }else {
			     logger.log(Status.PASS, "No errors on Job Calendar Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_4")).click();
		     logger.log(Status.INFO, "Cheking Job Reports Page");
		     driver.findElement(By.id("li_72")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Job Reports Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Job Reports Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking File Explorer Page");
		     driver.findElement(By.id("li_49")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "File Explorer Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on File Explorer Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Installed Services Page");
		     driver.findElement(By.id("li_50")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Installed Services Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Installed Services Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Running Processes Page");
		     driver.findElement(By.id("li_51")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Running Processes Page contains an \"Error\"");
			     
		     }else {
			     logger.log(Status.PASS, "No errors on Running Processes Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Event Logs Page");
		     driver.findElement(By.id("li_52")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Event Logs Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Event Logs Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Retrieve Registries Page");
		     driver.findElement(By.id("li_53")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Retrieve Registries Page contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on Retrieve Registries Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Screen Capture Page");
		     driver.findElement(By.id("li_70")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Capture Page contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on Capture Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_5")).click();
		     logger.log(Status.INFO, "Cheking Custom Commands Page");
		     driver.findElement(By.id("li_81")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Custom Commands Page contains an \"Error\"");

		     }else {
			     logger.log(Status.PASS, "No errors on Custom Commands Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_6")).click();
		     logger.log(Status.INFO, "Cheking Package Management Page");
		     driver.findElement(By.id("li_56")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Package Management Page contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on Package Management Page \n");	
		     }
 
		     extent.flush();
		     
		     
		     driver.findElement(By.id("li_6")).click();
		     logger.log(Status.INFO, "Cheking Create Package Page");
		     driver.findElement(By.id("li_57")).click();
		     Thread.sleep(2000);
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Create PackagePage contains an \"Error\"");
		     }else {
			     logger.log(Status.PASS, "No errors on Create PackagePage \n");	
		     }
		     
		     extent.flush();
		     
		     driver.close(); // close newly opened window when done with it
		     driver.switchTo().window(parentHandle);

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