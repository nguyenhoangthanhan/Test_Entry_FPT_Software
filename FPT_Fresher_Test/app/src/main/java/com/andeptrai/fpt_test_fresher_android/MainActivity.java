package com.andeptrai.fpt_test_fresher_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andeptrai.fpt_test_fresher_android.adapter.VehicleAdapter;
import com.andeptrai.fpt_test_fresher_android.database.VehicleDBHandle;
import com.andeptrai.fpt_test_fresher_android.interf.EditVehicleInterface;
import com.andeptrai.fpt_test_fresher_android.model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements EditVehicleInterface {

    VehicleDBHandle vehicleDBHandle;

    TextView txtAdd, txtDelete;
    Button btnArrange, btnSearchByName, btnFilterByPriceBigger;
    Spinner spinnerArrange;
    EditText edtNameSearch, edtPriceFilter;

    RecyclerView rv_list_vehicle;
    VehicleAdapter vehicleAdapter;
    ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        txtAdd = findViewById(R.id.txtAdd);
        txtDelete = findViewById(R.id.txtDelete);
        btnArrange = findViewById(R.id.btnArrange);
        btnFilterByPriceBigger = findViewById(R.id.btnFilterByPriceBigger);
        spinnerArrange = findViewById(R.id.spinnerArrange);
        edtPriceFilter = findViewById(R.id.edtPriceFilter);
        edtNameSearch = findViewById(R.id.edtNameSearch);
        rv_list_vehicle = findViewById(R.id.rv_list_vehicle);

        //set up database
        setDatabase();

        //add data demo for list vehicle
        //addDataDemo();

        //add new vehicle
        addNewVehicle();

        //edit vehicle
        editVehicle();

        //delete vehicle
        deleteVehicle();

        //set spinner for arrange
        arrangeSpinner();

        //search by name
        searchByName();

        //show vehicles have price bigger x
        filterByPrice();
    }

    private void setDatabase() {
        //create new database
        vehicleDBHandle = new VehicleDBHandle(this);
        vehicleArrayList = vehicleDBHandle.getAllVehicles();
        /*
        for (int i = 0; i < vehicleArrayList.size(); i++){
            Log.d("vehicle_new", vehicleArrayList.get(i).getId()+vehicleArrayList.get(i).getName()
                    +vehicleArrayList.get(i).getKind()+vehicleArrayList.get(i).getPrice());
        }
         */

        vehicleAdapter = new VehicleAdapter(this, vehicleArrayList, this);
        rv_list_vehicle.setAdapter(vehicleAdapter);
        rv_list_vehicle.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addNewVehicle() {
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogAddNewVehicle();
            }

            private void OpenDialogAddNewVehicle() {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_add_new_vehicle);
                final EditText edtId = dialog.findViewById(R.id.edtId);
                final EditText edtName = dialog.findViewById(R.id.edtName);
                final EditText edtKind = dialog.findViewById(R.id.edtKind);
                final EditText edtPrice = dialog.findViewById(R.id.edtPrice);
                Button btnOk = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputId = edtId.getText().toString();
                        String inputName = edtName.getText().toString();
                        String inputKind = edtKind.getText().toString();
                        long inputPrice = Long.parseLong(edtPrice.getText().toString());
                        boolean check = false;
                        for (int i = 0; i < vehicleArrayList.size(); i++){
                            if (inputId.equalsIgnoreCase(vehicleArrayList.get(i).getId())){
                                check = true; break;
                            }
                        }

                        if (check == false){
                            Vehicle vehicle = new Vehicle(inputId, inputName, inputKind, inputPrice);
                            vehicleDBHandle.addVehicle(vehicle);
                            vehicleArrayList.add(vehicle);
                            vehicleAdapter.notifyItemInserted(vehicleArrayList.size());
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(MainActivity.this, R.string.check_id, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void editVehicle() {

    }

    private void deleteVehicle() {
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_delete_vehicle);

                final EditText edtIdNeedDelete = dialog.findViewById(R.id.edtIdNeedDelete);
                Button btnOk = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputId = edtIdNeedDelete.getText().toString();
                        int indexNeedDelete = -1;
                        for (int i = 0; i < vehicleArrayList.size(); i++){
                            if(inputId.equalsIgnoreCase(vehicleArrayList.get(i).getId())){
                                indexNeedDelete = i;
                                break;
                            }
                        }

                        if (indexNeedDelete == -1){
                            Toast.makeText(MainActivity.this, "Không tồn tại phương tiện có id=" + inputId, Toast.LENGTH_LONG).show();
                        }
                        else{
                            vehicleArrayList.remove(indexNeedDelete);
                            vehicleDBHandle.deleteVehicle(inputId);
                            vehicleAdapter.notifyItemRemoved(indexNeedDelete);
                            dialog.dismiss();
                        }
                    }


                });

                dialog.show();
            }
        });
    }

    private void arrangeSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this
                , R.array.arrange_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerArrange.setAdapter(arrayAdapter);

        spinnerArrange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0){
                    Collections.sort(vehicleArrayList, Vehicle.vehicleComparatorAZ);
                    vehicleAdapter.notifyDataSetChanged();
                }
                else if (i == 1){
                    Collections.sort(vehicleArrayList, Vehicle.vehicleComparatorZA);
                    vehicleAdapter.notifyDataSetChanged();
                }
                else if (i == 2){
                    Collections.sort(vehicleArrayList, Vehicle.vehicleComparatorAscending);
                    vehicleAdapter.notifyDataSetChanged();
                }
                else if (i == 3){
                    Collections.sort(vehicleArrayList, Vehicle.vehicleComparatorDescending);
                    vehicleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void searchByName() {
        edtNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vehicleAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filterByPrice() {
        btnFilterByPriceBigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByPriceBigger();
            }
        });
    }

    private void filterByPriceBigger() {
        String inputPriceStr = edtPriceFilter.getText().toString();
        long inputPrice = -1;
        if (!inputPriceStr.equalsIgnoreCase("")){
            inputPrice = Long.parseLong(inputPriceStr);
        }
        if (inputPrice != -1){
            ArrayList<Vehicle> resultFilterPriceArr = new ArrayList<>();
            for (int i = 0; i < vehicleArrayList.size(); i++){
                if (vehicleArrayList.get(i).getPrice() > inputPrice){
                    resultFilterPriceArr.add(vehicleArrayList.get(i));
                }
            }
            VehicleAdapter adapterFilter = new VehicleAdapter(MainActivity.this, resultFilterPriceArr, this);
            rv_list_vehicle.setAdapter(adapterFilter);
            adapterFilter.notifyDataSetChanged();
        }
        else{
            rv_list_vehicle.setAdapter(vehicleAdapter);
        }
    }

    private void addDataDemo() {

        vehicleArrayList.add(new Vehicle("vehicle01","Xe dap dien demo 1", "Xe dap dien", 1500000));
        vehicleArrayList.add(new Vehicle("vehicle02","Rebel 500 2021", "Xe may", 180000000));
        vehicleArrayList.add(new Vehicle("vehicle03","Winner X", "Xe may", 46000000));
        vehicleArrayList.add(new Vehicle("vehicle04","Lead 125 FI", "Xe may", 3800000));
        vehicleArrayList.add(new Vehicle("vehicle05","CB1000R", "Xe may", 468000000));
        vehicleArrayList.add(new Vehicle("vehicle06","Future 125 FI", "Xe may", 3000000));
        vehicleArrayList.add(new Vehicle("vehicle07","Sh Mode 125", "Xe may", 5400000));
        vehicleArrayList.add(new Vehicle("vehicle08","Air Blade 125/150", "Xe may", 4200000));
        vehicleArrayList.add(new Vehicle("vehicle09","Blade 110", "Xe may", 1800000));
        vehicleArrayList.add(new Vehicle("vehicle10","Vision", "Xe may", 3000000));
        vehicleArrayList.add(new Vehicle("vehicle11","Wave RSX FI 110", "Xe may", 2100000));
        vehicleArrayList.add(new Vehicle("vehicle12","Africa Twin 2021", "Xe may", 589000000));
        vehicleArrayList.add(new Vehicle("vehicle13","SH350i", "Xe may", 14500000));
        vehicleArrayList.add(new Vehicle("vehicle14","CBR150R", "Xe may", 7800000));
        vehicleArrayList.add(new Vehicle("vehicle15","Xe demo 5", "Xe", 1500000));
        vehicleArrayList.add(new Vehicle("vehicle16","Xe tai demo 6", "Xe tai", 200000000));
        vehicleArrayList.add(new Vehicle("vehicle17","Xe tai to demo 7", "Xe tai to", 900000000));
        vehicleArrayList.add(new Vehicle("vehicle18","Mitsubishi Triton 4×2.MT", "Xe ban tai", 400000000));
        vehicleArrayList.add(new Vehicle("vehicle19","Xe hoi demo 9", "Xe hoi", 300000000));
        vehicleArrayList.add(new Vehicle("vehicle20","zzz demo", "Xe hoi", 900000000));
        vehicleAdapter.notifyDataSetChanged();
    }

    @Override
    public void editVehicleClickListener(final Vehicle vehicle, final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_vehicle);

        TextView txtIdEdit = dialog.findViewById(R.id.txtIdEdt);
        final TextView edtNameEdit = dialog.findViewById(R.id.edtNameEdit);
        final TextView edtKindEdt = dialog.findViewById(R.id.edtKindEdt);
        final TextView edtPriceEdt = dialog.findViewById(R.id.edtPriceEdt);
        TextView btnOk = dialog.findViewById(R.id.btnOk);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);

        txtIdEdit.setText(vehicle.getId());
        edtNameEdit.setText(vehicle.getName());
        edtKindEdt.setText(vehicle.getKind());
        edtPriceEdt.setText(vehicle.getPrice()+"");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long edtPrice = Long.parseLong(edtPriceEdt.getText().toString());
                Vehicle edtVehicle = new Vehicle(vehicle.getId(), edtNameEdit.getText().toString(), edtKindEdt.getText().toString()
                , edtPrice);
                vehicleDBHandle.updateVehicle(edtVehicle);
                vehicleArrayList.remove(position);
                vehicleArrayList.add(position, edtVehicle);
                vehicleAdapter.notifyItemChanged(position);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}