import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

@Test
public class demo1
{
//  @Test
//  public void testMethod2() throws Exception
//  {
//    navigate.goTo(ClinicalUrls.Residents.ResidentList.VIEW_CURRENT);
//    
//    ClinicalResidentListPage listPage = new ClinicalResidentListPage(driver);
//    ResidentCriteria resCriteria = new ResidentCriteria();
//    resCriteria.withStatus("Current");
//    
//    listPage.goToRandomResident(resCriteria);
//  
//    ClinicalResidentPage residentPage = new ClinicalResidentPage(driver);
//    residentPage.goToVitalsTab();
//    
//    WebElement vitalsTab = driver.findElement(By.id("tab7"));
//    vitalsTab.click();
//    
//  }
  
  public void testMethod1() throws InterruptedException
  {
    System.out.println("Testing TestNG!");
    
    
    System.setProperty("webdriver.chrome.driver",
    		"C:/Users/Haolin Huang/selenium-2.46.0/chromedriver.exe");
    
    WebDriver driver = new ChromeDriver();
    
    driver.get("http://yahoo.com");
 
    WebElement link = driver.findElement(By.cssSelector("a[title*='Jack King']"));
    link.click();
    
    Thread.sleep(2000);
  }
}