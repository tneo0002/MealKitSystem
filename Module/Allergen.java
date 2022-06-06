package Module;

import java.util.ArrayList;

public class Allergen {
    private String emailAddress;
    private ArrayList<String> allergensList;

    public Allergen()
    {
        emailAddress = "";
        allergensList = new ArrayList<String>();
    }

    public Allergen(String newEmailAddress)
    {
        emailAddress = newEmailAddress;
        allergensList = new ArrayList<String>();
    }

    public void setEmailAddress(String newEmailAddress)
    {
        emailAddress = newEmailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setAllergensList(String newAllergen)
    {
        allergensList.add(newAllergen);
    }

    public String getAllergen(int index)
    {
        return allergensList.get(index);
    }
}
