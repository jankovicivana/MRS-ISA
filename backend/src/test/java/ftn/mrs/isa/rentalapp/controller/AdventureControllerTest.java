package ftn.mrs.isa.rentalapp.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdventureControllerTest {

    private static final String URL_PREFIX = "/api/adventures";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @WithMockUser(roles={"admin"})
    public void testGetAllAdventures() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Fenomenalna avantura")))
                .andExpect(jsonPath("$.[*].price").value(hasItem(200.0)))
                .andExpect(jsonPath("$.[*].maxPersonNum").value(hasItem(10)));
    }

    @Test
    public void testGetAdventure() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + 2)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Fenomenalna avantura"))
                .andExpect(jsonPath("$.price").value(200.0));
    }

    @Test
    @Transactional
    @Rollback(true)
    @WithMockUser(roles={"fishingInstructor"})
    public void testDeleteAdventure() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/deleteAdventure/" + 2)).andExpect(status().isBadRequest());
    }
}
