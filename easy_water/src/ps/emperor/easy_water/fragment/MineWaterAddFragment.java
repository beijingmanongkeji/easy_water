package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.IrrigationAddAdapter;
import ps.emperor.easy_water.adapter.IrrigationAddDisAdapter;
import ps.emperor.easy_water.entity.KeyWordBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;
import ps.emperor.easy_water.entity.UserReleIrrInfoBeanAdd;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 添加配水設備
 * 
 * @author 毛國江
 * @version 2016-5-18 上午9:42
 */
@SuppressLint("NewApi")
public class MineWaterAddFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private IrrigationAddDisAdapter adapter;
	private TextView irrigation_add, irrigation_canel;
	private ImageView image_irrigation_add;
	private EditText ed_irrigation_add;
	private List<infoList> beens;
	private List<String> list;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_irrigation_add,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_irrigation_add);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("关联新配水设备");
		actionBar.setActionBarOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.list_irrigation_add);// 关联设备列表
		irrigation_add = (TextView) view.findViewById(R.id.text_irrigation_add);// 关联选中设备
		irrigation_canel = (TextView) view
				.findViewById(R.id.text_irrigation_canel);// 清空当前选中
		irrigation_add.setOnClickListener(this);
		irrigation_canel.setOnClickListener(this);
		image_irrigation_add = (ImageView) view.findViewById(R.id.image_irrigation_add);
		ed_irrigation_add = (EditText) view.findViewById(R.id.ed_irrigation_add);
		image_irrigation_add.setOnClickListener(this);
		
		// checkBox.setOnCheckedChangeListener(listener1);
		adapter = new IrrigationAddDisAdapter(getActivity());
		String str1 = "";
		try {
			str1 = java.net.URLEncoder.encode("3","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findUserNoReleDisInfo+str1);  // 网址(请替换成实际的网址) 
//		 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request2 = new JSONObject();
		try {
			param3.setAsJsonContent(true);
		} catch (Exception e) {
			e.printStackTrace();
			param3.setAsJsonContent(true);
		}//根据实际需求添加相应键值对
		
	        x.http().request(HttpMethod.GET ,param3, new CommonCallback<String>() {  
	            @Override  
	            public void onCancelled(CancelledException arg0) {  
	                  
	            }  
	  
	         // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
	            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
	            @Override  
	            public void onError(Throwable ex, boolean isOnCallback) {  
	                  
	                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
	                if (ex instanceof HttpException) { // 网络错误    
	                    HttpException httpEx = (HttpException) ex;  
	                    int responseCode = httpEx.getCode();  
	                    String responseMsg = httpEx.getMessage();  
	                    String errorResult = httpEx.getResult();  
	                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
	                    // ...  
	                    progressDialog.dismiss();
	                } else { // 其他错误    
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
	                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
	                  Gson gson = new Gson();
	                  System.out.println(arg0);
	                  UserReleDisInfoBeanAdd fromJson = gson.fromJson(arg0, UserReleDisInfoBeanAdd.class);
//	                  authorizedBeen = new AuthorizedBeen();
//	                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
	                  beens = fromJson.getAuthNameList();
	                  for (infoList authNameListBean : beens) {
	                	authNameListBean.getAuthName();
					}
	                  adapter.addData(beens, true);
	                  listView.setAdapter(adapter);
	                  progressDialog.dismiss();
	            }  
	        }); 
//		adapter.addData(beans, false);
//		listView.setAdapter(adapter);
//		beans = adapter.getData();
		return view;
	}


	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineWaterDistributionFragment fragment = new MineWaterDistributionFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.text_irrigation_add:
			RequestParams param2 = new RequestParams(URL.disEquID);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("userID", "3");
				list = new ArrayList<String>();
				for (int i = 0; i < beens.size(); i++) {
					if(beens.get(i).isCheck==true){
						list.add(beens.get(i).getDisEquID());
					}
				}
//				String[] array = new String[list.size()];
//				for (int i = 0; i < list.size(); i++) {
//					array[i] = list.get(i);
//				}
				js_request.put("disEquID", list);
				param2.setBodyContent(js_request.toString());
			} catch (Exception e) {
				e.printStackTrace();
				param2.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.POST ,param2, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		         // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误    
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                    progressDialog.dismiss();
		                } else { // 其他错误    
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
		                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
		                  Gson gson = new Gson();
		                  System.out.println(arg0);
		                  Toast.makeText(getActivity(), "关联成功", Toast.LENGTH_SHORT).show();
		                  progressDialog.dismiss();
		            }  
		        }); 
			break;
		case R.id.text_irrigation_canel:
			for (int i = 0; i < beens.size(); i++) {
				beens.get(i).isCheck = false;
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.image_irrigation_add:
			String str1 = "";
			String str2 = "";
			try {
				str1 = java.net.URLEncoder.encode("3","UTF-8");
				str2 = java.net.URLEncoder.encode(ed_irrigation_add.getText().toString(),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(CheckUtil.IsEmpty(str2)){
				Toast.makeText(getActivity(), "请输入需要查询的关键字！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param3 = new RequestParams(URL.userNoReleDisInfo+str1+"/"+str2);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request2 = new JSONObject();
			try {
				param3.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param3.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param3, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		         // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误    
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                    progressDialog.dismiss();
		                } else { // 其他错误    
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
		                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
		                  Gson gson = new Gson();
		                  System.out.println(arg0);
		                  UserReleDisInfoBeanAdd fromJson = gson.fromJson(arg0, UserReleDisInfoBeanAdd.class);
//		                  authorizedBeen = new AuthorizedBeen();
//		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  for (infoList authNameListBean : beens) {
		                	authNameListBean.getAuthName();
						}
		                  adapter.addData(beens, true);
		                  listView.setAdapter(adapter);
		                  progressDialog.dismiss();
		            }  
		        }); 
			}
			break;
		default:
			break;
		}
	}

}
