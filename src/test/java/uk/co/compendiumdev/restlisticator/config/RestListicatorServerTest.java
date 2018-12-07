package uk.co.compendiumdev.restlisticator.config;

import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;

public class RestListicatorServerTest {

    @Test
    public void canConstructHTTP_root_URL_string(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234);
        Assert.assertEquals("http://localhost:1234", server.getHTTPHost());
    }

    @Test
    public void canConstructDefault(){
        RestListicatorServer server = RestListicatorServer.getDefault();
        Assert.assertEquals("http://localhost:4567/listicator", server.getHTTPHost());
    }

    @Test
    public void canConstructToUseHTTPs(){
        // a little awkward, but this was an easy way to retain backwards compatibility
        RestListicatorServer server = RestListicatorServer.getDefault().setScheme("https");
        Assert.assertEquals("https://localhost:4567/listicator", server.getHTTPHost());
    }

    @Test
    public void canExcludePortAndApiPath(){
        RestListicatorServer server = RestListicatorServer.getDefault().setScheme("https").withNoPort().setApiRoot("");
        Assert.assertEquals("https://localhost", server.getHTTPHost());
    }

    @Test
    public void canExcludePort(){
        RestListicatorServer server = RestListicatorServer.getDefault().setScheme("https").withNoPort();
        Assert.assertEquals("https://localhost/listicator", server.getHTTPHost());
    }

    @Test
    public void canRemoveApiPath(){
        RestListicatorServer server = RestListicatorServer.getDefault().setScheme("https").setApiRoot("");
        Assert.assertEquals("https://localhost:4567", server.getHTTPHost());
    }

}
