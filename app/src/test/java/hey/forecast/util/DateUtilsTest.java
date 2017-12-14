package hey.forecast.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import hey.forecast.R;

import static org.junit.Assert.*;

/**
 * Created by yhb on 17-12-14.
 */
public class DateUtilsTest {
    @Test
    public void isToday() throws Exception {
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        dateFormat.applyPattern("yy-MM-dd");

        assertTrue(DateUtils.isToday(new Date().getTime()));
        assertFalse(DateUtils.isToday(dateFormat.parse("2017-12-15").getTime()));
    }

}