
using System;
//Add Phidgets Library
using Phidget22;
using System.Collections;

namespace SequenceMemory
{
    class Sequence
    {
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

            //Open | Connect your program to your physical devices.
            redButton.Open(1000);
            redLED.Open(1000);
            greenButton.Open(1000);
            greenLED.Open(1000);

            //ArrayLists for sequenceKey(answer key) and userAnswer.
            var sequenceKey = new ArrayList();
            var userAnswer = new ArrayList();

            //Proccess that determines the sequence of the flashing lights.
            //A for loop that assigns an integer of 0 or 1 to the sequenceKey ArrayList until filled to startingNumberOfColours.
            var startingNumberOfColours = 3;
            Random RNG = new Random();
            for (int indexToAssign = 0; indexToAssign < startingNumberOfColours; indexToAssign++)
            {   //Randomly generates number between 0 and 1 to add to the ArrayList sequenceKey
                int randomNumber = RNG.Next(0, 2);
                sequenceKey.Add(randomNumber);
            }

            Console.WriteLine("Starting game...");
            Boolean continueGame = true;
            //Loop that keeps game going
            while (continueGame  == true)
            {
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

                //Block for eventual
                //events buttons
                //wooo

                //A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user is correct or incorrect. If correct, game moves on. If incorrect, game stops.
                for (int indexToCompare = 0; indexToCompare < sequenceKey.Count; indexToCompare++)
                {
                    if (Convert.ToInt32(userAnswer[indexToCompare]) != Convert.ToInt32(sequenceKey[indexToCompare]))
                    {//Indicates the user got the sequence incorrect
                        redLED.State = true;
                        System.Threading.Thread.Sleep(3000);
                        redLED.State = false;
                        continueGame = false;
                        Console.WriteLine("Game over...");
                    }
                    else
                    {//Indicates the user got the sequence correct
                        greenLED.State = true;
                        System.Threading.Thread.Sleep(3000);
                        greenLED.State = false;
                        startingNumberOfColours += 1;
                        Console.WriteLine("Starting next level...");
                    }
                }
                System.Threading.Thread.Sleep(150);
            }

        }
    }
}
