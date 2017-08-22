package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.RestAssured;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;


public class HeartbeatAPIEndpointTest {

    @Test
    public void canCheckThatServerIsRunning(){

        RestListicatorServer server = new RestListicatorServer("localhost",4567);

        RestAssured.
                get(server.getHTTPHost() + "/heartbeat").
                then().assertThat().
                statusCode(200);
    }
}
