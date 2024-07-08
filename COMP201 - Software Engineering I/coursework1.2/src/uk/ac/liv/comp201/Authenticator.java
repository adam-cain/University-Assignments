package uk.ac.liv.comp201;

import static uk.ac.liv.comp201.ResponseCode.*;

public class Authenticator {
	private Card card;	// this is the Card this is being checked
	private String passcodeFire;
	private String passcodeBurglary;
	
	public Authenticator(Card card) {
		this.card=card;
	}
	
	public ResponseCode checkFireCode(String passCodeFire) throws CardException {
		checkStatus();
		if (card.getFireAttempts() >= 3) {
			return CARD_LOCKED_FIRE;
		}
		if(!(passCodeFire.matches("[a-zA-Z\\d]+") && passCodeFire.length() >= 10 && passCodeFire.length() <= 14)){
			return INVALID_FIRE_CODE;
		}
		if(!passCodeFire.equals(card.getCardFireCode())){
			card.incrementFireAttempts();
			if (card.getFireAttempts() >= 3) {
				card.setCardStatus(2);
				return CARD_LOCKED_FIRE;
			}
			return BAD_FIRE_CODE;
		}
		card.resetFireAttempts();
		return OK;
	}
	
	public ResponseCode checkBurglaryCode(String passCodeBurglary) throws CardException {
		checkStatus();
		if (card.getBurglaryAttempts() >= 3) {
			return CARD_LOCKED_BURGLAR_ALARM;
		}
		System.out.println(passCodeBurglary.matches("[\\d]+"));
		if(!(passCodeBurglary.matches("[\\d]+") && passCodeBurglary.length() >= 8 && passCodeBurglary.length() <= 10)){
			return INVALID_BURGLARY_CODE;
		}
		if(!passCodeBurglary.equals(card.getcardBurglaryCode())){
			card.incrementBurglaryAttempts();
			if (card.getBurglaryAttempts() >= 3) {
				card.setCardStatus(2);
				return CARD_LOCKED_BURGLAR_ALARM;
			}
			return BAD_BURGLARY_CODE;
		}
		card.resetBurglaryAttempts();
		return OK;
	}

	private void checkStatus() throws CardException{
		System.out.println("Card status: " + card.getCardStatus());
		if(card.getCardStatus() == 2){
			throw new CardException(CARD_STATUS_BAD);
		}
	}
}
