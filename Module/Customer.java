package Module;
import java.util.*;

public class Customer {
    private String emailAddress;
    private String password;
    private String userFname;
    private String userLname;
    private String dateOfBirth;
    private String gender;
    private String mobileNumber;
    private String referralCode;
    private boolean hasDiscount;
//    private ArrayList<String> allergentsList;


    public Customer()
    {
        emailAddress = "";
        password = "";
        userFname = "";
        userLname = "";
        dateOfBirth = "";
        gender = "";
        mobileNumber = "";
        referralCode = "";
        hasDiscount = false;
//        allergentsList = new ArrayList<String>();
    }

    public Customer(String newEmailAddress, String newPassword, String newUserFname, String newUserLname,
                    String newDateOfBirth, String newGender, String newMobileNumber, String newReferralCode, boolean hasADiscount)
    {
        emailAddress = newEmailAddress;
        password = newPassword;
        userFname = newUserFname;
        userLname = newUserLname;
        dateOfBirth = newDateOfBirth;
        gender = newGender;
        mobileNumber = newMobileNumber;
        referralCode = newReferralCode;
        hasDiscount = hasADiscount;
//        allergentsList = new ArrayList<String>();

    }

    public void setEmailAddress(String newEmailAddress)
    {
        emailAddress = newEmailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setPassword(String newPassword)
    {
        password = newPassword;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUserFname(String newUserFname)
    {
        userFname = newUserFname;
    }

    public String getUserFname()
    {
        return userFname;
    }

    public void setUserLname(String newUserLname)
    {
        userLname = newUserLname;
    }

    public String getUserLname()
    {
        return userLname;
    }

    public void setDateOfBirth(String newDateOfBirth)
    {
        dateOfBirth = newDateOfBirth;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setGender(String newGender)
    {
        gender = newGender;
    }

    public String getGender()
    {
        return gender;
    }

    public void setMobileNumber(String newMobileNumber)
    {
        mobileNumber = newMobileNumber;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setReferralCode(String newReferralCode)
    {
        referralCode = newReferralCode;
    }

    public String getReferralCode()
    {
        return referralCode;
    }

}
