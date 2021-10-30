/**
 * 
  * @author Sneha Shahaji Kadam 
 * 			Transaction.java : This class represents an ATM transaction
 *
 */
public abstract class Transaction {
	private String accountNumber;
	private BankDatabase bankDatabase;
	
	/**
	 * Transaction constructor invoked by subclasses by using super()
	 * @param userAccountNumber
	 * @param atmBankDatabase
	 */
	public Transaction(String userAccountNumber, BankDatabase atmBankDatabase) {
		accountNumber = userAccountNumber;
		bankDatabase = atmBankDatabase;
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
	 * This method returns object of BankDatabase
	 * @return
	 */
	public BankDatabase getBankDatabase() {
		return bankDatabase;
	}
	
	/**
	 * This method is overridden by subclasses to execute different types of transactions
	 * @return
	 */
	abstract public String execute();
}
