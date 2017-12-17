package hey.forecast.choose_city;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hey.forecast.util.Const;
import hey.forecast.util.SPUtils;

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

}
