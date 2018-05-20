package hey.forecast.add_city;

import java.util.ArrayList;
import java.util.List;


import hey.forecast.entity.db.City;

import static hey.forecast.util.CityDBUtils.searchCities;

/**
 * Created by yhb on 17-12-16.
 */

public class CityAddPresenter implements CityAddContract.Presenter {

    private CityAddContract.View mView;

    public CityAddPresenter(CityAddContract.View cityChooseView) {
        mView = cityChooseView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }


    @Override
    public void performQuery(String query) {

        if (query == null || query.equals("")) {
            return;
        }

        List<City> results = searchCities(mView.getContext(),query);

        if (results.size() > 20) {
            results = results.subList(0,19);
        }

        List<String> list = new ArrayList<>();
        for (City result : results) {
            list.add(result.mCityName);
        }
        mView.showQueryResult(list);
    }
}
