//Test for US-1 Scenario-2
//Tests that a logged in user can successfully make a repo


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US2S2MakeNewRepo {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  private static final String USERNAME = "pittqa";
  private static final String PASSWORD = "pittqa1";
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
    logIn(USERNAME, PASSWORD);
  }
  
/**
 * Test that we can successfully make a new repository. 
 * We know the creation is successful if we land on the repository's page after creation.
 */
  @Test
  public void testMakeNewRepo() throws Exception {
	  	// Make repository
	    driver.get("https://github.com/new");
	    driver.findElement(By.id("repository_name")).clear();
	    driver.findElement(By.id("repository_name")).sendKeys("testrepo");
	    driver.findElement(By.id("repository_description")).clear();
	    driver.findElement(By.id("repository_description")).sendKeys("very nice");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    // Make sure we are on the new repo page
	    String expected = "https://github.com/" + USERNAME + "/testrepo";
	    String observed = driver.getCurrentUrl();
	    assertEquals(expected, observed);
  }
  
  
  private void deleteRepository() {
	    driver.findElement(By.xpath("(//a[contains(@href, '/pittqa/testrepo/settings')])[2]")).click();
	    driver.findElement(By.linkText("Delete this repository")).click();
	    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).clear();
	    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).sendKeys("testrepo");
	    driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();	  
  }

  @After
  public void tearDown() throws Exception {
	deleteRepository();
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
