//Test for US-3 Scenario-5
//Tests that user can transfer the repo's ownership to another

//Notes:
//The test transfer ownership to my own github account, but will be ignored until it is deleted so ownership does not technically leave the user.
//In order to actually test the transfer, one would have to log onto the existing user that it is transferred to in order to accept,
//and then back to the original temp user. Thus the hacky solution of only testing that that the user is prompted that their half of
//the transfer process was complete.

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US3S5TransferRepo {
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
  
  public void abortTransfer() {
      driver.get(baseUrl + "/");
      driver.findElement(By.id("your-repos-filter")).clear();
      driver.findElement(By.id("your-repos-filter")).sendKeys("temprepo");
      driver.findElement(By.linkText("temprepo")).click();
      driver.findElement(By.xpath("(//a[contains(@href, '/cs1632user/temprepo/settings')])[2]")).click();
      driver.findElement(By.cssSelector("button.btn.boxed-action")).click();
      driver.findElement(By.cssSelector("span.mega-octicon.octicon-mark-github")).click();
}
  
  private void deleteRepo() {
	  driver.get(baseUrl + "/");
	  driver.findElement(By.cssSelector("span.repo")).click();
	  driver.findElement(By.xpath("(//a[contains(@href, '/cs1632user/temprepo/settings')])[2]")).click();
	  driver.findElement(By.linkText("Delete this repository")).click();
	  driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).clear();
	  driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).sendKeys("cs1632user/temprepo");
	  driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();
  }
  
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
    createRepo(); //Make temp repo
  }

  @Test
  public void testUS3US5TransferRepo() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.cssSelector("span.repo")).click();
        driver.findElement(By.xpath("//ul[3]/li/a/span[2]")).click();
        driver.findElement(By.id("transfer_button")).click();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > dl.form > dd > #confirm_repository_name")).clear();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > dl.form > dd > #confirm_repository_name")).sendKeys("temprepo");
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > dl.form > dd > #confirm_new_owner")).clear();
        driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > dl.form > dd > #confirm_new_owner")).sendKeys("graphitezeppelin");
        
        //Hack to trigger the onchange javascript event we are looking for.
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("$(arguments[0]).change();", driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > dl.form > dd > #confirm_new_owner")));
        
        driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();
        assertEquals("This repository is being transferred to @graphitezeppelin.", driver.findElement(By.xpath("//div[@id='options_bucket']/div[4]/div/p[2]")).getText());
  }

  @After
  public void tearDown() throws Exception {
	deleteRepo(); //delete temp repo
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
