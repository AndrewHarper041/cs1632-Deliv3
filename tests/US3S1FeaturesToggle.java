//Test for US-3 Scenario-1
//Tests that user can toggle is repo's options in the features field

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US3S1FeaturesToggle {
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
  public void testUS3S1FeaturesToggle() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//div[@id='your_repos']/div/a")).click();
        driver.findElement(By.id("repository_name")).clear();
        driver.findElement(By.id("repository_name")).sendKeys("temprepo");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//ul[3]/li/a/span[2]")).click();
        driver.findElement(By.id("wiki-feature")).click();
        try {
            assertEquals("off", driver.findElement(By.id("wiki-feature")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("wiki-feature")).click();
        try {
            assertEquals("on", driver.findElement(By.id("wiki-feature")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("wiki-pusher-access")).click();
        try {
            assertEquals("on", driver.findElement(By.id("wiki-pusher-access")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("wiki-pusher-access")).click();
        try {
            assertEquals("off", driver.findElement(By.id("wiki-pusher-access")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("issue-feature")).click();
        try {
            assertEquals("off", driver.findElement(By.id("issue-feature")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("issue-feature")).click();
        try {
            assertEquals("on", driver.findElement(By.id("issue-feature")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
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
