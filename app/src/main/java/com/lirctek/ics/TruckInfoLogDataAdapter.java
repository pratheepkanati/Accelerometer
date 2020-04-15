package com.lirctek.ics;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lirctek.ics.database.SensorData;
import com.lirctek.ics.databinding.SensorItmeBinding;

import java.util.List;

public class TruckInfoLogDataAdapter extends RecyclerView.Adapter<TruckInfoLogDataAdapter.MyViewHolder> {
    private List<SensorData> truckDataLogList;
    private LayoutInflater layoutInflater;

    public TruckInfoLogDataAdapter(List<SensorData> truckDataLogList) {
        this.truckDataLogList = truckDataLogList;
    }

    @NonNull
    @Override
    public TruckInfoLogDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SensorItmeBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.sensor_itme, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull TruckInfoLogDataAdapter.MyViewHolder holder, int position) {
        SensorData tr=truckDataLogList.get(position);
        holder.truckInfoItemBinding.setTruckdata(tr);
    }

    @Override
    public int getItemCount() {
        return truckDataLogList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
       private  SensorItmeBinding truckInfoItemBinding;
        MyViewHolder(@NonNull SensorItmeBinding truckInfoItemBinding) {
            super(truckInfoItemBinding.getRoot());
            this.truckInfoItemBinding=truckInfoItemBinding;
        }
    }
}
