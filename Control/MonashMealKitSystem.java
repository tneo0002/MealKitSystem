package Control;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Boundary.*;
import Module.*;

/**
 * The MonashMealKitSystem class, which is the controller of this system, It is the main enchance of the software.
 *
 * @author (Hengru Jiang - 31006272)
 * @version (FIT5136, version 1 - 11 / May / 2022)
 */
public class MonashMealKitSystem {

    private UserInterface userInterface;
    private MonashMealKit monashMealKit;
    private int allergensSize = 0;

    /**
     * constructor without parameter
     */
    public MonashMealKitSystem() {
        userInterface = new UserInterface();
        monashMealKit = new MonashMealKit();
    }

    /**
     * The function of browse the meal plan.
     *
     * @return return a boolean to show whether the function works well or not
     */
    public boolean browseMealDetails() {
        boolean breakOuterLoop = false;
        while (!breakOuterLoop) {
            breakOuterLoop = true;
            System.out.println("");
            userInterface.displayMealOptions();
            monashMealKit.printMealOptions();
            System.out.println("(0)\t\tReturn to Subscribed Order Management\n");
            System.out.print("Please choose an option: ");
            int outerChoice = Integer.parseInt(getInputNumber());
            if (outerChoice <= monashMealKit.getMealList().size() && outerChoice > 0) {
                userInterface.displayMealDetails();
                Meal meal = monashMealKit.getMealList().get(outerChoice - 1);
                System.out.println("Name: " + meal.getTitle());
                System.out.println("Protein: " + meal.getSuppliedIngredients().get(0));
                Iterator<String> it = meal.getSuppliedIngredients().iterator();
                String ingredients = "";
                while (it.hasNext()) {
                    ingredients = ingredients + it.next() + "   ";
                }
                System.out.println("Ingredients: " + ingredients);
                Iterator<String> it1 = meal.getCookingSteps().iterator();
                String cookingSteps = "";
                while (it1.hasNext()) {
                    cookingSteps = cookingSteps + it1.next() + "   ";
                }
                System.out.println("Cooking steps: " + cookingSteps.substring(0, 26) + "..");
//                System.out.println("Allergen: ");
                System.out.println("\nOptions:");
                System.out.println("(1) Display another meal details");
                System.out.println("(0) Return to Subscribed Order Management");
                System.out.print("\nPlease choose an option: ");
                boolean breakInnerLoop = false;
                while (!breakInnerLoop) {
                    breakInnerLoop = true;
                    String innerChoice = getInputNumber();
                    switch (innerChoice) {
                        case "1":
                            breakOuterLoop = false;
                            break;
                        case "0":
                            return true;
                        default:
                            System.out.println("Invalid input!");
                            System.out.print("Please choose an option: ");
                            breakInnerLoop = false;
                            break;
                    }
                }
            } else if (outerChoice == 0) {
                return true;
            } else {
                System.out.println("Invalid input!");
                breakOuterLoop = false;
            }
        }
        return true;
    }

    public boolean browseMealPlan() {
        MealPlan mealPlan = new MealPlan();
        userInterface.displayMealPlanOptions();
        boolean isValid = false;
        String choice;

        while (!isValid) {
            choice = getInputNumber();
            isValid = true;
            switch (choice) {
                case "1":
                case "2":
                    mealPlan = chooseMealPlan(choice, true);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
                    System.out.print("Please choose an option: ");
                    isValid = false;
                    break;
            }
        }
        if (!mealPlan.getPlanTime().equals("")) {
            while (true) {
                userInterface.displayMealPlan(mealPlan, monashMealKit.getMondayTimes(), monashMealKit.getMealList());
                System.out.println("Options:");
                System.out.println("(1) Display the meal list");
                System.out.println("(0) Return to Subscribed Order Management");
                System.out.print("\nPlease choose an option: ");
                choice = getInputNumber();
                if (choice.equals("1")) {
                    browseMealDetails();
                } else if (choice.equals("0")) break;
                else System.out.print("Valid input! Please input again: ");
            }
        }
        return true;
    }

    /**
     * choice --> "2" --> change meal plan
     * choice --> "3" --> pay meal plan
     *
     * @param choice whether it will change or pay the meal plan
     * @return whether the function works well or not
     */

    public boolean changeAndPayMealPlan(String choice) {
        MealPlan mealPlan = chooseMealPlan("1", false);
        ArrayList<String> mondayTimes = monashMealKit.getMondayTimes();
        ArrayList<Meal> mealList = monashMealKit.getMealList();
        if (!mealPlan.getPlanTime().equals("")) {
            if (choice.equals("2")) {
                changeOneMealPlan(mealPlan, mondayTimes, mealList);
            } else if (choice.equals("3")) {
                payOneMealPlan(mealPlan);
            } else return true;
        }
        return true;
    }

