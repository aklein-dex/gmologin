package com.alex;

import com.alex.tools.UserManagement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accessRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Please register")));
    }
    
    @Test
	public void shouldCreateUser() throws Exception {
        MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex")
            .param("password", "asdQ!WE12")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasNoErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenMissingUsername() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("password", "asdQ!WE12")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
        
    @Test
	public void shouldFailedToCreateUserWhenMissingPassword() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenShortUsername() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "a")
            .param("password", "123abcA!")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenUsernameWithSpace() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex space")
            .param("password", "123abcA!")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenShortPassword() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex")
            .param("password", "123")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenNoUpperCase() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex")
            .param("password", "123abc!")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
    
    @Test
	public void shouldFailedToCreateUserWhenNoLowerCase() throws Exception {
		MockHttpServletRequestBuilder createUser = post("/register")
            .param("username", "alex")
            .param("password", "123AB!")
            .with(csrf());
        
        mockMvc.perform(createUser).andExpect(model().hasErrors());
    }
}
