package hey.forecast.choose_city;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import hey.forecast.R;
import hey.forecast.add_city.CityAddActivity;
import hey.forecast.common.SimpleAdapter;
import hey.forecast.common.SimpleHolder;
import hey.forecast.common.SimpleItemTouchHelper;
import hey.forecast.entity.City;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yhb on 17-12-16.
 */

public class CityChooseFragment extends android.support.v4.app.Fragment implements CityChooseContract.View {

    private static final int ADD_CITY = 1;
    public static final String CITY_NAME = "CITY_NAME";
    private CityChooseContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewCity;
    private FloatingActionButton mFab;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_choose, container, false);

        mFab = view.findViewById(R.id.fab_add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CityAddActivity.newIntent(getActivity());
                startActivityForResult(intent, ADD_CITY);
            }
        });

        mRecyclerViewCity = view.findViewById(R.id.recycler_view_city_choose);
        mRecyclerViewCity.setAdapter(new SimpleAdapter<City>(getActivity(), R.layout.item_city) {
            @Override
            public void forEachHolder(SimpleHolder holder, final City city) {
                ((TextView) holder.getView(R.id.text_view_city)).setText(city.getName());
                ((TextView) holder.getView(R.id.text_view_weather_temperature)).setText(
                        String.format("%s%s%s",
                                city.getWeather(),
                                city.getTemperature(),
                                getString(R.string.temperature_unit)
                        )
                );
                holder.getView(R.id.image_view_city).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, String.format("%s%s%s",
                                city.getWeather(),
                                city.getTemperature(),
                                getString(R.string.temperature_unit)
                        ), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        mRecyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((SimpleAdapter<City>) mRecyclerViewCity.getAdapter()).performDataChanged(
                new City[]{
                        new City("广州", "晴天", "8"),
                        new City("汕头", "阴天", "-3"),
                        new City("北京", "晴天", "8")
                }
        );
        new SimpleItemTouchHelper().attachToRecyclerView(mRecyclerViewCity);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra(CITY_NAME);
                ((SimpleAdapter<City>) mRecyclerViewCity.getAdapter())
                        .addSingleData(new City(cityName, "", ""));
                Snackbar.make(getView(), "新增城市:" + cityName, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setPresenter(CityChooseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static CityChooseFragment newInstance() {
        Bundle args = new Bundle();
        CityChooseFragment cityChooseFragment = new CityChooseFragment();
        cityChooseFragment.setArguments(args);
        return cityChooseFragment;
    }
}
