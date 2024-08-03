package testing;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Fitpeo {
	@Test 
	public void test()
	{
	System.setProperty("webdriver.chrome.driver", "./softwares/chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	JavascriptExecutor js=(JavascriptExecutor)driver;
	Actions Fitpeo_act=new Actions(driver);
	WebDriverWait wait =new WebDriverWait(driver, 10);
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	//1.	Navigate to the FitPeo Homepage:
	driver.get("https://fitpeo.com/");
	//2.	Navigate to the Revenue Calculator Page:
	driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Patients should be between 0 to 2000']")))); //wait untill Patients should be between 0 to 2000 is displayed
			
	//3.	Scroll Down to the Slider section:
	js.executeScript("window.scrollTo(0,300)");
	WebElement Fitpeo_drag_button = driver.findElement(By.xpath("//input[@aria-orientation='horizontal']"));
	//4.	Adjust the Slider:
	Fitpeo_act.dragAndDropBy(Fitpeo_drag_button, 94, 0).build().perform();
	
	WebElement Fitpeo_text_filed = driver.findElement(By.xpath("//input[@type='number']"));
	Fitpeo_text_filed.sendKeys(Keys.CONTROL+"a",Keys.DELETE); //Delete the value from Textfield
	
	//5.	Update the Text Field:
	Fitpeo_text_filed.sendKeys("560");
	//6.	Validate Slider Value:
	Assert.assertEquals(Fitpeo_text_filed.getAttribute("value"), "560");
	//7.	Scroll down:
	js.executeScript("window.scrollTo(0,300)");
	//8.    Select CPT Codes:
	List<WebElement> Fitpeo_check_box = driver.findElements(By.xpath("//div[@class='MuiBox-root css-4o8pys']/label/span[1]"));
	Fitpeo_check_box.get(0).click(); //select the checkboxes for CPT-99091
	Fitpeo_check_box.get(1).click();//select the checkboxes for CPT-99453
	Fitpeo_check_box.get(2).click();//select the checkboxes for CPT-99454
	Fitpeo_check_box.get(7).click();//select the checkboxes for CPT-99474
	//9.    Scroll up: 
	js.executeScript("window.scrollTo(0,-100)");
	//10.   Find the text of Total Recurring Reimbursement for all Patients Per Month:
	String Fitpeo_actual_Total_Gross_evenue_Per_Year = driver.findElement(By.xpath("//h3[@class='MuiTypography-root MuiTypography-h3 crimsonPro css-k7aeh2']")).getText();
	
	System.out.println(Fitpeo_actual_Total_Gross_evenue_Per_Year);

	String replaed = Fitpeo_actual_Total_Gross_evenue_Per_Year.replace("$","");
	System.out.println(replaed);
	double  Fitpeo_actualvalue= Double.parseDouble(replaed); //convert string to double 
	double expected_Total_Gross_evenue_Per_Year=110700.00;
	//11.	Validate Total Recurring Reimbursement
	Assert.assertEquals(Fitpeo_actualvalue, expected_Total_Gross_evenue_Per_Year,"Total Recurring Reimbursement for all Patients Per Month is Not valid");

	driver.quit();
	}

}
