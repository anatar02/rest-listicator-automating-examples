package uk.co.compendiumdev.restlisticator.automating;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;
import uk.co.compendiumdev.restlisticator.payloads.ListsPayload;

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

    @Test
    public void createListAndCheckResultBody(){

        RestListicatorServer server = new RestListicatorServer("localhost",4567);

        // Proxy for interctive debugging of messages
        //RestAssured.proxy("localhost", 8080);

        ListPayload list = new ListPayload();
        list.title = "my title";

        Response response = RestAssured.
                given().
                auth().preemptive().
                basic("admin", "password").
                body(list).
                when().
                post(server.getHTTPHost() + "/lists").
                andReturn();

        response.then().assertThat().statusCode(201);

        ListsPayload createdLists = response.getBody().as(ListsPayload.class);

        ListPayload createdList = createdLists.lists.get(0);

        Assert.assertNotNull(createdList.guid);
        Assert.assertEquals("my title",createdList.title);
        Assert.assertNotNull(createdList.description);
        Assert.assertNotNull(createdList.createdDate);
        Assert.assertNotNull(createdList.amendedDate);

    }
}
