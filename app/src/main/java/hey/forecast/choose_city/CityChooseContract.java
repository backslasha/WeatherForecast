package hey.forecast.choose_city;

import java.util.List;

import hey.forecast.BasePresenter;
import hey.forecast.BaseView;

/**
 * Created by yhb on 17-12-16.
 */

public interface CityChooseContract {

    interface Presenter extends BasePresenter {
        void loadCitesFromSP();

        void saveCitesToSP(String city);
    }

    interface View extends BaseView<Presenter> {
        void showCites(List<String> cities);
    }

}
