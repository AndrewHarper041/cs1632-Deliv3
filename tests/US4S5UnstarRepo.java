// Test for US-4 Scenario-5
// Test to make sure that unstarring functionality works.

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US4S5UnstarRepo {
  private WebDriver driver;
  private String baseUrl;
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
  private void starRepo() {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Click star button
	    driver.findElement(By.xpath("//form[2]/button")).click();
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
    starRepo();
  }
  
/**
 * When we unstar a repo, it should no longer appear in the user's stars page.
 */
  @Test
  public void testUnstarRepo() throws Exception {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Click unstar button
	  driver.findElement(By.xpath("//li[2]/div/form/button")).click();
	  // Make sure repo is not starred
	  driver.get("https://github.com/stars");
	  WebElement repo = driver.findElement(By.partialLinkText("ArnoldC"));
	  assertNull(repo);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
