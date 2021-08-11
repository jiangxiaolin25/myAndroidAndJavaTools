//package macc.paxsz.com.myapplication.Androidtool;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.toolstest.R;
//
//import java.util.List;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//
///**
// * 作者：jiangxiaolin on 2019/11/11
// * 邮箱：jiangxiaolin@xgd.com
// * ToDo：
// */
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
//
//    private List<String> mFruitList;
//
//    /**
//     * 初始化字视图界面，并封装成ViewHolder类型，可以通过
//     */
//    static  class ViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView testcasename;
////        TextView fruitctx;
//
//        View fruitview;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            fruitview=itemView; //保存整个itemView
//            //这里需要注解，因为没有对应的视图
////            testcasename = (TextView) itemView.findViewById(R.id.name);
////            fruitctx= (TextView) itemView.findViewById(R.id.content);
////            fruitImg = (ImageView) itemView.findViewById(R.id.image);
//
//        }
//    }
//    public RecyclerAdapter(List fruitList){
//        mFruitList=fruitList;   //要传入的总集合
//    }
//
//    /**
//     * @param pos 删除一个子项
//     */
//    public  void removeData(int pos)
//    {
//        mFruitList.remove(pos);
//        notifyItemRemoved(pos);
//        notifyItemRangeChanged(pos,mFruitList.size());//通知数据与界面重新绑定
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//         Log.v("TAG","onCreateViewHolder"+viewType);
////这里需要注解，因为没有对应的视图
//        View view;
//        view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main,parent,false);
//        final ViewHolder viewHolder =new ViewHolder(view);//实例化viewholder
//        //分别注册整个的监听器
////        viewHolder.fruitview.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                //第一步都是先拿位置
////                int position= viewHolder.getAdapterPosition();
////                //第二步再实例化这个对象
////                String fruit =mFruitList.get(position);
////
////
////            }
////        });
//        viewHolder.fruitview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                final int position =viewHolder.getAdapterPosition();
//                new AlertDialog.Builder(view.getContext())
//                        .setTitle("确定删除测试用例？").setNegativeButton("取消",null)
//                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                removeData(position);
//                            }
//                        }).show();
//                return false;
//            }
//        });
//        //再单独注册图片的监听器
////        viewHolder.fruitImg.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                int pos =viewHolder.getLayoutPosition();
////                Fruit fruit=mFruitList.get(pos);
////                Toast.makeText(view.getContext(),"点的是图片",Toast.LENGTH_SHORT).show();
////
////            }
////        });
//        return viewHolder;
//    }
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
////        Fruit fruit =mFruitList.get(position);
//         Log.v("TAG","onBindViewHolder");
//
//        holder.testcasename.setText(mFruitList.get(position));
////        holder.fruitctx.setText(fruit.getContent());
////        holder.fruitImg.setImageResource(fruit.getImage());
//    }
//
//    @Override
//    public int getItemCount() {
//         Log.v("TAG","getItemCount"+mFruitList.size());
//
//        return mFruitList.size();
//    }
//}
