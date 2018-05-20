package hey.forecast.main.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hey.forecast.R;
import hey.forecast.entity.response.Hourly;
import hey.forecast.util.AdapterDataExtractor;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    private final Context context;
    private Hourly[] mHourlies;

    public HourlyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_hourly, parent, false);
        return new HourlyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        holder.bindData(mHourlies[position]);
    }

    @Override
    public int getItemCount() {
        if (mHourlies == null) {
            return 0;
        }
        return mHourlies.length;
    }

    public void flush(Hourly[] hourlies) {
        if (hourlies == null) {
            Toast.makeText(context, R.string.bug_notice, Toast.LENGTH_SHORT).show();
            return;
        }
        this.mHourlies = hourlies;
        notifyItemRangeChanged(0, hourlies.length);
    }

    class HourlyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTime, mTextViewWeather, mTextViewTemperature;
        private ImageView mImageViewWeather;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            mTextViewTime = itemView.findViewById(R.id.text_view_time_hourly);
            mTextViewWeather = itemView.findViewById(R.id.text_view_weather_hourly);
            mTextViewTemperature = itemView.findViewById(R.id.text_view_temperature_hourly);
            mImageViewWeather = itemView.findViewById(R.id.image_view_weather_hourly);
        }

        public void bindData(Hourly hourly) {
            mTextViewTime.setText(hourly.getTime().substring(11));
            AdapterDataExtractor.showWeatherIcon(mImageViewWeather, hourly.getCond_code(), context.getAssets());
            mTextViewTemperature.setText(String.format("%s ℃", hourly.getTmp()));
            mTextViewWeather.setText(hourly.getCond_txt());
        }
    }
}
