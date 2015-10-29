//Test for US-2 Scenario-3
// Test to make sure we are not allowed to create a duplicate repository


import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US2S3DuplicateRepo {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  
  private static final String USERNAME = "pittqa";
  private static final String PASSWORD = "pittqa1";
  private static final String REPO = "testrepo";
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
  private void createRepository(String username,  String repo) {
	    driver.get("https://github.com/new");
	    driver.findElement(By.id("repository_name")).clear();
	    driver.findElement(By.id("repository_name")).sendKeys(repo);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
    createRepository(USERNAME, REPO); // Make first copy
  }
  
/**
 * Test to make sure we are not allowed to create a duplicate repository
 * We know this is true if we are stuck on the same page when we try to make a duplicate.
 */
  @Test
  public void testDuplicateRepo() throws Exception {
	    driver.get("https://github.com/new");
	    driver.findElement(By.id("repository_name")).clear();
	    driver.findElement(By.id("repository_name")).sendKeys(REPO);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    boolean samePage = driver.getCurrentUrl() == "https://github.com/repositories" || driver.getCurrentUrl() == "https://github.com/new"  ;
	    assertTrue(samePage);
  }

  @After
  public void tearDown() throws Exception {
	deleteRepository(USERNAME, REPO);
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
