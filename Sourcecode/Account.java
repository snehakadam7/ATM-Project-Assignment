import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			Account.java : This class represents a bank account
 *
 */
public class Account {
	private String accountNumber; // User's Account Number
	private String pin; // User's pin
	private double availableBalance; // User's Current available balance

	// Map and list to keep track of user's transactions
	private Map<String, List<String>> transHistoryMap = new HashMap<String, List<String>>();
	private List<String> transactions = new ArrayList<String>();

	/**
	 * Constructor for class Account
	 * 
	 * @param userAccountNumber
	 * @param userPin
	 * @param theAvailableBalance
	 */
	public Account(String userAccountNumber, String userPin, double theAvailableBalance) {
		accountNumber = userAccountNumber;
		pin = userPin;
		availableBalance = theAvailableBalance;
	}

	/**
	 * This method validates user's pin with the value present in Account
	 * information
	 * 
	 * @param userPIN
	 * @return true if successful match, false if unsuccessful
	 */
	public boolean validatePIN(String userPIN) {
		if (pin.equals(userPIN))
			return true;
		else
			return false;
	}

	/**
	 * This method returns available balance from current account object
	 * 
	 * @return
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * This method returns account number from current account object
	 * 
	 * @return
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * This method adds the amount specified to current money available in account
	 * 
	 * @param amount
	 */
	public void credit(double amount) {
		availableBalance += amount;
	}

	/**
	 * This method reduces the amount specified from current money available in
	 * account
	 * 
	 * @param amount
	 */
	public void debit(double amount) {
		availableBalance -= amount;
	}

	/**
	 * This method adds the transaction history to user's account
	 * 
	 * @param amount
	 */
	public void transHistory(double amount) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		transactions.add(formatter.format(date) + " " + amount + " " + availableBalance);
		transHistoryMap.put(getAccountNumber(), transactions);
	}

	/**
	 * This method retrieves the transaction history for specific user account.
	 * 
	 * @param userAccountNumber
	 */
	public void getTransHistory(String account_number) {
		List<String> history = transHistoryMap.get(account_number);

		// Checks if transaction history is empty
		if (history == null || history.isEmpty()) {
			System.out.println("No history found.");
		} else { // Prints transaction history in reverse chronological order
			for (int i = history.size() - 1; i >= 0; i--) {
				System.out.println(history.get(i));
			}
		}
	}
}
