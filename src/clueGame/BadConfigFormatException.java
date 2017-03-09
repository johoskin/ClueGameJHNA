package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Configuration file format is incorrect");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
}
