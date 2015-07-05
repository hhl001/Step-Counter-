import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

@Test
public class Demo{
	public void testMethod1() throws InterruptedException
	  {
	    System.out.println("Testing TestNG!");
	    
	    
	    System.setProperty("webdriver.chrome.driver",
	    		"C:/Users/Haolin Huang/selenium-2.46.0/chromedriver.exe");
	    
	    WebDriver driver = new ChromeDriver();
	    
	    driver.get("http://www.google.com");
	 
	    WebElement element = driver.findElement(By.name("q"));
	    element.sendKeys("Automation Demo");
	    
	    element.submit();
	    
	    
	    Thread.sleep(5000);
	    
	    driver.quit();
	  }
}