package hey.forecast.choose_city;

import java.util.List;
import java.util.Map;

import hey.forecast.BasePresenter;
import hey.forecast.BaseView;

/**
 * Created by yhb on 17-12-16.
 */

public interface CityChooseContract {

    interface Presenter extends BasePresenter {

        void getWeatherAndTemperature();

        void loadCitesFromSP();

        void saveCitesToSP(String city);

        void removeCityFromSP(String city);
    }

    interface View extends BaseView<Presenter> {
        void showCites(List<String> cities);

        void setRealTimeDataSupport(RealTimeDataSupport realTimeDataSupport);
    }


    interface RealTimeDataSupport {
        Map<String, String> getWeatherAndTemperature();
    }

}
