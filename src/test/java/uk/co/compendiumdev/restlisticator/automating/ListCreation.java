package uk.co.compendiumdev.restlisticator.automating;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;
import uk.co.compendiumdev.restlisticator.payloads.AListPayload;
import uk.co.compendiumdev.restlisticator.payloads.AListsPayload;
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

        AListPayload list = new AListPayload();
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

        AListPayload list = new AListPayload();
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

        AListsPayload createdLists = response.getBody().as(AListsPayload.class);

        AListPayload createdList = createdLists.lists.get(0);

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
        list.setTitle("title and description");
        list.setDescription("description used to create list");

        ApiUser user = new ApiUser("admin", "password");

        Response response = api.createList(user, list);

        response.then().assertThat().statusCode(201);

        AListsPayload createdLists = response.getBody().as(AListsPayload.class);

        AListPayload createdList = createdLists.lists.get(0);

        Assert.assertNotNull(createdList.guid);
        Assert.assertEquals("title and description",createdList.title);
        Assert.assertEquals("description used to create list", createdList.description);
        Assert.assertNotNull(createdList.createdDate);
        Assert.assertNotNull(createdList.amendedDate);
    }

    // note:
    // created default Admin user static method on ApiUser
    // created an ApiResponse wrapper around response object
    //   - think I might want to instantiate RestListicatorApi(user) and then have api.createList(list)
    //   - or could have user.createList(list)
    @Test
    public void createListWithTitleAndDescriptionAndGUID(){

        RestListicatorApi api = new RestListicatorApi();

        ListPayload list = new ListPayload();
        list.setGuid("thereisalwaystheriskthatthisisnotunique");
        list.setTitle("title and description");
        list.setDescription("description used to create list");


        Response response = api.createList(ApiUser.getDefaultAdminUser(), list);

        ApiResponse apiResponse = new ApiResponse(response);
        Assert.assertEquals(201, apiResponse.getStatusCode());

        ListsPayload createdLists = apiResponse.getLists();
        
        ListPayload createdList = createdLists.getLists().get(0);

        Assert.assertEquals("thereisalwaystheriskthatthisisnotunique", createdList.getGuid());
        Assert.assertEquals("title and description",createdList.getTitle());
        Assert.assertEquals("description used to create list", createdList.getDescription());
        Assert.assertNotNull(createdList.getCreatedDate());
        Assert.assertNotNull(createdList.getAmendedDate());
    }


    // note: - added ability for API to use XML to send messages
    // also added methods to the payloads rather than public fields
    //     - because of backwards compatibility with previous tests I renamed the previous ListPayload to AListPayload
    //           - but this didn't keep all backwards compatibility so had to amend earlier test a little
    //     - new tests should use the ListPayload
    //     - and the fields should now be private
    //     - this helps minimize impact on change if payload changes
    // Added a payload builder for the same reason
    @Test
    public void createListWithTitleAndDescriptionAndGUIDUsingXML(){

        RestListicatorApi api = new RestListicatorApi();

        ListPayload list = ListPayload.builder().
                with().
                title("title for the title and description").
                description("description used to create list using xml").
                and().
                guid("ihopethisguidisunique").
                build();


        api.sendContentAsXML();

        // note don't have any tests that double check that RestAssured continues to
        // serialise to XML based on content type so have used proxy to check
        //RestAssured.proxy("localhost", 8080);

        Response response = api.createList(ApiUser.getDefaultAdminUser(), list);

        ApiResponse apiResponse = new ApiResponse(response);

        Assert.assertEquals(201, apiResponse.getStatusCode());

        // did not set accept so default should be json
        Assert.assertTrue(apiResponse.payloadIsJson());

        ListsPayload createdLists = apiResponse.getLists();

        ListPayload createdList = createdLists.getLists().get(0);

        Assert.assertEquals("ihopethisguidisunique", createdList.getGuid());
        Assert.assertEquals("title for the title and description",createdList.getTitle());
        Assert.assertEquals("description used to create list using xml", createdList.getDescription());
        Assert.assertNotNull(createdList.getCreatedDate());
        Assert.assertNotNull(createdList.getAmendedDate());
    }

    @Test
    public void createListUsingXMLAndAcceptXML(){

        RestListicatorApi api = new RestListicatorApi();

        ListPayload list = ListPayload.builder().
                with().
                title("title for xml response").
                description("description used to create list using xml and expected in xml").
                and().
                guid("xmlguidmustbeuniqueyes").
                build();


        api.sendContentAsXML();
        api.acceptXML();

        // note don't have any tests that double check that RestAssured continues to
        // serialise to XML based on content type so have used proxy to check
        RestAssured.proxy("localhost", 8080);

        Response response = api.createList(ApiUser.getDefaultAdminUser(), list);

        ApiResponse apiResponse = new ApiResponse(response);

        Assert.assertEquals(201, apiResponse.getStatusCode());

        // did not set accept so default should be json
        Assert.assertTrue(apiResponse.payloadIsXML());

        ListsPayload createdLists = apiResponse.getLists();

        ListPayload createdList = createdLists.getLists().get(0);

        Assert.assertEquals("xmlguidmustbeuniqueyes", createdList.getGuid());
        Assert.assertEquals("title for xml response",createdList.getTitle());
        Assert.assertEquals("description used to create list using xml and expected in xml", createdList.getDescription());
        Assert.assertNotNull(createdList.getCreatedDate());
        Assert.assertNotNull(createdList.getAmendedDate());
    }
}
