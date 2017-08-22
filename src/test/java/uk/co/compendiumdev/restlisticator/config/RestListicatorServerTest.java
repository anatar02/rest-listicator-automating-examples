package uk.co.compendiumdev.restlisticator.config;

import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;

/**
 * Created by Alan on 22/08/2017.
 */
public class RestListicatorServerTest {

    @Test
    public void canConstructHTTP_root_URL_string(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234);
        Assert.assertEquals("http://localhost:1234", server.getHTTPHost());
    }
}
