import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
@Provider("student_provider") // Set up name of tested provider
@PactFolder("/Users/sudha/pact/Pact-demo/sudha-pact/target/pacts")
public class StudentProviderTest {

    @State("There is a student with id 1") // Method will be run before testing interactions that require "with-data" state
    public void studentDetails() {
        System.out.println("There is a student with id 1" );
    }


    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new HttpTarget("http", "localhost",8080);

}
