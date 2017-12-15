package hey.forecast.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import hey.forecast.R;
import hey.forecast.entity.Basic;
import hey.forecast.entity.DailyForecast;
import hey.forecast.entity.Hourly;
import hey.forecast.entity.LifeStyle;
import hey.forecast.entity.Now;
import hey.forecast.main.recycler.AttrAdapter;
import hey.forecast.main.recycler.DailyForecastAdapter;
import hey.forecast.main.recycler.HourlyAdapter;
import hey.forecast.main.recycler.LifeStyleAdapter;
import hey.forecast.util.AdapterDataExtractor;

import static android.Manifest.permission.INTERNET;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static hey.forecast.main.MainActivity.TAG;

/**
 * Created by yhb on 17-12-14.
 */

public class MainFragment extends Fragment implements MainContract.View {
    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewAttr, mRecyclerViewHourly, mRecyclerViewDailyForecast, mRecyclerViewLifeStyle;
    private SeekBar mSeekBar;
    private FloatingActionButton mFab;

    private TextView mTextViewTemperatureMain, mTextViewWeatherMain, mTextViewEllipseMain;
    private CollapsingToolbarLayout mActivityToolbarLayout;
    private AppBarLayout mAppBarLayout;

    private void bindViews(View view) {
        mSeekBar = view.findViewById(R.id.seek_bar);
        mRecyclerViewAttr = view.findViewById(R.id.recycler_view_attr);
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

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), INTERNET) != PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{INTERNET}, 5078);
                } else {
                    mPresenter.getWeatherNow();
                    mPresenter.getWeatherDailyForecast();
                    mPresenter.getWeatherHourly();
                    mPresenter.getWeatherLifeStyle();
                }
            }
        });

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        bindViews(root);
        mPresenter.start();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                Snackbar.make(mFab, "获取数据成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ((AttrAdapter) mRecyclerViewAttr.getAdapter()).flush(
                        AdapterDataExtractor.keys_now,
                        AdapterDataExtractor.extractValues(now),
                        AdapterDataExtractor.units_now
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

}