package pl.aia.wallet.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal random(int range) {
		BigDecimal max = new BigDecimal(range);
		BigDecimal randFromDouble = new BigDecimal(Math.random());
		BigDecimal actualRandomDec = randFromDouble.multiply(max);
		actualRandomDec = actualRandomDec.setScale(2, BigDecimal.ROUND_DOWN);
		return actualRandomDec;
	}

}
