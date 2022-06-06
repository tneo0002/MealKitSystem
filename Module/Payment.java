package Module;

public class Payment {

    private String cardType;
    private String emailAddress;
    private String cardHolderName;
    private String creditCardNumber;
    private String expirationDate;
    private String ccv;

    public Payment(String cardType, String emailAddress, String cardHolderName, String creditCardNumber, String expirationDate, String ccv) {
        this.cardType = cardType;
        this.emailAddress = emailAddress;
        this.cardHolderName = cardHolderName;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.ccv = ccv;
    }

    public Payment() {
        this.cardType = "credit";
        this.emailAddress = "123@student.monash.edu";
        this.cardHolderName = "Hengru";
        this.creditCardNumber = "123456789";
        this.expirationDate = "2022-10-03";
        this.ccv = "321";
    }

    /**
     * accessor
     * @return
     */
    public String getCardType() {
        return cardType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCcv() {
        return ccv;
    }

    /**
     * mutator
     * @param cardType
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }
}