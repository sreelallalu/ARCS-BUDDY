package com.acrs.buddies.ui.medicineadd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acrs.buddies.R;

import java.util.List;

/**
 * Created by sreelal on 16/9/17.
 */

public class CustomAdapter extends BaseAdapter {


    List<CitizenModel> arraylist;
    Context context;
    boolean state;

    public CustomAdapter(Context context,
                         List<CitizenModel> arrayofUsers) {

        arraylist=arrayofUsers;
        this.context=context;

    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int positon, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.spinner_item_view, parent,false);

        CitizenModel users = arraylist.get(positon);
        TextView textView=view.findViewById(R.id.spinneride);
        textView.setText(users.getUsername());
        return view;
    }

    /*@Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.spinner_item_view, parent,false);

       try{ Country users = arraylist.get(position);
           TextView spinner =  view.findViewById(R.id.spinneride);
           View hide =  view.findViewById(R.id.viewStatehide);
           LinearLayout viewspinner =  view.findViewById(R.id.viewSpinner);
           ImageView imageView=view.findViewById(R.id.imageView27);
           ImageView imageView1=view.findViewById(R.id.imageView25);
           if(position==0)
           {
               spinner.setVisibility(View.GONE);
               viewspinner.setVisibility(View.GONE);
               imageView.setVisibility(View.GONE);
               imageView1.setVisibility(View.GONE);


           }
           else{
               hide.setVisibility(View.VISIBLE);
               viewspinner.setVisibility(View.VISIBLE);
               spinner.setText(users.getName());
               spinner.setTextSize(17);

               imageView.setVisibility(View.INVISIBLE);
               imageView1.setVisibility(View.INVISIBLE);
           }


       }catch (Exception e){e.printStackTrace();}


        return view;

    }*/
}
