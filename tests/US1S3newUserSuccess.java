
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US1S3newUserSuccess {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://github.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUS1S3newUserSuccess() throws Exception {
    driver.get(baseUrl + "/join");
    driver.findElement(By.linkText("Sign up")).click();
    driver.findElement(By.id("user_login")).clear();
    driver.findElement(By.id("user_login")).sendKeys("thisisnotarealperson");
    driver.findElement(By.id("user_login")).clear();
    driver.findElement(By.id("user_login")).sendKeys("thisisatempuser");
    driver.findElement(By.id("user_email")).clear();
    driver.findElement(By.id("user_email")).sendKeys("asdf@sad.com");
    driver.findElement(By.id("signup_button")).click();
    assertTrue(isElementPresent(By.xpath("//div[@id='js-pjax-container']/div/div[2]/div/form/table/tbody/tr[5]/td[3]")));
    driver.findElement(By.xpath("//ul[@id='user-links']/li[3]/a")).click();
    driver.findElement(By.cssSelector("div.modal-backdrop")).click();
    driver.findElement(By.xpath("//ul[@id='user-links']/li[3]/a")).click();
    driver.findElement(By.linkText("Your profile")).click();
    driver.findElement(By.linkText("Edit profile")).click();
    driver.findElement(By.linkText("Account settings")).click();
    driver.findElement(By.id("user_old_password")).clear();
    driver.findElement(By.id("user_old_password")).sendKeys("CS1632class");
    driver.findElement(By.linkText("Delete your account")).click();
    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > p > #sudo_login")).clear();
    driver.findElement(By.cssSelector("div.facebox-content.dangerzone > form > p > #sudo_login")).sendKeys("thisisatempuser");
    driver.findElement(By.xpath("(//input[@id='confirmation_phrase'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@id='confirmation_phrase'])[2]")).sendKeys("delete my account");
    driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
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
