package uk.co.compendiumdev.restlisticator.automating.config;


public class RestListicatorServer {
    private final String host;
    private final int port;
    private static String HTTPHost="localhost";
    private String scheme="http";
    private String portFormatTemplate=":%d";


    public RestListicatorServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static RestListicatorServer getDefault() {
        return new RestListicatorServer(HTTPHost, 4567);
        //return new RestListicatorServer("rest-list-system.herokuapp.com", 443).setScheme("https");
    }


    public String getHTTPHost() {
        return String.format("%s://%s" + portFormatTemplate, scheme, host, port);
    }

    public RestListicatorServer setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public RestListicatorServer withNoPort() {
        portFormatTemplate = "";
        return this;
    }
}
