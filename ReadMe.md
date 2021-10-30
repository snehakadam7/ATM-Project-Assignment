Assignment has been completed and uploaded in the following directory structure.

**Assumptions:** 
1. csv file format will remain same with first line being header.
2. amount has a range of 5.0 × 10^(−345) to 1.7 × 10^(308)

**Sourcecode:** This directory contains 10 files

initial_account_data.csv :represents account data

ATM.java : Represents an automated teller machine. This class has main method.

Keypad.java: Represents the keypad of the ATM and is responsible for receiving all user inputs.

Account.java: Represents a bank account. Each account has three attributes- account number, pin, available balance.

BankDatabase.java: Models the bank database with which ATM inetracts to access and modify user's account information.

Transaction.java: Represents the notion of an ATM transaction which includes features like checking balance, withdraw and deposit money.

Balance.java: Represents balance of an account.

Deposit.java: Represents deposit transaction of an account.

Withdraw.java: Represents withdraw transactions of an account.

History.java: Represents transaction history of an account.

**Documentation:**
This directory contains ATM_unit_testcases.xls which were executed to perform unit testing.

ATM_unit_testcases.xls: represents unit test cases for the assignment.

**Steps to execute program**

Prerequisite: Java should be installed and environment variables should be set.

1. Clone github repository
2. Open command prompt/terminal
3. Cd to SourceCode folder with command prompt/terminal
4. Compile using command "javac *.java"
5. Run program using "java ATM"

