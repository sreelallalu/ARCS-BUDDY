package com.acrs.buddies.ui.viewuser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acrs.buddies.R;
import com.acrs.buddies.ui.medicineadd.CitizenModel;

import java.util.List;

public class ViewUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<CitizenModel> list_array;
    OnAdapterListener listener;
    public interface OnAdapterListener {
        void adapterItemClick(CitizenModel item);
    }
    public ViewUserListAdapter(List<CitizenModel> list_array, OnAdapterListener listener) {
        this.list_array = list_array;
        this.listener = listener;
    }

    public void setList(List<CitizenModel> list) {
        if (list.size() > 0) {
            this.list_array = list;
            notifyDataSetChanged();

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_single, parent, false);
        return new ViewHolderH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (list_array.size() > 0 && holder instanceof ViewHolderH) {
            try {
                ((ViewHolderH) holder).name.setText(list_array.get(position).getUsername());
                ((ViewHolderH) holder).phone.setText(list_array.get(position).getPhone_no());
                ((ViewHolderH) holder).uid.setText(list_array.get(position).getUniqueId());
            /*    ((ViewHolderH) holder).sheduleStartTime.setText(list_array.get(position).getFromTime());
                ((ViewHolderH) holder).sheduleEndTime.setText(list_array.get(position).getToTime());
                ((BuddyListAdapter.ViewHolderH) holder).bind(list_array.get(position));*/


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public int getItemCount() {
        return list_array.size();
    }

    private class ViewHolderH extends RecyclerView.ViewHolder {
        private TextView name,phone,uid;

        public ViewHolderH(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.buddy_name);
            phone = (TextView) v.findViewById(R.id.buddy_phone);
            uid = (TextView) v.findViewById(R.id.user_id);



        }
        public void bind(final CitizenModel items)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.adapterItemClick(items);
                }
            });
        }

    }

}
