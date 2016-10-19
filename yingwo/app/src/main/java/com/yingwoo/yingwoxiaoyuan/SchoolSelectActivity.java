package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.Adapter.ListSchoolGroupItemAdapter;
import com.yingwoo.yingwoxiaoyuan.model.SchoolListModel;
import com.yingwoo.yingwoxiaoyuan.model.SchoolListModel.InfoBean;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

/**
 * Created by wangyu on 9/2/16.
 */

public class SchoolSelectActivity extends AppCompatActivity {
    @BindView(R.id.school_search)
    EditText schoolSearch;
    @BindView(R.id.back_btn)
    TextView backBtn;
    @BindView(R.id.school_group_list)
    ListView schoolGroupList;

    private AppCompatActivity context;
    private ListSchoolGroupItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_school_select);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        schoolSearch.setHint("输入学校名字");
        getSchoolList();
    }

    @OnClick(R.id.back_btn)
    public void onClick() {
        finish();
    }

    public void getSchoolList() {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.getSchools()
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SchoolListModel>() {
                    @Override
                    public void onCompleted() {
//                        Toast.makeText(SchoolSelectActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SchoolSelectActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SchoolListModel schoolListModel) {
                        initSchoolList(schoolListModel.getInfo());
                    }
                });
    }

    public void initSchoolList(List<InfoBean> data) {
        adapter = new ListSchoolGroupItemAdapter(context, data);
        schoolGroupList.setAdapter(adapter);
    }
}
