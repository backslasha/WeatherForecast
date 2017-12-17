package hey.forecast.choose_city;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import hey.forecast.BaseFragmentActivity;


public class CityChooseActivity extends BaseFragmentActivity {

    private CityChooseContract.Presenter mPresenter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CityChooseActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return CityChooseFragment.newInstance();
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
