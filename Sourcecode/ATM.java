import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sneha Shahaji Kadam
 *
 *         ATM.java This Class represents an ATM.
 */

public class ATM {

	private boolean userAuthenticated; // if the user is authenticated
	private String currentAccountNumber; // Current user's account number
	private Keypad keypad; // ATM's keypad for inputs
	private BankDatabase bankDatabase; // bank account information

	// constants for the menu options
	private static final String BALANCE = "1";
	private static final String WITHDRAW = "2";
	private static final String DEPOSIT = "3";
	private static final String HISTORY = "4";
	private static final String LOGOUT = "5";
	private static final String END = "6";

	public ATM(List<Account> accounts) {
		userAuthenticated = false;
		currentAccountNumber = "";
		keypad = new Keypad();
		bankDatabase = new BankDatabase(accounts);
	}

	// Initiate the ATM
	public void run() {
		// welcome, authenticate user and perform transaction
		while (true) {
			while (!userAuthenticated) {
				System.out.println("\nWelcome User");
				authenticateUser();
			} // end while

			performTransactions();
			userAuthenticated = false;
			currentAccountNumber = "";
			System.out.println("Thank You.");
		} // end while

	} // end method run

	/**
	 * This method initiates User Authentication based on account number and pin
	 */
	public void authenticateUser() {
		System.out.println("Please enter your bank account number: ");
		String accountNumber = keypad.getInput();
		if (accountNumber.equals("timeout")) { // Returns to the ATM Run method in case of Timeout
			return;
		} else if (!accountNumber.matches("^[0-9]*$")) { // Returns to the ATM Run method in case of invalid input.
			System.out.println("Invalid Account Number.");
			return;
		}

		System.out.println("Please enter your PIN: ");
		String pin = keypad.getInput();
		if (pin.equals("timeout")) { // Returns to the ATM Run method in case of Timeout
			return;
		} else if (!pin.matches("^[0-9]*$")) { // Returns to the ATM Run method in case of invalid input.
			System.out.println("Invalid pin Number.");
			return;
		}

		// Calls Bank DB to verify User information
		userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

		// check whether authentication is successful
		if (userAuthenticated) {
			System.out.println("Account number [" + accountNumber + "] successfully authorized.");
			currentAccountNumber = accountNumber;
		} else {
			System.out.println("Authorization failed. Please try again.");
		}
	}

	/**
	 * This method provides options to the authenticated user to perform
	 * transactions. Authenticated user can perform 1. Balance Inquiry 2. Withdrawal
	 * 3. Deposit 4. Check Transaction history 5. Logout 6. End the program
	 */
	private void performTransactions() {
		Transaction currentTransaction = null;

		boolean userExited = false;

		// loop while user has not chosen exit, keep showing him transaction options
		while (!userExited) {
			// Display menu and get user selection
			String mainMenuSelection = displayMainMenu();
			if (mainMenuSelection.equals("timeout")) { // Returns to the ATM Run method in case of Timeout
				return;
			}

			// Performs transaction based on user selection
			switch (mainMenuSelection) {
			// user choose to perform on of three transaction types
			case BALANCE:
			case WITHDRAW:
			case DEPOSIT:
			case HISTORY:
				currentTransaction = createTransaction(mainMenuSelection);
				String status = currentTransaction.execute();
				// Returns to the ATM Run method in case of Timeout or invalid inputs
				if (status.equals("timeout") || status.equals("invalid")) {
					return;
				}
				break;
			case LOGOUT:
				System.out.println("Account logged out.");
				userExited = true;
				break;
			case END:
				System.out.println("Shutting the server!!");
				System.exit(0);
			default:
				System.out.print("You have not entered a valid selection. Try again.");
				break;
			}
		}
	}

	/**
	 * This method displays Menu items to the user in console
	 * 
	 * @return User selection
	 */
	private String displayMainMenu() {
		System.out.println("\nMenu:");
		System.out.println("1 - Balance");
		System.out.println("2 - Withdraw");
		System.out.println("3 - Deposit");
		System.out.println("4 - History");
		System.out.println("5 - Log out");
		System.out.println("6 - End\n");

		return keypad.getInput();
	}

	/**
	 * This method initiates Transaction objects based on user selection
	 * 
	 * @param type can take Balance, Withdraw, Deposit or History as values
	 * @return transaction object
	 */
	private Transaction createTransaction(String type) {
		Transaction tmp = null;

		// determine which type of transaction create
		switch (type) {
		case BALANCE:
			tmp = new Balance(currentAccountNumber, bankDatabase);
			break;
		case WITHDRAW:
			tmp = new Withdraw(currentAccountNumber, bankDatabase, keypad);
			break;
		case DEPOSIT:
			tmp = new Deposit(currentAccountNumber, bankDatabase, keypad);
			break;
		case HISTORY:
			tmp = new History(currentAccountNumber, bankDatabase, keypad);
		}
		return tmp;
	}

	/**
	 * This is the starting point of the ATM project. It reads the initial account
	 * data CSV and initializes the Bank Database. Later it initiates the ATM and
	 * perform all the actions as per user inputs.
	 * 
	 * @param args: Does not take any command line arguments
	 */
	public static void main(String[] args) {
		List<Account> accounts = new ArrayList<Account>();

		// Read Input CSV File before beginning ATM Application
		try {
			// Parsing a CSV file
			BufferedReader br = new BufferedReader(new FileReader("initial_account_data.csv"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("ACCOUNT_ID")) { // Skipping the Header Line
					continue;
				}
				String[] accountInfo = line.split(",");
				accounts.add(new Account(accountInfo[0], accountInfo[1], Double.parseDouble(accountInfo[2])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Initiate the ATM object with account information.
		ATM theATM = new ATM(accounts);
		theATM.run();
	}
}
