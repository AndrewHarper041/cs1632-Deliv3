//Test for US-3 Scenario-6
//Tests that user can delete their repo

//Notes:
//In testing order, the 'temprepo' in question will be transfered, but the user seems to retain ownership until the new owner accepts, so can still be deleted.
//In order to test that the repo has been deleted, we assert that the name 'temprepo' IS NOT included in the users repos.

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US3S6DeleteRepo {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  //Hardcoded auth for 'permenent' user. Hopefully a bot doesn't steal this account.
  private static final String USERNAME = "cs1632user";
  private static final String PASSWORD = "cs1632pass";
  
  //Utility function
  private void logIn(String username, String password) {
	  driver.get(baseUrl + "/");
	  driver.findElement(By.linkText("Sign in")).click();
      driver.findElement(By.id("login_field")).clear();
      driver.findElement(By.id("login_field")).sendKeys(username);
      driver.findElement(By.id("password")).clear();
      driver.findElement(By.id("password")).sendKeys(password);
      driver.findElement(By.name("commit")).click();
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD); //login
  }

  @Test
  public void testUS3US5DeleteRepo() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("temprepo")).click();
        driver.findElement(By.xpath("//ul[3]/li/a/span[2]")).click();
        driver.findElement(By.linkText("Delete this repository")).click();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).clear();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).sendKeys("cs1632user/temprepo");
        driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();
        driver.findElement(By.id("your-repos-filter")).clear();
        driver.findElement(By.id("your-repos-filter")).sendKeys("temprepo");
        driver.findElement(By.linkText("All")).click();
        driver.findElement(By.id("your-repos-filter")).clear();
        driver.findElement(By.id("your-repos-filter")).sendKeys("");
        assertEquals("newrepo", driver.findElement(By.cssSelector("span.repo")).getText());
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
