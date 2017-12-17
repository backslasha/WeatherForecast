package hey.forecast.choose_city;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hey.forecast.entity.response.Data;
import hey.forecast.entity.response.Now;
import hey.forecast.util.Const;
import hey.forecast.util.SPUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static hey.forecast.util.Const.ENDPOINT;
import static hey.forecast.util.Const.KEY;
import static hey.forecast.util.Const.NOW;
import static hey.forecast.util.Const.okHttp;

/**
 * Created by yhb on 17-12-16.
 */

public class CityChoosePresenter implements CityChooseContract.Presenter {

    private Context mContext;
    private CityChooseContract.View mView;

    public CityChoosePresenter(Context context, CityChooseContract.View cityChooseView) {
        mContext = context;
        mView = cityChooseView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCitesFromSP();
        getWeatherAndTemperature();
    }

    @Override
    public void getWeatherAndTemperature() {
        Map<String, ?> citiesMap = SPUtils.getAll(mContext);
        Set<? extends Map.Entry<String, ?>> entries = citiesMap.entrySet();
        Iterator<? extends Map.Entry<String, ?>> iterator = entries.iterator();

        final List<String> cities = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> next = iterator.next();
            if (!next.getKey().equals(Const.SP_KEY_CURRENT_CITY)) {
                cities.add((String) next.getValue());
            }
        }

        final Map<String, String> realTimeData = new HashMap();
        final int[] flag = new int[]{0, cities.size()};

        for (final String city : cities) {
            final HttpUrl httpUrl = HttpUrl.parse(ENDPOINT + NOW)
                    .newBuilder()
                    .addQueryParameter("key", KEY)
                    .addQueryParameter("location", city)
                    .build();
            final Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .build();

            Call call = okHttp.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                    String dataString = response.body().string();
                    Now now = new Gson().fromJson(dataString, Data.class).getHeWeather6()[0].getNow();
                    realTimeData.put(city, now.getCond_txt() + " " + now.getTmp());
                    flag[0]++;

                    if (flag[0] == flag[1]) {
                        mView.setRealTimeDataSupport(new CityChooseContract.RealTimeDataSupport() {
                            @Override
                            public Map<String, String> getWeatherAndTemperature() {
                                return realTimeData;
                            }
                        });
                        mView.showCites(cities);
                    }
                }

            });
        }

    }

    @Override
    public void loadCitesFromSP() {
        Map<String, ?> citiesMap = SPUtils.getAll(mContext);
        Set<? extends Map.Entry<String, ?>> entries = citiesMap.entrySet();
        Iterator<? extends Map.Entry<String, ?>> iterator = entries.iterator();

        List<String> cities = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> next = iterator.next();
            if (!next.getKey().equals(Const.SP_KEY_CURRENT_CITY)) {
                cities.add((String) next.getValue());
            }
        }
        mView.showCites(cities);
    }

    @Override
    public void saveCitesToSP(String city) {
        SPUtils.put(mContext, city, city);
    }

    @Override
    public void removeCityFromSP(String city) {
        SPUtils.remove(mContext, city);
    }

}
