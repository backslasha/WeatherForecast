package hey.forecast.add_city;

import java.util.ArrayList;
import java.util.List;

import hey.forecast.util.Const;

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
        List<String> results = new ArrayList<>();

        if (query == null || query.equals("")) {
            return;
        }
        for (String city : Const.CITIES) {
            if (city.contains(query)) {
                results.add(city);
                if (results.size() >= 20) {
                    break;
                }
            }
        }
        mView.showQueryResult(results);
    }
}
