// Deposit.java
// Represents a deposit ATM transaction
/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			Deposit.java : This Class represents an ATM
 *         					deposit transaction
 *
 */
public class Deposit extends Transaction {

	private double amount; // amount to deposit
	private Keypad keypad; // reference to ATM keypad for taking input
	private final static String CANCELED = "0";

	double availableBalance;

	/**
	 * Constructor to class Deposit
	 * 
	 * @param currentAccountNumber
	 * @param atmBankDatabase
	 * @param atmKeypad
	 */
	public Deposit(String currentAccountNumber, BankDatabase atmBankDatabase, Keypad atmKeypad) {
		super(currentAccountNumber, atmBankDatabase);

		keypad = atmKeypad;
	}

	/**
	 * This method performs Deposit transaction on user account Returns String when
	 * done.
	 */
	@Override
	public String execute() {
		// Reference to bank database
		BankDatabase bankDatabase = getBankDatabase();

		String amountStr = promptForDepositAmount(); // Get user input from user
		// Validate input for correctness
		try {
			if (amountStr.equals("timeout")) {
				return "timeout";
			} else if (amountStr.matches("^[0-9]*.[0-9]{2}$") || amountStr.matches("^[0-9]*$")) {
				amount = Double.parseDouble(amountStr);
			} else {
				System.out.println("Invalid Amount Entered. Account logged out.");
				return "invalid";
			}
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid Amount. Account logged out.");
			return "invalid";
		}

		// check whether user entered deposit amount or canceled
		if (!amountStr.equals(CANCELED)) {
			// Credit to user's account
			bankDatabase.credit(getAccountNumber(), amount);
			availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			System.out.println("Current Balance: $" + availableBalance);
			// Credit to ATM available money
			bankDatabase.creditATM(amount);
			// Add entry to transaction history
			bankDatabase.transHistory(getAccountNumber(), amount);

		} else {
			System.out.println("Canceling transaction...");
		}
		return "Done";
	}

	/**
	 * This method displays prompt for user to enter deposit amount
	 * 
	 * @return the amount entered or 0 if cancelled
	 */
	private String promptForDepositAmount() {

		System.out.print("\nPlease enter a deposit amount in Dollars (or 0 to cancel): ");
		String input = keypad.getInput();

		// check if canceled
		if (input.equals(CANCELED))
			return CANCELED;
		else {
			return input;
		}
	}
}
