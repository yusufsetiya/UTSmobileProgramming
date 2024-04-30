package com.example.utsmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    EditText editTanggal,editNama, editNik, editTempat, editAlamat, editEmail;
    Spinner editGender;
    Calendar calendar;
    TextView editUsia;
    RadioButton radioWni,radioWna;
    RadioGroup groupCountry;
    CheckBox cService, cDevelop, cDesign,cWriting,cFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String[] dataArray = {"Laki-laki", "Perempuan"};

        // Buat ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataArray);

        // Tentukan layout dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter ke Spinner
        Spinner spinner = findViewById(R.id.editGender);
        spinner.setAdapter(adapter);

        editNik = findViewById(R.id.editNik);
        editNama = findViewById(R.id.editNama);
        editTanggal = findViewById(R.id.editTanggal);
        calendar = Calendar.getInstance();
        editTempat = findViewById(R.id.editTempat);
        editAlamat = findViewById(R.id.editAlamat);
        editUsia = findViewById(R.id.editUsia);
        editEmail = findViewById(R.id.editEmail);
        editGender = findViewById(R.id.editGender);
        groupCountry = findViewById(R.id.groupCountry);

        //Inisialisasi checkbox
        cDevelop = findViewById(R.id.cDevelop);
        cService = findViewById(R.id.cService);
        cDesign = findViewById(R.id.cDesign);
        cWriting = findViewById(R.id.cWriting);
        cFinance = findViewById(R.id.cFinance);


        editTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button submitForm = findViewById(R.id.submit);
        Button resetForm = findViewById(R.id.reset);
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    String nik = editNik.getText().toString();
                    String nama = editNama.getText().toString();
                    String tanggal = editTanggal.getText().toString();
                    String tempat = editTempat.getText().toString();
                    String alamat = editAlamat.getText().toString();
                    String email = editEmail.getText().toString();
                    String gender = editGender.getSelectedItem().toString();
                    String usia = calculateAge(tanggal) + " Tahun";

                    Intent view = new Intent(MainActivity.this, ViewsActivity.class);
                    view.putExtra("NIK", nik);
                    view.putExtra("Nama", nama);
                    view.putExtra("Tanggal", tanggal);
                    view.putExtra("Tempat", tempat);
                    view.putExtra("Alamat", alamat);
                    view.putExtra("Email", email);
                    view.putExtra("Gender", gender);
                    view.putExtra("Usia", usia);

                    // Tambahkan data checkbox
                    view.putExtra("Service", cService.isChecked());
                    view.putExtra("Develop", cDevelop.isChecked());
                    view.putExtra("Design", cDesign.isChecked());
                    view.putExtra("Writing", cWriting.isChecked());
                    view.putExtra("Finance", cFinance.isChecked());

                    // Tambahkan data radio button
                    int selectedId = groupCountry.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selectedId);
                    String country = radioButton.getText().toString();
                    view.putExtra("Country", country);

                    startActivity(view);
                }
            }
        });

        resetForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNik.setText("");
                editNama.setText("");
                editTanggal.setText("");
                editTempat.setText("");
                editAlamat.setText("");
                editEmail.setText("");
                editUsia.setText("");
                editGender.setSelection(0);
                groupCountry.clearCheck();
                cService.setChecked(false);
                cDevelop.setChecked(false);
                cDesign.setChecked(false);
                cWriting.setChecked(false);
                cFinance.setChecked(false);
            }
        });
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTanggal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        editUsia.setText(calculateAge(dayOfMonth + "/" + (month + 1) + "/" + year) + " Tahun");
                    }
                },
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private boolean validateForm() {
        String nik = editNik.getText().toString();
        if (TextUtils.isEmpty(nik) || nik.length() != 16) {
            Toast.makeText(MainActivity.this, "NIK harus terdiri dari 16 karakter", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(editTanggal.getText())
                || TextUtils.isEmpty(editNama.getText())
                || TextUtils.isEmpty(editTempat.getText())
                || TextUtils.isEmpty(editAlamat.getText())
                || TextUtils.isEmpty(editEmail.getText())) {
            Toast.makeText(MainActivity.this, "Harap lengkapi semua kolom!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validasi radio button (country)
        if (groupCountry.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.this, "Harap pilih negara asal anda!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validasi checkbox
        if (!cService.isChecked() && !cDevelop.isChecked() && !cDesign.isChecked()
                && !cWriting.isChecked() && !cFinance.isChecked()) {
            Toast.makeText(MainActivity.this, "Harap pilih setidaknya satu bidang kompetensi!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validasi Email
        if (TextUtils.isEmpty(editEmail.getText())) {
            Toast.makeText(MainActivity.this, "Harap lengkapi semua kolom!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private String calculateAge(String tanggal) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(sdf.parse(tanggal));
            Calendar currentCalendar = Calendar.getInstance();
            int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
            if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return String.valueOf(age);
        } catch (ParseException e) {
            e.printStackTrace();
            return "-";
        }
    }
}