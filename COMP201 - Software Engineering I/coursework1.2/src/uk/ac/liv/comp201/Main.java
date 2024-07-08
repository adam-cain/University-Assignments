package uk.ac.liv.comp201;

public class Main {
	public static void main(String[] args) {
		String cardName="coopesabc";
		try {
			Card.createNewCard(cardName);
		} catch (CardException e) {
			e.printStackTrace();
		}
		
		Card card;
		Authenticator auth;
		try {
			card = Card.loadCard(cardName);
			System.out.println("Status: "+card.getCardStatus());
		} catch (CardException e) {
			e.printStackTrace();
		}
		try {
			card = Card.loadCard(cardName);
			System.out.println("Status: "+card.getCardStatus());
			card.setCodes("invalid", "ValidC0desss");
		} catch (CardException e) {
			e.printStackTrace();
		}

		try {
			card = Card.loadCard(cardName);
			card.setCodes("ValidC0dess", "123%56789");
		} catch (CardException e) {
			e.printStackTrace();
		}

		try {
			card = Card.loadCard(cardName);
			card.setCodes("ValidC0desss", "123456789");
			System.out.println("Status: "+card.getCardStatus());
		} catch (CardException e) {
			e.printStackTrace();
		}

		try {
			card = Card.loadCard(cardName);
			System.out.println("Fire Code: "+card.getCardFireCode());
			System.out.println("Burglary Code: "+card.getcardBurglaryCode());
			System.out.println("Burglary Attempts: "+card.incorrectBurglaryAttempts);
			System.out.println("Fire Attempts: "+card.incorrectFireAttempts);
			auth = new Authenticator(card);
			System.out.println(auth.checkFireCode("12345678"));
			System.out.println(card.incorrectBurglaryAttempts);
			System.out.println(card.incorrectFireAttempts);
		} catch (CardException e) {
			e.printStackTrace();
		}
	}

}
