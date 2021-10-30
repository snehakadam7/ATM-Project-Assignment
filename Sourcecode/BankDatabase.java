import java.util.List;

/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			BankDatabase.java : This Class represents the
 *       						bank account database
 *
 */

public class BankDatabase {
	private Account[] accounts; // array of accounts
	private double atmBalance;
	Account acc;

	/**
	 * Constructor for class BankDatabase
	 * 
	 * @param inputAccounts : Accounts read from CSV file
	 */
	public BankDatabase(List<Account> inputAccounts) {
		atmBalance = 10000; // Initial ATM amount
		accounts = new Account[inputAccounts.size()]; // Accounts read from the CSV file
		for (int i = 0; i < inputAccounts.size(); i++) {
			accounts[i] = inputAccounts.get(i);
		}
	}

	/**
	 * This method retrieve account object based on account number
	 * 
	 * @param accountNumber
	 * @return Account object, null if no matching account was found
	 */
	private Account getAccount(String accountNumber) {
		for (Account currentAccount : accounts) {
			if (currentAccount.getAccountNumber().equals(accountNumber)) {
				return currentAccount;
			}
		}
		return null;
	}

	/**
	 * This method authenticates user based on account number and pin
	 * 
	 * @param accountNumber
	 * @param pin
	 * @return true if successful, false if unsuccessful
	 */
	public boolean authenticateUser(String accountNumber, String pin) {
		Account userAccount = getAccount(accountNumber);
		if (userAccount != null) {
			return userAccount.validatePIN(pin);
		} else {
			return false;
		}
	}

	//
	/**
	 * This method returns available balance from account based on account number
	 * 
	 * @param userAccountNumber
	 * @return
	 */
	public double getAvailableBalance(String userAccountNumber) {
		return getAccount(userAccountNumber).getAvailableBalance();
	}

	/**
	 * 
	 * @return current money available in ATM
	 */
	public double getATMBalance() {
		return atmBalance;
	}

	/**
	 * This method reduces the amount specified from current money available in ATM
	 * 
	 * @param amount
	 */
	public void debitATM(double amount) {
		atmBalance -= amount;
	}

	/**
	 * This method adds the amount specified to current money available in ATM
	 * 
	 * @param amount
	 */
	public void creditATM(double amount) {
		atmBalance += amount;
	}

	/**
	 * This method credit an amount from the account with specified account number
	 * 
	 * @param userAccountNumber
	 * @param amount
	 */
	public void credit(String userAccountNumber, double amount) {
		getAccount(userAccountNumber).credit(amount);
	}

	/**
	 * This method reduces an amount from account with specified account number
	 * 
	 * @param userAccountNumber
	 * @param amount
	 */
	public void debit(String userAccountNumber, double amount) {
		getAccount(userAccountNumber).debit(amount);
	}

	/**
	 * This method adds the transaction history to user's account
	 * 
	 * @param userAccountNumber
	 * @param amount
	 */
	public void transHistory(String userAccountNumber, double amount) {
		getAccount(userAccountNumber).transHistory(amount);
	}

	/**
	 * This method retrieves the transaction history for specific user account.
	 * 
	 * @param userAccountNumber
	 */
	public void getTransHistory(String userAccountNumber) {
		getAccount(userAccountNumber).getTransHistory(userAccountNumber);
	}

}
