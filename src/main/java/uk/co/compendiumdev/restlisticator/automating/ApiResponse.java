package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.response.Response;
import uk.co.compendiumdev.restlisticator.payloads.ListsPayload;

/**
 * Created by Alan on 23/08/2017.
 */
public class ApiResponse {
    private final Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public ListsPayload getLists() {
        return this.response.getBody().as(ListsPayload.class);
    }

    public boolean payloadIsJson() {
        return response.header("content-type").endsWith("/json");
    }
}
