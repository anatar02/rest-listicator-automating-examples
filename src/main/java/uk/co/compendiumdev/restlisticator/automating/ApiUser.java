package uk.co.compendiumdev.restlisticator.automating;

/**
 * Created by Alan on 23/08/2017.
 */
public class ApiUser {
    private final String username;
    private final String password;


    public ApiUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
