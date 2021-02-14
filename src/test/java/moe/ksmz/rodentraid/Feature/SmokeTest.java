package moe.ksmz.rodentraid.Feature;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import moe.ksmz.rodentraid.Auth.AuthStatus;
import moe.ksmz.rodentraid.Auth.UserService;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@SpringJUnitWebConfig
public class SmokeTest {
    @Autowired MockMvc http;
    @Autowired MockHttpSession session;
    @MockBean AuthStatus authStatus;
    @MockBean UserRepository userRepository;
    @MockBean UserService userService;

    @Test
    void itWorks() throws Exception {
        this.http.perform(get("/")).andExpect(status().isOk());
    }
}
