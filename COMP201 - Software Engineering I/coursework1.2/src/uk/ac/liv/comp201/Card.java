package uk.ac.liv.comp201;

import static uk.ac.liv.comp201.ResponseCode.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Card {
	// TO DO
	// Needs to add in bad code count for fire code
	// and burglar alarm
	// You need to modify loadCard and saveCard
	// As soon as the card has a valid burglar code
	// or fire code change its status from NEW
	// to OK
	private static final int CARD_ID_LENGTH = 9;
	private static final int CARD_OK = 1;
	private static final int CARD_BLOCKED = 2;
	/*
	 * CARD_NEW is a card with no fire code or burglar code
	 */
	private static final int CARD_NEW = 3;

	public int incorrectFireAttempts = 0;
	public int incorrectBurglaryAttempts = 0;
	/**
	 * The fire code must be between 10 and 14 characters
	 * It is made up of alphanumeric characters only
	 */
	private String cardFireCode = "";
	/**
	 * The burglary code must be between 8 and 10 characters
	 * It is made up of numeric digits only 0 to 9
	 */
	private String cardBurglaryCode = "";
	/*
	 * If a CARD_STATUS = CARD_NEW doing any form of authentication
	 * (checkFireCode or checkBurglaryCode)
	 * will throw the CardException exception, with CARD_STATUS_BAD
	 */
	private int cardStatus = CARD_NEW;

	/**
	 * User of card, this is a alpha string 9 characters long
	 * The id is case insensivite so SEBCOOPET = sebcoopet
	 */
	private String cardUsername = "";

	public Card(String cardUsername) throws CardException {
		checkCardName(cardUsername);
		this.cardUsername = cardUsername;
	}

	public int getFireAttempts() {
		return incorrectFireAttempts;
	}

	public int getBurglaryAttempts() {
		return incorrectBurglaryAttempts;
	}

	public void incrementFireAttempts() {
		incorrectFireAttempts++;
		if (incorrectFireAttempts >= 3) {
			cardStatus = CARD_BLOCKED;
		}
		saveCard();
	}

	public void incrementBurglaryAttempts() {
		incorrectBurglaryAttempts++;
		if (incorrectBurglaryAttempts >= 3) {
			cardStatus = CARD_BLOCKED;
		}
		saveCard();
	}

	public void resetFireAttempts() {
		incorrectFireAttempts = 0;
		saveCard();
	}

	public void resetBurglaryAttempts() {
		incorrectBurglaryAttempts = 0;
		saveCard();
	}

	private void checkCardName(String cardUsername) throws CardException {
		if (cardUsername.length() != CARD_ID_LENGTH) {
			throw new CardException(ResponseCode.INVALID_CARD_ID_LENGTH);
		}
		if (!cardUserNameValid(cardUsername)) {
			throw new CardException(ResponseCode.INVALID_CARD_ID);
		}
	}

	public static void createNewCard(String cardUsername) throws CardException {
		Card card = new Card(cardUsername);
		card.saveCard();
	}

	private boolean cardUserNameValid(String cardUsername) {
		for (int idx = 0; idx < cardUsername.length(); idx++) {
			if (!Character.isAlphabetic(cardUsername.charAt(idx))) {
				return false;
			}
		}
		return true;
	}

	private void saveCard() {
		try {
			FileWriter fileWriter = new FileWriter(cardUsername);
			fileWriter.write(cardFireCode + "\n");
			fileWriter.write(cardBurglaryCode + "\n");
			fileWriter.write("" + cardStatus + "\n");
			fileWriter.write("" + incorrectFireAttempts + "\n");
			fileWriter.write("" + incorrectBurglaryAttempts + "\n");
			fileWriter.close();
		} catch (IOException e) {
		}
	}

	public static Card loadCard(String cardUsername) throws CardException {
		Card card = new Card(cardUsername);
		try {
			File file = new File(cardUsername);
			Scanner myReader = new Scanner(file);
			if (myReader.hasNextLine()) {
				card.cardFireCode = myReader.nextLine();
			}
			if (myReader.hasNextLine()) {
				card.cardBurglaryCode = myReader.nextLine();
			}
			if (myReader.hasNextLine()) {
				card.cardStatus = Integer.parseInt(myReader.nextLine());
			}
			if (myReader.hasNextLine()) {
				card.incorrectFireAttempts = Integer.parseInt(myReader.nextLine());
			}
			if (myReader.hasNextLine()) {
				card.incorrectBurglaryAttempts = Integer.parseInt(myReader.nextLine());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			throw new CardException(CARD_NOT_FOUND, cardUsername);
		}
		return (card);
	}

	public String getCardFireCode() {
		return cardFireCode;
	}

	public String getcardBurglaryCode() {
		return cardBurglaryCode;
	}

	private void setCardFireCode(String cardFireCode) throws CardException {
		if (!(cardFireCode.matches("[a-zA-Z\\d]+") && cardFireCode.length() >= 10 && cardFireCode.length() <= 14)) {
			throw new CardException(INVALID_FIRE_CODE);
		}
		this.cardFireCode = cardFireCode;
	}

	private void setcardBurglaryCode(String cardBurglaryCode) throws CardException {
		if (!(cardBurglaryCode.matches("[\\d]+") && cardBurglaryCode.length() >= 8
				&& cardBurglaryCode.length() <= 10)) {
			throw new CardException(INVALID_BURGLARY_CODE);
		}
		this.cardBurglaryCode = cardBurglaryCode;
	}

	public void setCodes(String cardFireCode, String cardBurglaryCode) throws CardException {
		setCardFireCode(cardFireCode);
		setcardBurglaryCode(cardBurglaryCode);
		cardStatus = Card.CARD_OK;
		saveCard();
	}

	public int getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(int cardStatus) {
		this.cardStatus = cardStatus;
	}
}
