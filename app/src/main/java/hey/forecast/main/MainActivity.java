package hey.forecast.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import hey.forecast.R;
import hey.forecast.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private MainPresenter mPresenter;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mToolbarLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        }


        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainFragment, R.id.container);
        }
        mPresenter = new MainPresenter(this, mainFragment);

        int screenHeight = ActivityUtils.getScreenHeight(this);

        mToolbarLayout = findViewById(R.id.collapsing_tool_bar_layout);
        mToolbarLayout.getLayoutParams().height = (int) (screenHeight * ActivityUtils.UP_PART_PERCENT);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.GRAY);
        setSupportActionBar(mToolbar);

    }


}
