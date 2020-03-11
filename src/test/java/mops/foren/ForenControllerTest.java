package mops.foren;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
public class ForenControllerTest {

    @Autowired
    MockMvc mvcMock;

    @Autowired
    WebApplicationContext context;

    /**
     * Building up a security environment for the Test.
     */
    @BeforeEach
    public void setup() {
        mvcMock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testForumMainpage() throws Exception {
        mvcMock.perform(get("/foren/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testProfileTemplate() throws Exception {
        mvcMock.perform(get("/foren/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }


    @Test
    void testMyForumTemplate() throws Exception {
        mvcMock.perform(get("/foren/myForum"))
                .andExpect(status().isOk())
                .andExpect(view().name("myForums"));
    }

}
