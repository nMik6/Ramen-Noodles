package src.main;

public class CardReader {

	public CardReader() {}

	/**
	 * Reads the card in
	 * @param val card number
	 * @return integer value of card
	 */
	public int readCard(String val) {
		return Integer.parseInt(val);
	}
}
