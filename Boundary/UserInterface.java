package Boundary;

import Module.Meal;
import Module.MealPlan;
import Module.Payment;

import java.util.ArrayList;

/**
 * The UserInterface class, which is the boundary of this system, stores design of all the screen.
 *
 * @author (Hengru Jiang - 31006272)
 * @version (FIT5136, version 1 - 11 / May / 2022)
 */
public class UserInterface {
    public UserInterface() {

    }
    //- displayPaymentDetails(): boolean
    //- displayFutureOrPastMealPlanOptions(): boolean

    public int displayMealPlan(MealPlan mealPlan, ArrayList<String> mondayTimes, ArrayList<Meal> meals) {
        String planTime = mealPlan.getPlanTime();
        String title = "";
        System.out.println("******************************************");
        if (planTime.equals(mondayTimes.get(0))) title = "The week before the past week";
        else if (planTime.equals(mondayTimes.get(1))) title = "The past week";
        else if (planTime.equals(mondayTimes.get(2))) title = "The Current week";
        else if (planTime.equals(mondayTimes.get(3))) title = "The next week";
        else if (planTime.equals(mondayTimes.get(4))) title = "The week after the next week";
        else System.out.println("Some error in getting date");

        //plantime, ispaid mealist(i) servingno(i), p(i)
        int cost;
        int total = 0;
        System.out.println(title + " Meal Plan                ");
        System.out.println("******************************************");
        System.out.println("NO. Meal    Serving     Protein     Cost");
        for (int i = 0; i < mealPlan.getMealList().size(); i++) {
            Meal compareMeal = mealPlan.getMealList().get(i);
            String compareProtein = compareMeal.getSuppliedIngredients().get(0);
            String mealName = compareMeal.getTitle();
            mealName = mealName.substring(0, 9) + "..";
            int serving = Integer.parseInt(mealPlan.getServingNo().get(i));
            String protein = mealPlan.getProtein().get(i);
            if (serving == 2)
                cost = 20;
            else if (serving == 4) {
                cost = 30;
            } else {
                System.out.println("Some data wrong of serving");
                break;
            }
            if (!compareProtein.equals(protein)) {
                if (!compareProtein.equals("Lamb") && !compareProtein.equals("Beef")) {
                    if (protein.equals("Lamb") || protein.equals("Beef")) cost = cost + 5 * serving;
                }
            }
            System.out.println(i + 1 + "\t" + mealName + "\t" + serving + "\t" + "\t" + protein + "\t" + "\t" + cost);
            total += cost;
        }
        System.out.println("                          ________________");
        String displayTxt = total < 100 ? "$ " : "$";
        System.out.println("                               Total: " + displayTxt + total);
        String displayTxt1 = mealPlan.getIsPaid().equals("false") ? "N" : "Y";
        System.out.println("                                Is paid: " + displayTxt1);
        String displayTxt2 = mealPlan.getIsSkipped().equals("false") ? "N" : "Y";
        System.out.println("                             Is skipped: " + displayTxt2);
        System.out.println("                          Date: " + mealPlan.getPlanTime());

        return total;
    }

    public boolean displayDifferentWeekMealPlan(String choice) {
        String displayTxt;
        System.out.println("*******************************************");
        displayTxt = choice.equals("1") ? "          Next Weeks's Meal Plan           " :
                "   The Week After Next Week's Meal Plan    ";
        System.out.println(displayTxt);
        System.out.println("*******************************************");
        return true;
    }

//    public boolean displayDifferentWeekMealPlan(String choice) {
//        String displayTxt;
//        System.out.println("*******************************************");
//        displayTxt =choice.equals("1")?"          Next Weeks's Meal Plan           ":
//                "   The Week After Next Week's Meal Plan    ";
//        System.out.println(displayTxt);
//        System.out.println("*******************************************");
//        return true;
//    }

    public boolean displayMealPlanOptions() {
        System.out.println(""); //To introduce a clear separation between user's decisions
        System.out.println("*******************************************");
        System.out.println("         Which meal plan to browse         ");
        System.out.println("*******************************************");
        System.out.println("Options:");
        System.out.println("(1) An upcoming meal plan");
        System.out.println("(2) A past meal plan");

        System.out.println("(0) Return to Subscribed Order Management\n");
        System.out.print("Please choose an option: ");
        return true;
    }

