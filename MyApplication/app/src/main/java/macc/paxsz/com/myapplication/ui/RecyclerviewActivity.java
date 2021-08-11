package macc.paxsz.com.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import macc.paxsz.com.myapplication.Androidtool.RecycleViewDivider;
import macc.paxsz.com.myapplication.Androidtool.myrecyclerviewadapte;
import macc.paxsz.com.myapplication.R;
import macc.paxsz.com.myapplication.assit.recycview;

import static macc.paxsz.com.myapplication.MyApplication.mContext;

/**
 * Created by jiangxiaolin on 2021/8/5.
 */

public class RecyclerviewActivity extends AppCompatActivity implements recycview {
    myrecyclerviewadapte myrecyclerviewadapte;
    private static final List<String> myper = new ArrayList<String>();
    RecyclerView recycler;
    RecycleViewDivider mRecycleViewDivider;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycycleviewactivity);
        recycler = (RecyclerView) findViewById(R.id.recyc_1);
        mRecycleViewDivider = new RecycleViewDivider(RecyclerviewActivity.this, LinearLayoutManager.HORIZONTAL);
        ListAddData();
        myrecyclerviewadapte = new myrecyclerviewadapte(myper, RecyclerviewActivity.this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
//        recycler.setItemAnimator(mRecycleViewDivider);
        recycler.addItemDecoration(mRecycleViewDivider);
        recycler.setAdapter(myrecyclerviewadapte);

        //为RecyclerView添加HeaderView和FooterView
        setHeaderView(recycler);
        setFooterView(recycler);



    }


    public void ListAddData() {
        myper.add("开始一个界面");
        myper.add("开始个界面");
        myper.add("132");
        myper.add("456");
        myper.add("789");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");
        myper.add("开始一个界面");

    }

    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.recyclyheadhead, view, false);
        myrecyclerviewadapte.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.recyclyheadroot, view, false);
        myrecyclerviewadapte.setFooterView(footer);
    }


    @Override
    public void itemOnClickListener(int pos) {
        switch (pos) {
            case 0:
//                Intent intent = new Intent(RecyclerviewActivity.this, SystoolTestActivity.class);
//                intent.setAction("com.pax.intent.action.SystoolTestActivity");
//                intent.addCategory("com.pax.intent.action.SystoolTestActivity");
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "你选的文件数值为：", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Intent intent = new Intent(RecyclerviewActivity.this, SystoolTestActivity.class);
                intent.setAction("com.pax.intent.action.SystoolTestActivity");
                intent.addCategory("com.pax.intent.action.SystoolTestActivity");
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Recyclerview Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
