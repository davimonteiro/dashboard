package br.com.davimonteiro;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.davimonteiro.web.ContactControllerTest;
import br.com.davimonteiro.web.HomeControllerTest;


/**
 * 
 * @author Davi Monteiro Barbosa
 * 
 * Suite de testes.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	ModelTests.class,
	HomeControllerTest.class,
	ContactControllerTest.class
})
public class SuiteTest { }
