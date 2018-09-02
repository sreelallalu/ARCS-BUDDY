package com.acrs.buddies.ui.medicineadd;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface MedicineAdd_i_Presenter<T extends MedicineAddView> extends MvpPresenter<T> {

    void addMedicineApi(HashMap<String,String> hashMap);
    void viewCitizensApi(HashMap<String,String> hashMap);

}
