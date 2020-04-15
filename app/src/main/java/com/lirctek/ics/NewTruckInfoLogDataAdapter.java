package com.lirctek.ics;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lirctek.ics.database.SensorData;
import com.lirctek.ics.databinding.SensorItemNewBinding;
import com.lirctek.ics.databinding.SensorItmeBinding;

import java.util.List;

public class NewTruckInfoLogDataAdapter extends RecyclerView.Adapter<NewTruckInfoLogDataAdapter.MyViewHolder> {
    private List<SensorData> truckDataLogList;
    private LayoutInflater layoutInflater;

    public NewTruckInfoLogDataAdapter(List<SensorData> truckDataLogList) {
        this.truckDataLogList = truckDataLogList;
    }

    @NonNull
    @Override
    public NewTruckInfoLogDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SensorItemNewBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.sensor_item_new, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NewTruckInfoLogDataAdapter.MyViewHolder holder, int position) {
        SensorData tr=truckDataLogList.get(position);
        holder.truckInfoItemBinding.setTruckdata(tr);
    }

    @Override
    public int getItemCount() {
        return truckDataLogList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
       private  SensorItemNewBinding truckInfoItemBinding;
        MyViewHolder(@NonNull SensorItemNewBinding truckInfoItemBinding) {
            super(truckInfoItemBinding.getRoot());
            this.truckInfoItemBinding=truckInfoItemBinding;
        }
    }
}
