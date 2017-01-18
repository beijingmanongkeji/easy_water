package ps.emperor.easy_water.adapter;


import android.content.Context;


import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.UserReleDisInfoBean.infoList;

/**
 * 维护灌溉/配水
 * @author 毛国江
 * @version 2016-6-2 下午14:06
 */
public class MainTainDisAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;

	public MainTainDisAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_irrigate_list, null);
			viewHolder = new ViewHolder();
			viewHolder.maintain = (TextView) convertView.findViewById(R.id.text_maintain_units);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		viewHolder.maintain.setText(infoList.getDisEquName());
		return convertView;

	}

	class ViewHolder {
		TextView maintain;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
