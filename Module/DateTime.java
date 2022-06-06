//package Module;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//public class DateTime {
//    private String today;
//    private ArrayList<String> mondayTimes;
//
//    public DateTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        mondayTimes = new ArrayList<>();
//        try {
//            int weekNo = 0;
//            Date date = sdf.parse(sdf.format(System.currentTimeMillis()));
//            today = sdf.format(date);
//            for (int i = 0; i < 5; i++) {
//                weekNo++;
//                if (i == 2) {
//                    mondayTimes.add(sdf.format(getThisWeekMonday(date)));
//                    weekNo--;
//                }
//                else mondayTimes.add(sdf.format(getWeekMonday(date, weekNo)));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * Output the data of Mondays
//     */
//    public void disPlayWeekMonday() {
//        System.out.println("Today is: " + today);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(mondayTimes.get(i));
//        }
//    }
//
//    public Date getWeekMondays(Date date, int weekNo) {
//        Calendar cal = Calendar.getInstance();
//        int amount = 0;
//        cal.setTime(getThisWeekMonday(date));
//        switch (weekNo) {
//            case 1:
//                amount = -14;
//                break;
//            case 2:
//                amount = -7;
//                break;
//            case 3:
//                amount = 7;
//                break;
//            case 4:
//                amount = 14;
//                break;
//            default:
//                System.out.println("The weekNo error!");
//                System.out.println("1 --> The week before last week");
//                System.out.println("2 --> The last week");
//                System.out.println("3 --> The next week");
//                System.out.println("4 --> The week after next week");
//                System.out.print("This week's Monday: ");
//        }
//        cal.add(Calendar.DATE, amount);
//        return cal.getTime();
//    }
//
//    public Date getThisWeekMonday(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        //get which week of this week in the month
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if (1 == dayWeek) {
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//        }
//        //set the first day of a week
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        //get the index of this day of this week
//        int day = cal.get(Calendar.DAY_OF_WEEK);
//        //today's date minus the day's index of this week
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
//        return cal.getTime();
//    }
//}