    /**
     * Change the meal plan
     */
    // ToDo Needs rework or discard!
//    public void changeOheMealPlan(MealPlan mealPlan) {
//        String mealPlanDate = mealPlan.getPlanTime();
//        String nextWeekMealPlanDate = monashMealKit.getMondayTimes().get(3);
//        if (mealPlan.isPaid()) {
//            if (mealPlanDate.equals(nextWeekMealPlanDate)) {
//                System.out.println("Next week's meal plan has already been paid.");
//            } else {
//                System.out.println("The week after next week's meal plan has already been paid.");
//            }
//        } else if(!mealPlan.isPaid() && !monashMealKit.isBeforeWednesday() && mealPlanDate.equals(nextWeekMealPlanDate)) {
//            if (mealPlanDate.equals(nextWeekMealPlanDate)) {
//                System.out.println("Cutoff for next week has passed. You can no longer change next " +
//                        "week's meal plan.");
//            } else {
//                userInterface.displayDifferentWeekMealPlan("");
//                System.out.println();
//            }
//        } else if(monashMealKit.isBeforeWednesday()) {
//            if (mealPlanDate.equals(nextWeekMealPlanDate)) {
//                userInterface.displayDifferentWeekMealPlan("1");
//                System.out.println();
//            } else {
//                userInterface.displayDifferentWeekMealPlan("");
//                System.out.println();
//            }
//        }
//    }
    public boolean changeOneMealPlan(MealPlan mealPlan, ArrayList<String> mondayTimes, ArrayList<Meal> mealList) {
        userInterface.displayMealPlan(mealPlan, mondayTimes, mealList);

        System.out.println("Options:");
        System.out.println("(1)\tAdd a meal");
        System.out.println("(2)\tRemove a meal");
        System.out.println("(3)\tChange serving size");
        System.out.println("(4)\tChange protein source");
        System.out.println("(5)\tSkip next meal plan");
        System.out.println("(0) Return to Subscribed Order Management");
        System.out.print("\nPlease choose an option: ");
        boolean breakLoop = false;
        while (!breakLoop) {
            breakLoop = true;
            String choice = getInputNumber();
            switch (choice) {
                case "1":
                    userInterface.displayMealOptions();
                    monashMealKit.printMealOptions();
                    boolean breakLoop1 = false;
                    while (!breakLoop1 && mealPlan.getMealList().size() < 10) {
                        System.out.println("Which meal to add to the list?");
                        System.out.print("\nPlease choose an option: ");
                        String addChoice = getInputNumber();
                        int position = Integer.parseInt(addChoice) - 1;
                        if (position < monashMealKit.getMealList().size() && mealPlan.getMealList().size() < 10) {
                            Meal mealToAdd = monashMealKit.getMealList().get(position);
                            String servingChoice = "2";
                            String protein = mealToAdd.getSuppliedIngredients().get(0);
                            mealPlan.addMeal(mealToAdd, servingChoice, protein);
                            System.out.println("\nSuccessfully added item (" + addChoice + ")");
                            System.out.println("\nOptions:");
                            System.out.println("(1) Add another item");
                            System.out.println("(2) Return to previous page");
                            System.out.print("\nPlease choose an option: ");
                            String addAnother = getInputNumber();
                            if (!addAnother.equals("1") || mealPlan.getMealList().size() >= 10) {
                                breakLoop1 = true;
                            }
                        } else {
                            System.out.println("\nMeal plan is full!");
                            breakLoop1 = true;
                        }
                    }
                    changeOneMealPlan(mealPlan, mondayTimes, mealList);
                    break;

                case "2":
                    boolean breakLoop2 = false;
                    while (!breakLoop2 && mealPlan.getMealList().size() > 0) {
                        System.out.println("Which meal to remove from the list?");
                        System.out.print("\nPlease choose an option: ");
                        String removeChoice = getInputNumber();
                        int position = Integer.parseInt(removeChoice) - 1;
                        if (position >= 0 && position < mealPlan.getMealList().size()) {
                            mealPlan.deleteMeal(position);
                            System.out.println("\nSuccessfully deleted item (" + removeChoice + ")");
                            if (mealPlan.getMealList().size() > 0) {
                                System.out.println("\nOptions:");
                                System.out.println("(1) Remove another item");
                                System.out.println("(2) Return to previous page");
                                System.out.print("\nPlease choose an option: ");
                                String removeAnother = getInputNumber();
                                if (!removeAnother.equals("1")) {
                                    breakLoop2 = true;
                                } else {
                                    userInterface.displayMealPlan(mealPlan, mondayTimes, mealList);
                                }
                            } else {
                                breakLoop2 = true;
                            }
                        } else {
                            System.out.println("Invalid input!");
                        }
                    }
                    if (mealPlan.getMealList().size() == 0) {
                        System.out.println("The meal plan is empty!");
                    }
                    changeOneMealPlan(mealPlan, mondayTimes, mealList);
                    break;

                case "3":
                    boolean breakLoop3 = false;
                    while (!breakLoop3 && mealPlan.getMealList().size() > 0) {
                        System.out.println("Which meal would you like to change the serving size?");
                        System.out.print("\nPlease choose an option: ");
                        String servingOf = getInputNumber();
                        int position = Integer.parseInt(servingOf) - 1;
                        if (position >= 0 && position < mealPlan.getMealList().size()) {
                            System.out.println("What is the serving size would you like?\n2 or 4");
                            String servingInput = getInputNumber();
                            switch (servingInput) {
                                case "2":
                                    mealPlan.getServingNo().add(position, "2");
                                    System.out.println("\nMeal No. " + (position + 1) +
                                            " has been successfully changed to 2 servings");
                                    breakLoop3 = true;
                                    break;
                                case "4":
                                    mealPlan.getServingNo().add(position, "4");
                                    System.out.println("\nMeal No. " + (position + 1) +
                                            " has been successfully changed to 4 servings");
                                    breakLoop3 = true;
                                    break;
                                default:
                                    System.out.println("Invalid input!");
                                    break;
                            }
                        } else {
                            System.out.println("Invalid input");
                            System.out.println("The meal plan has less than " + position + 1 + " item.");
                            userInterface.displayMealPlan(mealPlan, mondayTimes, mealList);
                        }
                    }
                    changeOneMealPlan(mealPlan, mondayTimes, mealList);
                    break;

                case "4":
                    boolean breakLoop4 = false;
                    while (!breakLoop4 && mealPlan.getMealList().size() > 0) {
                        breakLoop4 = true;
                        System.out.println("Which meal would you like to change the protein source?");
                        System.out.print("\nPlease choose an option: ");
                        String proteinOf = getInputNumber();
                        int position = Integer.parseInt(proteinOf) - 1;
                        if (position >= 0 && position < mealPlan.getMealList().size()) {
                            String selectedMealProtein = mealPlan.getProtein().get(position);

                            String defaultProtein = mealPlan.getMealList().get(position).getSuppliedIngredients().get(0);
                            //System.out.println(selectedMealProtein + defaultProtein);
                            System.out.println("The current protein source is " + selectedMealProtein);
                            if (defaultProtein.equals("Beef") || defaultProtein.equals("Lamb") ||
                                    defaultProtein.equals("Chicken") || defaultProtein.equals("Fish")) {
                                System.out.println("Which protein source would you like?\n" +
                                        "(1) Beef\n(2) Lamb\n(3) Chicken\n(4) Fish\n(5) Pork");
                                System.out.print("Please choose an option: ");
                                String proteinChoice = getInputNumber();
                                switch (proteinChoice) {
                                    case "1":
                                        mealPlan.getProtein().set(Integer.parseInt(proteinOf) - 1, "Beef");
                                        break;
                                    case "2":
                                        mealPlan.getProtein().set(Integer.parseInt(proteinOf) - 1, "Lamb");
                                        break;
                                    case "3":
                                        mealPlan.getProtein().set(Integer.parseInt(proteinOf) - 1, "Chicken");
                                        break;
                                    case "4":
                                        mealPlan.getProtein().set(Integer.parseInt(proteinOf) - 1, "Fish");
                                        break;
                                    case "5":
                                        mealPlan.getProtein().set(Integer.parseInt(proteinOf) - 1, "Pork");
                                        break;
                                    default:
                                        System.out.println("Invalid input!");
                                        System.out.print("Please choose an option: ");
                                        breakLoop4 = false;
                                        break;
                                }
                            } else {
                                System.out.println("This is a vegetarian meal");
                            }
                        } else {
                            System.out.println("There are less than " + (position + 1) + " meals in the meal plan");
                        }
                    }
                    changeOneMealPlan(mealPlan, mondayTimes, mealList);
                    break;

                case "5":
                    mealPlan.setIsSkipped();
                    System.out.println("The next meal plan has been skipped.");

                case "0":
                    breakLoop = true;
                    break;

                default:
                    System.out.println("Invalid input!");
                    System.out.print("Please choose an option: ");
                    breakLoop = false;
                    break;
            }
        }
        return true;
    }


