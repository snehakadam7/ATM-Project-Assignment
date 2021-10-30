/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			Balance.java : This class represents a balance inquiry transaction
 *
 */
public class Balance extends Transaction {
	/**
	 * Constructor to class Balance
	 * 
	 * @param userAccountNumber
	 * @param atmBankDatabase
	 */
	public Balance(String userAccountNumber, BankDatabase atmBankDatabase) {
		super(userAccountNumber, atmBankDatabase);
	}

	/**
	 * This method performs Balance inquiry transaction on user account Returns
	 * String when done.
	 */
	@Override
	public String execute() {
		// Reference to bank database
		BankDatabase bankDatabase = getBankDatabase();

		// Get Current Account Balance from DB
		double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

		// Display Balance information to the screen
		System.out.println("Current balance: $" + availableBalance);
		return "Done";

	}
}
