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

import java.util.List;

import hey.forecast.R;
import hey.forecast.add_city.CityAddActivity;
import hey.forecast.common.SimpleAdapter;
import hey.forecast.common.SimpleHolder;
import hey.forecast.common.SimpleItemTouchHelper;

import static android.app.Activity.RESULT_OK;
import static hey.forecast.util.Const.EXTRA_CITY_NAME;

/**
 * Created by yhb on 17-12-16.
 */

public class CityChooseFragment extends android.support.v4.app.Fragment implements CityChooseContract.View {

    public static CityChooseFragment newInstance() {
        Bundle args = new Bundle();
        CityChooseFragment cityChooseFragment = new CityChooseFragment();
        cityChooseFragment.setArguments(args);
        return cityChooseFragment;
    }


    private static final int ADD_CITY = 1;
    private CityChooseContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewCity;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_choose, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CityAddActivity.newIntent(getActivity());
                startActivityForResult(intent, ADD_CITY);
            }
        });

        mRecyclerViewCity = view.findViewById(R.id.recycler_view_city_choose);

        initCitesRecyclerView(mRecyclerViewCity);

        mPresenter.start();

        return view;
    }


    private void initCitesRecyclerView(RecyclerView recyclerViewCity) {
        recyclerViewCity.setAdapter(new SimpleAdapter<String>(getActivity(), R.layout.item_city) {
            @Override
            public void forEachHolder(SimpleHolder holder, final String city) {
                ((TextView) holder.getView(R.id.text_view_city)).setText(city);
                ((TextView) holder.getView(R.id.text_view_weather_temperature)).setText(
                        String.format("- -%s", getString(R.string.temperature_unit))
                );
                holder.getView(R.id.image_view_city).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestSwitchTo(city);
                    }
                });

            }
        });
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));
        new SimpleItemTouchHelper().attachToRecyclerView(recyclerViewCity);
    }


    private void requestSwitchTo(String city) {
        Intent intent = getActivity().getIntent();
        intent.putExtra(EXTRA_CITY_NAME, city);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showCites(List<String> cities) {
        ((SimpleAdapter<String>) mRecyclerViewCity.getAdapter()).performDataChanged(cities);
    }

    @Override
    public void setPresenter(CityChooseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra(EXTRA_CITY_NAME);
                ((SimpleAdapter<String>) mRecyclerViewCity.getAdapter())
                        .addSingleData(cityName);

                mPresenter.saveCitesToSP(cityName);
                Snackbar.make(getView(), "新增城市:" + cityName, Snackbar.LENGTH_SHORT).show();

            }
        }
    }


}
