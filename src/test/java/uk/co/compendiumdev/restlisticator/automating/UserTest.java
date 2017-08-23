package uk.co.compendiumdev.restlisticator.automating;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alan on 23/08/2017.
 */
public class UserTest {

    @Test
    public void canCreateAnApiUSer(){
        ApiUser aUser = new ApiUser("bob", "dobbs");
        Assert.assertEquals("bob", aUser.getUsername());
        Assert.assertEquals("dobbs", aUser.getPassword());
    }
}
