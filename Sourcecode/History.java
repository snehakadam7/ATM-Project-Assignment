/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			History.java : This class represents 'view
 *         					transaction history' transaction
 *
 */
public class History extends Transaction {
	private BankDatabase bankDatabase;
	private String userAcc;

	/**
	 * Constructor to class History
	 * 
	 * @param currentAccountNumber
	 * @param atmBankDatabase
	 * @param atmKeypad
	 */
	public History(String currentAccountNumber, BankDatabase atmBankDatabase, Keypad atmKeypad) {
		super(currentAccountNumber, atmBankDatabase);
		bankDatabase = atmBankDatabase;
		userAcc = currentAccountNumber;
	}

	/**
	 * This method views transaction history on user account Returns String when
	 * done.
	 */
	@Override
	public String execute() {
		bankDatabase.getTransHistory(userAcc);
		return "Done";
	}
}
