package hey.forecast.util;

import android.text.format.Time;

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
}
