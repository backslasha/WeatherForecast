package hey.forecast.main.recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hey.forecast.R;
import hey.forecast.entity.response.LifeStyle;

/**
 * Created by yhb on 17-12-15.
 */

public class LifeStyleAdapter extends RecyclerView.Adapter<LifeStyleAdapter.LifeStyleViewHolder> {
    private LifeStyle[] mLifeStyles;
    private Context mContext;

    public LifeStyleAdapter(Context context) {
        mContext = context;
    }

    @Override

    public LifeStyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_life_style, parent, false);
        return new LifeStyleAdapter.LifeStyleViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(LifeStyleViewHolder holder, int position) {
        holder.bindData(mLifeStyles[position]);
    }

    @Override
    public int getItemCount() {
        if (mLifeStyles == null) {
            return 0;
        }
        return mLifeStyles.length;
    }

    public void flush(LifeStyle[] lifeStyles) {
        this.mLifeStyles = lifeStyles;
        notifyItemChanged(0, lifeStyles.length);
    }

    class LifeStyleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewType, mTextViewDesc;
        private ImageView mImageViewLifeStyle;

        public LifeStyleViewHolder(View itemView) {
            super(itemView);
            mTextViewType = itemView.findViewById(R.id.text_view_life_style_type);
            mTextViewDesc = itemView.findViewById(R.id.text_view_life_style_desc);
            mImageViewLifeStyle = itemView.findViewById(R.id.image_view_life_style);
        }

        public void bindData(LifeStyle lifeStyle) {
            String type = null;
            Drawable drawable = null;
            switch (lifeStyle.getType()) {
                case "comf":
                    type = "舒适度指数";
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_life_style_comforts);
                    break;
                case "cw":
                    type = "洗车指数";
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_life_style_car_wash);
                    break;
                case "sport":
                    type = "运动指数";
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_life_style_sports);
                    break;
                case "uv":
                    type = "紫外线指数";
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_life_style_ultra_violent);
                    break;
                default:
                    type = lifeStyle.getType();
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_life_style_comforts);
                    break;

            }
            mImageViewLifeStyle.setImageDrawable(drawable);
            mTextViewType.setText(type);
            mTextViewDesc.setText(lifeStyle.getBrf());
        }
    }
}
