//Test for US-3 Scenario-1
//Tests that user can add colobarators for a repo

//NOTES:
//Needed js hack
//Must delete collaborator after

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US3S2AddCollaborator {
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
  public void testUS3S2AddCollaborator() throws Exception {
	  driver.get(baseUrl + "/");
      driver.findElement(By.id("your-repos-filter")).clear();
      driver.findElement(By.id("your-repos-filter")).sendKeys("newrepo");
      driver.findElement(By.xpath("//ul[@id='repo_listing']/li[2]/a/span[2]/span")).click();
      driver.findElement(By.xpath("//ul[3]/li/a/span[2]")).click();
      driver.findElement(By.linkText("Collaborators")).click();
      driver.findElement(By.id("search-member")).clear();
      driver.findElement(By.id("search-member")).sendKeys("graphitezeppelin");
      
      //Hack to trigger the onchange javascript event we are looking for.
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("$(arguments[0]).change();", driver.findElement(By.id("search-member")));
      
      driver.findElement(By.id("search-member")).click();
      driver.findElement(By.xpath("//div[@id='collaborators']/form/div[4]/ul/li")).click();
      driver.findElement(By.cssSelector("button.btn.js-add-new-collab")).click();
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      
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
