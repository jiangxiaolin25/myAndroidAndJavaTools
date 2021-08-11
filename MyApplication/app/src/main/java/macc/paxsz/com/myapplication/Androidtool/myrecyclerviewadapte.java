package macc.paxsz.com.myapplication.Androidtool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import macc.paxsz.com.myapplication.R;
import macc.paxsz.com.myapplication.assit.recycview;
import macc.paxsz.com.myapplication.ui.MainActivity;


/**
 * 作者：jiangxiaolin on 2020/6/30
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
    public class  myrecyclerviewadapte extends RecyclerView.Adapter<myrecyclerviewadapte.ViewHolder> {

        private List<String> mFruitList;
        private recycview mrecycview;
    private View mHeaderView;
    private View mFooterView;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
         * 初始化字视图界面，并封装成ViewHolder类型，可以通过
         */
          class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView testcasename;
            View fruitview;

            public ViewHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView){
                    return;
                }
                if (itemView == mFooterView){
                    return;
                }
                fruitview=itemView; //保存整个itemView
                testcasename = (TextView) itemView.findViewById(R.id.BTre_1);

            }
        }
        public myrecyclerviewadapte(List fruitList,recycview recycview){
            mFruitList=fruitList;   //要传入的总集合
            mrecycview=recycview;
        }

        /**
         * @param pos 删除一个子项
         */
        public  void removeData(int pos)
        {
            mFruitList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,mFruitList.size());//通知数据与界面重新绑定
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             Log.v("TAG","onCreateViewHolder"+viewType);
            //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
            if(mHeaderView != null && viewType == TYPE_HEADER) {
                return new ViewHolder(mHeaderView);
            }
            if(mFooterView != null && viewType == TYPE_FOOTER){
                return new ViewHolder(mFooterView);
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycycleitemview,parent,false);
            final ViewHolder viewHolder =new ViewHolder(view);//实例化viewholder
            //分别注册整个的监听器
    //        viewHolder.fruitview.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                //第一步都是先拿位置
    //                int position= viewHolder.getAdapterPosition();
    //                //第二步再实例化这个对象
    //                String fruit =mFruitList.get(position);
    //
    //
    //            }
    //        });
            viewHolder.fruitview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int position =viewHolder.getAdapterPosition();
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("确定删除测试用例？").setNegativeButton("取消",null)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    removeData(position);
                                }
                            }).show();
                    return false;
                }
            });
            //再单独注册图片的监听器
            viewHolder.testcasename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos =viewHolder.getLayoutPosition();
//                    String fruit=mFruitList.get(pos);
                    mrecycview.itemOnClickListener(pos);




                }
            });
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(getItemViewType(position) == TYPE_NORMAL){
                if(holder instanceof ViewHolder) {
                    //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                    ((ViewHolder) holder).testcasename.setText(mFruitList.get(position-1));
                    return;
                }
                return;
            }else if(getItemViewType(position) == TYPE_HEADER){
                return;
            }else{
                return;
            }



//            String fruit =mFruitList.get(position);
//             Log.v("TAG","onBindViewHolder");
//            holder.testcasename.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            holder.testcasename.setText(mFruitList.get(position));
        }

        @Override
        public int getItemCount() {
             Log.v("TAG","getItemCount"+mFruitList.size());
            if(mHeaderView == null && mFooterView == null){
                return mFruitList.size();
            }else if(mHeaderView == null && mFooterView != null){
                return mFruitList.size() + 1;
            }else if (mHeaderView != null && mFooterView == null){
                return mFruitList.size() + 1;
            }else {
                return mFruitList.size() + 2;
            }
        }
    }

