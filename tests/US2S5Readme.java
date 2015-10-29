// Test for US-2 Scenario-5
// Test to make sure that a our repository contains a readme.md when we specify one.

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US2S5Readme {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  
  private static final String USERNAME = "pittqa";
  private static final String PASSWORD = "pittqa1";
  private static final String REPO = "windows69";
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
	    driver.findElement(By.className("btn")).click();	  
}

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
  }
  
/**
 * Make sure when we check the readme check box on the repository form
 * that the repository will contain a file named readme.md
 * We know this if, when we go to our repository page, the readme.md exists there.
 */
  @Test
  public void testReadme() throws Exception {
	    driver.get("https://github.com/new");
	    driver.findElement(By.id("repository_name")).clear();
	    driver.findElement(By.id("repository_name")).sendKeys(REPO);
	    driver.findElement(By.id("repository_auto_init")).click(); // readme checkbox
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    List<WebElement> fileElements = driver.findElements(By.className("js-directory-link"));
	    boolean readmeExists = false;
	    for (WebElement e: fileElements) {
	    	if (e.getText().equals("README.md")) {
	    		readmeExists = true;
	    		// Make sure that the readme file link takes us to the page for the readme file.
	    		e.click();
	    		String readmeUrl = "https://github.com/" + USERNAME + "/" + REPO + "/blob/master/README.md";
	    		assertEquals(readmeUrl, driver.getCurrentUrl());
	    		break;
	    	}
	    }
	    assertTrue(readmeExists);
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
