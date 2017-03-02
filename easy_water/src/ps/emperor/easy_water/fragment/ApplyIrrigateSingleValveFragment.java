package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateSingleValveAdapter;
import ps.emperor.easy_water.adapter.ImageAdapterGroup;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean.groupList;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

/**
 * 单阀显示
 * 
 * @author 毛国江
 * @version 2016-5-26 上午11:34
 */
@SuppressLint("NewApi")
public class ApplyIrrigateSingleValveFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
	private List<infoList> beens;
	private List<groupList> beans;
	private ApplyIrrigateSingleValveAdapter adapter2;
	private String units;
	private ProgressDialog progressDialog;
	private TextView irriUnit;
	private LinearLayout linearLayout_gridtableLayout;
	private FrameLayout frameLayout_gridtableLayout;

	// private DBHelper dbHelper;
	// private SingleValue singleValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater
				.inflate(R.layout.fragment_apply_irrigate_single_valve,
						container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigat_single_valve);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("切换");
		actionBar.setTitle("单阀显示");
		actionBar.setActionBarOnClickListener(this);

		units = getArguments().getString("units");
		// dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		linearLayout_gridtableLayout = (LinearLayout) view.findViewById(R.id.linearLayout_gridtableLayout_single);
		frameLayout_gridtableLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout_gridtableLayout_single);
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos_single);
		gridView.setOnItemClickListener(this);
		adapter2 = new ApplyIrrigateSingleValveAdapter(getActivity());
		irriUnit = (TextView) view
				.findViewById(R.id.text_apply_irriagte_single_valve_unit);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		// for (int i = 0; i < beans.size(); i++) {
		// singleValue = new SingleValue();
		// singleValue.setValueID(beans.get(i).getValve());
		// dbHelper.saveValue(singleValue);
		// }
		return view;

	}

	private void init() {
		String str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		String str2 = "";
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
			str2 = java.net.URLEncoder.encode("4", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriUnitChan + str1
				+ "/" + str2); // 网址(请替换成实际的网址)
		// params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request2 = new JSONObject();
		try {
			param3.setAsJsonContent(true);
		} catch (Exception e) {
			e.printStackTrace();
			param3.setAsJsonContent(true);
		}// 根据实际需求添加相应键值对

		x.http().request(HttpMethod.GET, param3, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {

			}

			// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
			// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG)
						.show();
				if (ex instanceof HttpException) { // 网络错误 
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					// ...
					progressDialog.dismiss();
				} else { // 其他错误 
					// ...
					progressDialog.dismiss();
				}

			}

			// 不管成功或者失败最后都会回调该接口
			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Gson gson = new Gson();
				ApplyIrrigateSingleValveBean fromJson = gson.fromJson(arg0,
						ApplyIrrigateSingleValveBean.class);
				beens = fromJson.getAuthNameList();
				beans = fromJson.getGroupList();
					if (!CheckUtil.IsEmpty(beens)) {
						int[] array = new int[beens.size()];
						for (int i = 0; i < array.length; i++) {
							array[i] = Integer.valueOf(beens.get(i).getTotalChanNum());
						}
						if (!CheckUtil.IsEmpty(beens)) {
							int temp;
							for (int i = 0; i < array.length; i++) {
								for (int j = i + 1; j < array.length; j++) {
									if (array[i] < array[j]) {
										temp = array[i];
										array[i] = array[j];
										array[j] = temp; // 两个数交换位置
									}
								}
							}
							for (int i = 0; i < beens.size(); i += array[0]) {
								if (Integer.valueOf(beens.get(i).getTotalChanNum()) < array[0]) {
									for (int j = 0; j < array[0]
											- Integer.valueOf(beens.get(i)
													.getTotalChanNum()); j++) {
										infoList infoList = new infoList();
										infoList.setChanNum("");
										infoList.setGroupName("");
										infoList.setTotalChanNum(beens.get(i)
												.getTotalChanNum());
										beens.add(
												i
														+ Integer.valueOf(beens.get(i)
																.getTotalChanNum()),
												infoList);
									}
								}
							}
							adapter2.addData(beens, true);
						}
						if (array[0] == 5) {
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 360);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 1){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 72);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 2){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 144);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 3){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 216);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 4){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 288);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 6){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 432);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 7){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 504);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 8){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 576);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 9){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 648);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 10){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 720);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 11){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 792);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 12){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 864);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 13){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 936);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 14){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 1008);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}else if(array[0] == 15){
							FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
									.getLayoutParams();
							int weight = DensityUtil.dip2px(getActivity(), 1080);
							layoutParams.width = weight;
							linearLayout_gridtableLayout.setLayoutParams(layoutParams);
							linearLayout_gridtableLayout.requestLayout();
							gridView.setLayoutParams(layoutParams);
						}
						gridView.setNumColumns(array[0]);
						gridView.setAdapter(adapter2);
					gridView.setPadding(10, 10, 5, 10);
				}
				if (!CheckUtil.IsEmpty(units)) {
					irriUnit.setText(units);
				} else {
					irriUnit.setText("");
				}
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			if (!CheckUtil.IsEmpty(beans)) {
				if (CheckUtil.IsEmpty(beans.get(0).getGroupNum())
						|| "0".equals(beans.get(0).getGroupNum())) {
					Toast.makeText(getActivity(), "请进行灌溉维护后再尝试执行此操作！",
							Toast.LENGTH_LONG).show();
				} else {
					ApplyIrrigateControlFragment fragment1 = new ApplyIrrigateControlFragment();
					Bundle bundle1 = new Bundle();
					bundle1.putString("units", units);
					fragment1.setArguments(bundle1);
					transaction.setCustomAnimations(
							R.anim.slide_fragment_horizontal_left_in,
							R.anim.slide_fragment_horizontal_right_out);
					transaction.replace(R.id.fl, fragment1, "main");
					transaction.commit();
				}
			} else {
				Toast.makeText(getActivity(), "请进行灌溉维护后再尝试执行此操作！",
						Toast.LENGTH_LONG).show();
			}

			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateControlValveFragment fragment2 = new ApplyIrrigateControlValveFragment();
		Bundle bundle = new Bundle();
		bundle.putString("units", units);
		SharedUtils.setParam(getActivity(), "ValueControlChanID",
				beens.get(arg2).getValueControlChanID() + "");
		SharedUtils.setParam(getActivity(), "ChanNum", beens.get(arg2)
				.getChanNum());
		fragment2.setArguments(bundle);
		transaction.setCustomAnimations(
				R.anim.slide_fragment_horizontal_left_in,
				R.anim.slide_fragment_horizontal_right_out);
		transaction.replace(R.id.fl, fragment2, "main");
		transaction.commit();
	}

}
