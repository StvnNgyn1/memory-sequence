import com.phidget22.*;//Add Phidgets Library.

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
		
		//Open 
		redButton.open(1000);
		redLED.open(1000);
		greenButton.open(1000);
		greenLED.open(1000);
		//Process that determines the sequence of the flashing lights.
		//Randomly generates number between 0 and 1. 0 for red, 1 for green.
		System.out.println("test println");
	}

}
//RNG between 0 and 1
//If 0, flash red, add red to answers array. if 1, flash green, add green to answers array.
//While loop
//If correct, game will flash green LED 3 times and the game will continue. If incorrect, game will flash red LED 3 times and software will stop