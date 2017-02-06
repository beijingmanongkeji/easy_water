package ps.emperor.easy_water.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter{    
    private Context mContext;			// 定义Context
    private List<infoList> mImageIds;	// 定义一个向量作为图片源
    private List<Integer> mImage_bs = new ArrayList<Integer>();	// 定义一个向量作为选中与否容器
    private int lastPosition = -1;		//记录上一次选中的图片位置，-1表示未选中任何图片
    private boolean multiChoose;		//表示当前适配器是否允许多选
    private int screenWidth,screenHeigh;
    int mImage = 0;
    
    public ImageAdapter(Context c, boolean isMulti,List<infoList>  mImageId){
    	mContext = c;
    	multiChoose = isMulti;
    	mImageIds = mImageId;
    	
//    	// װ����Դ 
//    	mImageIds.add(R.drawable.item1);
//    	mImageIds.add(R.drawable.item2);
//    	mImageIds.add(R.drawable.item3);
//    	mImageIds.add(R.drawable.item4);
//    	mImageIds.add(R.drawable.item5);
//    	mImageIds.add(R.drawable.item6);
    	
    	for(int i=0; i<mImageIds.size(); i++)
    		mImage_bs.add(0);
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageIds.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		ImageView imageView;
		TextView textView;
        if (convertView == null)
        {
//        	imageView = new ImageView(mContext);		// 给ImageView设置资源
//            imageView.setLayoutParams(new GridView.LayoutParams(110, 120));	// 设置布局图片
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);		// 设置显示比例类型
        	screenWidth = (int) SharedUtils.getParam(mContext, "screenWidth", 0);
        	screenHeigh = (int) SharedUtils.getParam(mContext, "screenHeigh", 0);
        	textView = new TextView(mContext);
        	if(mImage < mImageIds.size()){
        		if(!CheckUtil.IsEmpty(mImageIds.get(position).getIsAllocationGrowers())){
        			if(mImageIds.get(position).getIsAllocationGrowers().equals("1")){
        				mImage_bs.set(position, 2);
        			}  
        		}else if(!CheckUtil.IsEmpty(mImageIds.get(position).getIsAllocationCrop())){
        			if(mImageIds.get(position).getIsAllocationCrop().equals("1")){
        				mImage_bs.set(position, 2);
        			}  
        		}
        		mImage ++;
        	}
            if(screenWidth == 0){
            	textView.setLayoutParams(new GridView.LayoutParams(100, 100));
            }else{
            	if(screenWidth>1080||screenHeigh>1440){
            	textView.setLayoutParams(new GridView.LayoutParams(screenWidth/5-60, 150));
            	}else{
            	textView.setLayoutParams(new GridView.LayoutParams(screenWidth/5-40, 100));
            	}
            }
        }
        else
        {
//        	imageView = (ImageView) convertView;
        	textView = (TextView) convertView;
        }
//        imageView.setImageDrawable(makeBmp(mImageIds.elementAt(position).getGates(),
//        		mImage_bs.elementAt(position)));
        textView.setText(mImageIds.get(position).getChanNum());
        textView.setBackgroundResource(R.drawable.value_on);
        textView.setGravity(Gravity.BOTTOM|Gravity.CENTER);
        textView.setPadding(10, 10, 5, 10);
        if(mImage_bs.get(position)==1){
        	textView.setBackgroundResource(R.drawable.value_selected);
        }else if(mImage_bs.get(position)==2){
        	textView.setBackgroundResource(R.drawable.value_grower);
        }else{
        	textView.setBackgroundResource(R.drawable.value_on);
        }
        return textView;
	}
	
	// 修改选中的״̬
    public void changeState(int position){
    	// 多选
    	if(multiChoose == true){
    		if(mImage_bs.get(position) == 2){
	    		mImage_bs.set(position, 1);	//直接取反即可	
	    	}else if(mImage_bs.get(position) == 1){
    			mImage_bs.set(position, 0);	//直接取反即可	
    		}else if(mImage_bs.get(position) == 0){
    			mImage_bs.set(position, 1);	//直接取反即可	
    		}
    		if((mImage_bs.get(position))== 0){
    			mImageIds.get(position).setIstrue(false);
    		}else if((mImage_bs.get(position))== 1){
    			mImageIds.get(position).setIstrue(true);
    		}
    	}
    	// 单选
    	else{						
	    	if(lastPosition != -1)
	    		mImage_bs.set(lastPosition, 0);	//取消上一次的选中状态
	    	if(mImage_bs.get(position) == 2){
	    		mImage_bs.set(position, 1);	//直接取反即可	
	    	}else if(mImage_bs.get(position) == 1){
    			mImage_bs.set(position, 0);	//直接取反即可	
    		}else if(mImage_bs.get(position) == 0){
    			mImage_bs.set(position, 1);	//直接取反即可	
    		}
	    	lastPosition = position;		//记录本次选中的位置
    	}
    	notifyDataSetChanged();		//通知适配器进行更新
    }
    public void changeAllState(boolean all){
    	if(multiChoose ==true){
    		//��ѡ 
    		if(all){
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.set(i, 1);
    				mImageIds.get(i).setIstrue(true);
    			}
    			
    		}else{
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.set(i, 0);
    				mImageIds.get(i).setIstrue(false);
    			}
    		}
    		notifyDataSetChanged();
    	}
    }
}
