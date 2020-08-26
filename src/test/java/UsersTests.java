import com.model.Users;
import org.junit.*;
import com.controllers.UsersController;

import java.net.URISyntaxException;

public class UsersTests {

    UsersController uc = new UsersController();

    @Test
    public void checkUserAdded() throws URISyntaxException {
        Users u = new Users();
        Assert.assertFalse(uc.addUser(u));
        u.setEmail("testtwo@host.com");
        u.setPassword("password");
        Assert.assertFalse(uc.addUser(u));
    }



}
