package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;

/**
 * Created by Alan on 23/08/2017.
 */
public class RestListicatorApi {

    private RestListicatorServer server;

    public RestListicatorApi(){
        this.server = RestListicatorServer.getDefault();
    }

    public Response createList(ApiUser user, ListPayload list) {
        return RestAssured.
            given().
                auth().preemptive().
                basic(user.getUsername(), user.getPassword()).
                body(list).
            when().
                post(server.getHTTPHost() + "/lists").
            andReturn();
    }
}
