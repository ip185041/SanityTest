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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class EJSearch {
	
	final static Logger log = Logger.getLogger(EJSearch.class);
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
    public void EJSearch(){
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/EJSearch.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("EJSearchTest");
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
		     extent.flush();

	         
			 Thread.sleep(1500);
			 driver.findElement(By.id("sidebar-nav")).click();
		     Thread.sleep(1000);
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     
		     driver.findElement(By.id("navigationCategory_electronicjournal")).click();
		     driver.findElement(By.id("submenuItem_electronicjournal")).click();
		     extent.flush();

		     String error = driver.findElement(By.tagName("body")).getText();
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "this page contains an \"Error\"");
		     }else if (error.contains("Unable to load portlet")) {
		    	 logger.log(Status.FAIL, "Ending test, the EJ serivce is not available");
		    	 Logout();
		    	 }     
		     
		     
		     Thread.sleep(2000);
		     WebDriverWait wait = new WebDriverWait(driver, 15);
		     wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearchOpen"))).click();
		     Thread.sleep(3000);
		     extent.flush();

		     
		     logger.log(Status.INFO, "Starting date change");
		     if(driver.getCurrentUrl().contains("becu") || driver.getCurrentUrl().contains("safe")) {
			     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
			     LocalDate past = LocalDate.now().minusDays(5);		
			     driver.findElement(By.id("inputFrom")).clear();
			   	 driver.findElement(By.id("inputFrom")).sendKeys(dtf.format(past));
			   	 driver.findElement(By.xpath("//*[@id=\"column1\"]/div/span[1]/i")).click();
			     Thread.sleep(2000);
				 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch"))).click();
				 Thread.sleep(3000);
		     }
		     else {
			     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyy");
			     LocalDate past = LocalDate.now().minusDays(5);		
			     driver.findElement(By.id("inputFrom")).clear();
			   	 driver.findElement(By.id("inputFrom")).sendKeys(dtf.format(past));
			   	 driver.findElement(By.xpath("//*[@id=\"column1\"]/div/span[1]/i")).click();
			     Thread.sleep(2000);
				 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch"))).click();
				 Thread.sleep(3000);
		     }
		     extent.flush();

		     
		     driver.findElement(By.id("listViewItem_1")).click();
		     Thread.sleep(2000);
		     
		     boolean raw = driver.findElement(By.id("electronicJournalAppContainer")).isDisplayed();
		     
		     if(raw==true) {
		    	 String raw_data = driver.findElement(By.id("electronicJournalAppContainer")).getText();
		    	 if(raw_data.contains("Tran")) {
		    		 logger.log(Status.PASS, "The raw data is displayed");
		    	 }
		     }
		     extent.flush();

		     
		     wait.until(ExpectedConditions.elementToBeClickable(By.id("switchToParsedView"))).click();
		     
		     if(driver.findElement(By.tagName("body")).getText().contains("EJ Raw Information have not been processed for Tabular View\r\n" + 
		     		"")) {
		    	 logger.log(Status.PASS, "The tablaur view is giving \" EJ Raw Information have not been processed for Tabular View \" ");
		     }else {
		    	 logger.log(Status.WARNING, "The tablaur view is also displaying correctly");
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