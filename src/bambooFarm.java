/*
This is a relativly small program designed to emulate the workflow for the order of activation
of various redstone components for a bamboo farm in Minecraft that I'm working on.

The objective of the machine is to convert bone meal into bamboo to be used as fuel for furnaces
and various building/crafting blocks.

Actively Switched Components:
- (1) Piston - breaks the bamboo, making space available for the dispensers to repeat their task
- (4) Bone Meal Dispenser - Automatically grows the bamboo (Speed dependent on the clock)
- (2) T Flip Flop - used to increase the cycles required
- (1) Monostable Circuit - used to shorten/control a long pulse being sent to the piston, ensuring the piston
      is retracted fast enough
- (1) Etho Hopper Clock, or EHC, set to 1 item with both outputs linked,
      creating a clock that pulses the redstone wire every ~ .35 seconds
- (1) Comparator clock set to run @ signal strength 15
- (1) Crafter - Crafts 9 Bamboo -> 1 Bamboo Block

Constant throughput regardless of machine status:
- Bone meal feed
- Collection Hoppers
- Crafter

Logic Flow Map:
|___> On Switch
|   |___> Switches the clock on, cycling every ~ .35 seconds
|       |___> T Flip Flop
|       |  |___> T Flip Flop
|       |     |___> Monostable Circuit shortens the long pulse gererated by the nature of the T-Flip Flop's
|       |        |   "ON" state.
|       |        |___> Pluses the Piston every 8 clock cycles
|       |___> Bone meal dispensors are pulsed in time with the clock.
|
|___> Bamboo Collection Hoppers @4x hopper speed
|   |___> Crafter
|       |___> Auto Craft circuit determines if the crafter is full / if yes - cycle crafting clock until empty;
|             otherwise standby
|
|___> Bone meal distribution hoppers the storage input evenly into 4 streams for the 4 dispensers

*/

// Code Start
import jdk.jfr.Event;
import java.util.Objects;
import java.util.Scanner;

public class bambooFarm {

    // Global Variables
    static byte cyclePos = 0;                      // Keeps track of clock cycle position out of 8
    static int boneMeal = 0;                       // Keeps track of if the Bamboozler has Bone Meal left
    static Scanner input = new Scanner(System.in); // Initializes new scanner to receive user input
    static boolean refill = false,                 // Enable refills when Bone Meal runs out
            emptyShutdown = true;                  // Enable the machine to shut down if empty

    public static void main(String[] args) {

        // Variables
        final int CRAFT_BLOCK = 1,
                  CRAFT_PLANK = 2;
        int bambooCollected = 0,
            bambooBlocksCrafted = 0,
            bambooPlanksCrafted = 0;
        boolean powerStatus = false,  // Enable EHC
                blocks = false,       // Enable crafting of "Block of Bamboo"
                planks = false;       // Enable crafting of "Bamboo Planks"

        // main code start---------------------------------------------------------------------------------------------

        // Goes through the startup procedure and sets powerStatus(true/false)
        powerStatus = helperMethods.confirmStart(powerStatus);

        helperMethods.startUpProcedure();


        //       normalOperation();
    }
}



