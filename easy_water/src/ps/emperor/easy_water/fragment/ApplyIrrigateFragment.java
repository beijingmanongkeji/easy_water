package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigationAdapter;
import ps.emperor.easy_water.application.ApplicationFragment;
import ps.emperor.easy_water.entity.ApplyIrrigationBean;
import ps.emperor.easy_water.view.MainActionBar;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 灌溉（应用）
 * 
 * @author 毛国江
 * @version 2016-5-24 上午8:34
 */

@SuppressLint("NewApi")
public class ApplyIrrigateFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private ApplyIrrigationAdapter adapter;
	private List<ApplyIrrigationBean> beans;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("灌溉");
		actionBar.setActionBarOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.list_apply_irrigate_add);
		listView.setOnItemClickListener(this);
		adapter = new ApplyIrrigationAdapter(getActivity());
		beans = new ArrayList<ApplyIrrigationBean>();
		
		ApplyIrrigationBean bean;
		for (int i = 0; i < 18; i++) {
			bean = new ApplyIrrigationBean();
			bean.setUnits("第五大队第四中队第三小队");
			bean.setElement("2#灌溉单元");
			bean.setWhether("正在执行");
			bean.setCurrent_state(180);
			;
			beans.add(bean);
		}
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();

		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplicationFragment fragment = new ApplicationFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
		Bundle bundle = new Bundle();
		bundle.putString("units", beans.get(position).getUnits());
		fragment.setArguments(bundle);
		// transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment, "main");
		transaction.commit();
	}

}
