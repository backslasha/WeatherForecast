package hey.forecast.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import hey.forecast.R;
import hey.forecast.util.ActivityUtils;
import hey.forecast.util.Const;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private MainPresenter mPresenter;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mToolbarLayout;
    private SwipeRefreshLayout mRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Const.screenHeight = ActivityUtils.getScreenHeight(this);
        Const.screenWidth = ActivityUtils.getScreenWidth(this);

        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRefreshLayout.setProgressViewOffset(true, Const.screenHeight / 20, Const.screenHeight = ActivityUtils.getScreenHeight(this)/5);
        Const.screenWidth = ActivityUtils.getScreenWidth(this);

        //设置刷新progressbar颜色
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
        //设置刷新监听
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent, getTheme()));
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mRefreshLayout.setEnabled(event.getRawY() < ActivityUtils.getScreenHeight(this) / 5);
        }
        return super.dispatchTouchEvent(event);
    }
}
