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

public class Login {
	
	final static Logger log = Logger.getLogger(Login.class);
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
     public void Login(){
		 
    	 String fileName = "../logininfo.properties";
		 File file = new File(fileName);
		 FileInputStream fileInput = null;
		 
		 ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/login.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("LoginTest");

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

	     driver.findElement(By.name("username")).sendKeys(prop.getProperty("username")); 
         logger.log(Status.INFO, "Inserted the username");
		 driver.findElement(By.name("j_password")).sendKeys(prop.getProperty("password")); 
         logger.log(Status.INFO, "Inserted the password");
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

		 driver.findElement(By.id("navigationCategory_user")).click();
		 driver.findElement(By.id("submenuItem_logout")).click();
		 logger.log(Status.PASS, "Logout Successful!");
		 
		 extent.flush();
	     
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