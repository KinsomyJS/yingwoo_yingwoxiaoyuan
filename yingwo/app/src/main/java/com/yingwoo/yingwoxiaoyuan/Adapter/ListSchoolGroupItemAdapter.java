package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.MakeupinfoActivity;
import com.yingwoo.yingwoxiaoyuan.model.SchoolListModel.InfoBean;
import com.yingwoo.yingwoxiaoyuan.model.SchoolListModel.InfoBean.DataBean;
import com.yingwoo.yingwoxiaoyuan.utils.ListViewUtil;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.ArrayList;
import java.util.List;

public class ListSchoolGroupItemAdapter extends BaseAdapter {
    private List<InfoBean> objects = new ArrayList<InfoBean>();
    private ListSchoolListItemAdapter adapter;

    private AppCompatActivity context;
    private LayoutInflater layoutInflater;

    public ListSchoolGroupItemAdapter(AppCompatActivity context, List<InfoBean> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public InfoBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_school_group_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((InfoBean)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final InfoBean object, ViewHolder holder) {
        //TODO implement
        holder.tvSchoolGroup.setText(object.getGroup());
        adapter = new ListSchoolListItemAdapter(context,object.getData());
        final List<DataBean> objects = object.getData();
        holder.schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBean bean = objects.get(position);
                String school_id = bean.getId();
                String school_name = bean.getName();
                Intent intent = new Intent(context, MakeupinfoActivity.class);
                intent.putExtra("school_id",school_id);
                intent.putExtra("school_name",school_name);
                if (context instanceof AppCompatActivity) {
                    context.setResult(MakeupinfoActivity.SCHOOL_RES,intent);
                    context.finish();
                }
            }
        });
        holder.schoolList.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(holder.schoolList);
    }

    protected class ViewHolder {
        private TextView tvSchoolGroup;
        private ListView schoolList;

        public ViewHolder(View view) {
            tvSchoolGroup = (TextView) view.findViewById(R.id.tv_school_group);
            schoolList = (ListView) view.findViewById(R.id.school_list);
        }
    }

    public class ListSchoolListItemAdapter extends BaseAdapter{

        private List<DataBean> objects = new ArrayList<DataBean>();

        private Context context;
        private LayoutInflater layoutInflater;

        public ListSchoolListItemAdapter(Context context,List<DataBean> objects) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public DataBean getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_school_list_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((DataBean)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(DataBean object, ViewHolder holder) {
            //TODO implement
            holder.tvSchoolGroupItem.setText(object.getName());
        }

        protected class ViewHolder {
            private TextView tvSchoolGroupItem;

            public ViewHolder(View view) {
                tvSchoolGroupItem = (TextView) view.findViewById(R.id.tv_school_group_item);
            }
        }
    }

}
