// Test for US-2 Scenario-4
// Test to make sure we can make a repository with an appropriate description


import java.util.regex.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class US2S4Description {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  private static final String USERNAME = "pittqa";
  private static final String PASSWORD = "pittqa1";
  private static final String REPO = "mynameaborat";
  private static final String DESC = "High five!!!";
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
  private void createRepository(String username, String repo, String desc) {
	    driver.get("https://github.com/new");
	    driver.findElement(By.id("repository_name")).clear();
	    driver.findElement(By.id("repository_name")).sendKeys(repo);
	    driver.findElement(By.id("repository_description")).clear();
	    driver.findElement(By.id("repository_description")).sendKeys(desc);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    logIn(USERNAME, PASSWORD);
  }
  
/**
 * Make sure when we enter something into the description field when creating a repo,
 * that the repo will actually contain that description.
 * We know this if, when we go to our list of repositories, the description is there.
 */
  @Test
  public void testDescription() throws Exception {
	    createRepository(USERNAME, REPO, DESC);
	    driver.get("https://github.com/" + USERNAME + "?tab=repositories");
	    List<WebElement> descElements = driver.findElements(By.className("repo-list-description"));
	    System.out.println(descElements.size());
	    boolean descExists = false;
	    for (WebElement e: descElements) {;
	    	if (e.getText().equals(DESC)) {
	    		descExists = true;
	    		break;
	    	}
	    }
	   assertTrue(descExists);
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
