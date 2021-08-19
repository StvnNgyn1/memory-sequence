
using System;
//Add Phidgets Library
using Phidget22;
using System.Collections;

namespace SequenceMemory
{
    class Sequence
    {
        //Global variable for the number of button events. Represents the number of answers recorded.
        static int numEvents = 0;
        //Turn on/off LEDs with Global Variables
        static bool turnRedLEDOn = false;
        static bool turnGreenLEDOn = false;
        //Global ArrayLists for sequenceKey(answer key) and userAnswer.
        public static ArrayList sequenceKey = new ArrayList();
        public static ArrayList userAnswer = new ArrayList();

        //Event
        private static void redButton_StateChange(object sender, Phidget22.Events.DigitalInputStateChangeEventArgs e)
        {
            turnRedLEDOn = e.State;
            if (e.State)
            {
                userAnswer.Add(0);
                numEvents++;
            }
        }
        //Event
        private static void greenButton_StateChange(object sender, Phidget22.Events.DigitalInputStateChangeEventArgs e)
        {
            turnGreenLEDOn = e.State;
            if (e.State)
            {
                userAnswer.Add(1);
                numEvents++;
            }
        }

        static void Main(string[] args)
        {
            //Create | Create objects for your buttons and LEDs.
            DigitalInput redButton = new DigitalInput();
            DigitalOutput redLED = new DigitalOutput();
            DigitalInput greenButton = new DigitalInput();
            DigitalOutput greenLED = new DigitalOutput();

            //Address | Address your four objects which lets your program know where to find them.
            redButton.HubPort = 0;
            redButton.IsHubPortDevice = true;
            redLED.HubPort = 1;
            redLED.IsHubPortDevice = true;
            greenButton.HubPort = 5;
            greenButton.IsHubPortDevice = true;
            greenLED.HubPort = 4;
            greenLED.IsHubPortDevice = true;

            //Subscribe to events
            redButton.StateChange += redButton_StateChange;
            greenButton.StateChange += greenButton_StateChange;

            //Open | Connect your program to your physical devices.
            redButton.Open(1000);
            redLED.Open(1000);
            greenButton.Open(1000);
            greenLED.Open(1000);

            //Creates random number generator
            Random RNG = new Random();

            var startingNumberOfColours = 3; //int variable stating the number of colours flashed the game will start with.
            Console.WriteLine("Starting game, look at the LEDS.");
            System.Threading.Thread.Sleep(1000);
            bool continueGame = true; //Boolean that determines if while loop below should continue or not.
            //Loop that keeps game going
            while (continueGame  == true)
            {
                sequenceKey.Clear(); //Ensures the sequence is reset for each level
                //Proccess that determines the sequence of the flashing lights until the starting number of colours are met.
                for (int indexToAssign = 0; indexToAssign < startingNumberOfColours; indexToAssign++)
                {//Randomly generates number between 0 and 1 to add to the ArrayList sequenceKey
                    int randomNumber = RNG.Next(0, 2);
                    sequenceKey.Add(randomNumber);
                }

                //A for loop that will flash an LED according to the number assigned at the current index position
                for (int indexToFlash = 0; indexToFlash < sequenceKey.Count; indexToFlash++)
                {
                    if (Convert.ToInt32(sequenceKey[indexToFlash]) == 0) //0 is assigned to red
                    {
                        redLED.State = true;
                        System.Threading.Thread.Sleep(500);
                        redLED.State = false;
                    }
                    else //Only other option is 1, which is assigned to green.
                    {
                        greenLED.State = true;
                        System.Threading.Thread.Sleep(500);
                        greenLED.State = false;
                    }
                    System.Threading.Thread.Sleep(500);
                }

                numEvents = 0; //resets number of events pressed before the user answers
                userAnswer.Clear(); //clears ArrayList for the users answers before each answer
                Console.WriteLine("Please enter your answer:");
                while (numEvents < sequenceKey.Count) //While loop that checks if the users answer is complete
                {
                    redLED.State = turnRedLEDOn;
                    greenLED.State = turnGreenLEDOn;
                    System.Threading.Thread.Sleep(150);
                }

                //A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user incorrect. if incorrect, the game will stop
                for (int indexToCompare = 0; indexToCompare < sequenceKey.Count; indexToCompare++)
                {
                    if (Convert.ToInt32(userAnswer[indexToCompare]) != Convert.ToInt32(sequenceKey[indexToCompare]))
                    {//Indicates the user got the sequence incorrect
                        redLED.State = true;
                        Console.WriteLine("Game over...");
                        System.Threading.Thread.Sleep(2000);
                        redLED.State = false;
                        continueGame = false;
                        break;
                    }                       
                }

                if (continueGame) //If the user was correct on all, the game will continue
                {
                    //Indicates the user got the sequence correct
                    greenLED.State = true;
                    Console.WriteLine("Congrats!");
                    System.Threading.Thread.Sleep(2000);
                    greenLED.State = false;
                    System.Threading.Thread.Sleep(1000);
                    Console.WriteLine("\n" + "Starting next level...");
                    System.Threading.Thread.Sleep(2000);
                    startingNumberOfColours++;
                }
                System.Threading.Thread.Sleep(150);
            }

        }
    }
}
