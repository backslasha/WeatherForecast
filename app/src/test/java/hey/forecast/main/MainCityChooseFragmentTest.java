package hey.forecast.main;

import org.junit.Test;

import hey.forecast.entity.response.Now;
import hey.forecast.main.recycler.AttrAdapter;

import static hey.forecast.main.recycler.AttrAdapter.json_keys_now;
import static org.junit.Assert.*;

/**
 * Created by yhb on 17-12-14.
 */
public class MainCityChooseFragmentTest {
    Now now = new Now();

    @Test
    public void extractValues() throws Exception {

        int len = json_keys_now.length;
        now.setTmp("SAjiuj asij");
        now.setHum("sjai ");
        now.setWind_spd("sami jsa");

        assertTrue(AttrAdapter.extractValues(now).length == len);
        String[] strings = AttrAdapter.extractValues(now);
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