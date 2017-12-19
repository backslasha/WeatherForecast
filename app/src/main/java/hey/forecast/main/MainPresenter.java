package hey.forecast.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import hey.forecast.entity.response.Data;
import hey.forecast.util.ActivityUtils;
import hey.forecast.util.Const;
import hey.forecast.util.SPUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static hey.forecast.util.Const.SP_KEY_CURRENT_CITY;
import static hey.forecast.util.Const.ENDPOINT;
import static hey.forecast.util.Const.FORECAST;
import static hey.forecast.util.Const.HOURLY;
import static hey.forecast.util.Const.KEY;
import static hey.forecast.util.Const.LIFESTYLE;
import static hey.forecast.util.Const.NOW;
import static hey.forecast.util.Const.WALL_PAPER;
import static hey.forecast.util.Const.okHttp;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MainContract.View mMainView;
    private Context mContext;
    private static final String DEFAULT_CITY = "广州";
    private String mCurrentCity = DEFAULT_CITY;

    public MainPresenter(Context context, MainContract.View mainView) {
        mContext = context;
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        mCurrentCity = (String) SPUtils.get(mContext, SP_KEY_CURRENT_CITY, DEFAULT_CITY);
        getWeatherNow();
        getWeatherHourly();
        getWeatherDailyForecast();
        getWeatherLifeStyle();
//        getWallPaper();
    }

    /**
     * 发送请求获取天气实况数据，若成功，则将数据解析成实体类，再刷新界面
     */
    @Override
    public void getWeatherNow() {
        // 拼凑 URL
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + NOW)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", mCurrentCity)
                .build();

        // 构造请求
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        // 显示正在加载的进度条
        mMainView.showProgressBar();

        // 发送请求并设置回调函数
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                // 失败时，仅仅停止显示进度条
                mMainView.hideProgressBar();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                // 成功时，停止向时进度条，并使用数据刷新界面
                mMainView.hideProgressBar();
                String dataString = response.body().string();
                Data data = new Gson().fromJson(dataString, Data.class);
                mMainView.showWeatherNow(data.getHeWeather6()[0].getNow(), data.getHeWeather6()[0].getBasic());
            }

        });
    }

    /**
     * 发送请求获取逐小时天气实况数据，若成功，则将数据解析成实体类，再刷新界面
     */
    @Override
    public void getWeatherHourly() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + HOURLY)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", mCurrentCity)
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                String dataString = response.body().string();
                Data data = new Gson().fromJson(dataString, Data.class);
                mMainView.showWeatherHourly(
                        data.getHeWeather6()[0].getHourly(),
                        data.getHeWeather6()[0].getBasic()
                );
            }

        });
    }

    /**
     * 发送请求获取生活指数数据，若成功，则将数据解析成实体类，再刷新界面
     */
    @Override
    public void getWeatherLifeStyle() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + LIFESTYLE)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", mCurrentCity)
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                String dataString = response.body().string();
                Data data = new Gson().fromJson(dataString, Data.class);
                mMainView.showWeatherLifeStyle(
                        data.getHeWeather6()[0].getLifestyle(),
                        data.getHeWeather6()[0].getBasic()
                );
            }

        });
    }

    /**
     * 发送请求获取未来七天天气实况数据，若成功，则将数据解析成实体类，再刷新界面
     */
    @Override
    public void getWeatherDailyForecast() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + FORECAST)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", mCurrentCity)
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                String dataString = response.body().string();
                Data data = new Gson().fromJson(dataString, Data.class);
                mMainView.showWeatherDailyForecast(
                        data.getHeWeather6()[0].getDaily_forecast(),
                        data.getHeWeather6()[0].getBasic()
                );
            }

        });
    }

    @Override
    public void getWallPaper() {
        final HttpUrl httpUrl = HttpUrl.parse(WALL_PAPER)
                .newBuilder()
                .addQueryParameter("n", "1")
                .addQueryParameter("w", String.valueOf(Const.screenWidth))
                .addQueryParameter("h", String.valueOf(Const.screenHeight))
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                if (inputStream != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    Log.d(TAG, "onResponse: " + bitmap.getConfig().name() + ":" + bitmap.getHeight() + "," + bitmap.getWidth());
                    mMainView.setWallPaper(bitmap);
                }
            }

        });
    }

    @Override
    public void setCurrentCity(String currentCity) {
        mCurrentCity = currentCity;
        SPUtils.put(mContext, SP_KEY_CURRENT_CITY, mCurrentCity);
    }
}
