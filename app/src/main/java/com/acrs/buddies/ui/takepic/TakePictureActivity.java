package com.acrs.buddies.ui.takepic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.acrs.buddies.R;
import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.medicineadd.CitizenModel;
import com.acrs.buddies.ui.medicineadd.CustomAdapter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class TakePictureActivity extends BaseActivity implements TakePictureView {


    @Inject
    TakePicture_i_Presenter<TakePictureView> presenter;
    private Button submit;
    private Spinner spinner;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        getActivityComponent().inject(this);
        presenter.onAttach(this);
        initialize();
        getSupportActionBar().setTitle("Request");
    }

    @Override
    public void initialize() {
        spinner = findViewById(R.id.user_select);
        submit = findViewById(R.id.submit);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CitizenModel model = (CitizenModel) parent.getItemAtPosition(position);
                if (model != null && model.getUniqueId() != null) {
                    patientId = model.getUniqueId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientId == null) {
                    Toast.makeText(TakePictureActivity.this, "Select patient", Toast.LENGTH_SHORT).show();

                }else{

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("tag","takepic");
                    hashMap.put("userid",patientId);
                    hashMap.put("b_id",dataManager.getUserId());

                    TakePictureActivity.super.progresShow(true);
                    presenter.submitTakepic(hashMap);


                }

            }
        });

        super.progresShow(true);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tag", "viewuser");
        hashMap.put("b_id", dataManager.getUserId());
        presenter.viewCitizensApi(hashMap);

    }

    @Override
    public void viewUserListFailed() {
        super.progresShow(false);
    }

    @Override
    public void viewUserListSuccess(List<CitizenModel> list) {
        super.progresShow(false);
        CustomAdapter adapter = new CustomAdapter(this, list);
        spinner.setAdapter(adapter);
    }

    @Override
    public void requestFail() {
        super.progresShow(false);
    }

    @Override
    public void requestSucc() {
        super.progresShow(false);

        SnakBarCallback("success", new Callback() {
            @Override
            public void back() {
                finish();
            }
        });

    }
}
