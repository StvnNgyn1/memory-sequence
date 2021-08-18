import com.phidget22.*;//Add Phidgets Library.
import java.util.ArrayList;
public class Sequence 
{
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
		
		//Event
		redButton.addStateChangeListener(new DigitalInputStateChangeListener()
		{
			public void onStateChange(DigitalInputStateChangeEvent e)
			{
				System.out.println("State: " + e.getState());
			}
		});
		greenButton.addStateChangeListener(new DigitalInputStateChangeListener()
		{
			public void onStateChange(DigitalInputStateChangeEvent e)
			{
				System.out.println("Green Button State: " + e.getState());
			}
				
		});
		
		
		//Open 
		redButton.open(1000);
		redLED.open(1000);
		greenButton.open(1000);
		greenLED.open(1000);
		
		//ArrayLists for answerKey and userAnswer.
		ArrayList<Integer> sequenceKey = new ArrayList<Integer>();
		ArrayList<Integer> userAnswer = new ArrayList<Integer>();
		
		//Process that determines the sequence of the flashing lights.
		//A for loop that assigns an integer of 0 or 1 to each value of null in the sequenceKey ArrayList.
		int startingNumberOfColours = 3;
		for (int indexToAssign = 0; indexToAssign < startingNumberOfColours; indexToAssign++)
		{	//Randomly generates number between 0 and 1 to assign at the position indexToAssign
			sequenceKey.add((int)(Math.random() * 2));
		}
		
		//int numEvents = 0;
		System.out.println("Starting game...");
		boolean continueGame = true;
		while (continueGame == true)
		{	
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
			
			//While loop that checks if the users answer is complete
			/*
			boolean completedAnswer = false;
			while (completedAnswer == false)
			{
				if (userAnswer.size() == sequenceKey.size())
				{
					completedAnswer = true;
				}
				
				if (redButton.getState())
				{//Adds a 0 to the users answer key.
					redLED.setState(true);
					Thread.sleep(250);
					redLED.setState(false);
					userAnswer.add(0);
				}
				else if (greenButton.getState())
				{//Adds a 1 to the users answer key
					greenLED.setState(true); 
					Thread.sleep(250);
					greenLED.setState(false);
					userAnswer.add(1);
				}
				Thread.sleep(150);
			}*/
			
			//A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user is correct or incorrect. If correct, game moves on. If incorrect, game stops.
			for (int indexToCompare = 0; indexToCompare < sequenceKey.size(); indexToCompare++)
			{
				if (userAnswer.get(indexToCompare) != sequenceKey.get(indexToCompare))
				{
					redLED.setState(true); //Indicates the user got the sequence incorrect
					Thread.sleep(3000);
					redLED.setState(false);
					continueGame = false;
					System.out.print("Game over...");
				}
				else
				{
					greenLED.setState(true); //Indicates the user got the sequence correct
					Thread.sleep(3000);
					greenLED.setState(false);
					startingNumberOfColours += 1; //Adds 1 colour for the next level.
					System.out.println("Starting next level...");
				}
			}
			Thread.sleep(150);
		}

	}

}