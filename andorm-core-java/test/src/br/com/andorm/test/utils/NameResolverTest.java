package br.com.andorm.test.utils;

import br.com.andorm.utils.NameResolver;
import android.test.AndroidTestCase;


public class NameResolverTest extends AndroidTestCase {

	public void testToUnderscoreLowerCase() {
		String expected = "folha_pagamento";
		assertEquals(expected, NameResolver.toUnderscoreLowerCase("FolhaPagamento"));
		
		expected = "historico_log_cliente";
		assertEquals(expected, NameResolver.toUnderscoreLowerCase("HistoricoLogCliente"));
		
		expected = "historico_log_cliente_p";
		assertEquals(expected, NameResolver.toUnderscoreLowerCase("HistoricoLogClienteP"));
	}
	
}