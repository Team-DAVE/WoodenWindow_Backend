import com.controllers.ProfileController;
import com.controllers.UsersController;
import com.dao.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.*;
import com.service.ProfileService;
import com.service.UserService;
import org.hibernate.SessionFactory;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    WebApplicationContext wac;
    private MockMvc mockMvc;

    @Mock
    private UserService userServiceMock;

    @Mock
    private ProfileService profileServiceMock;

    @InjectMocks
    @Autowired
    private ProfileController profileController;

    @InjectMocks
    @Autowired
    private UsersController usersController;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private BusinessDao businessDao;

    @Autowired
    private JobPostingDao jobPostingDao;

    @Autowired
    private ProfileSkillDao profileSkillDao;

    @Autowired
    private RequiredSkillDao requiredSkillDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test for UserController
     */
    @Test
    public void testAddUser() throws Exception {
        when(userServiceMock.addUser("hello6@hello.com", "hello", "hello", "hello")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(asJsonString(new Users(5,
                "hello6@hello.com", "hello", "hello")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLogin() throws Exception {
        Users currentUser = new Users();
        currentUser.setEmail("hello@hello.com");
        currentUser.setPassword("hello");
        currentUser.setFirstName("hello");
        currentUser.setLastName("hello");
        when(userServiceMock.checkUser("hello@hello.com", "hello")).thenReturn(currentUser);
        MvcResult result = this.mockMvc.perform(post("/user/login")
                .content(asJsonString(new Users(2, "hello@hello.com", "hello", "hello")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertNotNull(result);
    }

    /**
     * Test for ProfileController
     */
    @Test
    public void testProfileByProfileID() throws Exception {
        int id = 1;
        Users currentUser = new Users();
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
        MvcResult result = this.mockMvc.perform(get("/profile/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertNotNull(result);
    }

    @Test
    public void testProfileByProfileUserID() throws Exception {
        int id = 1;
        Users currentUser = new Users();
        currentUser.setUserId(1);
        currentUser.setEmail("hello@hello.com");
        currentUser.setPassword("hello");
        currentUser.setFirstName("hello");
        currentUser.setLastName("hello");
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
        MvcResult result = this.mockMvc.perform(get("/profile/user/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertNotNull(result);
    }

    /**
     * Test for UserDao
     */
    @Test
    public void testAddUserDao() {
        userDao.addUser("Test@test.com", "test", "test", "test");
        Assert.assertNotNull(userDao);
    }

    @Test
    public void testFindUserByEmail() {
        userDao.findUserByEmail("Test@test.com");
        Assert.assertNotNull(userDao);

    }

    /**
     * Test for ProfileDao
     */
    @Test
    public void testAddProfile() {
        Users validUser = new Users();
        validUser.setUserId(15);
        validUser.setEmail("Test@test.com");
        validUser.setPassword("test");
        validUser.setFirstName("test");
        validUser.setLastName("test");
        profileDao.addProfile("test", "test", validUser);
        Assert.assertNotNull(profileDao);
    }

    @Test
    public void testgetProfileByProfileId() {
        profileDao.getProfileByProfileId(1);
        Assert.assertNotNull(profileDao);
    }

    @Test
    public void testgetProfilesByUserId() {
        profileDao.getProfilesByUserId(1);
        Assert.assertNotNull(profileDao);
    }

    /**
     * Test for BusinessDao
     */
    @Test
    public void testAddBusiness() {
        Users validUser = new Users();
        validUser.setUserId(15);
        validUser.setEmail("Test@test.com");
        validUser.setPassword("test");
        validUser.setFirstName("test");
        validUser.setLastName("test");

        businessDao.addBusiness("test", "test", "test", validUser);
        Assert.assertNotNull(businessDao);
    }

    @Test
    public void testgetBusinessByUserId() {
        businessDao.getBusinessesByUserId(1);
        Assert.assertNotNull(businessDao);
    }

    /**
     * Test for JobPostingDao
     */
    @Test
    public void testaddJobPosting() {
        Users validUser = new Users();
        validUser.setUserId(15);
        validUser.setEmail("Test@test.com");
        validUser.setPassword("test");
        validUser.setFirstName("test");
        validUser.setLastName("test");

        Business business = new Business();
        business.setBusinessId(1);
        business.setAddress("Test");
        business.setBusinessName("Test");
        business.setSummary("Testing");
        business.setUser(validUser);
        jobPostingDao.addJobPosting("test", "test", "test", business);
        Assert.assertNotNull(jobPostingDao);
    }

    @Test
    public void testgetJobPostingsByBusiness() {
        jobPostingDao.getJobPostingsByBusiness(1);
        Assert.assertNotNull(jobPostingDao);
    }

    /**
     * Test for ProfileSkillDao
     */
    @Test
    public void testaddProfileSkill() {
        Users validUser = new Users();
        validUser.setUserId(15);
        validUser.setEmail("Test@test.com");
        validUser.setPassword("test");
        validUser.setFirstName("test");
        validUser.setLastName("test");

        Profile testProfile = new Profile();
        testProfile.setProfileId(1);
        testProfile.setProfileName("hellotest1");
        testProfile.setResume("hellotest1");
        testProfile.setUser(validUser);
        profileSkillDao.addProfileSkill("test", testProfile);
        Assert.assertNotNull(profileSkillDao);
    }

    @Test
    public void testgetProfileSkillsByProfile() {

        profileSkillDao.getProfileSkillsByProfile(1);
        Assert.assertNotNull(profileSkillDao);
    }

    /**
     * Test for RequiredSkillDao
     */
    @Test
    public void testAddRequiredSkill() {
        Users validUser = new Users();
        validUser.setUserId(15);
        validUser.setEmail("Test@test.com");
        validUser.setPassword("test");
        validUser.setFirstName("test");
        validUser.setLastName("test");

        Business business = new Business();
        business.setBusinessId(1);
        business.setAddress("Test");
        business.setBusinessName("Test");
        business.setSummary("Testing");
        business.setUser(validUser);

        JobPosting currentJobPosting = new JobPosting();
        currentJobPosting.setJobPostingId(1);
        currentJobPosting.setPosition("Test");
        currentJobPosting.setJobSummary("Test");
        currentJobPosting.setSalary("Test");
        currentJobPosting.setBusiness(business);

        requiredSkillDao.addRequiredSkill("test", currentJobPosting);
        Assert.assertNotNull(requiredSkillDao);
    }

    @Test
    public void testGetRequiredSkillsByJobPosting() {

        requiredSkillDao.getRequiredSkillsByJobPosting(1);
        Assert.assertNotNull(requiredSkillDao);
    }

    /**
     * Test for BusinessModel
     */
    @Test
    public void testBusinessModel() {
        Users currentUser = new Users();
        ArrayList<Business> businesses = new ArrayList<>();
        currentUser.setUserId(1);
        currentUser.setEmail("hello@hello.com");
        currentUser.setPassword("hello");
        currentUser.setFirstName("hello");
        currentUser.setLastName("hello");

        Business business = new Business();
        business.setBusinessId(1);
        business.setAddress("Test");
        business.setBusinessName("Test");
        business.setSummary("Testing");
        business.setUser(currentUser);
        businesses.add(business);

        businesses.toString();
        Assert.assertEquals(1, business.getBusinessId());
        Assert.assertEquals("Test", business.getAddress());
        Assert.assertEquals("Test", business.getBusinessName());
        Assert.assertEquals("Testing", business.getSummary());
        Assert.assertEquals(currentUser, business.getUser());
    }

    /**
     * Test for ProfileSkillModel
     */
    @Test
    public void testProfileSkillModel() {
        Profile testProfile = new Profile();
        testProfile.setProfileId(1);
        testProfile.setProfileName("hellotest1");
        testProfile.setResume("hellotest1");

        ProfileSkill currentProfile = new ProfileSkill();
        currentProfile.setProfileSkillId(1);
        currentProfile.setProfileSkillName("hello@hello.com");
        currentProfile.setProfile(testProfile);


        currentProfile.toString();
        Assert.assertEquals(1, currentProfile.getProfileSkillId());
        Assert.assertEquals("hello@hello.com", currentProfile.getProfileSkillName());
        Assert.assertEquals(testProfile, currentProfile.getProfile());
    }

    /**
     * Test for RequiredSkillModel
     */
    @Test
    public void testRequiredSkillModel() {
        JobPosting currentJobPosting = new JobPosting();
        currentJobPosting.setJobPostingId(1);
        currentJobPosting.setPosition("Test");
        currentJobPosting.setJobSummary("Test");
        currentJobPosting.setSalary("Test");

        RequiredSkill currentRequiredSkill = new RequiredSkill();
        currentRequiredSkill.setRequiredSkillId(1);
        currentRequiredSkill.setRequiredSkillName("Test");
        currentRequiredSkill.setJobPosting(currentJobPosting);


        currentRequiredSkill.toString();
        Assert.assertEquals(1, currentRequiredSkill.getRequiredSkillId());
        Assert.assertEquals("Test", currentRequiredSkill.getRequiredSkillName());
        Assert.assertEquals(currentJobPosting, currentRequiredSkill.getJobPosting());
    }

    /**
     * Test for JobPostingModel
     */
    @Test
    public void testJobPostingModel() {
        Business business = new Business();
        business.setBusinessId(1);
        business.setAddress("Test");
        business.setBusinessName("Test");
        business.setSummary("Testing");

        JobPosting currentJobPosting = new JobPosting();
        currentJobPosting.setJobPostingId(1);
        currentJobPosting.setPosition("Test");
        currentJobPosting.setJobSummary("Test");
        currentJobPosting.setSalary("Test");
        currentJobPosting.setBusiness(business);

        currentJobPosting.toString();
        Assert.assertEquals(1, currentJobPosting.getJobPostingId());
        Assert.assertEquals("Test", currentJobPosting.getPosition());
        Assert.assertEquals("Test", currentJobPosting.getJobSummary());
        Assert.assertEquals("Test", currentJobPosting.getSalary());
        Assert.assertEquals(business, currentJobPosting.getBusiness());
    }
}
