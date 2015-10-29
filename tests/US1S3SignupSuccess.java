//Test for US-1 Scenario-3
//Checks when given a unique user name, a new user will be created

//NOTEs: 
//If user name is in use (taken by another user or if the automatic deletion of the new user fails), the test will fail.
//Make sure that the user is always deleted at the end, or it will fail without manual intervention

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US1S3SignupSuccess {
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
  public void testSignupSuccess() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Sign up")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("fakeRoboUser");
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("fake@temp.com");
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("fakepassword1");
        driver.findElement(By.id("signup_button")).click();
        assertEquals("Finish sign up", driver.findElement(By.xpath("//div[@id='js-pjax-container']/div/div[2]/div/form/div[4]/button")).getText());
        driver.findElement(By.xpath("//ul[@id='user-links']/li[3]/a/span")).click();
        driver.findElement(By.linkText("Settings")).click();
        driver.findElement(By.linkText("Account settings")).click();
        driver.findElement(By.linkText("Delete your account")).click();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > p > #sudo_login")).clear();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > p > #sudo_login")).sendKeys("fakeRoboUser");
        driver.findElement(By.xpath("(//input[@id='confirmation_phrase'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@id='confirmation_phrase'])[2]")).sendKeys("delete my account");
        driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
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
