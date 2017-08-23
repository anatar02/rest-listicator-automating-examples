package uk.co.compendiumdev.restlisticator.automating;


import io.restassured.RestAssured;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;

public class ListCreation {

    // POST /lists
    // requires authorized and authenticated user
    // create a list with partial payload e.g. {title:'my title'}

    @Test
    public void createList(){

        RestListicatorServer server = new RestListicatorServer("localhost",4567);

        // Proxy for interctive debugging of messages
        //RestAssured.proxy("localhost", 8080);

        ListPayload list = new ListPayload();
        list.title = "my title";

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(list).
            when().
                post(server.getHTTPHost() + "/lists").
            then().assertThat().
                statusCode(201);
    }
}
