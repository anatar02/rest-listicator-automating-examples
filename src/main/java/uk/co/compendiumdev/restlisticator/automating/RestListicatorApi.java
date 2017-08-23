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
    private String contentType;

    private final String CONTENT_IS_JSON = "application/json";
    private final String CONTENT_IS_XML = "application/xml";

    public RestListicatorApi(){
        this.server = RestListicatorServer.getDefault();
        this.contentType = CONTENT_IS_JSON; // default to json
    }

    public Response createList(ApiUser user, ListPayload list) {
        return RestAssured.
            given().
                contentType(contentType).
                auth().preemptive().
                basic(user.getUsername(), user.getPassword()).
                body(list).
            when().
                post(server.getHTTPHost() + "/lists").
            andReturn();
    }

    public RestListicatorApi sendContentAsXML() {
        this.contentType = CONTENT_IS_XML;
        return this;
    }

    public RestListicatorApi sendContentAsJSON() {
        this.contentType = CONTENT_IS_JSON;
        return this;
    }

}
