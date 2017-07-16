package com.prapps.iris.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prapps.iris.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royce on 08-04-2017.
 */

public class MessageListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> messageList;
    private List<Boolean> fromApp;

    public MessageListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        messageList = new ArrayList<>();
        fromApp = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return fromApp.get(position) ? 0 : 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String message = messageList.get(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            int res = 0;
            if (getItemViewType(position) == 1)
                res = R.layout.row_sent;
            else
                res = R.layout.row_received;
            convertView = mInflater.inflate(res, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.position = position;
            viewHolder.content = (TextView) convertView.findViewById(R.id.txtView_message);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();


        viewHolder.content.setText(message);


        return convertView;
    }

    public void add(String message, boolean fromApp) {
        messageList.add(message);
        this.fromApp.add(fromApp);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView content;
        int position;
    }

}
