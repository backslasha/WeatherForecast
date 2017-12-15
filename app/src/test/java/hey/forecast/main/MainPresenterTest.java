package hey.forecast.main;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yhb on 17-12-15.
 */
public class MainPresenterTest {

    private MainContract.View fragment = new MainFragment();
    MainPresenter mMainPresenter = new MainPresenter(fragment);
    @Test
    public void getWeatherHourly() throws Exception {
    }

}