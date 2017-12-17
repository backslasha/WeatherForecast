package hey.forecast.add_city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import hey.forecast.R;
import hey.forecast.common.SimpleAdapter;
import hey.forecast.common.SimpleHolder;

/**
 * Created by yhb on 17-12-16.
 */

public class CityAddFragment extends android.support.v4.app.Fragment implements CityAddContract.View {

    private CityAddContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_city_add, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_hot_cities);
        mRecyclerView.setAdapter(new SimpleAdapter<String>(getActivity(), R.layout.item_city_hot) {
            @Override
            public void forEachHolder(SimpleHolder holder, final String city) {
                Button button = holder.itemView.findViewById(R.id.button_hot_city);
                button.setText(city);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, city, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        ((SimpleAdapter<String>) mRecyclerView.getAdapter())
                .performDataChanged(new String[]{
                        "广州", "西安", "北京", "上海",
                        "杭州", "南京", "苏州", "深圳",
                        "成都", "重庆", "天津", "武汉"
                });

        return view;
    }

    @Override
    public void setPresenter(CityAddContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static CityAddFragment newInstance() {
        Bundle args = new Bundle();
        CityAddFragment cityAddFragment = new CityAddFragment();
        cityAddFragment.setArguments(args);
        return cityAddFragment;
    }
}
