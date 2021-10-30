/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			Withdraw.java : This Class represents an ATM
 *         					withdrawal transaction
 *
 */
public class Withdraw extends Transaction {

	private double amount; // amount to withdrawn
	private Keypad keypad; // reference to ATM keypad for taking input

	/**
	 * Constructor to class Withdraw
	 * 
	 * @param currentAccountNumber
	 * @param atmBankDatabase
	 * @param atmKeypad
	 */
	public Withdraw(String currentAccountNumber, BankDatabase atmBankDatabase, Keypad atmKeypad) {
		super(currentAccountNumber, atmBankDatabase);
		keypad = atmKeypad;
	}

	/**
	 * This method performs Withdraw transaction on user account Returns String when
	 * done.
	 */
	@Override
	public String execute() {
		boolean cashDispensed = false;
		double availableBalance;
		double atmBalance;

		// Reference to bank database
		BankDatabase bankDatabase = getBankDatabase();

		atmBalance = bankDatabase.getATMBalance(); // Get Current ATM Balance from DB
		availableBalance = bankDatabase.getAvailableBalance(getAccountNumber()); // Get Current Account Balance from DB

		// Condition 4: Check if ATM has money
		if (atmBalance < 20) {
			System.out.println("Unable to process your withdrawal at this time.");
		} else {
			// Get user input only if ATM has money
			System.out.print("\nWithdraw: ");
			String amountStr = keypad.getInput();

			// Validate input for correctness
			try {
				if (amountStr.equals("timeout")) {
					return "timeout";
				} else if (amountStr.matches("^[0-9]*.[0-9]{2}$") || amountStr.matches("^[0-9]*$")) {
					amount = Double.parseDouble(amountStr);
				} else {
					System.out.println("Invalid Amount. Account logged out.");
					return "invalid";
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Amount. Account logged out.");
				return "invalid";
			}

			// Condition: Check if user entered multiple of 20 else cancel the transaction
			if (amount % 20 == 0) {

				// Condition 3: Check if ATM has less money than user requested
				if (atmBalance < amount) {
					System.out.println("Unable to dispense full amount requested at this time.");
					cashDispensed = dispenseCash(atmBalance, availableBalance, bankDatabase);
					return "Done";
				}

				// Condition 5: Check for already overdrawn account
				if (availableBalance >= 0) {
					// loop until cash dispenses or user cancels
					do {
						// Condition 1 & 2 : Process transaction as per overdrawn status
						cashDispensed = dispenseCash(amount, availableBalance, bankDatabase);
					} while (!cashDispensed);
				} else {
					System.out.println("Your account is overdrawn! You may not make withdrawals at this time.");
					return "Done";
				}
			} else {
				System.out.println("\nCanceling transaction... Please Enter the amount in multiple of $20");
				return "Done";
			}
		}
		return "Done";
	}

	/**
	 * This method performs dispense cash transaction for the account which goes in
	 * overdrawn for first time or not in overdrawn status
	 * 
	 * @param amount
	 * @param availableBalance
	 * @param bankDatabase
	 * @return true for successful cash dispense
	 */
	public boolean dispenseCash(double amount, double availableBalance, BankDatabase bankDatabase) {
		if (amount <= availableBalance) { // Account has not been overdrawn
			//Debit from user's account
			bankDatabase.debit(getAccountNumber(), amount);
			//Debit from ATM available money
			bankDatabase.debitATM(amount);
			//Add entry to transaction history
			bankDatabase.transHistory(getAccountNumber(), -amount);

			System.out.println("\nAmount Dispensed: $" + amount);
			availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			System.out.println("Current Balance: $" + availableBalance);

			return true;
		} else { // Account has been overdrawn with this transaction
			//Debit from user's account
			bankDatabase.debit(getAccountNumber(), amount + 5);
			//Debit from ATM available money
			bankDatabase.debitATM(amount);
			//Add entry to transaction history
			bankDatabase.transHistory(getAccountNumber(), -amount);

			System.out.println("\nAmount Dispensed: $" + amount);
			availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			System.out.println("You have been charged an overdraft fee of $5. Current Balance: $" + availableBalance);
			return true;
		}
	}
}