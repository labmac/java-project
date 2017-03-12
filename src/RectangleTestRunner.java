import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notifications.Failure;

public class RectangleTestRunner {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(RectangleTest.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.prinln(result.wasSuccessful());
  }
}
