package com.ourincheon.wazap;

/**
 * Created by hsue on 16. 2. 25.
 */
import java.text.Collator;
import java.util.Comparator;

import android.graphics.drawable.Drawable;

public class AlarmData {


    public Drawable mIcon;
    int alarm_id;
    String msg;
    String msg_url;
    String alramdate;
    int is_check;

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg_url() {
        return msg_url;
    }

    public void setMsg_url(String msg_url) {
        this.msg_url = msg_url;
    }

    public String getAlramdate() {
        return alramdate;
    }

    public void setAlramdate(String alramdate) {
        this.alramdate = alramdate;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }
//public String mTitle;

    //public String mDate;


    /*
    public static final Comparator<AlarmData> ALPHA_COMPARATOR = new Comparator<AlarmData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, AlarmData mListDate_2) {
            return sCollator.compare(mListDate_1.mTitle, mListDate_2.mTitle);
        }
    };
    */
}
