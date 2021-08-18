#Add Phidgets Library 
from Phidget22.Phidget import *
from Phidget22.Devices.DigitalInput import *
from Phidget22.Devices.DigitalOutput import *
#Required for sleep statement 
import time
#Required for random number generator
import random

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

#Lists for sequenceKey(answer key) and userAnswer
sequenceKey = []
userAnswer = []

#Process that determines the sequence of the flashing lights.
#A for loop that assigns an integer of 0 or 1 to the sequenceKey ArrayList until filled to startingNumberOfColours.
startingNumberOfColours = 3
for indexToAssign in range(startingNumberOfColours):
    #Randomly generates number between 0 and 1 to append to the list sequenceKey
    sequenceKey.append(int(random.random() * 2))
    
print("Starting game...")
continueGame = True
#Loop that keeps game going
while continueGame == True:
    #A for loop that will flash an LED according to the number assigned at the current index position
    for indexToFlash in range(len(sequenceKey)):
        if sequenceKey[indexToFlash] == 0: #0 is assigned to red
            redLED.setState(True)
            time.sleep(0.500)
            redLED.setState(False)
        else: #Only other option is 1, which is assigned to green.
            greenLED.setState(True)
            time.sleep(0.500)
            greenLED.setState(False)
        time.sleep(0.500)
    
    #Block for events code
    
    #A for loop that will take in the users answers and compare it to the sequence answer key. Determines if the user is correct or incorrect. If correct, game moves on. If incorrect, game stops.
    for indexToCompare in range(len(sequenceKey)):
        if userAnswer[indexToCompare] != sequenceKey[indexToCompare]: #Indicates the user got the sequence incorrect
            redLED.setState(True)
            time.sleep(3)
            redLED.setState(False)
            continueGame = False
            print("Game over...")
        else:
            greenLED.setState(True) #Indicates the user got the sequence correct
            time.sleep(3)
            greenLED.setState(False)
            startingNumberOfColours += 1 #Adds a colour for the next level.
            print("Starting next level...")
time.sleep(0.150)