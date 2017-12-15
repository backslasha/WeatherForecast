package hey.forecast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yhb on 17-12-14.
 */

public class DateUtils {
    /**
     * @return true if the supplied when is today else false
     */
    public static boolean isToday(long when) {

        Calendar time = Calendar.getInstance(Locale.CHINA);
        int todayYear = time.get(Calendar.YEAR);
        int todayMonth = time.get(Calendar.MONTH);
        int todayMonthDay = time.get(Calendar.DAY_OF_MONTH);


        time.setTimeInMillis(when);
        int thenYear = time.get(Calendar.YEAR);
        int thenMonth = time.get(Calendar.MONTH);
        int thenMonthDay = time.get(Calendar.DAY_OF_MONTH);

        return (thenYear == todayYear)
                && (thenMonth == todayMonth)
                && (thenMonthDay == todayMonthDay);
    }

    public static String getWeekLabel(String MMddString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yy-MM-dd");
        String week = "";

        try {
            Date parse = simpleDateFormat.parse(calendar.get(Calendar.YEAR) + "-" + MMddString);
            calendar.setTime(parse);
            int i = calendar.get(Calendar.DAY_OF_WEEK);
            switch (i) {
                case 1:
                    week = "周日";
                    break;
                case 2:
                    week = "周一";
                    break;
                case 3:
                    week = "周二";
                    break;
                case 4:
                    week = "周三";
                    break;
                case 5:
                    week = "周四";
                    break;
                case 6:
                    week = "周五";
                    break;
                case 7:
                    week = "周六";
                    break;
            }
            return week;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
