package com.example.utsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_views);

        // Mendapatkan data dari intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nik = extras.getString("NIK");
            String nama = extras.getString("Nama");
            String tanggal = extras.getString("Tanggal");
            String tempat = extras.getString("Tempat");
            String alamat = extras.getString("Alamat");
            String email = extras.getString("Email");
            String gender = extras.getString("Gender");
            String usia = extras.getString("Usia");
            boolean service = extras.getBoolean("Service");
            boolean develop = extras.getBoolean("Develop");
            boolean design = extras.getBoolean("Design");
            boolean writing = extras.getBoolean("Writing");
            boolean finance = extras.getBoolean("Finance");
            String country = extras.getString("Country");

            // Menampilkan data ke TextView di layout ViewsActivity
            TextView textNIK = findViewById(R.id.vNik);
            textNIK.setText(nik);

            TextView textNama = findViewById(R.id.vNama);
            textNama.setText(nama);

            TextView textTanggal = findViewById(R.id.vTanggal);
            textTanggal.setText(tanggal);

            TextView textTempat = findViewById(R.id.vTempat);
            textTempat.setText(tempat);

            TextView textAlamat = findViewById(R.id.vAlamat);
            textAlamat.setText(alamat);

            TextView textUsia = findViewById(R.id.vUsia);
            textUsia.setText(usia);

            TextView textEmail = findViewById(R.id.vEmail);
            textEmail.setText(email);

            TextView textGender = findViewById(R.id.vGender);
            textGender.setText(gender);

            TextView textCountry = findViewById(R.id.vCountry);
            textCountry.setText(country);

            TextView textBidang = findViewById(R.id.vKompeten);
            StringBuilder bidang = new StringBuilder();
            if (service) bidang.append("Services, ");
            if (develop) bidang.append("Development & IT, ");
            if (design) bidang.append("Design Creative, ");
            if (writing) bidang.append("Writing, ");
            if (finance) bidang.append("Finance & Accounting");
            textBidang.setText(bidang.toString());

        }

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}