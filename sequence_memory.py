#Add Phidgets Library 
from Phidget22.Phidget import *
from Phidget22.Devices.DigitalInput import *
from Phidget22.Devices.DigitalOutput import *
#Required for sleep statement 
import time
#Required for random number generator
import random

#Global variable for number of button events. Represents the number of answers recorded.
numEvents = 0

#Lists for sequenceKey(answer key) and userAnswer
sequenceKey = []
userAnswer = []

#Event
def onRedButton_StateChange(self, state):
    global numEvents
    redLED.setState(state)
    if (state):
        userAnswer.append(0)
        numEvents+=1
#Event
def onGreenButton_StateChange(self, state):
    global numEvents
    greenLED.setState(state)
    if (state):
        userAnswer.append(1)
        numEvents+=1
        
#Create 
redButton = DigitalInput()
redLED =  DigitalOutput()
greenButton = DigitalInput()
greenLED = DigitalOutput()
 
#Address 
redButton.setHubPort(0)
redButton.setIsHubPortDevice(True)
redLED.setHubPort(1)
redLED.setIsHubPortDevice(True)
greenButton.setHubPort(5)
greenButton.setIsHubPortDevice(True)
greenLED.setHubPort(4)
greenLED.setIsHubPortDevice(True)

#Open 
redButton.openWaitForAttachment(1000)
redLED.openWaitForAttachment(1000)
greenButton.openWaitForAttachment(1000)
greenLED.openWaitForAttachment(1000)
    
#Subscribe to Events 
redButton.setOnStateChangeHandler(onRedButton_StateChange)
greenButton.setOnStateChangeHandler(onGreenButton_StateChange)
    
startingNumberOfColours = 3 #int variable stating the number of colours flashed the game will start with.   
print("Starting game, look at the LEDS.")
time.sleep(1)
continueGame = True #Boolean that determines if while loop below should continue or not.
#Loop that keeps game going
while (continueGame):
    sequenceKey.clear() #Ensures the sequence is reset for each level
    #Process that determines the sequence of the flashing lights until the starting number of colours are met.
    for indexToAssign in range(startingNumberOfColours):
        sequenceKey.append(int(random.random() * 2))#Randomly generates number between 0 and 1 to append to the list sequenceKey.
    
    #A for loop that will flash an LED according to the number assigned at the current index position
    for indexToFlash in range(len(sequenceKey)):
        if sequenceKey[indexToFlash] == 0: #0 is assigned to red
            redLED.setState(True)
            time.sleep(0.5)
            redLED.setState(False)
        else: #Only other option is 1, which is assigned to green.
            greenLED.setState(True)
            time.sleep(0.5)
            greenLED.setState(False)
        time.sleep(0.5)
    
    numEvents = 0#resets number of events pressed before the user answers
    userAnswer.clear()#//clears list for the users answers before each answer
    print("Please enter your answer:")
    while (numEvents < len(sequenceKey)):#While loop that checks if the users answer is complete
        #redLED.setState(turnRedLEDOn)
        #greenLED.setState(turnGreenLEDOn)
        time.sleep(0.15)
    
    #A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user incorrect. if incorrect, the game will stop
    for indexToCompare in range(len(sequenceKey)):
        if userAnswer[indexToCompare] != sequenceKey[indexToCompare]:
            #Indicates the user got the sequence incorrect
            redLED.setState(True)
            print("Game over...")
            time.sleep(2)
            redLED.setState(False)
            continueGame = False  
            break
        
    if (continueGame): #If the user was correct on all, the game will continue
        #Indicates the user got the sequence correct
        greenLED.setState(True)
        print("Congrats!")
        time.sleep(2)
        greenLED.setState(False)
        time.sleep(1)
        print("\n" + "Starting next level...")
        time.sleep(2)
        startingNumberOfColours+=1 #Adds a colour for the next level.
        
    time.sleep(0.15)