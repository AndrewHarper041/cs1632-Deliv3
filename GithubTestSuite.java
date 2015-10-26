import junit.framework.Test;
import junit.framework.TestSuite;

public class GithubTestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(US1S1loginNav.class);
    suite.addTestSuite(US1S2newAccountFail.class);
    suite.addTestSuite(US1S3newUserSuccess.class);
    suite.addTestSuite(US1S4existingUserName.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
