package br.com.diego.notafiscal;

import java.math.BigDecimal;

public class ICMS implements Imposto{

	
	public BigDecimal valorImposto() {
		return BigDecimal.valueOf(.11);
	}

}
