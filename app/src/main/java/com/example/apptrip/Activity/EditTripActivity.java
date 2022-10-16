package com.example.apptrip.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrip.Model.Trip;
import com.example.apptrip.R;
import com.example.apptrip.SQLite.SQLiteHelper;
import com.example.apptrip.Utils.Utils;
import com.example.apptrip.databinding.ActivityEditTripBinding;

import java.util.ArrayList;

public class EditTripActivity extends AppCompatActivity {
    private ActivityEditTripBinding binding;
    private Trip trip;
    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_trip);
        sqLiteHelper = new SQLiteHelper(EditTripActivity.this,"Database.sqlite",null,1);
        trip = new Trip();
        Intent intent = getIntent();
        trip = (Trip) intent.getSerializableExtra("trip");
        binding.edtNameTrip.setText(trip.getNameTrip());
        binding.edtDate.setText(trip.getDateTrip());
        binding.edtDestination.setText(trip.getLocateTrip());
        String riqured = trip.getRequiredTrip();
        if (riqured.equalsIgnoreCase("Yes")){
            binding.radioYes.isChecked();
        }else{
            binding.radioNo.isChecked();
        }
        EditTrip();
        SeeAllExpenses();
        DeleteTrip();
    }

    private void DeleteTrip() {
        binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditTripActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_delete);
                TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                txtContent.setText("Do you want to delete a Trip ?");
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sqLiteHelper.QueryData("DELETE FROM Trips WHERE Id = '"+trip.getIdTrip()+"' ");
                        Toast.makeText(EditTripActivity.this,"Delete Success !!!",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        startActivity(new Intent(EditTripActivity.this,MainActivity.class));
                    }
                });
                dialog.show();
            }
        });
    }

    private void SeeAllExpenses() {
        binding.btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.ID_TRIP = trip.getIdTrip();
                Intent intent = new Intent(EditTripActivity.this,ExpensesActivity.class);
                intent.putExtra("idTrip",trip.getIdTrip());
                startActivity(intent);
            }
        });

    }

    private void EditTrip() {
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtNameTrip.getText().toString().trim();
                String destination = binding.edtDestination.getText().toString().trim();
                String date = binding.edtDate.getText().toString().trim();
                String required = "";
                if (binding.radioYes.isChecked()){
                    required = "Yes";
                }else if (binding.radioNo.isChecked()){
                    required =  "No";
                }
                String description = binding.edtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)||TextUtils.isEmpty(date)
                        ||TextUtils.isEmpty(destination)||TextUtils.isEmpty(required)){
                    Dialog dialog = new Dialog(EditTripActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_confirm);
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                    txtContent.setText("You need to fill all required fields");
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    //Edit database trip
                    sqLiteHelper.QueryData("UPDATE Trips SET NameTrip = '"+name
                            +"',DateTrip = '"+date
                            +"',LocateTrip = '"+destination
                            +"',RequiredTrip = '"+required
                            +"' WHERE Id = '" + trip.getIdTrip() + "' ");

                    Dialog dialog = new Dialog(EditTripActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_confirm);
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
                    txtContent.setText("Edit Success !!!");
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            startActivity(new Intent(EditTripActivity.this,MainActivity.class));
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}