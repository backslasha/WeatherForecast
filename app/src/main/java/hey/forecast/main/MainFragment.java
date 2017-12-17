package hey.forecast.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import hey.forecast.R;
import hey.forecast.choose_city.CityChooseActivity;
import hey.forecast.entity.response.Basic;
import hey.forecast.entity.response.DailyForecast;
import hey.forecast.entity.response.Hourly;
import hey.forecast.entity.response.LifeStyle;
import hey.forecast.entity.response.Now;
import hey.forecast.main.recycler.AttrAdapter;
import hey.forecast.main.recycler.DailyForecastAdapter;
import hey.forecast.main.recycler.HourlyAdapter;
import hey.forecast.main.recycler.LifeStyleAdapter;
import hey.forecast.util.ActivityUtils;

import static android.app.Activity.RESULT_OK;
import static hey.forecast.util.Const.CITY_NAME;

/**
 * Created by yhb on 17-12-14.
 */

public class MainFragment extends Fragment implements MainContract.View {
    private static final int SWITCH_CITY = 3;
    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewAttr, mRecyclerViewHourly, mRecyclerViewDailyForecast, mRecyclerViewLifeStyle;
    private SeekBar mSeekBar;

    private TextView mTextViewTemperatureMain, mTextViewWeatherMain, mTextViewEllipseMain;
    private CollapsingToolbarLayout mActivityToolbarLayout;
    private AppBarLayout mAppBarLayout;

    private void bindViews(View view) {
        mSeekBar = view.findViewById(R.id.seek_bar);

        int screenHeight = ActivityUtils.getScreenHeight(getActivity());
        mRecyclerViewAttr = view.findViewById(R.id.recycler_view_attr);
        mRecyclerViewAttr.getLayoutParams().height = (int) (screenHeight * ActivityUtils.DOWN_PART_PERCENT);
        mRecyclerViewAttr.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewAttr.setAdapter(new AttrAdapter(getActivity()));

        mRecyclerViewHourly = view.findViewById(R.id.recycler_view_hourly);
        mRecyclerViewHourly.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewHourly.setAdapter(new HourlyAdapter(getActivity()));

        mRecyclerViewDailyForecast = view.findViewById(R.id.recycler_view_daily_forecast);
        mRecyclerViewDailyForecast.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewDailyForecast.setAdapter(new DailyForecastAdapter(getActivity()));

        mRecyclerViewLifeStyle = view.findViewById(R.id.recycler_view_life_style);
        mRecyclerViewLifeStyle.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerViewLifeStyle.setAdapter(new LifeStyleAdapter(getActivity()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mAppBarLayout = activity.findViewById(R.id.app_bar_layout);
        mActivityToolbarLayout = activity.findViewById(R.id.collapsing_tool_bar_layout);
        mTextViewTemperatureMain = activity.findViewById(R.id.text_view_temperature_main);
        mTextViewWeatherMain = activity.findViewById(R.id.text_view_weather_main);
        mTextViewEllipseMain = activity.findViewById(R.id.text_view_ellipse_main);

    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        bindViews(root);
        mPresenter.start();
        return root;
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showWeatherNow(final Now now, final Basic basic) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(getView(), "获取数据成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ((AttrAdapter) mRecyclerViewAttr.getAdapter()).flush(
                        AttrAdapter.keys_now,
                        AttrAdapter.extractValues(now),
                        AttrAdapter.units_now
                );

                mActivityToolbarLayout.setTitle(basic.getLocation());
                mTextViewTemperatureMain.setText(String.format("%s%s", now.getTmp(), getString(R.string.temperature_unit)));
                mTextViewWeatherMain.setText(now.getCond_txt());
                mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                            //  Collapsed
                            mActivityToolbarLayout.setTitle(
                                    basic.getLocation()
                                            + String.format("  %s", now.getCond_txt())
                                            + String.format("  %s%s", now.getTmp(), getString(R.string.temperature_unit)
                                    )
                            );


                        } else {
                            //Expanded
                            mActivityToolbarLayout.setTitle(
                                    basic.getLocation()
                            );

                        }
                    }
                });
            }
        });
    }

    @Override
    public void showWeatherDailyForecast(final DailyForecast[] dailyForecasts, Basic basic) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((DailyForecastAdapter) mRecyclerViewDailyForecast.getAdapter()).flush(dailyForecasts);

            }
        });
    }

    @Override
    public void showWeatherHourly(final Hourly[] hourlies, Basic basic) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((HourlyAdapter) mRecyclerViewHourly.getAdapter()).flush(hourlies);
            }
        });
    }

    @Override
    public void showWeatherLifeStyle(final LifeStyle[] lifeStyles, Basic basic) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LifeStyleAdapter) mRecyclerViewLifeStyle.getAdapter()).flush(lifeStyles);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SWITCH_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra(CITY_NAME);
                mPresenter.setCurrentCity(cityName);
                mPresenter.getWeatherNow();
                mPresenter.getWeatherHourly();
                mPresenter.getWeatherDailyForecast();
                mPresenter.getWeatherLifeStyle();
                Snackbar.make(getView(), "切换到城市:" + cityName, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.city_manage:
                Intent intent = CityChooseActivity.newIntent(getActivity());
                startActivityForResult(intent, SWITCH_CITY);
                break;
            case R.id.setting:
                break;
            case R.id.share:
                break;
            case R.id.refresh:
                mPresenter.getWeatherNow();
                mPresenter.getWeatherDailyForecast();
                mPresenter.getWeatherHourly();
                mPresenter.getWeatherLifeStyle();
                break;
            default:
                break;
        }
        return true;
    }

}