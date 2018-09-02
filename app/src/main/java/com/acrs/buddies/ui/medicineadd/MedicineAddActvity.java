package com.acrs.buddies.ui.medicineadd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.acrs.buddies.R;
import com.acrs.buddies.databinding.ActivityMedicineAddBinding;
import com.acrs.buddies.ui.base.BaseActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MedicineAddActvity extends BaseActivity implements MedicineAddView {

    @Inject
    MedicineAdd_i_Presenter<MedicineAddView> presenter;

    ActivityMedicineAddBinding binding;
    private String datecurrent;
    private String timecurrent;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_medicine_add);

        // Acttivity
        getActivityComponent().inject(this);
        presenter.onAttach(this);
        initialize();
        getSupportActionBar().setTitle("Request Patient");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void viewUserList() {
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
        binding.userSelect.setAdapter(adapter);

    }

    @Override
    public void addMedicine() {
        boolean check = true;

        String medicineDate = binding.medicinedate.getText().toString().trim();
        String medicineTime = binding.medicinetime.getText().toString().trim();
        String medicineNote = binding.medicinenotes.getText().toString().trim();
        String medicineName = binding.medicinename.getText().toString().trim();

        if (medicineDate.isEmpty()) {
            binding.medicinedate.setError("Invalid");
            check = false;
        }
        if (medicineName.isEmpty()) {
            binding.medicinename.setError("Invalid");
            check = false;
        }
        if (medicineTime.isEmpty()) {
            binding.medicinetime.setError("Invalid");
            check = false;
        }

        if (patientId == null) {
            check = false;
        }


        if (medicineNote.isEmpty()) {

            binding.medicinenotes.setError("Invalid");
            check = false;
        }

        if (check) {
            MedicineAddActvity.super.progresShow(true);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("tag", "medicineadd");
            hashMap.put("patientid", patientId);
            hashMap.put("medicinename", medicineName);
            hashMap.put("medicinetime", medicineDate + " " + medicineTime);
            hashMap.put("medicinenote", medicineNote);

            presenter.addMedicineApi(hashMap);

        }
    }

    @Override
    public void addMedicineSucces() {
        MedicineAddActvity.super.progresShow(false);

        SnakBarCallback("Medicine added successfully", new Callback() {
            @Override
            public void back() {
                finish();
            }
        });


    }

    @Override
    public void addMedicineFailed() {
        MedicineAddActvity.super.progresShow(false);

    }

    @Override
    public void initialize() {

        binding.medicinetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();


            }
        });

        binding.medicinedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });


        binding.userSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addMedicine();


            }
        });
        viewUserList();
    }

    private void datePicker() {

        try {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog pick = new DatePickerDialog(MedicineAddActvity.this, new DateCollect(binding.medicinedate), calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            try {
                pick.getDatePicker().setMinDate(calendar.getTime().getTime());

            } catch (Exception e) {
                e.printStackTrace();
            }

            pick.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timePicker() {
        try {

            Calendar calendar = Calendar.getInstance();
            TimePickerDialog pickerDialog = new TimePickerDialog(MedicineAddActvity.this, new TimeCollect(binding.medicinetime), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
            pickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class DateCollect implements DatePickerDialog.OnDateSetListener {
        Calendar myCalendar = Calendar.getInstance();

        EditText editText;


        public DateCollect(EditText edit) {
            this.editText = edit;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
            datecurrent = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(day);

            binding.medicinedate.setText(datecurrent == null ? "" : datecurrent);
        }
    }


    private class TimeCollect implements TimePickerDialog.OnTimeSetListener {
        public TimeCollect(TextInputEditText medicinetime) {
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timecurrent = String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + ":00";
            binding.medicinetime.setText(timecurrent == null ? "" : timecurrent);
        }
    }
}
