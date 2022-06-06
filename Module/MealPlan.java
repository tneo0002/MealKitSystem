package Module;

import java.util.*;

public class MealPlan {
    private String planTime;
    private ArrayList<Meal> mealList;
    private ArrayList<String> servingNo;
    private ArrayList<String> protein;
    private String isPaid;
    private String isSkipped;

    public MealPlan() {
        planTime = "";
        mealList = new ArrayList<>();
        servingNo = new ArrayList<>();
        protein = new ArrayList<>();
        isPaid = "";
        isSkipped = "false";
    }

    public MealPlan(String mondayTime, String isPaidOrNot, String isSkippedOrNot, ArrayList<Meal> mealsInMealPlan, ArrayList<String> servingNumberOfMeal, ArrayList<String> proteinOfMeal) {
        planTime = mondayTime;
        isPaid = isPaidOrNot;
        isSkipped = isSkippedOrNot;
        mealList = mealsInMealPlan;
        servingNo = servingNumberOfMeal;
        protein = proteinOfMeal;
    }

    public ArrayList<Meal> getMealList() {
        return mealList;
    }

    public ArrayList<String> getServingNo() {
        return servingNo;
    }

    public String getPlanTime() {
        return planTime;
    }

    public ArrayList<String> getProtein() {
        return protein;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String theMealIsPaid) {
        isPaid = theMealIsPaid;
    }

    public void deleteMeal(int number) {
        mealList.remove(number);
        servingNo.remove(number);
        protein.remove(number);
    }

    public void addMeal(Meal meal, String serving, String changeProtein) {
        mealList.add(meal);
        servingNo.add(serving);
        protein.add(changeProtein);
    }

    public void setIsSkipped() {
        isSkipped = "true";
    }

    public String getIsSkipped() {
        return isSkipped;
    }
}
