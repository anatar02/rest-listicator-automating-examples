package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.RestAssured;
import org.junit.Test;


public class HeartbeatAPIEndpointTest {

    @Test
    public void canCheckThatServerIsRunning(){

        RestAssured.
                get("http://localhost:4567/heartbeat").
                then().assertThat().
                statusCode(200);
    }
}
