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
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.model.AcademyListModel.InfoBean;
import com.yingwoo.yingwoxiaoyuan.model.AcademyListModel.InfoBean.DataBean;
import com.yingwoo.yingwoxiaoyuan.utils.ListViewUtil;

import java.util.ArrayList;
import java.util.List;

public class ListAcademyGroupItemAdapter extends BaseAdapter {
    private List<InfoBean> objects = new ArrayList<InfoBean>();
    private ListSchoolListItemAdapter adapter;
    private String school_name;
    private String school_id;

    private AppCompatActivity context;
    private LayoutInflater layoutInflater;

    public ListAcademyGroupItemAdapter(AppCompatActivity context, List<InfoBean> objects, String school_name, String school_id) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
        this.school_name = school_name;
        this.school_id = school_id;
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
        holder.tvAcademyGroup.setText(object.getGroup());
        adapter = new ListSchoolListItemAdapter(context,object.getData());
        final List<DataBean> objects = object.getData();
        holder.academyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBean bean = objects.get(position);
                String academy_id = bean.getId();
                String academy_name = bean.getName();
                Intent intent = new Intent(context, MakeupinfoActivity.class);
                intent.putExtra("academy_id",academy_id);
                intent.putExtra("academy_name",academy_name);
                intent.putExtra("school_name",school_name);
                intent.putExtra("school_id",school_id);
                if (context instanceof AppCompatActivity) {
                    context.setResult(MakeupinfoActivity.ACADEMY_RES,intent);
                    context.finish();
                }
            }
        });
        holder.academyList.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(holder.academyList);
    }

    protected class ViewHolder {
        private TextView tvAcademyGroup;
        private ListView academyList;

        public ViewHolder(View view) {
            tvAcademyGroup = (TextView) view.findViewById(R.id.tv_school_group);
            academyList = (ListView) view.findViewById(R.id.school_list);
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
            holder.tvAcademyGroupItem.setText(object.getName());
        }

        protected class ViewHolder {
            private TextView tvAcademyGroupItem;

            public ViewHolder(View view) {
                tvAcademyGroupItem = (TextView) view.findViewById(R.id.tv_school_group_item);
            }
        }
    }

}