    /**
     * A shard part of Browse/Change/Pay meal plan function,
     *
     * @param choice   1 --> upcoming meal plan, 2 --> past meal plan
     * @param isBrowse true --> will not be allowed to check the previous meal plan
     * @return show the function works well
     */
    public MealPlan chooseMealPlan(String choice, boolean isBrowse) {
        MealPlan mealPlan = new MealPlan();
        int mealPlanIndex = 0;
        boolean isRunning = true;
        while (isRunning) {
            userInterface.displayMealPlanSubOptions(choice, isBrowse);
            boolean isValid = false;
            String subChoice = "5";
            //If the isBrowse is false, the choice should be "1" to browse the upcoming meal plan
            //0 1 2 3 4
            while (!isValid) {
                subChoice = getInputNumber();
                isValid = true;
                isRunning = false;
                if (choice.equals("1")) {
                    switch (subChoice) {
                        case "1":
                            mealPlanIndex = 3;
                            mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                            if (!isBrowse) {
                                if (!monashMealKit.isBeforeWednesday()) {
                                    System.out.println("Today is after Tuesday.");
                                    System.out.println("You can only book the meal plan of the week after next week");
                                } else if (mealPlan.getIsPaid().equals("true")) {
                                    System.out.println("This meal plan has already been paid, the request rejected.");
                                } else if (mealPlan.getIsSkipped().equals("true")) {
                                    System.out.println("This meal plan has already been skipped, the request rejected.");
                                } else break;
                                System.out.print("Please choose an option: ");
                                isValid = false;
                                mealPlan = new MealPlan();
                            }
                            break;
                        case "2":
                            mealPlanIndex = 4;
                            mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                            if (!isBrowse) {
                                if (mealPlan.getIsPaid().equals("true")) {
                                    System.out.println("This meal plan has already been paid, the request rejected.");
                                } else if (mealPlan.getIsSkipped().equals("true")) {
                                    System.out.println("This meal plan has already been skipped, the request rejected.");
                                } else break;
                                System.out.print("Please choose an option: ");
                                isValid = false;
                                mealPlan = new MealPlan();
                            }
                            break;
                        case "3":
                            if (!isBrowse) {
                                System.out.println("Invalid input!");
                                System.out.print("Please choose an option: ");
                                isValid = false;
                                break;
                            }
                            //choice = "2";
                            choice = "2";
                            isRunning = true;
                            break;
                        case "0":
                            //Go back
                            break;
                        default:
                            System.out.println("Invalid input!");
                            System.out.print("Please choose an option: ");
                            isValid = false;
                            break;
                    }
                } else {
                    switch (subChoice) {
                        case "1":
                            mealPlanIndex = 2;
                            mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                            break;
                        case "2":
                            mealPlanIndex = 1;
                            mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                            break;
                        case "3":
                            mealPlanIndex = 0;
                            mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                            break;
                        case "4":
                            choice = "1";
                            isRunning = true;
                            break;
                        case "0":
                            //Go back
                            break;
                        default:
                            System.out.println("Invalid input!");
                            System.out.print("Please choose an option: ");
                            isValid = false;
                            break;
                    }
                }
                if (isValid) {
                    if (!mealPlan.getPlanTime().equals("")) {
                        userInterface.displayMealPlan(mealPlan, monashMealKit.getMondayTimes(), monashMealKit.getMealList());
                        System.out.println("Options:");
                        System.out.println("(1) Display a past week");
                        System.out.println("(2) Display a next week");
                        System.out.println("(0) Confirm choosing");
                        System.out.print("\nPlease choose an option: ");
                        int leftBoundary = 0;
                        int rightBoundary = 4;
                        if (!isBrowse) {
                            leftBoundary = 3;
                            if (!monashMealKit.isBeforeWednesday()) leftBoundary = 4;
                        }
                        while (true) {
                            String input = getInputNumber();
                            if (input.equals("1")) {
                                if (mealPlanIndex == leftBoundary) {
                                    System.out.println("No more past meal plan.");
                                    System.out.print("Please input again: ");
                                    continue;
                                } else {
                                    mealPlanIndex--;
                                    mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                                }
                            } else if (input.equals("2")) {
                                if (mealPlanIndex == rightBoundary) {
                                    System.out.println("No more upcoming meal plan.");
                                    System.out.print("Please input again: ");
                                    continue;
                                } else {
                                    mealPlanIndex++;
                                    mealPlan = monashMealKit.getMealPlanList().get(mealPlanIndex);
                                }
                            } else break;
                            userInterface.displayMealPlan(mealPlan, monashMealKit.getMondayTimes(), monashMealKit.getMealList());
                            System.out.println("\n\nOptions:");
                            System.out.println("(1) Display a past week");
                            System.out.println("(2) Display a next week");
                            System.out.println("(0) Confirm choosing");
                            System.out.print("\nPlease choose an option: ");
                        }
                    }
                }
            }
        }
        return mealPlan;
    }

