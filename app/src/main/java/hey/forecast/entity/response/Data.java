package hey.forecast.entity.response;

import java.util.Arrays;

/**
 * Created by yhb on 17-12-14.
 */

public class Data {
    HeWeather6[] HeWeather6;

    @Override
    public String toString() {
        return "Data{" +
                "HeWeather6=" + Arrays.toString(HeWeather6) +
                '}';
    }

    public hey.forecast.entity.response.HeWeather6[] getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(hey.forecast.entity.response.HeWeather6[] heWeather6) {
        HeWeather6 = heWeather6;
    }
}
