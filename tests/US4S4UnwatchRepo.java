// Test for US-4 Scenario-4
// Test to make sure that repository unwatching functionality works.

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US4S4UnwatchRepo {
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
  private void watchRepo() {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Open watch menu on repo
	  driver.findElement(By.cssSelector("span.js-select-button")).click();
	  List<WebElement> watchOptions = driver.findElements(By.className("select-menu-item-heading"));
	  for (WebElement e: watchOptions) {
		  if (e.getText().equalsIgnoreCase("Watching")) {
			  // Click on watch button
			  e.click();
			  break;
		  }
	  }	  
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
    watchRepo();
  }
  
/**
 * When we unwatch a repo, it should no longer appear in the watch list.
 */
  @Test
  public void testUnWatchRepo() throws Exception {
	  driver.get(baseUrl + "/lhartikk/ArnoldC");
	  // Open watch menu on repo
	  driver.findElement(By.cssSelector("span.js-select-button")).click();
	  List<WebElement> watchOptions = driver.findElements(By.className("select-menu-item-heading"));
	  boolean unwatchButtonClicked = false;
	  for (WebElement e: watchOptions) {
		  if (e.getText().equalsIgnoreCase("Not watching")) {
			  // Click on unwatch button
			  e.click();
			  unwatchButtonClicked = true;
			  break;
		  }
	  }	  
	  assertTrue(unwatchButtonClicked);
	  // Repo should not be in watch list
	  driver.get("https://github.com/watching");
	  List<WebElement> repoNames = driver.findElements(By.className("repo-name"));
	 boolean repoWatched = false;
	  for (WebElement e: repoNames) {
		  if (e.getText().equalsIgnoreCase( "ArnoldC")) {
			  repoWatched = true;
			  break;
		  }
	  }
	  assertFalse(repoWatched);
  }

  @After
  public void tearDown() throws Exception {
	// Unwatch repo
	driver.findElement(By.linkText("Unwatch all")).click();
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