    /**
     * Use the isBrowse to judge whether the display past meal plan will be shown or not
     *
     * @param isBrowse to judge whether the customer want to browse or not
     */
    public boolean displayMealPlanSubOptions(String choice, boolean isBrowse) {
//        System.out.println(choice); //ToDo Check if an empty line be better?
        String displayTxt;
        System.out.println("*******************************************");
        displayTxt = choice.equals("1") ? "Upcoming " : "  Past ";
        System.out.println("            " + displayTxt + "Meal Plan");
        System.out.println("*******************************************");
        System.out.println("Options:");
        if (choice.equals("2")) {
            System.out.println("(1) Current week");
        }
        displayTxt = choice.equals("1") ? "(1) Next " : "(2) Last ";
        System.out.println(displayTxt + "week");
        displayTxt = choice.equals("1") ? "(2) The week after next" : "(3) The week before last";
        System.out.println(displayTxt + " week");
        if (isBrowse) {
            displayTxt = choice.equals("1") ? "(3) Display a past" : "(4) Display an upcoming";
            System.out.println(displayTxt + " meal plan");
        }

        System.out.println("(0) Return to Subscribed Order Management\n");
        System.out.print("Please choose an option: ");
        return true;
    }
    public void displayPayment(MealPlan mealPlan, Payment payment) {
        System.out.println("*******************************************");
        System.out.println("                  Payment                  ");
        System.out.println("*******************************************");
        System.out.println("Thank you for your order!");
        System.out.println("Schedule delivery: " + mealPlan.getPlanTime());
        System.out.println("Debit/Credit card number: " + payment.getCreditCardNumber());
        System.out.println("Card holder name: " + payment.getCardHolderName());
        System.out.println("Expiration date: " + payment.getExpirationDate());
        System.out.println("ccv: " + payment.getCcv());
        System.out.println("\nOptions:");
        System.out.println("(0) Return to Subscribed Order Management\n");
        System.out.print("Please choose an option: ");
    }

    public void displayWelcomeScreen() {

    }

    //- displayLoginScreen(): boolean
    //- displaySignUpScreen(): boolean
    //    public int displayMainMenuScreen()

    /**
     * display subscribed Screen
     */
    public void displaySubscribedOrderManagementScreen() {
        System.out.println("*******************************************");
        System.out.println("        Subscribed Order Management        ");
        System.out.println("*******************************************");
        System.out.println("Options:");
        System.out.println("(1) Browse meal plan");
        System.out.println("(2) Change meal plan");
        System.out.println("(3) Pay meal plan");
        System.out.println("(4) Browse meal details");

        System.out.println("(0) Back to main screen\n");
        System.out.print("Please choose an option: ");
    }

    public boolean displayMealOptions() {
        System.out.println("*******************************************");
        System.out.println("               Meal  Options               ");
        System.out.println("*******************************************");
        return true;
    }

    public boolean displayMealDetails() {
        System.out.println("*******************************************");
        System.out.println("               Meal  Details               ");
        System.out.println("*******************************************");
        return true;
    }
    //- displayMealRecipeManagementScreen(): boolean
    //- displayReferralManagementScreen(): boolean
    //- displayMyAchievementsManagementScreen(): boolean
    //- displayExitScreen(): boolean

    public void welcomeScreen() {
        System.out.println("************************************");
        System.out.println("                MMKS");
        System.out.println("************************************");
        System.out.println("Welcome to monash food court!");
        System.out.println("If you have an account, please choose 'log-in'. Otherwise, Please select sign-up");
        System.out.println("(1) log-in");
        System.out.println("(2) sign-up");
        System.out.println("(0) Exit");
        System.out.println();
        System.out.println();
    }

    public void loginScreen() {
        System.out.println("************************************");
        System.out.println("              log-in");
        System.out.println("************************************");
        System.out.println("please enter your monash email :");
        //System.out.println("please enter your account password:");
        System.out.println();
        System.out.println();
    }

