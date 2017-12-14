package hey.forecast.main;

import hey.forecast.BasePresenter;
import hey.forecast.BaseView;
import hey.forecast.entity.Basic;
import hey.forecast.entity.DailyForecast;
import hey.forecast.entity.Now;

/**
 * Created by yhb on 17-12-14.
 */

public interface MainContract {
    interface View extends BaseView<Presenter>{
        void showProgressBar();
        void hideProgressBar();
        void showWeatherNow(Now now, Basic basic);
        void showWeatherDailyForecast(DailyForecast[] dailyForecasts);
        void showWeatherDailyHourly(DailyForecast[] dailyForecasts);

    }
    interface Presenter extends BasePresenter{
        void getWeatherNow();
    }
}
