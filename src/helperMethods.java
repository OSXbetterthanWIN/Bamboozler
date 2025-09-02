import java.util.Objects;

public class helperMethods {
    // Global Variables
    final static boolean DEFAULT_ANSWER_BOOL = true;

    // When called, takes a string input of yes|no from the user, and returns the appropriate boolean equivalent.
    // Method call - Input: N/a | Output: (return boolean)
    public static boolean yesNo() {
        while (true) {
            switch (bambooFarm.input.next().toLowerCase().charAt(0)) {
                case 'y':
                    return true;
                case 'n':
                    return false;
                default:
                    System.out.print("Invalid input. Please try again. (yes/no): ");
            }
        }
    }


    /* Cycle position/Clock for EHC
    Inputs - (byte advance) = number of cycles to advance the clock
    Output - (byte position) = cycle state <0,1,2,3,4,5,6,7>
     */
    public static byte machineClock(byte position, byte advance) {
        for (byte i = 0; i < advance; i++) {
            position++;
            if (position == 8) {
                position = 0;
            }
        }
        return position;
    }


    public static void boneMealRefill() {
        if (Objects.equals(bambooFarm.input.nextLine(), "yes")) {
            System.out.print("How much would you like to refill? ");
            int amount = bambooFarm.boneMeal + bambooFarm.input.nextInt();

            System.out.println(amount + " Bone Meal has been added to the system for a total of "
                    + bambooFarm.boneMeal + "Bone Meal.");
        } else if (Objects.equals(bambooFarm.input.nextLine(), "no")) {

        }
    }


    // This simple method checks if the Bamboozler has bone meal and if it doesnt to go through the shutdown procedure.
    public static void checkBoneMeal(boolean refillCheck) {
        if (refillCheck) {
            if (bambooFarm.boneMeal == 0) {
                System.out.println("The system currently has " + bambooFarm.boneMeal + " Bone Meal.");
                System.out.print("1728 Bone meal will be added, but if you would like to add a specified amount, " +
                        "to that please enter it here: ");
                int amount = bambooFarm.input.nextInt();
                bambooFarm.boneMeal += amount + 1728;
                System.out.println("Adding " + (bambooFarm.boneMeal) + " Bone Meal to the system.");
            }

            System.out.println("The system currently has " + bambooFarm.boneMeal + " Bone Meal.");
            System.out.print("Would you like to add to the reserves? (yes/no): ");
            if (yesNo()) {
                System.out.print ("How much would you like to add? ");
                int amount = bambooFarm.input.nextInt();
                bambooFarm.boneMeal += amount;
                System.out.println("Adding " + (bambooFarm.boneMeal) + " Bone Meal.");
                System.out.println("The system is now loaded with " + bambooFarm.boneMeal + " Bone Meal.");
            }
        }

        if (bambooFarm.boneMeal == 0) {
            shutdownProcedure();
        }
    }




    /*
    Method designed to go through machine startup procedure.
    Inputs -
    Outputs -
    */
    public static void startUpProcedure() {
        System.out.print("Would you like the Bamboozler to shutdown when empty? (yes/no): ");
        if (yesNo()) {
            System.out.print("Would you like to refill the bone meal when it runs out? (yes/no): ");
            if (!yesNo()) {
                bambooFarm.refill = true;
            }
        }
    }


    public static void shutdownProcedure() {
        System.out.println("The Bamboozler will now shut down. ");
        System.out.println("Shutting down the Bamboozler ...");
        System.exit(0);
    }


    /*
    This method prompts the user if they would like to turn the machine on and checks if the answer is yes. Looping
    the question until an acceptable input is entered.
    Inputs -
    Outputs - N/a
    */
    public static boolean confirmStart(boolean power) {
        System.out.print("Would you like to turn on the machine? (yes/no): ");
        while (true) {
            if (yesNo()) {
                startUpProcedure();
                System.out.println("Starting the Bamboozler with " + bambooFarm.boneMeal + " bone meal!");
                return power = true;
            } else  {
                System.out.println("Goodbye.");
                System.exit(0);
            }
        }

    }

    //end of class
}
