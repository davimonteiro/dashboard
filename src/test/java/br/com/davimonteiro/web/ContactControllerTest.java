package br.com.davimonteiro.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import br.com.davimonteiro.BaseTest;
import br.com.davimonteiro.domain.Contact;
import br.com.davimonteiro.service.ContactService;

public class ContactControllerTest extends BaseTest {
	
	private static final String URL = "/api/contacts/";
	
	private static final String MARIA = "Maria";

	private static final String ANA = "ANA";

	private static final String JOSE = "JOSE";

	private static final String MARIO = "MARIO";

	private static final String JOAO = "JOAO";
	
	private MockHttpServletRequestBuilder request;
	
	private Contact contato;
	
	@Inject
	private ContactService contactService;
	
	@Before
	public void excluirTodosOsContatos() {
		dadoQueEuNaoTenhoNenhumContatoCadastrado();
	}
	
	
	@Test
	public void criarContatoTest() throws Exception {
		dadoQueEuTenhoUmContatoComNome(MARIA);
		
		quandoEuCadastrarUmContato(contato);
		
		entaoEuDevoReceberUmContatoComNome(MARIA);
	}
	
	@Test
	public void listarTodosOsContatosTest() throws Exception {
		dadoQueEuNaoTenhoNenhumContatoCadastrado();
		
		quandoEuListarTodosOsContatos();
		
		entaoEuDevoReceberUmaListaDeContatosComTamanho(0);
	}
	
	@Test
	public void listarTodosOsContatosCadastradosTest() throws Exception {
		dadoQueEuTenho5ContatosCadastrados();
		
		quandoEuListarTodosOsContatos();
		
		entaoEuDevoReceberUmaListaDeContatosComTamanho(5);
	}
	
	
	@Test
	public void buscarContatoInexistentePeloIdTest() throws Exception {
		dadoQueEuNaoTenhoNenhumContatoCadastrado();
		
		quandoEuBuscarUmContatoPeloId(1L);
		
		entaoEuDevoReceberUmaMensagemDeErro("");
	}
	
	@Test
	public void buscarContatoExistentePeloIdTest() throws Exception {
		dadoQueEuTenhoUmContatoJaCadastradoComNome(ANA);
		
		quandoEuBuscarUmContatoPeloId(contato.getId());
		
		entaoEuDevoReceberUmContatoComNome(ANA);
	}
	
	@Test
	public void atualizarContatoInexistenteTest() throws Exception {
		dadoQueEuNaoTenhoNenhumContatoCadastrado();
		dadoQueEuTenhoUmContatoJaCadastradoComNome(ANA);
		contato.setId(11L);
		
		quandoEuAtualizarUmContato(contato);
		
		entaoEuDevoEsperarStatus(status().isNotFound());
		
	}
	
	@Test
	public void atualizarContatoExistenteTest() throws Exception {
		dadoQueEuTenhoUmContatoJaCadastradoComNome(ANA);
		contato.setName(MARIA);
		
		quandoEuAtualizarUmContato(contato);
		
		entaoEuDevoReceberUmContatoComNome(MARIA);
	}
	
	@Test
	public void deletarContatoExistenteTest() throws Exception {
		dadoQueEuTenhoUmContatoJaCadastradoComNome(ANA);
		
		quandoEuTentoExcluirUmContato(contato);
		
		entaoEuDevoEsperarStatus(status().isNoContent());
	}

	@Test(expected = Exception.class)
	public void deletarContatoInexistenteTest() throws Exception {
		dadoQueEuNaoTenhoNenhumContatoCadastrado();
		dadoQueEuTenhoUmContatoComNome(MARIA);
		contato.setId(11L);
		
		quandoEuTentoExcluirUmContato(contato);
		
		entaoEuDevoEsperarStatus(status().isInternalServerError());
	}
	
	private void quandoEuTentoExcluirUmContato(Contact contato) {
		request = delete(URL + contato.getId())
				.contentType(contentType)
				.header(HttpHeaders.AUTHORIZATION, bearerToken);
	}
	
	private void quandoEuAtualizarUmContato(Contact contato) {
		request = put(URL + contato.getId())
				.content(toJson(contato))
				.contentType(contentType)
				.header(HttpHeaders.AUTHORIZATION, bearerToken);
		
	}
	
	private void entaoEuDevoEsperarStatus(ResultMatcher status) throws Exception {
		mvc.perform(request).andExpect(status);
	}
	
	private void entaoEuDevoReceberUmaMensagemDeErro(String string) throws Exception {
		mvc.perform(request);
	}
	
	private void quandoEuBuscarUmContatoPeloId(Long id) {
		request = get(URL + id).header(HttpHeaders.AUTHORIZATION, bearerToken);
	}
	
	
	private void dadoQueEuTenho5ContatosCadastrados() {
		dadoQueEuTenhoUmContatoJaCadastradoComNome(MARIA);
		dadoQueEuTenhoUmContatoJaCadastradoComNome(JOAO);
		dadoQueEuTenhoUmContatoJaCadastradoComNome(MARIO);
		dadoQueEuTenhoUmContatoJaCadastradoComNome(JOSE);
		dadoQueEuTenhoUmContatoJaCadastradoComNome(ANA);
	}

	private void dadoQueEuTenhoUmContatoJaCadastradoComNome(String nome) {
		dadoQueEuTenhoUmContatoComNome(nome);
		contactService.save(contato);
	}

	private void entaoEuDevoReceberUmaListaDeContatosComTamanho(Integer size) throws Exception {
		mvc.perform(request).andExpect(jsonPath("$", hasSize(size)));
	}

	private void quandoEuListarTodosOsContatos() {
		request = get(URL).header(HttpHeaders.AUTHORIZATION, bearerToken);
	}

	private void dadoQueEuNaoTenhoNenhumContatoCadastrado() {
		contactService.deleteAll();
	}

	private void entaoEuDevoReceberUmContatoComNome(String nome) throws Exception {
		mvc.perform(request).andExpect(jsonPath("$.name", is(nome)));
	}

	private void quandoEuCadastrarUmContato(Contact contato) {
		request = post(URL)
				.content(toJson(contato))
				.contentType(contentType)
				.header(HttpHeaders.AUTHORIZATION, bearerToken);
		
	}

	private void dadoQueEuTenhoUmContatoComNome(String nome) {
		contato = Contact.builder().name(nome).build();
	}

}
