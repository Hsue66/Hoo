package com.ourincheon.wazap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.ourincheon.wazap.Retrofit.Alarms;
import com.ourincheon.wazap.Retrofit.Contests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlarmList extends AppCompatActivity {
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    Alarms alarms;
    ArrayList<AlarmData> alarm_list;
    int count;
    AlarmData con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        mListView = (ListView) findViewById(R.id.aList);



        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");

        alarm_list = new ArrayList<AlarmData>();

        loadAlarm(access_token);

        System.out.println("---------------------" + alarm_list.size());

/*
        mAdapter.addItem("qewrqwe",
                "sfdgsdfg",
                "2014-02-18");
        mAdapter.addItem("qewrqwe",
                "werweqrqwe",
                "2014-02-01");
                */
        //mAdapter.addItem(getResources().getDrawable(R.drawable.icon_user),"ewt2342","2014-02-04");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlarmData mData = mAdapter.mListData.get(position);
                Toast.makeText(AlarmList.this, mData.msg_url, Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    void loadAlarm(String access_token)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://come.n.get.us.to/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WazapService service = retrofit.create(WazapService.class);



        Call<Alarms> call = service.getAlarmlist(access_token, 10, 10);
        call.enqueue(new Callback<Alarms>() {
            @Override
            public void onResponse(Response<Alarms> response) {
                if (response.isSuccess() && response.body() != null) {

                    Log.d("SUCCESS", response.message());
                    alarms = response.body();


                    String result = new Gson().toJson(alarms);
                    Log.d("SUCESS-----", result);

                    JSONObject jsonRes;
                    try {
                        jsonRes = new JSONObject(result);
                        JSONArray jsonArr = jsonRes.getJSONArray("data");
                        count = jsonArr.length();
                        System.out.println(count);
                        for (int i = 0; i < count; i++) {
                      //      con = new AlarmData();
                      //      con.setAlramdate(jsonArr.getJSONObject(i).getString("alramdate"));
                       //     con.setMsg(jsonArr.getJSONObject(i).getString("msg"));
                       //     con.setMsg_url(jsonArr.getJSONObject(i).getString("msg_url"));
                       //     System.out.println(alarm_list.size() + "-------------------");
                        //    addItem(con);
                         //   System.out.println(con.getMsg());

                            mAdapter.addItem(jsonArr.getJSONObject(i).getString("msg_url"),jsonArr.getJSONObject(i).getString("msg"),jsonArr.getJSONObject(i).getString("alramdate"));
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                    }

                } else if (response.isSuccess()) {
                    Log.d("Response Body isNull", response.message());
                } else {
                    Log.d("Response Error Body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void addItem(AlarmData alarmData)
    {
        alarm_list.add(alarmData);
        System.out.println("addded");
    }


    private class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<AlarmData> mListData = new ArrayList<AlarmData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(String icon, String msg, String mDate){
            AlarmData addInfo = null;
            addInfo = new AlarmData();
            addInfo.msg = icon;
            addInfo.msg_url = msg;
            addInfo.alramdate = mDate;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mDate = (TextView) convertView.findViewById(R.id.mDate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            AlarmData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.msg);
            holder.mDate.setText(mData.alramdate);

            return convertView;
        }
    }
}
