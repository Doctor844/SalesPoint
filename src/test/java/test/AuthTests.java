package test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
@AutoConfigureMockMvc
public class AuthTests {
/*
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAccessPublic_thenOk() throws Exception {
        mockMvc.perform(get("/public/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserAccessUserEndpoint_thenOk() throws Exception {
        mockMvc.perform(get("/user/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserAccessAdminEndpoint_thenForbidden() throws Exception {
        mockMvc.perform(get("/admin/hello"))
                .andExpect(status().isForbidden());
    }*/
}