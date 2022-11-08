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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_trip);//Khởi tạo layout của activity
        sqLiteHelper = new SQLiteHelper(EditTripActivity.this,"Database.sqlite",null,1);//Khởi tạo hàm SQLite
        trip = new Trip(); //Khởi tạo class Trip
        Intent intent = getIntent(); //Khởi tạo intent
        trip = (Trip) intent.getSerializableExtra("trip"); //Nhận dữ liệu thông qua intent
        binding.edtNameTrip.setText(trip.getNameTrip());//gán dữ liệu vào Edittext NameTrip
        binding.edtDate.setText(trip.getDateTrip()); //gán dữ liệu vào Edittext Date
        binding.edtDestination.setText(trip.getLocateTrip()); //gán dữ liệu vào Edittext Destination
        String riqured = trip.getRequiredTrip(); //Khởi tạo 1 biến String để lưu dữ liệu của RequiredTrip
        if (riqured.equalsIgnoreCase("Yes")){ //Kiểm tra nếu riqured là " Yes"
            binding.radioYes.isChecked(); //Nghĩa là người dùng click vào radio Yes
        }else{ //Ngược lại
            binding.radioNo.isChecked(); //Người dùng click vào radio No
        }
        EditTrip(); //Hàm xử lý chức năng button EditTrip
        SeeAllExpenses(); //Hàm xử lý chức năng button SeeAllExpense
        DeleteTrip(); //Hàm xử lý chức năng DeleteTrip
    }

    private void DeleteTrip() {
        binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditTripActivity.this);//Khởi tạo dialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//ẩn thanh tiêu đề của dialog
                dialog.setCancelable(false);//hiển thị thông báo khi người dùng tương tác với dialog
                dialog.setContentView(R.layout.dialog_delete);//set layout cho dialog
                TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);//ánh xạ textview hiển thị nội dung của dialog
                txtContent.setText("Do you want to delete a Trip ?"); //set dữ liệu cho textview của dialog
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel); //ánh xạ button cancel
                Button btnOk = (Button) dialog.findViewById(R.id.btnOk); //ánh xạ button ok
                btnCancel.setOnClickListener(new View.OnClickListener() {//bắt sự kiện button cancel
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();//Nếu người dùng click button cancel thì sẽ đóng dialog
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {//bắt sự kiện button ok
                    @Override
                    public void onClick(View view) {
                        sqLiteHelper.QueryData("DELETE FROM Trips WHERE Id = '"+trip.getIdTrip()+"' "); //khởi tạo sqlite để xóa dữ liệu của bảng Trips theo Id
                        Toast.makeText(EditTripActivity.this,"Delete Success !!!",Toast.LENGTH_LONG).show(); //Hiển thị toast thông báo xóa thành công
                        dialog.dismiss(); //Đóng dialog
                        startActivity(new Intent(EditTripActivity.this,MainActivity.class)); //Chuyển màn hình từ EditTripActivity sang MainActivity
                    }
                });
                dialog.show(); //giúp hiển thị dialog
            }
        });
    }

    private void SeeAllExpenses() {
        binding.btnSeeAll.setOnClickListener(new View.OnClickListener() {//bắt sự kiện button see all
            @Override
            public void onClick(View view) {
                Utils.ID_TRIP = trip.getIdTrip(); //Gán idTrip vào biến tham chiếu
                Intent intent = new Intent(EditTripActivity.this,ExpensesActivity.class);//khởi tạo intent để chuyển màn hình từ EditTripActivity sang ExpenseActivity
                intent.putExtra("idTrip",trip.getIdTrip());//truyền dữ liệu IdTrip vào intent thông qua cặp khóa key ,value
                startActivity(intent); //hàm này được gọi thì intent sẽ bắt đầu
            }
        });

    }

    private void EditTrip() {
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {//xử lý sự kiện button EditTrip
            @Override
            public void onClick(View view) {
                //Khởi tạo và gán dữ liệu vào biến String khi người dùng nhập dữ liệu
                String name = binding.edtNameTrip.getText().toString().trim();
                String destination = binding.edtDestination.getText().toString().trim();
                String date = binding.edtDate.getText().toString().trim();
                String required = "";
                if (binding.radioYes.isChecked()){ //Nếu người dùng click vào radioYes
                    required = "Yes"; //Gán required = "Yes"
                }else if (binding.radioNo.isChecked()){ //Ngược lại nếu người dùng click vào radioNo
                    required =  "No"; //Gán required = "No"
                }
                String description = binding.edtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)||TextUtils.isEmpty(date) //Kiểm tra người dùng nhập đủ dữ liệu chưa
                        ||TextUtils.isEmpty(destination)||TextUtils.isEmpty(required)){
                    Dialog dialog = new Dialog(EditTripActivity.this);//Khởi tạo dialog
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//ẩn thanh tiêu đề của dialog
                    dialog.setCancelable(false);//hiển thị thông báo khi người dùng tương tác với dialog
                    dialog.setContentView(R.layout.dialog_confirm);//set layout cho dialog
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);//ánh xạ textview hiển thị nội dung của dialog
                    txtContent.setText("You need to fill all required fields");//set dữ liệu cho textview của dialog
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);//ánh xạ button close
                    btnClose.setOnClickListener(new View.OnClickListener() {//Xử lý sự kiện khi người dùng click vào button close
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        } //ẩn dialog
                    });
                    dialog.show();//giúp hiển thị dialog
                }else {
                    //Edit database trip
                    sqLiteHelper.QueryData("UPDATE Trips SET NameTrip = '"+name//Khởi tạo sqlite để sửa dữ liệu của bảng Trips trong database
                            +"',DateTrip = '"+date
                            +"',LocateTrip = '"+destination
                            +"',RequiredTrip = '"+required
                            +"' WHERE Id = '" + trip.getIdTrip() + "' ");

                    Dialog dialog = new Dialog(EditTripActivity.this);//Khởi tạo dialog
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//ẩn thanh tiêu đề của dialog
                    dialog.setCancelable(false);//hiển thị thông báo khi người dùng tương tác với dialog
                    dialog.setContentView(R.layout.dialog_confirm);//set layout cho dialog
                    TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);//ánh xạ textview hiển thị nội dung của dialog
                    txtContent.setText("Edit Success !!!");//set dữ liệu cho textview của dialog
                    Button btnClose = (Button) dialog.findViewById(R.id.btnClose);//ánh xạ button close
                    btnClose.setOnClickListener(new View.OnClickListener() {//Xử lý sự kiện khi người dùng click vào button close
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss(); //ẩn dialog
                            startActivity(new Intent(EditTripActivity.this,MainActivity.class));//khởi tạo intent để chuyển màn hình từ EditTripActivity sang MainActivity
                        }
                    });
                    dialog.show();//giúp hiển thị dialog
                }
            }
        });
    }
}