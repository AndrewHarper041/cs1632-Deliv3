// Test for US-4 Scenario-3
// Test to make sure that repository forking works.

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US4S3ForkRepo {
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
  private void deleteRepository(String username, String repo) {
	    driver.get("https://github.com/" + username + "/" + repo + "/settings");
	    driver.findElement(By.linkText("Delete this repository")).click();
	    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).clear();
	    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form.js-normalize-submit > p > input[name=\"verify\"]")).sendKeys(repo);
	    driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();	  
}
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
  }
  
/**
 * When we fork a repo, it should appear in the user's repo list.
 */
  @Test
  public void testForkRepo() throws Exception {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Click fork button
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  // Make sure fork is in repo list
	  driver.get("https://github.com/" + USERNAME + "?tab=repositories");
	  WebElement repo = driver.findElement(By.partialLinkText("ArnoldC"));
	  assertNotNull(repo);
  }

  @After
  public void tearDown() throws Exception {
	 // Delete forked repo
	deleteRepository(USERNAME, "ArnoldC");
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