    public void loginWrongInputScreen() {
        System.out.println("************************************");
        System.out.println("           Wrong input");
        System.out.println("************************************");
        System.out.println("The email address or password you entered is incorrect.");
        System.out.println("Please press enter to return to pre-interface and enter again.");
        System.out.println();
        System.out.println();
    }

    public void mainScreen() {
        System.out.println("************************************");
        System.out.println("            Main Screen");
        System.out.println("************************************");
        System.out.println("Please choose an option: ");
        System.out.println("(1) Order subscribe");
        System.out.println("(2) Meal recipe");
        System.out.println("(3) Referral");
        System.out.println("(4) My Achievement");
        System.out.println("(5) Calculate the number of purchased meal");
        System.out.println("(0) Exit");
        System.out.println();
        System.out.println();
    }

    public void invalidScreen() {
        System.out.println("************************************");
        System.out.println("          Invalid Screen");
        System.out.println("************************************");
        System.out.println("This function is still under development");
        System.out.println("Please choose an option:");
        System.out.println("(1) Back to main screen");
        System.out.println("(0) Exit");
        System.out.println();
        System.out.println();
    }

    public void signUpScreen() {
        System.out.println("************************************");
        System.out.println("             sign-up");
        System.out.println("************************************");
        System.out.println("Thank you for choosing our platform, we need some information from you.");
        System.out.println("Please enter your monash email:");
        //System.out.println("Enter 1 -- back to last screen");
        //System.out.println("Enter 0 -- exit system");
        System.out.println();
        System.out.println();
    }

    public void invalidEmailInputScreen() {
        System.out.println("************************************");
        System.out.println("          Invalid Screen");
        System.out.println("************************************");
        System.out.println("You have entered an invalid email address. Please check your spelling.");
        System.out.println("Only Monash mailbox can be used for registration.");
        System.out.println("Automatically return to the sign-up interface");
        System.out.println();
        System.out.println();
    }

    public void emailAlreadyExistScreen() {
        System.out.println("************************************");
        System.out.println("               Error");
        System.out.println("************************************");
        System.out.println("This email address has already been registered");
        System.out.println("Press enter to return to sign-up screen");
        System.out.println();
        System.out.println();
    }

    public void signUpPasswordScreen() {
        System.out.println("************************************");
        System.out.println("              sign-up");
        System.out.println("************************************");
        System.out.println("Please enter your password:");
        System.out.println("Note:at least eight items, at least one upper case and one number, unless your password is more than 16 items");
        System.out.println("Please enter your password again:");
        System.out.println();
        System.out.println();
    }

    public void invalidPasswordInputScreen() {
        System.out.println("************************************");
        System.out.println("         Invalid Password");
        System.out.println("************************************");
        System.out.println("You entered an invalid password");
        System.out.println("Note:at least eight items, at least one upper case and one number, unless your password is more than 16 items");
        System.out.println("Back to sign up page");
        System.out.println();
        System.out.println();
    }

    public void pwdTwiceInvalidScreen() {
        System.out.println("************************************");
        System.out.println("         Invalid Password");
        System.out.println("************************************");
        System.out.println("The password you entered is inconsistent!");
        System.out.println("Please try again");
        System.out.println();
        System.out.println();
    }

    public void enterProfileScreen() {
        System.out.println("************************************");
        System.out.println("         sign up-profile");
        System.out.println("************************************");
        System.out.println("Please enter your First name:");
        System.out.println("Please enter your Last name:");
        System.out.println("Please enter your Date of Birth((Eg. DD-MM-YYYY)):");
        System.out.println("Please enter your Gender (Male or Female):");
        //System.out.println("Please enter food that cannot be eaten");
        System.out.println("Please enter your phone number(must be an Australian phone number)");
        System.out.println("Please enter the referral code you obtained(Optional):");
        System.out.println();
        System.out.println();
    }

    public void exitScreen() {
        System.out.println("************************************");
        System.out.println("               Exit");
        System.out.println("************************************");
        System.out.println("You have successfully exited the system");
        System.out.println("Thanks for using!");
        System.out.println();
        System.out.println();
    }
}
