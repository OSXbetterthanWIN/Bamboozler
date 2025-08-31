/*
Small program designed to emulate the workflow for the order of activation
of various redstone components for a bamboo farm in Minecraft that I'm working on.

Objective of the machine is to convert bone meal into bamboo to be used as fuel for furnaces
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

import java.util.Scanner;

public class bambooFarm {
    static byte cyclePos = 0;               // Keeps track of clock cycle position out of 8, defined outside of main
                                            // to allow access for other methods.

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);                     // Initializes new scanner to receive user input

        int boneMeal = 0;     // Keeps track of if the Bamboozler has Bone Meal left

        boolean powerStatus = false,  // Enable EHC
                blocks = false,       // Enable crafting of "Block of Bamboo"
                planks = false,       // Enable crafting of "Bamboo Planks"
                emptyShutdown = true; // Enable the machine to shutdown if empty

        powerStatus = confirmStart(input, powerStatus, boneMeal); // Goes through the startup procedure and
                                                                  // sets powerStatus(true/false)
        boneMeal = startUpProcedure(boneMeal, input);

        checkBoneMeal(boneMeal, emptyShutdown, input);

    }

    /*
     This method prompts the user if they would like to turn the machine on and checks if the answer is yes. Looping
     the question until an acceptable input is entered. Using a switch statement here simplifies what would be a mess
     of if else statements with a difficult to follow flow. It also simplifies the code's ability to catch the input
     that you want while making it easy to go back through the cases if you want to either double-check an input or add
     a way to readjust your answer and go back through.
    */

    public static boolean confirmStart(Scanner input, boolean power, int boneMeal) {
        System.out.print("Would you like to turn on the machine? (yes/no): ");
        String answer = input.nextLine();

        while (true) {
            switch (answer) {
                case "yes":
                    startUpProcedure(boneMeal, input);
                    System.out.println("Starting the Bamboozler with " + boneMeal + " bone meal!");
                    return power = true;
                case "no":
                    System.out.println("Goodbye.");
                    System.exit(0);
                default:
                    System.out.println("Invalid input.");
                    System.out.print("Would you like to turn on the machine? (yes/no): ");
                    answer = input.nextLine();
            }
        }
    }

    public static int startUpProcedure(int bonesNum, Scanner input) {
        if (bonesNum == 0) {
            System.out.println("The system currently has " + bonesNum + " Bone Meal.");
            System.out.println("1728 Bone meal will be added, but if you would like to add a specified amount, " +
                    "to that please enter it here: ");
            int amount = input.nextInt();
            System.out.println("Adding " + (amount + 1728) + " Bone Meal to the system.");
            System.out.println("Would you like the Bamboozler to shutdown when empty? (yes/no): ");


            bonesNum += amount + 1728;
            if (bonesNum <= 0) {
                System.out.println("Invalid input.");
            }
            return bonesNum;
        }
    }

    public static void checkBoneMeal(int boneMeal, boolean shutdown, Scanner input) {
        if (boneMeal == 0 && shutdown) {
            System.out.println("The system currently has run out of Bone Meal. ");
            shutdownProcedure();
        } else if (boneMeal == 0 && !shutdown) {
            System.out.println("The system currently has run out of Bone Meal. Please enter the amount " +
                    "you would like to add. Type '0' to end operations: ");
            byte newAmount = input.nextByte();
            if (newAmount == 0) {
                shutdownProcedure();
            }
        }
    }


    public static void shutdownProcedure() {

        System.out.println("The Bamboozler will now shut down. ");
        System.out.println("Shutting down the Bamboozler ...");
    }

    // Cycle position/Clock for EHC
    public static byte machineClock(byte position, byte advance) {
        for (byte i = 0; i < advance; i++) {
            position++;
            if (position == 8) {
                position = 0;
            }
        }
        return position;
    }
}