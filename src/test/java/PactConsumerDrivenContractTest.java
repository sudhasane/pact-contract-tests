import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PactConsumerDrivenContractTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("student_provider", "localhost", 8990, this);

    @Pact(consumer = "student_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        DslPart etaResults = new PactDslJsonBody()
                .integerType("id", 1)
                .stringType("firstName","Vernon")
                .stringType("lastName","Harper")
                .stringType("email","egestas.rhoncus.Proin@massaQuisqueporttitor.org")
                .stringType("programme", "Financial Analysis")
                .array("courses")
                .stringValue("Accounting")
                .string("Statistics")
                . closeArray();



        return builder
                .given("There is a student with id 1")
                .uponReceiving("A request to get student details  with id 1")
                .path("/student/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(etaResults).toPact();

//        return builder.given("test GET").uponReceiving("GET REQUEST").
//                path("/student/1").method("GET").willRespondWith().status(200).headers(headers).body("{\"firstName\": Vernon, \"lastName\": \"Harper\"}")
//                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockProvider.getUrl() + "/student/1", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains( "firstName", "Vernon");

        // and
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        String jsonBody = "{\"name\": \"Michael\"}";

        // when
       // ResponseEntity<String> postResponse = new RestTemplate().exchange(mockProvider.getUrl() + "/pact", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
       // assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
    }
}
