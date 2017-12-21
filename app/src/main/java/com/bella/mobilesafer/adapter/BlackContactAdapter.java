package com.bella.mobilesafer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.bean.BlackContactInfo;
import com.bella.mobilesafer.database.BlackNumberDao;

import java.util.List;

/**
 * Created by huangyaling on 2017/12/20.
 */
public class BlackContactAdapter extends BaseAdapter {
    private List<BlackContactInfo> contactInfos;
    private Context mContext;
    private BlackNumberDao dao;
    private BlackContactCallBack mCallBack;
    public BlackContactAdapter(List<BlackContactInfo> systemContacts,Context context){
        super();
        this.contactInfos=systemContacts;
        mContext=context;
        dao = new BlackNumberDao(context);
    }

    public void setCallback(BlackContactCallBack callback){
        this.mCallBack=callback;
    }
    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BlackViewHolder holder=null;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.blacknumber_list_item,null);
            holder = new BlackViewHolder();
            holder.mNameTV= (TextView) convertView.findViewById(R.id.tv_list_black_name);
            holder.nModeTV= (TextView) convertView.findViewById(R.id.tv_list_black_mode);
            holder.mDeleteImg= (ImageView) convertView.findViewById(R.id.iv_black_delete);
            holder.mContactImg= (ImageView) convertView.findViewById(R.id.black_icon);
            convertView.setTag(holder);
        }else{
            holder= (BlackViewHolder) convertView.getTag();
        }
        holder.mNameTV.setText(contactInfos.get(position).contactName+"("+contactInfos.get(position).phoneNumber+")");
        holder.nModeTV.setText(contactInfos.get(position).getModeString(contactInfos.get(position).mode));
        holder.mDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean delete = dao.deleteContact(contactInfos.get(position));
                if(delete){
                    contactInfos.remove(contactInfos.get(position));
                    BlackContactAdapter.this.notifyDataSetChanged();
                    if(dao.getTotalNumber()==0){
                        mCallBack.DataSizeChanged();
                    }
                }else{
                    Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    static class BlackViewHolder{
        TextView mNameTV;
        TextView nModeTV;
        ImageView mContactImg;
        ImageView mDeleteImg;
    }

    public interface BlackContactCallBack{
        void DataSizeChanged();
    }
}
