package MakeMyTrip;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Make_My_Trip {
	WebDriver driver;
	JavascriptExecutor js;
	ExtentReports extent;
	ExtentTest extenttest;
	TakesScreenshot ts;
	String Hotel;
	String HotelName;
	String Activity;
	
	@BeforeTest
	public void step1_URL() {
		
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/MakeMyTrip.html");
        extent.loadConfig(new File(System.getProperty("user.dir")+"/src/test/java/extent-config.xml"));
        
		WebDriverManager.chromedriver().setup();
		ChromeOptions op = new ChromeOptions();
        op.addArguments("--disable-notifications");
		driver = new ChromeDriver(op);
		js = (JavascriptExecutor) driver;
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@Test(priority = 0)
	public void step2_SelectFromAndTO() throws InterruptedException {
		
		extenttest = extent.startTest("Make_My_Trip");
		
		driver.findElement(By.cssSelector("a[href='https://www.makemytrip.com/holidays-india/']")).click();
		extenttest.log(LogStatus.PASS, "Holiday Packages is chosen");
		
		driver.findElement(By.xpath("//*[contains(text(),'From City')]")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Bangalore')]")).click();
		extenttest.log(LogStatus.PASS, "From City is chosen");
		
		driver.findElement(By.id("toCity")).click();
	    driver.findElement(By.xpath("(//*[@class='dest-search-input'])")).sendKeys("Singapore");
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("(//*[contains(text(),'Singapore')])[1]")).click();
	    extenttest.log(LogStatus.PASS, "To City is chosen");
	}
	
	@Test(priority = 1)
	public void step3_SelectDateAndSearch() throws InterruptedException {
	    
	    driver.findElement(By.xpath("//div[@aria-label='Thu Jul 20 2023']")).click();
	    extenttest.log(LogStatus.PASS, "Departure Date is chosen");
	    
	    driver.findElement(By.xpath("//*[contains(text(),'APPLY')]")).click();
	    Thread.sleep(3000);
	    
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("window.scrollBy(0,200)");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[contains(text(),'APPLY')]")).click();
	    
	    driver.findElement(By.id("search_button")).click();
	    extenttest.log(LogStatus.PASS, "Search Button is clicked");
	    
	    driver.findElement(By.xpath("//*[contains(text(),'SKIP')]")).click();
	    WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='close closeIcon'])")));
        element.click();
        Thread.sleep(3000);
        
	}
	
	@Test(priority = 2)
	public void step4_SelectPackage() throws InterruptedException {
        
	    //js.executeScript("window.scrollBy(0,50)");
		WebElement packages = driver.findElement(By.xpath("(//*[contains(text(),'Specially curated Packages ')])"));
        js.executeScript("arguments[0].scrollIntoView();", packages);
	    Thread.sleep(3000);
	    
//	    driver.findElement(By.xpath("(//*[@class='slick-arrow slick-next '])[1]")).click();
//	    Thread.sleep(2000);
//	    driver.findElement(By.xpath("(//*[@class='slick-arrow slick-next '])[1]")).click();
//	    driver.findElement(By.xpath("//div[@data-index='1']")).click();
	    driver.findElement(By.xpath("(//*[@class='slick-slide slick-active slick-current'])[1]")).click();
	    extenttest.log(LogStatus.PASS, "Package is chosen");
	    
	    String currentWindowHandle = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//*[contains(text(),'SKIP')]")).click();
        Thread.sleep(7000);
        
	}
	
	@Test(priority = 3)
	public void step5_ChangeHotel() throws InterruptedException, AWTException {
        
        Robot rob = new Robot();
    	rob.mouseWheel(5);
    	Thread.sleep(3000);
       
    	Hotel = driver.findElement(By.xpath("//*[@class='hotel-row-details-title']")).getText();
    	System.out.println(Hotel);
    	driver.findElement(By.xpath("//*[@class='hotel-row-details-title']")).click();
    	
        Thread.sleep(10000);
        driver.findElement(By.xpath("//*[@class='chngeLink linkText cursorPointer']")).click();
        extenttest.log(LogStatus.PASS, "Change button to change hotel is clicked");
        
	}
	
	@Test(priority = 4)
	public void step6_SelectHotel() throws InterruptedException {
        
        Thread.sleep(10000);
        List<WebElement> hotels = driver.findElements(By.xpath("//*[@class='hotelHeading']"));
        hotels.get(4).click();
        
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//*[@class='primaryBtn fill selectBtn'])[3]")).click();
        extenttest.log(LogStatus.PASS, "Hotel is chosen");
        
	}
	
	@Test(priority = 5)
	public void step7_UpdatePackage() throws InterruptedException {
        
        driver.findElement(By.id("updateHotelBtnClick")).click();
        extenttest.log(LogStatus.PASS, "Package is updated");
	}
	
	@Test(priority = 6)
	public void step8_ValidateItinerary() throws InterruptedException {
        
        Thread.sleep(3000);
        HotelName = driver.findElement(By.xpath("//*[@class='hotel-row-details-title']")).getText();
        System.out.println(HotelName);
        
        Assert.assertNotEquals(HotelName,Hotel);
        System.out.println("Change of Hotel is reflected in the Itinerary");
        extenttest.log(LogStatus.PASS, "Itinerary is validated: Change of Hotel is reflected in the Itinerary");
        
	}
	
	@Test(priority = 7)
	public void step9_AddActivity() throws InterruptedException {
        
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@class=' ']")).click();
        driver.findElement(By.id("chooseAndAddBtn")).click();
        
        Thread.sleep(2000);
        List<WebElement> activities = driver.findElements(By.xpath("//*[@class='activityHeading']"));
        activities.get(5).click();
        
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//*[@class='primaryBtn fill selectBtn'])[5]")).click();
        extenttest.log(LogStatus.PASS, "Activity is added");
        
        
	}
	
	@Test(priority = 8)
	public void step10_UpdatePackage1() throws InterruptedException {
		
        driver.findElement(By.xpath("//*[@class='updatePackageBtnText font10 btn btn-primary btn-sm']")).click();
        extenttest.log(LogStatus.PASS, "Package is updated");
	}
	
	@Test(priority = 9)
	public void step11_ValidateActivityUpdate() throws InterruptedException {
        
        Thread.sleep(3000);
        Activity = driver.findElement(By.xpath("//*[@class='activity-row-details-title']")).getText();
        System.out.println(Activity);
        System.out.println("Activity is added to the plan");
        extenttest.log(LogStatus.PASS, "Activity update is validated");
	
	}
	
	@Test(priority = 10)
	public void step12_ValidateAllUpdates() throws InterruptedException, IOException {
        
        driver.findElement(By.xpath("//*[@class='initerary-nav']/li[1]")).click();
        Thread.sleep(2000);
        System.out.println("DayPlan hotel: "+HotelName+" is displayed");
        System.out.println("DayPlan Activity: "+Activity+" is displayed");
        ts = (TakesScreenshot)driver;
        File sfile = ts.getScreenshotAs(OutputType.FILE);
        File dfile = new File("/MakeMyTrip_proj/test-output/Images/DayPlan.png");
        FileUtils.copyFile(sfile, dfile);
        extenttest.log(LogStatus.PASS, extenttest.addScreenCapture("/MakeMyTrip_proj/test-output/Images/DayPlan.png")+"All updates are validated in the Day Plan link");
        
        driver.findElement(By.xpath("//*[@class='initerary-nav']/li[2]")).click();
        Thread.sleep(2000);
        System.out.println("Hotel section: "+HotelName);
        File sfile1 = ts.getScreenshotAs(OutputType.FILE);
        File dfile1 = new File("/MakeMyTrip_proj/test-output/Images/Hotel.png");
        FileUtils.copyFile(sfile1, dfile1);
        extenttest.log(LogStatus.PASS, extenttest.addScreenCapture("/MakeMyTrip_proj/test-output/Images/Hotel.png")+"All updates are validated in the Hotel link");
        
        driver.findElement(By.xpath("//*[@class='initerary-nav']/li[3]")).click();
        Thread.sleep(2000);
        String Transfer = driver.findElement(By.xpath("//*[@class='activity-row-text-desc']")).getText();
        System.out.println("Transfer section: "+Transfer);
        File sfile2 = ts.getScreenshotAs(OutputType.FILE);
        File dfile2 = new File("/MakeMyTrip_proj/test-output/Images/Transfer.png");
        FileUtils.copyFile(sfile2, dfile2);
        extenttest.log(LogStatus.PASS, extenttest.addScreenCapture("/MakeMyTrip_proj/test-output/Images/Transfer.png")+"All updates are validated in the Transfer link");
        
        driver.findElement(By.xpath("//*[@class='initerary-nav']/li[4]")).click();
        Thread.sleep(2000);
        System.out.println("Activities section: "+Activity);
        File sfile3 = ts.getScreenshotAs(OutputType.FILE);
        File dfile3 = new File("/MakeMyTrip_proj/test-output/Images/Activities.png");
        FileUtils.copyFile(sfile3, dfile3);
        extenttest.log(LogStatus.PASS, extenttest.addScreenCapture("/MakeMyTrip_proj/test-output/Images/Activities.png")+"All updates are validated in the Activities link");
        
	}
	
	@AfterTest
	public void AT() {
		
		driver.quit();
		extent.endTest(extenttest);
		extent.flush();
		extent.close();
		
	}

}
	   


