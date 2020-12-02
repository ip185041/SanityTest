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

public class MyFleet {
	
	final static Logger log = Logger.getLogger(MyFleet.class);
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
    public void MyFleet(){
		 ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/MyFleet.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("MyFleetTest");
	     try{


	    	 String fileName = "../logininfo.properties";
			 File file = new File(fileName);
			 FileInputStream fileInput = null;

//	    	 System.out.println(System.getProperty("user.dir" + fileName));

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
		     extent.flush();

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
		     
		     driver.findElement(By.id("navigationCategory_mynetwork")).click();
		     driver.findElement(By.id("submenuItem_managedclientadmin")).click();
		     extent.flush();

		     String error = driver.findElement(By.tagName("body")).getText();
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "This section contains an \"Error\"");
		     }else if (error.contains("Unable to load portlet")) {
		    	 logger.log(Status.FAIL, "Ending test, the Incident serivce is not available");
		    	 Logout();
		    	 Assert.fail("Test Failed");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking System Details");
		     String SD = driver.findElement(By.tagName("body")).getText();
		     if(SD.contains("Error") || SD.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in System Details");

		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking Custom Fields");
		     driver.findElementByXPath("//*[@id=\"accrdGroup_customFields\"]/div[1]/h4/a").click();
		     String CF = driver.findElement(By.tagName("body")).getText();
		     if(CF.contains("Error") || CF.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Custom Fields");
		     }
		     extent.flush();

		   
		     Thread.sleep(3000);
		     log.info("Checking Electronic Journal Attributes");
		     driver.findElementByXPath("//*[@id=\"accrdGroup_ejAttributes\"]/div[1]/h4/a").click();
		     String EJA = driver.findElement(By.tagName("body")).getText();
		     if(EJA.contains("Error") || EJA.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Electronic Journal Attributes ");
		     }
		     extent.flush();


		     Thread.sleep(3000);
		     log.info("Checking Electronic Journal Search");
		     driver.findElementByXPath("//*[@id=\"accrdGroup_ejSearchAttribute\"]/div[1]/h4/a").click();
		     String EJS = driver.findElement(By.tagName("body")).getText();
		     if(EJS.contains("Error") || EJS.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Electronic Journal Search ");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking Logical Cassette Levels");
		     driver.findElementByXPath("//*[@id=\"cashmgmLevelsAccordion\"]/div[1]/h4/a").click();
		     String LCL = driver.findElement(By.tagName("body")).getText();
		     if(LCL.contains("Error") || LCL.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Logical Cassette Levels ");
		     }
		     extent.flush();

		
		     Thread.sleep(3000);
		     log.info("Checking Transaction Thresholds");
		     driver.findElementByXPath("//*[@id=\"tranmgmAccordion\"]/div[1]/h4/a").click();
		     String TT = driver.findElement(By.tagName("body")).getText();
		     if(TT.contains("Error") || TT.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Transaction Thresholds ");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking PC Core");
		     driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/managed-client-admin-main-view/div/div/div/div/div[2]/accordion/div/inventory-accordion/div/div/inventory-groups-accordion[1]/div/div[1]/h4/a")).click();
		     String PCC = driver.findElement(By.tagName("body")).getText();
		     if(PCC.contains("Error") || PCC.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in PC Core ");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking Software");
		     driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/managed-client-admin-main-view/div/div/div/div/div[2]/accordion/div/inventory-accordion/div/div/inventory-groups-accordion[2]/div/div[1]/h4/a")).click();
		     String Soft = driver.findElement(By.tagName("body")).getText();
		     if(Soft.contains("Error") || Soft.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Software ");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking APTRA Promote Campaigns");
		     driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/managed-client-admin-main-view/div/div/div/div/div[2]/accordion/div/inventory-accordion/div/div/inventory-groups-accordion[3]/div/div[1]/h4/a")).click();
		     String APC = driver.findElement(By.tagName("body")).getText();
		     if(APC.contains("Error") || APC.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in APTRA Promote Campaigns ");
		     }
		     extent.flush();

		     
		     Thread.sleep(3000);
		     log.info("Checking Hardware");
		     driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/managed-client-admin-main-view/div/div/div/div/div[2]/accordion/div/inventory-accordion/div/div/inventory-groups-accordion[4]/div/div[1]/h4/a")).click();
		     String Hard = driver.findElement(By.tagName("body")).getText();
		     if(Hard.contains("Error") || Hard.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Hardware ");
		     }
		     extent.flush();

		     
		     
		     Thread.sleep(3000);
		     log.info("Checking Customer Power Campaigns");
		     driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/managed-client-admin-main-view/div/div/div/div/div[2]/accordion/div/inventory-accordion/div/div/inventory-groups-accordion[5]/div/div[1]/h4/a")).click();
		     String CPC = driver.findElement(By.tagName("body")).getText();
		     if(CPC.contains("Error") || CPC.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
		     }else {
		    	 logger.log(Status.PASS, "No errors in Customer Power Campaigns ");
		     }
		     Thread.sleep(6000);
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