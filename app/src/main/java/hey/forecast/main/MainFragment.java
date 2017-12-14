package hey.forecast.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hey.forecast.R;
import hey.forecast.entity.Basic;
import hey.forecast.entity.DailyForecast;
import hey.forecast.entity.Now;
import hey.forecast.main.recycler.AttrAdapter;

import static android.Manifest.permission.INTERNET;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static hey.forecast.main.MainActivity.TAG;

/**
 * Created by yhb on 17-12-14.
 */

public class MainFragment extends Fragment implements MainContract.View {
    private AssetManager mAssetManager;
    private MainContract.Presenter mPresenter;
    // Content View Elements
    private TextView mTextViewTemperature;
    private TextView mTextViewWeather;
    private TextView mTextViewHumidity;
    private TextView mTextViewWind_degree;
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private ImageView mImageView;
    private FloatingActionButton fab;

    // End Of Content View Elements
    public MainFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAssetManager = getResources().getAssets();
    }

    private void bindViews(View view) {
        mTextViewTemperature = view.findViewById(R.id.text_view_temperature);
        mTextViewWeather = view.findViewById(R.id.text_view_weather);
        mTextViewHumidity = view.findViewById(R.id.text_view_humidity);
        mTextViewWind_degree = view.findViewById(R.id.text_view_wind_degree);
        mSeekBar = view.findViewById(R.id.seek_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AttrAdapter(getActivity()));
        mImageView = view.findViewById(R.id.image_view_weather);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), INTERNET) != PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{INTERNET}, 5078);
                } else {
                    mPresenter.getWeatherNow();
                }
            }
        });
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


    final String[] json_keys_now = new String[]{
            "tmp", "fl", "cond_txt",
            "wind_dir", "wind_sc",
            "wind_spd", "hum", "pcpn",
            "pres", "vis"
    };
    final String[] keys_now = new String[]{
            "温度", "体感温度", "天气",
            "风向", "风力",
            "风速", "相对湿度", "降水量",
            "大气压强", "能见度"
    };
    final String[] units_now = new String[]{
            "℃", "℃", "",
            "", "",
            "公里/小时", "%", "",
            "", "公里"
    };

    @Override
    public void showWeatherNow(final Now now, final Basic basic) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(fab, "获取数据成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                final String[] values = extractValues(now);
                ((AttrAdapter) mRecyclerView.getAdapter()).flush(
                        keys_now,
                        values,
                        units_now
                );
                getActivity().setTitle("天气预报/" + basic.getLocation());
                showWeatherIcon(now.getCond_code());
                mTextViewTemperature.setText(String.format("%s%s", now.getTmp(), getString(R.string.temperature_unit)));
                mTextViewHumidity.setText(String.format("%s%s", now.getHum(), getString(R.string.percent)));
                mTextViewWeather.setText(now.getCond_txt());
                mTextViewWind_degree.setText(String.format("%s", now.getWind_sc()));
                Log.d(TAG, "now: " + now);

            }
        });
    }

    private String[] extractValues(Now now) {
        String[] values = new String[keys_now.length];
        Method[] declaredMethods = Now.class.getDeclaredMethods();
        for (int i = 0; i < json_keys_now.length; i++) {
            String methodName = "get" + json_keys_now[i];
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equalsIgnoreCase(methodName)) {
                    try {
                        values[i] = (String) declaredMethod.invoke(now);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return values;
    }

    private void showWeatherIcon(String cond_code) {
        InputStream iconStream = null;
        try {

            iconStream = mAssetManager.open(cond_code + ".png");
            if (iconStream != null)
                mImageView.setImageBitmap(BitmapFactory.decodeStream(iconStream));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iconStream != null) {
                    iconStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showWeatherDailyForecast(DailyForecast[] dailyForecasts) {

    }

    @Override
    public void showWeatherDailyHourly(DailyForecast[] dailyForecasts) {

    }
}
