import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author Sneha Shahaji Kadam 
 * 			Keypad.java : This class represents ATM Keypad
 *         
 */
public class Keypad {
	private BufferedReader input;

	/**
	 * Constructor for Keypad which takes input from system
	 */
	public Keypad() {
		input = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * This method is reads the input from console
	 * @return string with user input
	 */
	public String getInput() {
		String userInput="";
		try {
			userInput = readInput();
		} catch (InterruptedException | ExecutionException | AWTException e) {
			e.printStackTrace();
		}
		return userInput;
	}
	
	/**
	 * This method creates Future tasks for input collection.
	 * @return: Timeout if no input is provided for 2 minutes
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws AWTException
	 */
	public String readInput() throws InterruptedException, ExecutionException, AWTException
	{
		ExecutorService ex = Executors.newSingleThreadExecutor();
		Future<String> result = ex.submit(new Worker());
		Robot enterKey = new Robot();
		try
		{
			return result.get(2, TimeUnit.MINUTES);
		}
		catch (TimeoutException e)
		{
			// Press enter key to release console from current thread
			enterKey.keyPress(KeyEvent.VK_ENTER); 
			System.out.println("Session Timed Out. Exiting ...");
			return "timeout";
		}
	}
 
	/**
	 * 
	 * @author Sneha Shahaji Kadam 
	 * This class represents the worker thread for reading the input from console
	 *
	 */
	private class Worker implements Callable<String>
	{
		@Override
		public String call() throws Exception
		{
			return input.readLine();
		}
	}
	
}




