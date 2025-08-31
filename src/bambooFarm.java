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
    // Defined outside of main to allow access for other methods.
    static byte cyclePos = 0;                      // Keeps track of clock cycle position out of 8
    static int boneMeal = 0;                       // Keeps track of if the Bamboozler has Bone Meal left
    static Scanner input = new Scanner(System.in); // Initializes new scanner to receive user input


    public static void main(String[] args) {

        boolean powerStatus = false,  // Enable EHC
                blocks = false,       // Enable crafting of "Block of Bamboo"
                planks = false,       // Enable crafting of "Bamboo Planks"
                refill = false,       // Enable refills when Bone Meal runs out
                emptyShutdown = true; // Enable the machine to shut down if empty


        powerStatus = confirmStart(powerStatus, emptyShutdown, refill); // Goes through the startup procedure and
        // sets powerStatus(true/false)
        startUpProcedure(emptyShutdown, refill);

        checkBoneMeal(emptyShutdown, refill);

        //       normalOperation();
    }
/*
    public static void normalOperation() {
        if () {

        }
    }
*/
    /*
     This method prompts the user if they would like to turn the machine on and checks if the answer is yes. Looping
     the question until an acceptable input is entered. Using a switch statement here simplifies what would be a mess
     of if else statements with a difficult to follow flow. It also simplifies the code's ability to catch the input
     that you want while making it easy to go back through the cases if you want to either double-check an input or add
     a way to readjust your answer and go back through the cases.
    */

    public static boolean confirmStart(boolean power, boolean shutdown, boolean refill) {
        System.out.print("Would you like to turn on the machine? (yes/no): ");
        String answer = input.nextLine();

        while (true) {
            switch (answer) {
                case "yes":
                    startUpProcedure(shutdown, refill);
                    System.out.println("Starting the Bamboozler with " + bambooFarm.boneMeal + " bone meal!");
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

    public static boolean startUpProcedure(boolean shutdown, boolean refill) {
        System.out.println("The system currently has " + bambooFarm.boneMeal + " Bone Meal.");
        System.out.print("1728 Bone meal will be added, but if you would like to add a specified amount, " +
                "to that please enter it here: ");
        int amount = input.nextInt();

        bambooFarm.boneMeal += amount + 1728;

        System.out.println("Adding " + (bambooFarm.boneMeal) + " Bone Meal to the system.");
        System.out.print("Would you like the Bamboozler to shutdown when empty? (yes/no): ");

        if (yesNo(input.nextLine())) {
            System.out.print("Would you like to refill the bone meal when it runs out? (yes/no): ");
            if (yesNo(input.nextLine())) {

            }
        }
        if (bambooFarm.boneMeal <= 0) {
            System.out.println("Invalid input.");
        }
        return shutdown;
    }

    // This simple method checks if the Bamboozler has bone meal and if it doesnt to go through the shutdown procedure.
    public static boolean checkBoneMeal(boolean shutdown, boolean refillStatus) {
        if (boneMeal == 0 && shutdown) {
            System.out.println("The system currently has run out of Bone Meal. ");
            shutdownProcedure();
        } else {
            return refillStatus;
            }
        return shutdown;
    }

    public static void boneMealRefill() {


        if (Objects.equals(input.nextLine(), "yes")) {
            System.out.print("How much would you like to refill? ");
            int amount = bambooFarm.boneMeal + input.nextInt();

            System.out.println(amount + " Bone Meal has been added to the system for a total of "
                    + bambooFarm.boneMeal + "Bone Meal.");
        } else  if (Objects.equals(input.nextLine(), "no")) {

        }
    }

    public static void shutdownProcedure() {
        System.out.println("The Bamboozler will now shut down. ");
        System.out.println("Shutting down the Bamboozler ...");
        System.exit(0);
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

    /* This method is another version of the switch statement but also transforms an input of "yes" and "no" into
    true and false. The difference between this style of ifs compared to the switch statement is that for simple
    choices with simple answers, it makes more sense to go with a simpler solution.*/

    // Looks for a string input of "yes" or "no" and returns the equivilent expected boolean value.
    public static boolean yesNo(String yesNo) {
        while (true) {
            if (yesNo.equals("yes")) {
                return true;
            } else if (yesNo.equals("no")) {
                return false;
            } else {
                System.out.print("Invalid input. Please try again. (yes/no): ");
            }
        }
    }
}



