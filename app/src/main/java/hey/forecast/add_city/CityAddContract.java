package hey.forecast.add_city;

import android.content.Context;

import java.util.List;

import hey.forecast.BasePresenter;
import hey.forecast.BaseView;

/**
 * Created by yhb on 17-12-17.
 */

public interface CityAddContract {

    interface View extends BaseView<Presenter> {
        void showQueryResult(List<String> results);

        Context getContext();
    }

    interface Presenter extends BasePresenter {
        void performQuery(String query);
    }

}
