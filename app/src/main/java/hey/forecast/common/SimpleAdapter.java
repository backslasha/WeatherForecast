package hey.forecast.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import hey.forecast.R;

/**
 * Created by yhb on 17-12-16.
 */

public abstract class SimpleAdapter<Entity> extends RecyclerView.Adapter<SimpleHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected Entity[] mEntities;
    protected LayoutInflater mInflater;


    public SimpleAdapter(Context context, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }

    @Override
    public SimpleHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return SimpleHolder.get(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(SimpleHolder holder, int position) {
        forEachHolder(holder, mEntities[position]);
    }

    public abstract void forEachHolder(SimpleHolder holder, Entity entity);

    public void performDataChanged(Entity[] entities){
        if (entities == null) {
            Toast.makeText(mContext, R.string.netError, Toast.LENGTH_SHORT).show();
            return;
        }
        this.mEntities = entities;
        notifyItemChanged(0, entities.length);
    }

    @Override
    public int getItemCount() {
        if (mEntities == null) {
            return 0;
        }
        return mEntities.length;
    }
}