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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageControllerTest {

    private static final String URL_PREFIX = "/api/cottages";

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
    public void testGetAllCottages() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Divna vikendica")))
                .andExpect(jsonPath("$.[*].description").value(hasItem("Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra.")))
                .andExpect(jsonPath("$.[*].price").value(hasItem(200.0)));

    }

    @Test
    public void testGetCottageById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + 1)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Divna vikendica"))
                .andExpect(jsonPath("$.description").value("Ovo je jako kul vikendica. Najbolja vikendica na svetu. Stvarno je dobra."))
                .andExpect(jsonPath("$.price").value(200.0));
    }

    @Test
    @Transactional
    @Rollback(true)
    @WithMockUser(roles={"cottageOwner"})
    public void testDeleteAdventure() throws Exception {
        this.mockMvc.perform(delete(URL_PREFIX + "/deleteCottage/" + 1)).andExpect(status().isBadRequest());
    }


}
