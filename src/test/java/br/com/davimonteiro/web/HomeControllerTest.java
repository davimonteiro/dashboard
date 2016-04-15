package br.com.davimonteiro.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import br.com.davimonteiro.BaseTest;

public class HomeControllerTest extends BaseTest {
	
	@Test
	public void homeTest() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}
	
}
