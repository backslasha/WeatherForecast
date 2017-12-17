package hey.forecast.add_city;

import hey.forecast.choose_city.CityChooseContract;

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
}
