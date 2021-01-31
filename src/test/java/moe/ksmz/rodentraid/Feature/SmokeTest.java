package moe.ksmz.rodentraid.Feature;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class SmokeTest {
    @Autowired private MockMvc http;

    @Test
    void itWorks() throws Exception {
        this.http.perform(get("/")).andExpect(status().isOk());
    }
}
