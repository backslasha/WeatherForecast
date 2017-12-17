package hey.forecast.add_city;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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

import static hey.forecast.util.Const.EXTRA_CITY_NAME;

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
    private RecyclerView mRecyclerViewHotCities, mRecyclerViewQueryResult;
    private SearchView mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_city_add, container, false);
        mRecyclerViewHotCities = view.findViewById(R.id.recycler_view_hot_cities);
        mRecyclerViewHotCities.setAdapter(new SimpleAdapter<String>(getActivity(), R.layout.item_city_hot) {
            @Override
            public void forEachHolder(SimpleHolder holder, final String city) {
                final Button button = holder.itemView.findViewById(R.id.button_hot_city);
                button.setText(city);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSearchView.setQuery(button.getText(),true);
                    }
                });
            }
        });
        mRecyclerViewHotCities.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        ((SimpleAdapter<String>) mRecyclerViewHotCities.getAdapter())
                .performDataChanged(getResources().getStringArray(R.array.hot_cities));

        mRecyclerViewQueryResult = view.findViewById(R.id.recycler_view_query_result);
        mRecyclerViewQueryResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewQueryResult.setAdapter(
                new SimpleAdapter<String>(getActivity(), R.layout.item_simple_text_view) {
                    @Override
                    public void forEachHolder(SimpleHolder holder, final String cityName) {
                        ((TextView) holder.itemView).setText(cityName);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getActivity().getIntent();
                                intent.putExtra(EXTRA_CITY_NAME, cityName);
                                getActivity().setResult(Activity.RESULT_OK, intent);
                                getActivity().finish();
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
