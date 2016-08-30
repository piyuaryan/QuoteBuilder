package com.solutionwerk.qb;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.solutionwerk.qb.web.api.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class extends the functionality of AbstractTest. AbstractControllerTest
 * is the parent of all web controller unit test classes. The class ensures that
 * a type of WebApplicationContext is built and prepares a MockMvc instance for
 * use in test methods.
 *
 * @author Piyush Ramavat
 */
public abstract class AbstractControllerTest extends AbstractTest {

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    /**
     * Prepares the test class for execution of web tests. Builds a MockMvc
     * instance. Call this method from the concrete JUnit test class in the
     * <code>@Before</code> setup method.
     */
    protected void setUp() {
        super.setUp();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    /**
     * Prepares the test class for execution of web tests. Builds a MockMvc
     * instance using standalone configuration facilitating the injection of
     * Mockito resources into the controller class.
     *
     * @param controller A controller object to be tested.
     */
    protected void setUp(BaseController controller) {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     *
     * @param obj The Object to map.
     * @return A String of JSON.
     * @throws JsonProcessingException Thrown if an error occurs while mapping.
     */
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        return mapper.writeValueAsString(obj);
    }

    /**
     * Maps a String of JSON into an instance of a Class of type T. Uses a
     * Jackson ObjectMapper.
     *
     * @param json  A String of JSON.
     * @param clazz A Class of type T. The mapper will attempt to convert the
     *              JSON into an Object of this Class type.
     * @return An Object of type T.
     * @throws JsonParseException   Thrown if an error occurs while mapping.
     * @throws JsonMappingException Thrown if an error occurs while mapping.
     * @throws IOException          Thrown if an error occurs while mapping.
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, clazz);
    }

    /**
     * Prepeares the access token returned by oAuth2 for tests.
     *
     * @param username userName
     * @param password password
     * @return token string
     * @throws Exception
     */
    protected String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic "
                + new String(Base64Utils.encode("qbClientId:123456".getBytes()));
        String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

        String content = mvc.perform(
                post("/oauth/token")
                        .header("Authorization", authorization)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andReturn().getResponse().getContentAsString();

        // Validate Content Values:
//{"access_token":"569a0bc3-e0cb-4abb-af34-9f9355fdc25c","token_type":"bearer","refresh_token":"a6a34df7-c306-4869-ab5a-7be1f5c23a4a","expires_in":43199,"scope":"read write"}
//        andExpect(jsonPath("$.access_token", is(notNullValue())))
//                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
//                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
//                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
//                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
        return content.substring(17, 53);
    }
}
