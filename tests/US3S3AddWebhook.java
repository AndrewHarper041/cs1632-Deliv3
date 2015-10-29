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
  
  //Deletes the temprepo
  private void deleteRepo() {
	  driver.get(baseUrl + "/");
	  driver.findElement(By.cssSelector("span.repo")).click();
	  driver.findElement(By.xpath("(//a[contains(@href, '/cs1632user/temprepo/settings')])[2]")).click();
	  driver.findElement(By.linkText("Delete this repository")).click();
	  driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).clear();
	  driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).sendKeys("cs1632user/temprepo");
	  driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();
  }
  
  //Creates the temprepo
  private void createRepo() {
	  driver.get(baseUrl + "/");
      driver.findElement(By.xpath("//div[@id='your_repos']/div/a")).click();
      driver.findElement(By.id("repository_name")).clear();
      driver.findElement(By.id("repository_name")).sendKeys("temprepo");
      driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD); //login
    createRepo();
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
        assertTrue(isElementPresent(By.xpath("//div[@id='hooks_bucket']/div/div/ul/li/div/a[2]/span")));
        driver.findElement(By.xpath("//div[@id='hooks_bucket']/div/div/ul/li/div/a[2]/span")).click();
        driver.findElement(By.xpath("(//input[@value='Yes, delete webhook'])[2]")).click();
  }

  @After
  public void tearDown() throws Exception {
	deleteRepo();
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
