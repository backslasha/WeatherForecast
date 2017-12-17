package hey.forecast.choose_city;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import hey.forecast.BaseFragmentActivity;


public class CityChooseActivity extends BaseFragmentActivity {

    private CityChooseContract.Presenter mPresenter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CityChooseActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        CityChooseFragment cityChooseFragment = CityChooseFragment.newInstance();
        mPresenter = new CityChoosePresenter(this, cityChooseFragment);
        return cityChooseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
}
