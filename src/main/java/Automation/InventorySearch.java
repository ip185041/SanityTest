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

public class InventorySearch {
	
	final static Logger log = Logger.getLogger(InventorySearch.class);
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
    public void InventorySearch(){
		
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("../Reports/InventorySearch.html");
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 ExtentTest logger=extent.createTest("InventorySearchTest");
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
			 
	         
			 Thread.sleep(6000);
			 driver.findElement(By.id("sidebar-nav")).click();
		     Thread.sleep(3000);
		     String version = driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/div/div[2]/span")).getText();
		     log.info(version);
		     
		     driver.findElementByXPath("//*[@id=\"navigationCategory_inventory\"]/a").click();
		     driver.findElementByXPath("//*[@id=\"submenuItem_inventorysearch\"]/a/span").click();

	    	 String error = driver.findElement(By.tagName("body")).getText();
		     if(error.contains("Error") || error.contains("ERROR")) {
		    	 logger.log(Status.ERROR, "Ending test, this section contains an \"Error\"");
			     driver.close();
		    	 Assert.fail("Test Failed");
		     }else if (error.contains("Unable to load portlet")) {
		    	 logger.log(Status.FAIL, "Ending test, the serivce is not available");
			     driver.close();
		    	 Assert.fail("Test Failed");
		     }
		     
		     
		     Thread.sleep(3000);
		     driver.findElement(By.id("inventorySearchIcon")).click();
		     Thread.sleep(2000);
		     driver.findElement(By.id("dropdownAddCriteria")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("groupSoftware")).click();
		     Thread.sleep(1000);

		     driver.findElement(By.id("dropdownTypes")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("typeOperating System")).click();
		     Thread.sleep(1000);
		     
		     driver.findElement(By.id("dropdownProperties")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("propertyName")).click();
		     Thread.sleep(1000);
		     
		     driver.findElement(By.id("dropdownEquals")).click();
		     Thread.sleep(1000);
		     driver.findElement(By.id("contains")).click();
		     Thread.sleep(1000);
		     
		     driver.findElement(By.id("inputInventorySearch")).sendKeys("Microsoft");
		     Thread.sleep(1000);
		     driver.findElement(By.id("btn_nextTab")).click();
		     Thread.sleep(3000);
		     
		     logger.log(Status.PASS, "Search is completed!");
		     
		     if(driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/div/inventory-main-view/div/inventory-table-view/div/table/tbody/tr[1]")).isDisplayed()) {
		    	 driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/div/div/div/div/div/div/inventory-main-view/div/inventory-table-view/div/table/tbody/tr[1]")).click();
			     Thread.sleep(2000);
		     }
		     
		     if(driver.findElement(By.id("inventory")).isDisplayed()) {
		    	 
		    	 logger.log(Status.PASS, "Page showed up");
		    	 
		     }
		     
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