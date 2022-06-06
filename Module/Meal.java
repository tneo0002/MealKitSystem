package Module;

import java.util.ArrayList;

public class Meal {
    private String recipeNo;
    private String title;
    private String subtitle;
    private String cookTime;
    private String tag;
    private ArrayList<String> suppliedIngredients;
    private ArrayList<String> householdIngredients;
    private ArrayList<String> cookingSteps;

    /**
     * constructor with no parameter
     */
    public Meal() {
        recipeNo = "";
        title = "";
        subtitle = "";
        cookTime = "";
        tag = "";
        suppliedIngredients = null;
        householdIngredients = null;
        cookingSteps = null;
    }

    /**
     * constructor with parameter
     */
    public Meal(String recipeNo, String title, String subtitle, String cookTime, String tag, ArrayList<String> suppliedIngredients, ArrayList<String> householdIngredients, ArrayList<String> cookingSteps) {
        this.recipeNo = recipeNo;
        this.title = title;
        this.subtitle = subtitle;
        this.cookTime = cookTime;
        this.tag = tag;
        this.suppliedIngredients = suppliedIngredients;
        this.householdIngredients = householdIngredients;
        this.cookingSteps = cookingSteps;
    }

    /**
     * accessor
     */
    public String getCookTime() {
        return cookTime;
    }

    public ArrayList<String> getHouseholdIngredients() {
        return householdIngredients;
    }

    public String getRecipeNo() {
        return recipeNo;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ArrayList<String> getSuppliedIngredients() {
        return suppliedIngredients;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    /**
     * mutator
     */
    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public void setHouseholdIngredients(ArrayList<String> householdIngredients) {
        this.householdIngredients = householdIngredients;
    }

    public void setRecipeNo(String recipeNo) {
        this.recipeNo = recipeNo;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSuppliedIngredients(ArrayList<String> suppliedIngredients) {
        this.suppliedIngredients = suppliedIngredients;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getCookingSteps() {
        return cookingSteps;
    }

    public void setCookingSteps(ArrayList<String> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }
}