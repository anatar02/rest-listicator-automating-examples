package uk.co.compendiumdev.restlisticator.automating.config;

/**
 * Created by Alan on 22/08/2017.
 */
public class RestListicatorServer {
    private final String host;
    private final int port;
    private String HTTPHost;

    public RestListicatorServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static RestListicatorServer getDefault() {
        return new RestListicatorServer("localhost", 4567);
    }


    public String getHTTPHost() {
        return String.format("http://%s:%d", host, port);
    }
}
