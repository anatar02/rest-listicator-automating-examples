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

    // NOTE:
    // To set proxy for interactive debugging of messages
    // RestAssured.proxy("localhost", 8080);

    // Note: tests were created in order and when refactored to abstraction layer
    // the earlier tests were not amended to use the abstraction layer in order to
    // demonstrate the thought processes and flow of refactoring

    @Test
    public void createList(){

        RestListicatorServer server = new RestListicatorServer("localhost",4567);

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

    // note - refactored server details into a `getDefault` method
    // have duplicated "admin", "password" - will want to refactor that soon
    // have duplicated "/lists" endpoint - will want to refactor that soon
    // will want to refactor parsing of payloads as this will repeat quickly

    @Test
    public void createListAndCheckResultBody(){

        RestListicatorServer server = RestListicatorServer.getDefault();

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

    // refactored to an API call and User
    // bleed over from RestAssured with response object
    // assertions in the test, rather than the API Abstraction
    @Test
    public void createListWithTitleAndDescription(){

        RestListicatorApi api = new RestListicatorApi();

        ListPayload list = new ListPayload();
        list.title = "title and description";
        list.description = "description used to create list";

        ApiUser user = new ApiUser("admin", "password");

        Response response = api.createList(user, list);

        response.then().assertThat().statusCode(201);

        ListsPayload createdLists = response.getBody().as(ListsPayload.class);

        ListPayload createdList = createdLists.lists.get(0);

        Assert.assertNotNull(createdList.guid);
        Assert.assertEquals("title and description",createdList.title);
        Assert.assertEquals("description used to create list", createdList.description);
        Assert.assertNotNull(createdList.createdDate);
        Assert.assertNotNull(createdList.amendedDate);
    }
}
