package hey.forecast.add_city;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import hey.forecast.BaseFragmentActivity;
import hey.forecast.R;


public class CityAddActivity extends BaseFragmentActivity {

    private CityAddContract.Presenter mPresenter;
    private String TAG = "MainActivity";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CityAddActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        CityAddFragment cityAddFragment = CityAddFragment.newInstance();
        mPresenter = new CityAddPresenter(cityAddFragment);
        return cityAddFragment;
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            handleQuery(query);
        }

    }

    private void handleQuery(String query) {
        mPresenter.performQuery(query);
    }


}
