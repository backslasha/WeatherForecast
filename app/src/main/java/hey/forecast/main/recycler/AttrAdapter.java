package hey.forecast.main.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hey.forecast.R;

/**
 * Created by yhb on 17-12-14.
 */

public class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.AttrVieHolder> {

    private String[] mKeys, mValues, mUnits;
    private Context context;

    public AttrAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AttrVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attr, parent, false);
        return new AttrVieHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrVieHolder holder, int position) {
        holder.bindData(mKeys[position], mValues[position] + " " + mUnits[position]);
    }

    @Override
    public int getItemCount() {
        if (mKeys == null || mValues == null || mUnits == null)
            return 0;
        return Math.min(mKeys.length, mValues.length);
    }


    public void flush(String[] keys, String[] values, String[] units) {
        this.mKeys = keys;
        this.mValues = values;
        this.mUnits = units;
        notifyItemRangeChanged(0, mKeys.length);
    }


    static class AttrVieHolder extends RecyclerView.ViewHolder {
        private TextView key, value;

        public AttrVieHolder(View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.text_view_attr_key);
            value = itemView.findViewById(R.id.text_view_attr_value);
        }

        public void bindData(String keyString, String valueString) {
            key.setText(keyString);
            value.setText(valueString);
        }


    }
}
