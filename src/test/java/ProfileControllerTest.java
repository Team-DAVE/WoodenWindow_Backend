import com.controllers.ProfileController;
import com.dao.ProfileDao;
import com.model.Profile;
import com.model.Users;
import com.service.ProfileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@WebAppConfiguration

public class ProfileControllerTest {

    @Autowired
    WebApplicationContext wac;
    private MockMvc mockMvc;

    @Mock
    private ProfileService profileServiceMock;

//    @Mock
//    private ProfileDao profileDaoMock;
//    @InjectMocks
//    private ProfileService profileService;

    @InjectMocks
    @Autowired
    private ProfileController profileController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testProfileByProfileID() throws Exception{
        int id =1;
        Users currentUser= new Users();
        currentUser.setUserId(1);
        currentUser.setEmail("hello@hello.com");
        currentUser.setPassword("hello");
        currentUser.setFirstName("hello");
        currentUser.setLastName("hello");

        Profile profile = new Profile();
        profile.setProfileId(1);
        profile.setProfileName("hello");
        profile.setResume("helllllloooo");
        profile.setUser(currentUser);
        when(profileServiceMock.getProfileByProfileId(1)).thenReturn(profile);
//        when(profileDaoMock.getProfileByProfileId(1)).thenReturn(profile);

//        System.out.println(profileController.getProfileByProfileId(1));
//        assertThat(profileController.getProfileByProfileId(1),is(notNullValue()));
//        when(profileServiceMock.getProfileByProfileId(id)).thenReturn(new Profile());
//        System.out.println(profile);
        MvcResult result = this.mockMvc.perform(get("/profile/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertNotNull(result);
    }
    @Test
    public void testProfileByProfileUserID() throws Exception{
        int id =1;
        Users currentUser= new Users();
        currentUser.setUserId(1);
        currentUser.setEmail("hello@hello.com");
        currentUser.setPassword("hello");
        currentUser.setFirstName("hello");
        currentUser.setLastName("hello");

//        List<ArrayList<String>> listList = new ArrayList<ArrayList<String>>();
        List<Profile> profiles = new ArrayList<>();
        Profile testProfile = new Profile();
        testProfile.setProfileId(1);
        testProfile.setProfileName("hellotest1");
        testProfile.setResume("hellotest1");
        testProfile.setUser(currentUser);
        profiles.add(testProfile);

        Profile testProfile2 = new Profile();
        testProfile.setProfileId(2);
        testProfile.setProfileName("hellotest2");
        testProfile.setResume("hellotest2");
        testProfile.setUser(currentUser);
        profiles.add(testProfile2);

        when(profileServiceMock.getProfilesByUserId(1)).thenReturn(profiles);
//        when(profileDaoMock.getProfileByProfileId(1)).thenReturn(profile);

//        assertThat(profileController.getProfileByProfileId(1),is(notNullValue()));
//        when(profileServiceMock.getProfileByProfileId(id)).thenReturn(new Profile());
//        System.out.println(profile);
        MvcResult result = this.mockMvc.perform(get("/profile/user/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertNotNull(result);
    }
//    @Test
//        public void testProfileByProfile() throws Exception{
//        Users currentUser= new Users();
//        currentUser.setUserId(1);
//        currentUser.setEmail("hello@hello.com");
//        currentUser.setPassword("hello");
//        currentUser.setFirstName("hello");
//        currentUser.setLastName("hello");
//
//        Profile profile = new Profile();
//        profile.setProfileId(1);
//        profile.setProfileName("hello");
//        profile.setResume("helllllloooo");
//        profile.setUser(currentUser);
//        when(profileServiceMock.getProfileByProfileId(1)).thenReturn(profile);
//
//        System.out.println(profileController.getProfileByProfileId(1));
//        assertThat(profileController.getProfileByProfileId(1),is(notNullValue()));
//
//    }
//    @Test
//    public void testProfileByProfile() throws Exception{
//        int id =1;
//        this.mockMvc.perform(get("/profile/" + id)).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
//
//    }
}