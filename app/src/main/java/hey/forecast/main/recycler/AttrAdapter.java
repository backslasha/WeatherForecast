package hey.forecast.main.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hey.forecast.R;
import hey.forecast.entity.Now;


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
        if (keys == null || values == null || units == null) {
            Toast.makeText(context, R.string.netError, Toast.LENGTH_SHORT).show();
            return;
        }


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


    static final String[] json_keys_now = new String[]{
            "fl",
            "wind_dir", "wind_sc",
            "wind_spd", "hum", "pcpn",
            "pres", "vis"
    };
    public static final String[] keys_now = new String[]{
            "体感温度",
            "风向", "风力",
            "风速", "相对湿度", "降水量",
            "大气压强", "能见度"
    };
    public static final String[] units_now = new String[]{
            "℃",
            "", "",
            "公里/小时", "%", "",
            "", "公里"
    };

    public static String[] extractValues(Now now) {
        String[] values = new String[keys_now.length];
        Method[] declaredMethods = Now.class.getDeclaredMethods();
        for (int i = 0; i < json_keys_now.length; i++) {
            String methodName = "get" + json_keys_now[i];
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equalsIgnoreCase(methodName)) {
                    try {
                        values[i] = (String) declaredMethod.invoke(now);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return values;
    }
}
