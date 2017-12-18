package hey.forecast.main;

import android.graphics.Bitmap;

import hey.forecast.BasePresenter;
import hey.forecast.BaseView;
import hey.forecast.entity.response.Basic;
import hey.forecast.entity.response.DailyForecast;
import hey.forecast.entity.response.Hourly;
import hey.forecast.entity.response.LifeStyle;
import hey.forecast.entity.response.Now;

/**
 * Created by yhb on 17-12-14.
 */

public interface MainContract {
    interface View extends BaseView<Presenter>{
        void showProgressBar();
        void hideProgressBar();
        void showWeatherNow(Now now, Basic basic);
        void showWeatherDailyForecast(DailyForecast[] dailyForecasts, Basic basic);
        void showWeatherHourly(Hourly[] hourlies, Basic basic);
        void showWeatherLifeStyle(LifeStyle[] lifeStyles, Basic basic);
        void setWallPaper(Bitmap bitmap);

    }
    interface Presenter extends BasePresenter{
        void setCurrentCity(String currentCity);
        void getWeatherNow();
        void getWeatherHourly();
        void getWeatherLifeStyle();
        void getWeatherDailyForecast();
        void getWallPaper();
    }
}
