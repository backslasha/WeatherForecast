package hey.forecast.main.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hey.forecast.R;
import hey.forecast.entity.response.DailyForecast;
import hey.forecast.util.AdapterDataExtractor;
import hey.forecast.util.DateUtils;


public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder> {
    private final Context context;
    private DailyForecast[] mDailyForecasts;
    private final String[] dateName = new String[]{"今天", "明天", "后天"};

    public DailyForecastAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DailyForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false);
        return new DailyForecastViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(DailyForecastViewHolder holder, int position) {

        DailyForecast dailyForecast = mDailyForecasts[position];
        dailyForecast.setDate(dailyForecast.getDate().substring(5));
        if (position < dateName.length) {
            dailyForecast.setDate(dateName[position] + " " + dailyForecast.getDate());
        } else {
            dailyForecast.setDate(
                    DateUtils.getWeekLabel(dailyForecast.getDate()) + " " + dailyForecast.getDate()
            );
        }
        holder.bindView(dailyForecast);
    }

    @Override
    public int getItemCount() {
        if (mDailyForecasts == null) {
            return 0;
        }
        return mDailyForecasts.length;
    }

    public void flush(DailyForecast[] dailyForecasts) {
        this.mDailyForecasts = dailyForecasts;
        notifyItemRangeChanged(0, mDailyForecasts.length);
    }

    class DailyForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDate, mTextViewWeather, mTextViewTemperatureRange;
        private ImageView mImageViewWeather;

        public DailyForecastViewHolder(View itemView) {
            super(itemView);
            mTextViewDate = itemView.findViewById(R.id.text_view_date_daily);
            mTextViewWeather = itemView.findViewById(R.id.text_view_weather_daily);
            mTextViewTemperatureRange = itemView.findViewById(R.id.text_view_temperature_range_daily);
            mImageViewWeather = itemView.findViewById(R.id.image_view_weather_daily);
        }

        public void bindView(DailyForecast dailyForecast) {
            mTextViewDate.setText(dailyForecast.getDate());
            AdapterDataExtractor.showWeatherIcon(
                    mImageViewWeather,
                    dailyForecast.getCond_code_d(),
                    context.getResources().getAssets()
            );
            mTextViewWeather.setText(dailyForecast.getCond_txt_d());
            mTextViewTemperatureRange.setText(
                    String.format("%4s℃~%s℃",
                            dailyForecast.getTmp_min(),
                            dailyForecast.getTmp_max()
                    )
            );
        }
    }
}
