package com.android.apartmentmanagementsystem.user;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.ConnectionDetector;
import com.android.apartmentmanagementsystem.Constant;
import com.android.apartmentmanagementsystem.R;
import com.android.apartmentmanagementsystem.model.Contacts;
import com.android.apartmentmanagementsystem.remote.ApiClient;
import com.android.apartmentmanagementsystem.remote.ApiInterface;
import com.android.apartmentmanagementsystem.user.history.RentHistoryActivity;
import com.google.zxing.Result;
import com.google.zxing.WriterException;

import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class GuestActivity extends AppCompatActivity{
    String TAG = "GenerateQRCode";
    ImageView qrImage;
    Button start, save, send;
    Spinner spinner_flat_no, spinner_floor_no;
    String inputValue;
    String flat_no="";
    String floor_no="";
    EditText guest_name_et,guest_cell_et,total_guest_et,purpose_et,host_cell_et,txtTime,txtDate,host_name_et;
    String[] flat = { "101", "201", "102", "202","103", "203", "104", "204", "105", "205"};
    String[] floor = { "1st", "2nd", "3rd", "4th", "5th"};
    String name,cell,total,purpose,time;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String current_date,current_time,getCell;
    private ProgressDialog loading;
    public Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener appointment_date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Guest");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(GuestActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        name = getIntent().getStringExtra("name");
        cell = getIntent().getStringExtra("cell");
        total = getIntent().getStringExtra("total");
        purpose = getIntent().getStringExtra("purpose");
        qrImage = (ImageView) findViewById(R.id.QR_Image);
        txtDate=(EditText)findViewById(R.id.in_date_et);
        txtTime=(EditText)findViewById(R.id.in_time_et);
        host_name_et=findViewById(R.id.editTextHostName);
        guest_name_et=findViewById(R.id.editTextGuestName);
        guest_name_et.setText(name);
        guest_cell_et=findViewById(R.id.editTextGuestPhone);
        guest_cell_et.setText(cell);
        total_guest_et=findViewById(R.id.editTextTotalGuest);
        total_guest_et.setText(total);
        purpose_et=findViewById(R.id.editTextPurpose);
        purpose_et.setText(purpose);
        host_cell_et=findViewById(R.id.editTextHostCell);
        host_cell_et.setText(getCell);
        start = (Button) findViewById(R.id.start);
        save = (Button) findViewById(R.id.save);
        send = (Button) findViewById(R.id.send);
        save.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        spinner_flat_no =findViewById(R.id.spinner);
        spinner_flat_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flat_no=flat[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, flat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_flat_no.setAdapter(aa);
        spinner_floor_no =findViewById(R.id.spinner2);
        spinner_floor_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floor_no=floor[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, floor);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_floor_no.setAdapter(bb);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(GuestActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(GuestActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                if(hourOfDay>=0 && hourOfDay<12){
                                    time = hourOfDay + " : " + minute + " AM";
                                } else {
                                    if(hourOfDay == 12){
                                        time = hourOfDay + " : " + minute + " PM";
                                    } else{
                                        hourOfDay = hourOfDay -12;
                                        time = hourOfDay + " : " + minute + " PM";
                                    }
                                }

                                txtTime.setText(time);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }

        });

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                                String dateString = sdf.format(date);
                                current_date = dateString;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                                String dateString = sdf.format(date);
                                current_time = dateString;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();

        Random random = new Random();
        final String generatedPassword = String.format("%04d", random.nextInt(10000));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue = generatedPassword;

                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                }
                start.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GuestActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String name = guest_name_et.getText().toString();
                                String cell = guest_cell_et.getText().toString();
                                String total_guest = total_guest_et.getText().toString();
                                String purpose = purpose_et.getText().toString();
                                String guest_date = txtDate.getText().toString().trim();
                                String guest_time = txtTime.getText().toString().trim();
                                String host_name = host_name_et.getText().toString();
                                String host_cell = host_cell_et.getText().toString();
                                String flat_num = flat_no;
                                String floor_num = floor_no;
                                String qr_code = inputValue;
                                String date = current_date;
                                String time = current_time;


                                //validation

                                if (name.isEmpty()) {
                                    guest_name_et.setError("Guest name can't be empty! ");
                                    guest_name_et.requestFocus();

                                }
                                else if (cell.length() != 11 || !cell.startsWith("01")) {
                                    guest_cell_et.setError("Invalid cell!");
                                    guest_cell_et.requestFocus();

                                }
                                else if (total_guest.isEmpty()) {
                                    total_guest_et.setError("Please enter total guest number ! ");
                                    total_guest_et.requestFocus();

                                }
                                else if (purpose.isEmpty()) {
                                    purpose_et.setError("Please enter visit purpose ! ");
                                    purpose_et.requestFocus();
                                }
                                else if (host_name.isEmpty()) {
                                    host_name_et.setError("Host name can't be empty! ");
                                    host_name_et.requestFocus();

                                }
                                else if (host_cell.length() != 11 || !host_cell.startsWith("01")) {
                                    host_cell_et.setError("Invalid cell!");
                                    host_cell_et.requestFocus();

                                }
                                else if (flat_num.isEmpty()) {
                                    Toasty.error(GuestActivity.this, "Please select flat number !", Toast.LENGTH_SHORT).show();
                                }
                                else if (floor_num.isEmpty()) {
                                    Toasty.error(GuestActivity.this, "Please select floor number !", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    //call login method
                                    guestRequest(name,cell, total_guest,purpose,guest_date,guest_time,host_name,host_cell,flat_num,floor_num,qr_code,date,time);
                                }

                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
                save.setVisibility(View.INVISIBLE);
                send.setVisibility(View.VISIBLE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.setType("text/plain");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "title");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);

                OutputStream outstream;
                try {
                    outstream = getContentResolver().openOutputStream(uri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
                share.putExtra(Intent.EXTRA_TEXT, inputValue);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share QR Info"));
            }
        });
    }
    private void guestRequest(String name,String cell,String total_guest,String purpose,String guest_date,String guest_time,String host_name, String host_cell,String flat_no,String floor_no, String qr_code,String date, String time) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.guestRequest(name,cell,total_guest,purpose,guest_date,guest_time,host_name,host_cell,flat_no,floor_no,qr_code,date,time);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(GuestActivity.this, message, Toasty.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                }



                else {
                    loading.dismiss();
                    Toasty.error(GuestActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toasty.error(GuestActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
