package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ImageAdapterGroup;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.groupList;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 轮灌组维护（应用）
 * 
 * @author 毛国江
 * @version 2016-6-7 下午13:47
 */

@SuppressLint("NewApi")
public class MainTainIrrigationInfoFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
	// private PopupWindow popupWindow;
	private DBHelper dbHelper;
	ImageAdapterGroup adapter;
	private List<String> infoBeans;
	private RelativeLayout layout_irriagte_group;
	private Button btn_main_irrigate_info_group;
	private Button btn_image_cancel, btn_image_choose;// 全选、取消 隐藏
	private List<IrrigationGroup> irrigationGroups;
	private IrrigationGroup irrigationGroup;
	private String units;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private List<groupList> beans;
	private List<Irrigation> irrigation;
	private TextView text_maintain_irrigat_round;
	private FrameLayout frameLayout_gridtableLayout;
	private LinearLayout linearLayout_gridtableLayout;
	Long deleteId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_maintain_irrigation_info, container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_main_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("轮灌组维护");

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		units = (String) SharedUtils.getParam(getActivity(), "units", 1 + "");
		btn_main_irrigate_info_group = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_group);
		btn_main_irrigate_info_group.setOnClickListener(this);
		text_maintain_irrigat_round = (TextView) view
				.findViewById(R.id.text_maintain_irrigat_round);
		irrigationGroups = dbHelper.loadGroupByUnits(units);

		// for (int i = 0; i < 6; i++) {
		// MainTainIrrigationInfoBean bean = new MainTainIrrigationInfoBean();
		// bean.setGate("1-1");
		// beans.add(bean);
		// }
		
		frameLayout_gridtableLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout_gridtableLayout);
		linearLayout_gridtableLayout = (LinearLayout) view.findViewById(R.id.linearLayout_gridtableLayout);
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos);
		
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
		gridView.setOnItemClickListener(this);

		// beans = adapter.getData();
		actionBar.setActionBarOnClickListener(this);
		layout_irriagte_group = (RelativeLayout) view
				.findViewById(R.id.text_maintain_irrigat_info_round_of_irrigation_group);
		layout_irriagte_group.setOnClickListener(this);
		btn_image_cancel = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_allcanel);
		btn_image_choose = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_all);
		btn_image_cancel.setVisibility(View.GONE);
		btn_image_choose.setVisibility(View.GONE);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		infoBeans = new ArrayList<String>();
		return view;
	}

	private void init() {
		String str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str2 = "";
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
			str2 = java.net.URLEncoder.encode("3", "UTF-8");
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
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
					// ...
					progressDialog.dismiss();
				} else { // 其他错误 
					// ...
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
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
				MainTainIrrigationInfoBean fromJson = gson.fromJson(arg0,
						MainTainIrrigationInfoBean.class);
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
					}
					adapter = new ImageAdapterGroup(getActivity(), true, beens);
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
					gridView.setAdapter(adapter);
					text_maintain_irrigat_round.setText(beans.get(0)
							.getGroupNum());
				}
				if (!CheckUtil.IsEmpty(irrigationGroups)) {
					deleteId = irrigationGroups.get(0).getId();
					if (irrigationGroups.size() != Integer.valueOf(beans.get(0)
							.getGroupNum())) {
						for (int i = 0; i < irrigationGroups.size(); i++) {
							dbHelper.deleteGroupId(deleteId);
							deleteId++;
						}
					}
				}
				if (CheckUtil.IsEmpty(irrigationGroups)
						|| irrigationGroups.size() != Integer.valueOf(beans
								.get(0).getGroupNum())) {
					for (int i = 1; i <= Integer.valueOf(beans.get(0)
							.getGroupNum()); i++) {
						irrigationGroup = new IrrigationGroup();
						irrigationGroup.setIrrigation(units);
						irrigationGroup.setMatchedNum(i);
						dbHelper.saveGroup(irrigationGroup);
					}
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
			MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;

		case R.id.acitionbar_right:
			int area = 0;
			infoBeans = new ArrayList<String>();
			for (int i = 0; i < beens.size(); i++) {
				if (!CheckUtil.IsEmpty(beens.get(i).getIstrue())) {
					if (beens.get(i).getIstrue() == true
							&& !beens.get(i).getIsAllocationGroup().equals("1")) {
						area += Float.valueOf(beens.get(i).getArea());
						infoBeans.add(beens.get(i).getValueControlChanID());
					}
				}
			}
			irrigation = dbHelper.loadContinue(units);
			int irriagte_group = irrigation.get(0).getGroupnumber();
			int irriagte_value = irrigation.get(0).getValuenumber();
			if (CheckUtil.IsEmpty(infoBeans)) {
				Toast.makeText(getActivity(), "并未选中任何阀门！", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (infoBeans.size() > irriagte_value) {
					Toast.makeText(getActivity(), "已超出最大阀门总数！",
							Toast.LENGTH_SHORT).show();
				} else if (Integer.valueOf(beans.get(0).getGroupNum()) + 1 > irriagte_group) {
					Toast.makeText(getActivity(), "已超出最大轮灌组数 无法进行编组！",
							Toast.LENGTH_SHORT).show();
				} else if (!NetStatusUtil.isNetValid(getActivity())) {
					Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
							Toast.LENGTH_SHORT).show();
				} else {
					RequestParams param2 = new RequestParams(
							URL.addChanGroupInfo); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request = new JSONObject();
					try {
						param2.setAsJsonContent(true);
						String str1 = (String) SharedUtils.getParam(
								getActivity(), "FirstDerviceID", "");
						;
						try {
							str1 = java.net.URLEncoder.encode(str1, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						js_request.put("firstDerviceID", str1);
						js_request.put("valueControlChanID", infoBeans);
						js_request.put("groupNum", beans.get(0).getGroupNum());
						js_request.put("area", area);
						param2.setBodyContent(js_request.toString());
					} catch (Exception e) {
						e.printStackTrace();
						param2.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.POST, param2,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
										String errorResult = httpEx.getResult();
										Toast.makeText(getActivity(), "请求失败",
												Toast.LENGTH_SHORT);
										// ...
										progressDialog.dismiss();
									} else { // 其他错误 
										// ...
										Toast.makeText(getActivity(), "请求失败",
												Toast.LENGTH_SHORT);
										progressDialog.dismiss();
									}

								}

								// 不管成功或者失败最后都会回调该接口
								@Override
								public void onFinished() {
								}

								@Override
								public void onSuccess(String arg0) {
									progressDialog.dismiss();
									init();
								}
							});
				}
			}
			break;
		case R.id.text_maintain_irrigat_info_round_of_irrigation_group:
			MainTainPresentIrrigateFragment fragment1 = new MainTainPresentIrrigateFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_left_in,
					R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment1, "main");
			transaction.commit();
			break;
		case R.id.btn_main_irrigate_info_group:// 重置编组信息
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (!CheckUtil.IsEmpty(beens)) {
					String str1 = (String) SharedUtils.getParam(getActivity(),
							"FirstDerviceID", "");
					try {
						str1 = java.net.URLEncoder.encode(str1, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					RequestParams param2 = new RequestParams(URL.queryIrriPlan
							+ str1); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request = new JSONObject();
					try {
						param2.setAsJsonContent(true);
						param2.setBodyContent(js_request.toString());
					} catch (Exception e) {
						e.printStackTrace();
						param2.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.GET, param2,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
										String errorResult = httpEx.getResult();
										Toast.makeText(getActivity(), "请求失败",
												Toast.LENGTH_SHORT);
										// ...
										progressDialog.dismiss();
									} else { // 其他错误 
										// ...
										Toast.makeText(getActivity(), "请求失败",
												Toast.LENGTH_SHORT);
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
									progressDialog.dismiss();
									new AlertDialog.Builder(getActivity())
											.setTitle("系统提示")
											// 设置对话框标题

											.setMessage(
													"重置轮灌组信息将删除当前所有已编组的阀门信息！您确认要重置轮灌组吗？")
											// 设置显示的内容

											.setPositiveButton(
													"确定",
													new DialogInterface.OnClickListener() {// 添加确定按钮

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {// 确定按钮的响应事件

															RequestParams param2 = new RequestParams(
																	URL.resetIrriGroupInfo); // 网址(请替换成实际的网址)
															// params.addQueryStringParameter("key",
															// "value"); //
															// 参数(请替换成实际的参数与值)
															progressDialog = ProgressDialog
																	.show(getActivity(),
																			"Loading...",
																			"Please wait...",
																			true,
																			false);
															JSONObject js_request = new JSONObject();
															try {
																param2.setAsJsonContent(true);
																String str1 = (String) SharedUtils
																		.getParam(
																				getActivity(),
																				"FirstDerviceID",
																				"");
																;
																try {
																	str1 = java.net.URLEncoder
																			.encode(str1,
																					"UTF-8");
																} catch (UnsupportedEncodingException e1) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	e1.printStackTrace();
																}
																js_request
																		.put("firstDerviceID",
																				str1);
																param2.setBodyContent(js_request
																		.toString());
															} catch (Exception e) {
																e.printStackTrace();
																param2.setAsJsonContent(true);
															}// 根据实际需求添加相应键值对

															x.http()
																	.request(
																			HttpMethod.PUT,
																			param2,
																			new CommonCallback<String>() {
																				@Override
																				public void onCancelled(
																						CancelledException arg0) {

																				}

																				// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
																				// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
																				@Override
																				public void onError(
																						Throwable ex,
																						boolean isOnCallback) {

																					Toast.makeText(
																							x.app(),
																							ex.getMessage(),
																							Toast.LENGTH_LONG)
																							.show();
																					if (ex instanceof HttpException) { // 网络错误
																														// 
																						HttpException httpEx = (HttpException) ex;
																						int responseCode = httpEx
																								.getCode();
																						String responseMsg = httpEx
																								.getMessage();
																						String errorResult = httpEx
																								.getResult();
																						// ...
																						progressDialog
																								.dismiss();
																					} else { // 其他错误
																								// 
																						// ...
																						progressDialog
																								.dismiss();
																					}

																				}

																				// 不管成功或者失败最后都会回调该接口
																				@Override
																				public void onFinished() {
																				}

																				@Override
																				public void onSuccess(
																						String arg0) {
																					progressDialog
																							.dismiss();
																					init();
																				}
																			});
														}

													})
											.setNegativeButton(
													"返回",
													new DialogInterface.OnClickListener() {// 添加返回按钮

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {// 响应事件

															// TODO
															// Auto-generated
															// method stub

															dialog.dismiss();
														}

													}).show();// 在按键响应事件中显示此对话框
								}
							});
				}
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		adapter.changeState(position);
	}
}