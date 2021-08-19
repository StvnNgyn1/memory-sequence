import com.phidget22.*;//Add Phidgets Library.
import java.util.ArrayList;
public class Sequence 
{	
	//Global variable for the number of button events. Represents the number of answers recorded.
	public static int numEvents = 0;
	//Turn on/off LEDs with Global Variables
	static boolean turnRedLEDOn = false;
	static boolean turnGreenLEDOn = false;
	//Handle Exceptions
	public static void main(String[] args) throws Exception
	{
		
		//Create
		DigitalInput redButton = new DigitalInput();
		DigitalOutput redLED = new DigitalOutput();
		DigitalInput greenButton = new DigitalInput();
		DigitalOutput greenLED = new DigitalOutput();
		
		//Address
		redButton.setHubPort(0);
		redButton.setIsHubPortDevice(true);
		redLED.setHubPort(1);
		redLED.setIsHubPortDevice(true);
		greenButton.setHubPort(5);
		greenButton.setIsHubPortDevice(true);
		greenLED.setHubPort(4);
		greenLED.setIsHubPortDevice(true);
		
		//Open 
		redButton.open(1000);
		redLED.open(1000);
		greenButton.open(1000);
		greenLED.open(1000);
		
		//ArrayLists for sequenceKey(answer key) and userAnswer.
		ArrayList<Integer> sequenceKey = new ArrayList<Integer>();
		ArrayList<Integer> userAnswer = new ArrayList<Integer>();
		
		//Event for red button
		redButton.addStateChangeListener(new DigitalInputStateChangeListener()
		{
			public void onStateChange(DigitalInputStateChangeEvent e)
			{
				turnRedLEDOn = e.getState();
				if(e.getState())
				{
					userAnswer.add(0);
					numEvents++;
				}			
			}
		});
		//Event for green button
		greenButton.addStateChangeListener(new DigitalInputStateChangeListener()
		{
			public void onStateChange(DigitalInputStateChangeEvent e)
			{
				turnGreenLEDOn = e.getState();
				if(e.getState())
				{
					userAnswer.add(1);
					numEvents++;
				}
			}			
		});
		
		int startingNumberOfColours = 3; //int variable stating the number of colours flashed the game will start with.
		System.out.println("Starting game, look at the LEDs.");
		Thread.sleep(1000);
		boolean continueGame = true; //Boolean that determines if while loop below should continue or not.
		//Loop that keeps game going
		while (continueGame == true) 
		{
			sequenceKey.clear(); //Ensures the sequence is reset for each level
			//Process that determines the sequence of the flashing lights until the starting number of colours are met.
			for (int indexToAssign = 0; indexToAssign < startingNumberOfColours; indexToAssign++)
			{	
				sequenceKey.add((int)(Math.random() * 2)); //Randomly generates number between 0 and 1 to add to the ArrayList sequenceKey.
			}
			
			//A for loop that will flash an LED according to the number assigned at the current index position
			for (int indexToFlash = 0; indexToFlash < sequenceKey.size(); indexToFlash++)
			{
				if (sequenceKey.get(indexToFlash) == 0) //0 is assigned to red
				{
					redLED.setState(true);
					Thread.sleep(500);
					redLED.setState(false);
				}
				else //Only other option is 1, which is assigned to green.
				{
					greenLED.setState(true);
					Thread.sleep(500);
					greenLED.setState(false);
				}
				Thread.sleep(500);
			}
			
			numEvents = 0; //resets number of events pressed before the user answers
			userAnswer.clear(); //clears ArrayList for the users answers before each answer
			System.out.println("Please enter your answer:");
			while (numEvents < sequenceKey.size()) //While loop that checks if the users answer is complete
			{
				redLED.setState(turnRedLEDOn);
				greenLED.setState(turnGreenLEDOn);
			}
			
			//A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user incorrect. if incorrect, the game will stop
			for (int indexToCompare = 0; indexToCompare < sequenceKey.size(); indexToCompare++)
			{
				if (userAnswer.get(indexToCompare) != sequenceKey.get(indexToCompare))
				{//Indicates the user got the sequence incorrect
					redLED.setState(true);
					System.out.println("Game over...");
					Thread.sleep(2000);
					redLED.setState(false);
					continueGame = false;
					break;
				}
			}
			
			if (continueGame) //If the user was correct on all, the game will continue
			{
				//Indicates the user got the sequence correct
				greenLED.setState(true); 
				System.out.println("Congrats!");
				Thread.sleep(2000);
				greenLED.setState(false);
				Thread.sleep(1000);
				System.out.println("\n" + "Starting next level...");
				Thread.sleep(2000);
				startingNumberOfColours++;		
			}
			Thread.sleep(150);
		}
	}
}
