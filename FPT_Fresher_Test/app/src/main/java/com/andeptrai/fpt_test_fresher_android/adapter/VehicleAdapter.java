package com.andeptrai.fpt_test_fresher_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andeptrai.fpt_test_fresher_android.MainActivity;
import com.andeptrai.fpt_test_fresher_android.R;
import com.andeptrai.fpt_test_fresher_android.interf.EditVehicleInterface;
import com.andeptrai.fpt_test_fresher_android.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter implements Filterable {

    private Context context;
    private ArrayList<Vehicle> vehicleArrayList;
    private EditVehicleInterface editVehicleInterface;

    private ArrayList<Vehicle> displayFilterVehicleArrayList;

    public VehicleAdapter(Context context, ArrayList<Vehicle> vehicleArrayList, EditVehicleInterface editVehicleInterface) {
        this.context = context;
        this.vehicleArrayList = vehicleArrayList;
        this.displayFilterVehicleArrayList = vehicleArrayList;
        this.editVehicleInterface = editVehicleInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_vehicle, parent, false);

        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final Vehicle currentVehicle = vehicleArrayList.get(position);
        VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;

        vehicleViewHolder.txtId.setText(currentVehicle.getId()+"");
        vehicleViewHolder.txtKind.setText(currentVehicle.getKind());
        vehicleViewHolder.txtName.setText(currentVehicle.getName());
        vehicleViewHolder.txtPrice.setText(currentVehicle.getPrice()+"");

        vehicleViewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editVehicleInterface.editVehicleClickListener(currentVehicle, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleArrayList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = displayFilterVehicleArrayList.size();
                    filterResults.values = displayFilterVehicleArrayList;

                }else{
                    ArrayList<Vehicle> resultsModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();

                    for(Vehicle vehicle:displayFilterVehicleArrayList){
                        if(vehicle.getName().toLowerCase().contains(searchStr)){
                            resultsModel.add(vehicle);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                vehicleArrayList = (ArrayList<Vehicle>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder{

        TextView txtId, txtName, txtPrice, txtKind;
        ImageView imgEdit;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtKind = itemView.findViewById(R.id.txtKind);
            imgEdit = itemView.findViewById(R.id.imgEdit);

        }
    }
}
