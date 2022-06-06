import Control.MonashMealKitSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The entrance of whole system.
 */
public class Engine {
    public static void main(String[] args) {
        MonashMealKitSystem monashMealKitSystem = new MonashMealKitSystem();
        monashMealKitSystem.runSystem();
    }
}