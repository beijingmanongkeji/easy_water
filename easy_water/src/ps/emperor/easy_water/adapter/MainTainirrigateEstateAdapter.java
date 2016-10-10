package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigateEstateBean;

/**
 * 灌溉组控制
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class MainTainirrigateEstateAdapter extends MyBaseAdapter<MainTainIrrigateEstateBean> implements OnClickListener {

	private Context context;

	public MainTainirrigateEstateAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_irrigate_estate_list, null);
			viewHolder = new ViewHolder();
			viewHolder.valves = (TextView) convertView.findViewById(R.id.text_maintain_irrigate_estate_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MainTainIrrigateEstateBean mainTainIrrigateEstateBean = list.get(position);
		viewHolder.valves.setText(mainTainIrrigateEstateBean.valve);
		return convertView;

	}

	class ViewHolder {
		TextView valves;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
