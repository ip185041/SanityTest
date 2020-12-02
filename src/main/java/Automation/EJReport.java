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

public class EJReport {
	
	final static Logger log = Logger.getLogger(EJReport.class);
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
    public void EJReport(){
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/EJReport.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("EJReportTest");
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

	         
			 driver.findElement(By.id("sidebar-nav")).click();
		     Thread.sleep(3000);
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     
		     driver.findElement(By.id("navigationCategory_electronicjournal")).click();
		     driver.findElement(By.id("submenuItem_retrievalreport")).click();
		     extent.flush();

		     String error = driver.findElement(By.tagName("body")).getText();
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "This section contains an \"Error\"");
		     }else if (error.contains("Unable to load")) {
		    	 logger.log(Status.FAIL, "Ending test, the serivce is not available");
		    	 Logout();
		    	 Assert.fail("the serivce is not available");
		    	 }
		     extent.flush();

		     Thread.sleep(2000);
		     driver.findElement(By.id("checkbox_selectAll")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("ejSearch")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("btn_search")).click();
		     Thread.sleep(1000);
		     extent.flush();

		     if(driver.findElement(By.id("overallTableRow")).isDisplayed()){
		    	 logger.log(Status.PASS, "The page works");
		    	 String rPercent = driver.findElement(By.id("overallPercentage")).getText();
		    	 logger.log(Status.PASS, "Received Percent: " + rPercent);
		    	 String mPercent = driver.findElement(By.id("terminalOverallPercentage")).getText();
		    	 logger.log(Status.PASS, "Missing Percent: " + mPercent);
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