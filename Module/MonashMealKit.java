package Module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class MonashMealKit {
    private String today;
    private ArrayList<String> mondayTimes;
    private boolean isBeforeWednesday;
    private ArrayList<Referral> referrals;
    private ArrayList<Meal> mealList;
    private ArrayList<MealPlan> mealPlanList;
    private Customer customer;
    private Payment payment;

    public MonashMealKit() {
        customer = new Customer();
        payment = new Payment();

        //Set values of today and mondayTimes
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        mondayTimes = new ArrayList<>();
        try {
            int weekNo = 0;
            Date date = sdf.parse(sdf.format(System.currentTimeMillis()));
            today = sdf.format(date);
            for (int i = 0; i < 5; i++) {
                weekNo++;
                if (i == 2) {
                    mondayTimes.add(sdf.format(getThisWeekMonday(date)));
                    weekNo--;
                } else mondayTimes.add(sdf.format(getWeekMondays(date, weekNo)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isBeforeWednesday = cal.get(Calendar.DAY_OF_WEEK) < 3;

        //Set value of lists
        referrals = new ArrayList<>();
        mealPlanList = new ArrayList<>();
        mealList = new ArrayList<>();
        readFile("1");
        readFile("2");
        updateMealPlanList();

    }

    public void disPlayWeekMonday() {
        System.out.println("Today is: " + today);
        for (int i = 0; i < 5; i++) {
            System.out.println(mondayTimes.get(i));
        }
    }

    /**
     * accessor
     */
    public ArrayList<MealPlan> getMealPlanList() {
        return mealPlanList;
    }

    public static ArrayList<String> getListOfCustomerEmail() {
        return null;
    }
    //customerEmailList;

    /**
     * "1" --> readMeals
     * "2" --> readMealPlans
     * Read csv file and create a meal list
     */
    public void readFile(String choice) {
        String path = "";
        if (choice.equals("1")) path = "./Data/Meal.csv";
        else if (choice.equals("2")) path = "./Data/" + getCustomer().getReferralCode() + ".csv";
        ArrayList<ArrayList<String>> testData = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                testData.add(getDataFromLine(scanner.nextLine().replace("\uFEFF", "")));
            }
            if (choice.equals("1")) mealList = populateMealList(testData);
            else if (choice.equals("2")) mealPlanList = populateMealPlanList(testData);
        } catch (FileNotFoundException e) {
            if (choice.equals("2")) generateDefaultMealPlan(5);
            System.out.println("CSV File Not Found!");
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * get the data of every row
     *
     * @param line Send line into this function and get
     * @return a arraylist of data
     */
    private ArrayList<String> getDataFromLine(String line) {
        ArrayList<String> row = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                row.add(rowScanner.next());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return row;
    }

    public ArrayList<String> getMondayTimes() {
        return mondayTimes;
    }

    public Date getWeekMondays(Date date, int weekNo) {
        Calendar cal = Calendar.getInstance();
        int amount = 0;
        cal.setTime(getThisWeekMonday(date));
        switch (weekNo) {
            case 1:
                amount = -14;
                break;
            case 2:
                amount = -7;
                break;
            case 3:
                amount = 7;
                break;
            case 4:
                amount = 14;
                break;
            default:
                System.out.println("The weekNo error!");
                System.out.println("1 --> The week before last week");
                System.out.println("2 --> The last week");
                System.out.println("3 --> The next week");
                System.out.println("4 --> The week after next week");
                System.out.print("This week's Monday: ");
        }
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment aPayment) {
        payment = aPayment;
    }

    /**
     * Get the date of this week's Monday
     *
     * @param date put today's data and get
     * @return this week's monday
     */
    public Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //get which week of this week in the month
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //set the first day of a week
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //get the index of this day of this week
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //today's date minus the day's index of this week
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public boolean isBeforeWednesday() {
        return isBeforeWednesday;
    }

    /**
     * create the meal list with meal objects
     *
     * @param data use the data which already be handled and
     * @return use them to build the mealList
     */
    private ArrayList<Meal> populateMealList(ArrayList<ArrayList<String>> data) {
        for (int idx = 0; idx < data.size() / 4; idx++) {
            ArrayList<String> suppliedIngredients = data.get(idx * 4 + 1);
            suppliedIngredients.removeIf(String::isEmpty);
            ArrayList<String> householdIngredients = data.get(idx * 4 + 2);
            householdIngredients.removeIf(String::isEmpty);
            ArrayList<String> cookingSteps = data.get(idx * 4 + 3);
            householdIngredients.removeIf(String::isEmpty);
            Meal meal = new Meal(data.get(idx * 4).get(0),
                    data.get(idx * 4).get(1),
                    data.get(idx * 4).get(2),
                    data.get(idx * 4).get(3),
                    data.get(idx * 4).get(4),
                    suppliedIngredients,
                    householdIngredients,
                    cookingSteps);
            mealList.add(meal);
        }
        return mealList;
    }

    /**
     * put data into meal plan
     *
     * @param data use the data which already be handled and
     * @return use them to build the mealPlanList
     */
    private ArrayList<MealPlan> populateMealPlanList(ArrayList<ArrayList<String>> data) {
        String planTime = "";
        String planIsPaid = "false";
        String planIsSkipped = "false";
        String protein = "";
        ArrayList<Meal> mealsInMealPlan = new ArrayList<>();
        ArrayList<String> servingNumberOfMeal = new ArrayList<>();
        ArrayList<String> proteinOfMeal = new ArrayList<>();

        for (int idx = 0; idx < data.size(); idx++) {
            String mealIndex = "";
            Meal meal = new Meal();
            mealsInMealPlan = new ArrayList<>();
            servingNumberOfMeal = new ArrayList<>();
            proteinOfMeal = new ArrayList<>();
            planTime = data.get(idx).get(0);
            planIsPaid = data.get(idx).get(1).toLowerCase(Locale.ROOT);
            planIsSkipped = data.get(idx).get(2).toLowerCase(Locale.ROOT);

            for (int idy = 0; idy < (data.get(idx).size() - 3) / 3; idy++) {
                mealIndex = data.get(idx).get(idy * 3 + 3);
                meal = mealList.get(Integer.parseInt(mealIndex));
                mealsInMealPlan.add(meal);
                servingNumberOfMeal.add(data.get(idx).get(idy * 3 + 4));

                protein = data.get(idx).get(idy * 3 + 5).toLowerCase(Locale.ROOT);
                protein = protein.substring(0, 1).toUpperCase(Locale.ROOT) + protein.substring(1);
                proteinOfMeal.add(protein);
            }
            MealPlan mealPlan = new MealPlan(planTime, planIsPaid, planIsSkipped, mealsInMealPlan,
                    servingNumberOfMeal,
                    proteinOfMeal);
            mealPlanList.add(mealPlan);
        }
        return mealPlanList;
    }

    /**
     * @return whether it works well
     */
    public boolean printMealOptions() {
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

    public int getMealListSize() {
        return mealList.size();
    }

    public ArrayList<Meal> getMealList() {
        return mealList;
    }

    /**
     * Generate Default MealPlan
     *
     * @param numberOfMeal --> how many number meal plan you want to generate and put it into meal plan list
     */
    public void generateDefaultMealPlan(int numberOfMeal) {
        Random rand = new Random();
        Meal meal = new Meal();
        String protein = "";
        String time = "";
        ArrayList<Meal> meals;
        ArrayList<String> mealsServing = new ArrayList<>();
        ArrayList<String> mealsProtein = new ArrayList<>();
        int index;
        if (!mealPlanList.isEmpty()) {
            for (int i = 5 - numberOfMeal; i < 5; i++) {
                mealPlanList.remove(0);
            }
        }
        for (int i = 5 - numberOfMeal; i < 5; i++) {
            meals = new ArrayList<>();
            mealsServing = new ArrayList<>();
            mealsProtein = new ArrayList<>();
            time = mondayTimes.get(i);
            for (int j = 0; j < 2; j++) {
                index = rand.nextInt(mealList.size());
                meal = mealList.get(index);
                meals.add(meal);
                mealsServing.add(2 + "");
                protein = meal.getSuppliedIngredients().get(0);
                mealsProtein.add(protein);
            }
            mealPlanList.add(new MealPlan(time, "false", "false", meals, mealsServing, mealsProtein));
        }
    }

    public void updateMealPlanList() {
        int numberOfMeal = 0;
        String lastWeekMonday = mealPlanList.get(mealPlanList.size() - 1).getPlanTime();
        for (int i = 0; i < 5; i++) {
            if (mondayTimes.get(i).equals(lastWeekMonday)) {
                numberOfMeal = mealPlanList.size() - (i + 1);
                generateDefaultMealPlan(numberOfMeal);
            }
        }
    }

    public void setCustomer(Customer settingCustomer) {
        customer = settingCustomer;
    }
}