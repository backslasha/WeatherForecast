package hey.forecast.main;

import org.junit.Test;

import hey.forecast.entity.Now;

import static org.junit.Assert.*;

/**
 * Created by yhb on 17-12-14.
 */
public class MainFragmentTest {
    Now now = new Now();

    @Test
    public void extractValues() throws Exception {

        int len = MainFragment.json_keys_now.length;
        now.setTmp("SAjiuj asij");
        now.setHum("sjai ");
        now.setWind_spd("sami jsa");

        assertTrue(new MainFragment().extractValues(now).length == len);
        String[] strings = new MainFragment().extractValues(now);
        int nullCount = 0;
        int valueCount = 0;
        for (String string : strings) {
            if (string == null) {
                nullCount++;
            } else {
                System.out.println(string);
                valueCount++;
            }
        }
        assertTrue(valueCount == 2);
        assert (nullCount == len - 3);
    }

}