    /**
     * change the meal plan
     */
    public void payOneMealPlan(MealPlan mealPlan) {
        userInterface.displayMealPlan(mealPlan, monashMealKit.getMondayTimes(), monashMealKit.getMealList());
        boolean isValid = false;
        String choice;
        System.out.println("Options:");
        System.out.println("(1) Pay for this mealPlan");
        System.out.println("(0) Return to Subscribed Order Management");
        System.out.print("\nPlease choose an option: ");
        while (!isValid) {
            choice = getInputNumber();
            isValid = true;
            switch (choice) {
                case "1":
                    String isPaid = mealPlan.getIsPaid();
                    if (isPaid.equals("false")) {
                        mealPlan.setIsPaid("true");
                        userInterface.displayPayment(mealPlan, monashMealKit.getPayment());
                        if (getInputNumber().equals(0)) break;
                    } else System.out.println("Error! Something wrong on payment work flow.");
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
                    System.out.print("Please choose an option: ");
                    isValid = false;
                    break;
            }
        }
    }

    /**
     * @return boolean
     */
    public boolean subscribedOrderManagement() {
        boolean isValid;
        String choice;

        while (true) {
            userInterface.displaySubscribedOrderManagementScreen();
            isValid = false;
            while (!isValid) {
                choice = getInputNumber();
                isValid = true;
                switch (choice) {
                    case "1":
                        browseMealPlan();
                        break;
                    case "2":
                    case "3":
                        changeAndPayMealPlan(choice);
                        break;
                    case "4":
                        browseMealDetails();
                        break;
                    case "0":
                        return true;
                    default:
                        System.out.println("Invalid input!");
                        System.out.print("Please choose an option: ");
                        isValid = false;
                        break;
                }
            }
        }
    }

    public boolean printMealOptions(int increment, ArrayList<Meal> mealList) {
        for (int idx = 0; idx < mealList.size(); idx++) {
            int runningNumber = idx + 1;
            if (runningNumber < 10) {
                System.out.print("(" + runningNumber + ")" + "\t\t");
            } else if (runningNumber < 100000) {
                System.out.print("(" + runningNumber + ")" + "\t");
            }
            System.out.println(mealList.get(idx).getTitle());
        }
        return true;
    }

//    private boolean printResults(ArrayList<ArrayList<String>> data) {
//        for (int idx = 0; idx < data.size() / 3; idx++) {
//            int runningNumber = idx + 1;
//            if (runningNumber < 10) {
//                System.out.print("(" + runningNumber + ")" + "\t\t");
//            } else if (runningNumber < 100000) {
//                System.out.print("(" + runningNumber + ")" + "\t");
//            }
//            ArrayList<String> line = data.get(idx * 3);
//            System.out.print(line.get(1));
//        }
//        return true;
//    }

