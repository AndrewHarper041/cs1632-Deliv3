// Test for US-4 Scenario-2
// Test to make sure that repository starring works.

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US4S2StarRepo {
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

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
  }
  
/**
 * When we star a repo, it should appear in the user's stars page.
 */
  @Test
  public void testStarRepo() throws Exception {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Click star button
	    driver.findElement(By.xpath("//form[2]/button")).click();
	  // Check that repo is in starred list
	  driver.get("https://github.com/stars");
	  WebElement starredRepo = driver.findElement(By.partialLinkText("ArnoldC"));
	  assertNotNull(starredRepo);
  }

  @After
  public void tearDown() throws Exception {
	 // Unstar repo
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  driver.findElement(By.xpath("//li[2]/div/form/button")).click();
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
