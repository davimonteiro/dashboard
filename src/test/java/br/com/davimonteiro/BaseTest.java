package br.com.davimonteiro;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author Davi Monteiro Barbosa
 * 
 * 
 * Classe base para cenários de aceitação.
 * 
 * Cenário de aceitação (Para testes de software)
 * Dado <Algum contexto inicial>
 * Quando <Algum evento ocorrer>
 * Então <Garantir alguns resultados>
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class BaseTest {
	
	@Inject
	private WebApplicationContext context;

	@Inject
	private FilterChainProxy springSecurityFilterChain;

	protected MockMvc mvc;
	
	protected String bearerToken;
	
	protected static final MediaType contentType = MediaType.APPLICATION_JSON;
	
	protected static final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
		bearerToken = "Bearer " + getAccessToken(mvc, "davimonteiro", "12345");
	}
	
	public String toJson(Object src) {
		return gson.toJson(src);
	}
	
	public String getAccessToken(MockMvc mvc ,String username, String password) throws Exception {
		String authorization = "Basic " + new String(Base64Utils.encode("clientapp:123456".getBytes()));
		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

		String content = mvc
				.perform(
						post("/oauth/token")
								.header("Authorization", authorization)
								.contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.param("username", username)
								.param("password", password)
								.param("grant_type", "password")
								.param("scope", "read write")
								.param("client_id", "clientapp")
								.param("client_secret", "123456"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
				.andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
				.andExpect(jsonPath("$.scope", is(equalTo("read write"))))
				.andReturn().getResponse().getContentAsString();

		return content.substring(17, 53);
	}
	
}
