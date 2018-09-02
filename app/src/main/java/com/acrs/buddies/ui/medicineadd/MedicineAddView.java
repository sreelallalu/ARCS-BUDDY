package com.acrs.buddies.ui.medicineadd;

import com.acrs.buddies.ui.base.MvpView;

import java.util.List;

public interface MedicineAddView extends MvpView{

    void viewUserList();
    void viewUserListFailed();
    void viewUserListSuccess(List<CitizenModel> list);
    void addMedicine();
    void addMedicineSucces();
    void addMedicineFailed();
    void initialize();
}
