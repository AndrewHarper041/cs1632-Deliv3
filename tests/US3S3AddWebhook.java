//Test for US-3 Scenario-3
//Tests that user can add a webhook to their repo

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US3S3AddWebhook {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUS3S3AddWebhook() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.cssSelector("span.repo")).click();
        driver.findElement(By.xpath("//ul[3]/li/a/span[2]")).click();
        driver.findElement(By.linkText("Webhooks & services")).click();
        driver.findElement(By.linkText("Add webhook")).click();
        driver.findElement(By.id("hook_url")).clear();
        driver.findElement(By.id("hook_url")).sendKeys("cs1632@pitt.edu");
        driver.findElement(By.id("hook_secret")).clear();
        driver.findElement(By.id("hook_secret")).sendKeys("cs1632pass");
        driver.findElement(By.id("hook_url")).clear();
        driver.findElement(By.id("hook_url")).sendKeys("https://www.payload.com");
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
        assertEquals("https://www.payload.com", driver.findElement(By.cssSelector("span.hook-url.css-truncate-target")).getText());
        assertTrue(isElementPresent(By.xpath("//div[@id='hooks_bucket']/div/div/ul/li/div/a[2]/span")));
        driver.findElement(By.xpath("//div[@id='hooks_bucket']/div/div/ul/li/div/a[2]/span")).click();
        driver.findElement(By.xpath("(//input[@value='Yes, delete webhook'])[2]")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