    public String getInputNumber() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                int countNumber = 0;
                char saveChar;
                for (int i = 0; i < input.length(); i++) {
                    saveChar = input.charAt(i);
                    if (Character.isDigit(saveChar)) countNumber++;
                }
                if (countNumber == input.length()) break;
            }
            System.out.println("Invalid input!");
            System.out.print("Please choose an option: ");
        }
        return input;
    }

    /**
     * The main management function of this system.
     */
    public void runSystem() {
        selection(1);
        System.out.println("Thanks for using! Hope you enjoy the meals!");
        writeFileToMealPlan();
    }

    public void writeFileToMealPlan() {
        ArrayList<MealPlan> customerMealPlanList = monashMealKit.getMealPlanList();
        String fileName = "./Data/" + monashMealKit.getCustomer().getReferralCode() + ".csv";
        try {
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int idx = 0; idx < customerMealPlanList.size(); idx++) {
                MealPlan mealPlan = customerMealPlanList.get(idx);
                String plan = mealPlan.getPlanTime() +
                        "," + mealPlan.getIsPaid() +
                        "," + mealPlan.getIsSkipped();
                for (int idy = 0; idy < customerMealPlanList.get(idx).getMealList().size(); idy++) {
                    Meal meal = mealPlan.getMealList().get(idy);
                    String numberServingProtein = "," + meal.getRecipeNo() +
                            "," + mealPlan.getServingNo().get(idy) +
                            "," + mealPlan.getProtein().get(idy);
                    plan += numberServingProtein;
                }
                outputFile.println(plan);
            }
            outputFile.close();
        } catch (IOException e) {
            System.out.println("Fail to write content to " + fileName);
        }
    }


    public MonashMealKit getMonashMealKit() {
        return monashMealKit;
    }

    public boolean isCustomer(String customerEmail) {
        ArrayList<String> customerEmails = MonashMealKit.getListOfCustomerEmail();
        int idx = 0;
        while (idx < customerEmails.size()) {
            if (customerEmails.get(idx).equals(customerEmail)) {
                return true;
            }
            idx++;
        }
        return false;
    }

    /**
     * Used to help system ensure whether the input is an english letter
     */
    public boolean isAlphabetic(String input) {
        if (!input.trim().isEmpty()) {
            char firstCharacter = input.trim().charAt(0);
            return Character.isLetter(firstCharacter);
        }
        return false;
    }

    /**
     * Used to help system ensure whether the input is number
     */
    public boolean isNumeric(String input) {
        input = input.trim();
        if (input.length() > 0) {
            int countNumber = 0;
            char saveChar;
            for (int i = 0; i < input.length(); i++) {
                saveChar = input.charAt(i);
                if (Character.isDigit(saveChar)) countNumber++;
            }
            if (countNumber == input.length()) return true;
        }
        System.out.println("Invalid input!");
        return false;
    }

    public boolean checkEmailAddressExist(String emailAddress) {
        ArrayList<String> checkEmailList = new ArrayList<String>();
        try {
            FileReader customerInfo = new FileReader("./Data/customer.txt");
            Scanner console = new Scanner(customerInfo);
            do {
                String eachCustomer = console.nextLine();
                String[] customerInfoList = eachCustomer.split(",");
                String eachEmailAddress = customerInfoList[0];
                checkEmailList.add(eachEmailAddress);
            } while (console.hasNextLine());
            for (int index = 0; index < checkEmailList.size(); index++) {
                if (emailAddress.trim().equals(checkEmailList.get(index))) {
                    return true;
                }
                //System.out.println(checkEmailList.get(index));
            }
        } catch (FileNotFoundException exception) {
            System.out.println("File not found");
        }
        return false;
    }

    public boolean checkEmailAddressValid(String emailAddress) {
        String emailContent = "student.monash.edu";
        char x = '@';
        for (int i = 0; i < emailAddress.length(); i++) {
            if (emailAddress.charAt(i) == x) {
                if (emailAddress.trim().split("@")[0].length() > 0 && emailAddress.trim().split("@")[1].equals(emailContent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPasswordValid(String password) {
        boolean x = false;
        boolean y = false;
        if (password.trim().length() < 16 && password.trim().length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                boolean containNumber = Character.isDigit(password.charAt(i));
                boolean containUpperCase = Character.isUpperCase(password.charAt(i));
                if (containNumber)
                    x = true;
                if (containUpperCase)
                    y = true;
            }
            if (x && y) {
                return true;
            }
        }
        return false;
    }

    public boolean emailPasswordMatch(String emailAddress, String password) {
        ArrayList<String> checkEmailList = new ArrayList<String>();
        ArrayList<String> checkPasswordList = new ArrayList<String>();
        ArrayList<String> userFnameList = new ArrayList<String>();
        ArrayList<String> userLnameList = new ArrayList<String>();
        ArrayList<String> dateOfBirthList = new ArrayList<String>();
        ArrayList<String> genderList = new ArrayList<String>();
        ArrayList<String> mobileNumberList = new ArrayList<>();
        ArrayList<String> referralCodeList = new ArrayList<>();
        try {
            File customerInfo = new File("./Data/customer.txt");
            Scanner console = new Scanner(customerInfo);
            do {
                String eachCustomer = console.nextLine();
                String[] customerInfoList = eachCustomer.split(",");
                String eachEmailAddress = customerInfoList[0];
                String eachPassword = customerInfoList[1];
                checkEmailList.add(eachEmailAddress);
                checkPasswordList.add(eachPassword);
                userFnameList.add(customerInfoList[2]);
                userLnameList.add(customerInfoList[3]);
                dateOfBirthList.add(customerInfoList[4]);
                genderList.add(customerInfoList[5]);
                mobileNumberList.add(customerInfoList[6]);
                referralCodeList.add(customerInfoList[7]);
            } while (console.hasNextLine());
        } catch (Exception e) {
            System.out.println("Please check the file named customer.txt1");
        }
        for (int index = 0; index <= checkEmailList.size() - 1; index++) {
            if (emailAddress.trim().equals(checkEmailList.get(index)) && password.trim().equals(checkPasswordList.get(index)))
            {
                monashMealKit.setCustomer(new Customer(checkEmailList.get(index),checkPasswordList.get(index),userFnameList.get(index),userLnameList.get(index),dateOfBirthList.get(index),genderList.get(index),mobileNumberList.get(index),referralCodeList.get(index),false));
                return true;
            }
        }
        return false;
    }

    public boolean checkPasswordConsistent(String passwordFirst, String passwordSecond) {
        return passwordFirst.trim().equals(passwordSecond.trim());
    }

    public boolean checkReferralCode(String finalReferralCode) {
        ArrayList<String> checkReferralCodeList = new ArrayList<String>();
        try {
            FileReader customerInfo = new FileReader("./Data/customer.txt");
            Scanner console = new Scanner(customerInfo);
            do {
                String eachCustomer = console.nextLine();
                String[] customerInfoList = eachCustomer.split(",");
                String eachReferralCode = customerInfoList[7];
                checkReferralCodeList.add(eachReferralCode);

            } while (console.hasNextLine());
        } catch (Exception e) {
            System.out.println("Please check the file named customer.txt");
        }

        for (int i = 0; i < checkReferralCodeList.size(); i++) {
            if (finalReferralCode.trim().equals(checkReferralCodeList.get(i)))
                return true;
        }
        //System.out.println("This referral code doesn't exist.");
        return false;
    }

    public String generateUniqueReferralCode() {

        StringBuffer referralCode = new StringBuffer();
        do {
            int item = (int) ((10) * Math.random());
            referralCode.append(item);
        } while (referralCode.length() < 4);
        String code = "";
        do {
            code = referralCode.toString();

        } while (checkReferralCode(code));
        return code;
    }

    public void saveNewCustomer(String inputEmailAddress, String inputPasswordSecond, String userFname,
                                String userLname, String dateOfBirth, String gender, String mobileNumber, String personalReferralCode, boolean hasDiscount) {
        try {
            FileWriter newWriter = new FileWriter(".\\Data\\customer.txt", true);
            PrintWriter newCustomer = new PrintWriter(newWriter);
            newCustomer.println(inputEmailAddress + "," + inputPasswordSecond + "," + userFname + "," + userLname + "," + dateOfBirth + "," + gender + "," + mobileNumber + "," + personalReferralCode + "," + hasDiscount);
            newCustomer.close();
        } catch (Exception e) {
            System.out.println("There are some issues in you file, please check file customer.txt.");
        }
    }

    public void savePaymentWay(String cardType, String emailAddress, String cardHolderName, String creditCardNumber, String expirationDate, String ccv) {
        try {
            FileWriter newWriter = new FileWriter(".\\Data\\payment.txt", true);
            PrintWriter newCustomer = new PrintWriter(newWriter);
            newCustomer.println(cardType + "," + emailAddress + "," + cardHolderName + "," + creditCardNumber + "," + expirationDate + "," + ccv);
            newCustomer.close();
        } catch (Exception e) {
            System.out.println("Error, maybe payment.txt is not exist");
        }
    }

    //-----------------------------------------------------------------------------------------------
    public void selection(int menuIndex) {
        if (menuIndex == 1)
            welcome();
        if (menuIndex == 2)
            login();
        if (menuIndex == 3)
            loginWrongInput();
        if (menuIndex == 4)
            signUp();
        if (menuIndex == 5)
            invalidEmailInput();
        if (menuIndex == 6)
            emailAlreadyExist();
        if (menuIndex == 7)
            signUpPassword();
        if (menuIndex == 8)
            invalidPasswordInput();
        if (menuIndex == 9)
            pwdTwiceInvalid();
        if (menuIndex == 10)
            mainPage();
        if (menuIndex == 11)
            invalid();
        if (menuIndex == 12)
            exit();
        if (menuIndex == 13)
            getMealPlanByDate();
    }

    //Process and check input------------------------------
    public String getInput() {
        Scanner s = new Scanner(System.in);
        return s.nextLine().trim();
    }

    private boolean isOptionNumber(String s, char rangex, char rangey) {
        if (s.length() != 1)
            return false;
        char x = s.charAt(0);
        return x >= rangex && x <= rangey;
    }

    private boolean isAllNumeric(String s) {
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);
            if (x < '0' || x > '9')
                return false;
        }
        return true;
    }

    private boolean isAllLetter(String s) {
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(0);
            if (x < 'A' || x > 'z')
                return false;
        }
        return true;
    }

    private boolean nameCheck(String name) {
        if (!isAllLetter(name)) {
            System.out.println("Please enter letter");
            return false;
        }
        return true;
    }

    //need for improve
    private boolean checkDOB(String DOB) {
        if (!isAllNumeric(DOB)) {
            System.out.println("It should be digits");
            return false;
        }
        if (DOB.equals("01020222"))
            return true;
        return true;
    }

    private boolean genderCheck(String gender) {
        if (!isAllLetter(gender))
            return false;
        return gender.equals("male") || gender.equals("female");
    }

    private boolean phoneCheck(String phone) {
        if (!isAllNumeric(phone))
            return false;
        else if (phone.length() != 10) {
            System.out.println("The phone number should be 10 digits");
            return false;
        }
        return true;
    }

    private boolean codeCheck(String code) {
        if (!isAllNumeric(code))
            return false;
        else if (code.length() != 4) {
            System.out.println("The referral code should be 4 digits");
            return false;
        }
        return true;
    }

    private boolean cardTypeCheck(String type) {
        if (!isAllLetter(type)) {
            System.out.println("The card type check should be credit or debit");
            return false;
        }
        return type.equals("credit") || type.equals("debit");
    }

    private boolean cardNumberCheck(String number) {
        if (!isAllNumeric(number)) {
            System.out.println("The card number should be digits");
            return false;
        } else if (number.length() != 16) {
            System.out.println("The card number should be 16 digits");
            return false;
        }
        return true;
    }

    //Not completed
    private boolean cardDateCheck(String date) {
        return true;
    }

    private boolean ccvCheck(String ccv) {
        if (!isAllNumeric(ccv)) {
            System.out.println("The referral code should be digits");
            return false;
        } else if (ccv.length() != 3) {
            System.out.println("The referral code should be 3 digits");
            return false;
        }
        return true;
    }

    private boolean customerAllergensCheck(String allergensList) {
        //allergensList:customer allergens input
        if (isAllNumeric(allergensList)) {
            System.out.println("Please use comma to separate");
            return false;
        }
        if (!isAllLetter(allergensList)) {
            System.out.println("Please input the index e.g. 1,5,9");
            return false;
        }
        return true;
    }

    //maximum number within a string(e.g.   1,5,7,13   max is:13 ), if allergensSize=10, then max is out of range
    public boolean checkAllergensInputMaxNumber(String s) {
        int max = 0;
        String[] ss = s.split(",");
        for (int i = 0; i < ss.length; i++) {
            if (max < Integer.parseInt(ss[i])) {
                max = Integer.parseInt(ss[i]);
            }
        }
        if (max > allergensSize) {
            System.out.println("Index out of range, the index should between 1-" + allergensSize);
            return false;
        }
        return true;
    }

    //-----------------------------------------------------------------------------
    private void welcome() {
        userInterface.welcomeScreen();
        String input = getInput();
        boolean x = isOptionNumber(input, '0', '2');
        if (x && input.equals("1")) {
            login();
        } else if (x && input.equals("2")) {

            signUp();
        } else if (x && input.equals("0"))
            exit();
        else {
            System.out.println("error input:The input should be 0/1/2");
            welcome();
        }
    }

    private void login() {
        userInterface.loginScreen();
        String input = getInput();
        boolean x = checkEmailAddressExist(input);
        if (x) {
            System.out.println("please enter your account password: ");
            String pwd = getInput();
            boolean y = emailPasswordMatch(input, pwd);
            //----
            if (y)
                mainPage();
            else
                loginWrongInput();
        } else if (input.equals("0"))
            exit();
        else {
            System.out.println("please input correct email address(enter 0 to exit):");
            login();
        }
    }

    private void loginWrongInput() {
        userInterface.loginWrongInputScreen();
        login();
    }

    public void signUp() {
        userInterface.signUpScreen();
        String inputEmailAddress = getInput();
        if (checkEmailAddressValid(inputEmailAddress)) {
            if (!checkEmailAddressExist(inputEmailAddress)) {
                System.out.println("Please enter your password");
                String inputPasswordFirst = getInput();
                if (checkPasswordValid(inputPasswordFirst)) {
                    System.out.println("Please enter your password again for double check");
                    String inputPasswordSecond = getInput();
                    if (checkPasswordConsistent(inputPasswordFirst, inputPasswordSecond))
                        enterProfile(inputEmailAddress, inputPasswordSecond);
                    else {
                        pwdTwiceInvalid();
                        inputPasswordSecond = getInput();
                        boolean z = checkPasswordConsistent(inputPasswordFirst, inputPasswordSecond);
                        if (z)
                            enterProfile(inputEmailAddress, inputPasswordFirst);
                        else {
                            System.out.println("Inconsistent password before and after");
                            System.out.println("\n\nOptions:");
                            System.out.println("(1)Back to sign up page");
                            System.out.println("(0)Exit");
                            System.out.print("\nPlease choose an option: ");
                            String input = getInput();
                            boolean isNumber = isOptionNumber(input, '0', '1');
                            if (isNumber && input.equals("1"))
                                signUp();
                            else if (isNumber && input.equals("0"))
                                exit();
                            else {
                                System.out.println("Invalid input, automatically return to the welcome interface");
                                welcome();
                            }
                        }
                    }
                } else {
                    invalidPasswordInput();
                    signUp();
                }
            } else {
                emailAlreadyExist();
            }
        } else {
            invalidEmailInput();
            signUp();
        }
    }

    private void signUpPassword() {
        userInterface.signUpPasswordScreen();
    }

    public void enterProfile(String inputEmailAddress, String inputPasswordSecond) {
        userInterface.enterProfileScreen();
        String userFname = "";
        String userLname = "";
        String dateOfBirth = "";
        String gender = "";
        String mobileNumber = "";
        String referralCode = "";
        boolean hasDiscount = false;
        String cardType = null;
        String cardHolderName = userFname + " " + userLname;
        String creditCardNumber = "";
        String expirationDate = "";
        String ccv = "";
        ArrayList<String> allergensList = new ArrayList<>();
        String customerAllergens = null;

        System.out.println("Your first name:");
        do {
            userFname = getInput();
        } while (!nameCheck(userFname));
        System.out.println("Your first name is:" + userFname);

        System.out.println("Your last name:");
        do {
            userLname = getInput();
        } while (!nameCheck(userLname));
        System.out.println("Your last name is:" + userLname);

        System.out.println("Your date of birth:");
        do {
            dateOfBirth = getInput();
        } while (!checkDOB(dateOfBirth));
        System.out.println("Your date of birth is:" + dateOfBirth);

        System.out.println("Your gender:");
        do {
            gender = getInput();
        } while (!genderCheck(gender));
        System.out.println("Your gender is:" + gender);

        System.out.println("Your phone number:");
        do {
            mobileNumber = getInput();
        } while (!phoneCheck(mobileNumber));
        System.out.println("Your mobile number is:" + mobileNumber);

        System.out.println("Referral code you will be using: ");
        do {
            referralCode = getInput();
        } while (!codeCheck(referralCode) || !checkReferralCode(referralCode));
        System.out.println("The referralCode you use is:" + referralCode);

        hasDiscount = true;
        System.out.println("You will have a discount for your first meal");

        String personalReferralCode = generateUniqueReferralCode();
        System.out.println("Your personal referralCode is :" + personalReferralCode);

        //payment method------------------------------
        System.out.println("Please input your card type:");
        do {
            cardType = getInput();
        } while (!cardTypeCheck(cardType));
        System.out.println("Your card type is:" + cardType);

        System.out.println("Please input your credit card number:");
        do {
            creditCardNumber = getInput();
        } while (!cardNumberCheck(creditCardNumber));
        System.out.println("Your credit card number is:" + creditCardNumber);

        System.out.println("Please input your card expiration date:");
        do {
            expirationDate = getInput();
        } while (!checkDOB(expirationDate));
        System.out.println("Your card expiration date is:" + expirationDate);

        System.out.println("Please input your card ccv:");
        do {
            ccv = getInput();
        } while (!ccvCheck(ccv));
        System.out.println("Your card ccv is:" + ccv);

        //let user enter allergens
        System.out.println("Please enter the index to choose your allergens,use comma to separate---e.g.  1,5,9");
        allergensList = readAllergensList();
        String allergen = null;
        for (int i = 0; i < allergensList.size(); i++) {
            allergen = allergensList.get(i);
            System.out.println((i + 1) + ":" + allergen);
        }
        do {
            customerAllergens = getInput();
        } while (customerAllergensCheck(customerAllergens));
        System.out.println("Allergen indexes you selected are:" + customerAllergens);


        createCustomerAllergensFile(inputEmailAddress, customerAllergens);

        saveNewCustomer(inputEmailAddress, inputPasswordSecond, userFname,
                userLname, dateOfBirth, gender, mobileNumber, personalReferralCode, hasDiscount);

        monashMealKit.setCustomer(new Customer(inputEmailAddress, inputPasswordSecond, userFname,
                userLname, dateOfBirth, gender, mobileNumber, personalReferralCode, hasDiscount));

        savePaymentWay(cardType, inputEmailAddress, cardHolderName, creditCardNumber, expirationDate, ccv);

        monashMealKit.setPayment(new Payment(cardType, inputEmailAddress, cardHolderName, creditCardNumber, expirationDate, ccv));

        monashMealKit.generateDefaultMealPlan(5);
        System.out.println("Account created successfully, return to the start interface");
        welcome();
    }

    private void invalidEmailInput() {
        userInterface.invalidEmailInputScreen();
        signUp();
    }

    private void emailAlreadyExist() {
        userInterface.emailAlreadyExistScreen();
        signUp();
    }

    private void invalidPasswordInput() {
        userInterface.invalidPasswordInputScreen();
    }

    private void pwdTwiceInvalid() {
        userInterface.pwdTwiceInvalidScreen();
    }

    private boolean mainPage() {
        boolean isValid;
        String choice;
        while (true) {
            userInterface.mainScreen();
            isValid = false;
            while (!isValid) {
                choice = getInputNumber();
                isValid = true;
                switch (choice) {
                    case "1":
                        subscribedOrderManagement();
                        break;
                    case "2":
                        browseMealDetails();
                        break;
                    case "3":
                        System.out.println("Function developing...");
                        break;
                    case "4":
                        browseMealDetails();
                        break;
                    case"5":
                        getMealPlanByDate();
                        break;
                    case "0":
                        return true;
                    default:
                        System.out.println("Invalid input!");
                        System.out.print("Please choose an option: ");
                        isValid = false;
                        break;
                }
            }
        }
    }

    private void invalid() {
        userInterface.invalidScreen();
        String input = getInput();
        boolean x = isOptionNumber(input, '0', '2');
        if (x && input.equals("1"))
            mainPage();
        else if (x && input.equals("0"))
            exit();
        else System.out.println("error");
    }

    private void exit() {
        userInterface.exitScreen();
    }

    public Date stringToDate(String needToTransfer) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(needToTransfer);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getMealPlanByDate() {

        System.out.println("You can use this function to calculate the number of meals you have purchased over a period of time");
        System.out.println("Please follow the guide of statement.");
        String inputStartYear = "";
        do {
            System.out.println("Please enter the startDate, the time of year, We can only support 2022 queries right now.");
            inputStartYear = getInput();
        } while (!inputStartYear.trim().equals("2022") || inputStartYear.length() == 0 || !isAllNumeric(inputStartYear) || inputStartYear.equals(" "));

        String inputStartMonth = "";
        do {
            System.out.println("Please enter the startDate, the time of month (1 to 12).");
            inputStartMonth = getInput();
        } while (!checkValidMounth(inputStartMonth) || inputStartMonth.length() == 0 || !isAllNumeric(inputStartMonth) || inputStartMonth.equals(" "));

        String inputStartDay = "";
        do {
            System.out.println("Please enter the startDate, the time of day (1 to 30).");
            inputStartDay = getInput();
        } while (!checkValidDay(inputStartMonth, inputStartDay) || inputStartDay.length() == 0 || !isAllNumeric(inputStartDay) || inputStartDay.equals(" "));

        String startDate = inputStartYear + "-" + inputStartMonth + "-" + inputStartDay;
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
        String inputEndYear = "";
        do {
            System.out.println("Please enter the endDate, the time of year, We can only support 2022 queries right now.");
            inputEndYear = getInput();
        } while (!inputEndYear.trim().equals("2022") || inputEndYear.length() == 0 || !isAllNumeric(inputEndYear) || inputEndYear.equals(" "));

        String inputEndMonth = "";
        do {
            System.out.println("Please enter the startDate, the time of month (1 to 12).");
            inputEndMonth = getInput();
        } while (!checkValidMounth(inputEndMonth) || inputEndMonth.length() == 0 || !isAllNumeric(inputEndMonth) || inputEndMonth.equals(" "));

        String inputEndDay = "";
        do {
            System.out.println("Please enter the startDate, the time of day (1 to 30).");
            inputEndDay = getInput();
        } while (!checkValidDay(inputEndMonth, inputEndDay) || inputEndDay.length() == 0 || !isAllNumeric(inputEndDay) || inputEndDay.equals(" "));

        String endDate = inputEndYear + "-" + inputEndMonth + "-" + inputEndDay;

        MonashMealKit s = new MonashMealKit();
        Date startDateWeekMondayFormatDate = s.getThisWeekMonday(stringToDate(startDate));
        Date endDateWeekMondayFormatDate = s.getThisWeekMonday(stringToDate(endDate));
        int amountOfWeekMealPlan = monashMealKit.getMealPlanList().size();
        int totalNumberMealForTime = 0;
        for (int index = 0; index < amountOfWeekMealPlan; index++) {
            String planTimeMonday = monashMealKit.getMealPlanList().get(index).getPlanTime();
            Date planTimeFormatDate = stringToDate(planTimeMonday);
            int amountOfMealThisWeek = monashMealKit.getMealPlanList().get(index).getServingNo().size();
            String statusOfPay = monashMealKit.getMealPlanList().get(index).getIsPaid();
            if (isEffectiveDate(planTimeFormatDate, startDateWeekMondayFormatDate, endDateWeekMondayFormatDate) && statusOfPay.trim().equals("true")) {
                totalNumberMealForTime = totalNumberMealForTime + amountOfMealThisWeek;
            }

        }

        System.out.println("You have ordered a total of " + totalNumberMealForTime + " meals during this time");

    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkValidMounth(String inputMonth) {
        for (int index = 1; index <= 12; index++) {
            String number = String.valueOf(index);
            if (inputMonth.trim().equals(number)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkValidDay(String inputStartMounth, String inputStartDay) {
        if (inputStartMounth.trim().equals("2") == true) {
            for (int index = 1; index <= 28; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("1")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("3")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("4")) {
            int dayScope = 30;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("5")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("6")) {
            int dayScope = 30;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("7")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("8")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("9")) {
            int dayScope = 30;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }

        } else if (inputStartMounth.trim().equals("10")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("11")) {
            int dayScope = 30;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        } else if (inputStartMounth.trim().equals("12")) {
            int dayScope = 31;
            for (int index = 1; index <= dayScope; index++) {
                String number = String.valueOf(index);
                if (inputStartDay.trim().equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }
//----------------------

    //after sign up, create a meal plan file by default named referral code
//    public void createMealPlan(String referralCode) {
//        String planTime;
//        String isPaid;
//        String isSkipped;
//        String indexOfMeal = "";
//        String indexOfMeal2 = "";
//        String protin;
//        String protin2;
//        String servingNo;
//        String servingNo2;
//        try {
//            FileWriter newWriter = new FileWriter(referralCode + ".txt", true);
//            PrintWriter newCustomer = new PrintWriter(newWriter);
//            monashMealKit.generateDefaultMealPlan(5);
//            for (int i = 0; i < 5; i++) {
//                planTime = monashMealKit.getMealPlanList().get(i).getPlanTime();
//                isPaid = monashMealKit.getMealPlanList().get(i).getIsPaid();
//                isSkipped = monashMealKit.getMealPlanList().get(i).getIsSkipped();
//                indexOfMeal = indexOfMeal + monashMealKit.getMealPlanList().get(i).getMealList().get(0).getRecipeNo();
//                indexOfMeal2 = indexOfMeal2 + monashMealKit.getMealPlanList().get(i).getMealList().get(1).getRecipeNo();
//                servingNo = monashMealKit.getMealPlanList().get(i).getServingNo().get(0);
//                servingNo2 = monashMealKit.getMealPlanList().get(i).getServingNo().get(1);
//                protin = monashMealKit.getMealPlanList().get(i).getProtein().get(0);
//                protin2 = monashMealKit.getMealPlanList().get(i).getProtein().get(1);
//                newCustomer.println(planTime + "," + isPaid + "," + isSkipped + "," + indexOfMeal + "," + servingNo + "," + protin + "," + indexOfMeal2 + "," + servingNo2 + "," + protin2);
//            }
//            newCustomer.close();
//        } catch (Exception e) {
//            System.out.println("Can not create or find mealPlan file");
//        }
//        System.out.println("The system generates a 5-week meal plan for you!");
//    }

    //allergen
    public ArrayList<String> readAllergensList() {
        String allergens = null;
        ArrayList<String> allergensList = new ArrayList<>();
        try {
            FileReader customerInfo = new FileReader(".\\Data\\AllergensList.txt");
            Scanner console = new Scanner(customerInfo);
            do {
                allergens = console.nextLine();
                allergensList.add(allergens);
            } while (console.hasNextLine());
        } catch (Exception e) {
            System.out.println("Error: Please check the file AllergensList");
        }
        allergensSize = allergensList.size();
        return allergensList;
    }

    public void createCustomerAllergensFile(String inputEmailAddress, String customerAllergensInput) {
        try {
            FileWriter newWriter = new FileWriter(".\\Data\\CustomerAllergens.txt", true);
            PrintWriter newCustomerAllergens = new PrintWriter(newWriter);
            newCustomerAllergens.println(inputEmailAddress + "," + customerAllergensInput);
            newCustomerAllergens.close();
        } catch (Exception e) {
            System.out.println("Can not create or find CustomerAllergens.txt");
        }
    }
}