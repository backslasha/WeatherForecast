package hey.forecast.add_city;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hey.forecast.R;
import hey.forecast.common.SimpleAdapter;
import hey.forecast.common.SimpleHolder;

/**
 * Created by yhb on 17-12-16.
 */

public class CityAddFragment extends android.support.v4.app.Fragment implements CityAddContract.View {

    public static CityAddFragment newInstance() {
        Bundle args = new Bundle();
        CityAddFragment cityAddFragment = new CityAddFragment();
        cityAddFragment.setArguments(args);
        return cityAddFragment;
    }

    private CityAddContract.Presenter mPresenter;
    private RecyclerView mRecyclerView, mRecyclerViewQueryResult;
    private SearchView mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        mRecyclerViewQueryResult = view.findViewById(R.id.recycler_view_query_result);
        mRecyclerViewQueryResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewQueryResult.setAdapter(
                new SimpleAdapter<String>(getActivity(), R.layout.item_simple_text_view) {
            @Override
            public void forEachHolder(SimpleHolder holder, final String s) {
                ((TextView) holder.itemView).setText(s);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
    }

    @Override
    public void setPresenter(CityAddContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showQueryResult(List<String> results) {
        ((SimpleAdapter<String>) mRecyclerViewQueryResult.getAdapter())
                .performDataChanged(results.toArray(new String[results.size()]));
        mRecyclerViewQueryResult.setVisibility(View.VISIBLE);
    }
}
