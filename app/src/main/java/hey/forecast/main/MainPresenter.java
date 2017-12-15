package hey.forecast.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import hey.forecast.entity.Data;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static hey.forecast.util.Const.ENDPOINT;
import static hey.forecast.util.Const.FORECAST;
import static hey.forecast.util.Const.HOURLY;
import static hey.forecast.util.Const.KEY;
import static hey.forecast.util.Const.LIFESTYLE;
import static hey.forecast.util.Const.NOW;
import static hey.forecast.util.Const.okHttp;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    private MainContract.View mMainView;


    public MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        getWeatherNow();
        getWeatherHourly();
        getWeatherDailyForecast();
        getWeatherLifeStyle();
    }

    @Override
    public void getWeatherNow() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + NOW)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", "北京")
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        mMainView.showProgressBar();

        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                mMainView.hideProgressBar();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                mMainView.hideProgressBar();
                String dataString = response.body().string();
                Data data = new Gson().fromJson(dataString, Data.class);
                mMainView.showWeatherNow(data.getHeWeather6()[0].getNow(), data.getHeWeather6()[0].getBasic());
            }

        });
    }

    @Override
    public void getWeatherHourly() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + HOURLY)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", "北京")
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

    @Override
    public void getWeatherLifeStyle() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + LIFESTYLE)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", "北京")
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

    @Override
    public void getWeatherDailyForecast() {
        final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + FORECAST)
                .newBuilder()
                .addQueryParameter("key", KEY)
                .addQueryParameter("location", "北京")
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
}