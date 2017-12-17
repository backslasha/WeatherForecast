package hey.forecast.choose_city;

/**
 * Created by yhb on 17-12-16.
 */

public class CityChoosePresenter implements CityChooseContract.Presenter {

    private CityChooseContract.View mView;

    public CityChoosePresenter(CityChooseContract.View cityChooseView) {
        mView = cityChooseView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
