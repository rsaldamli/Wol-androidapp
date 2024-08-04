package com.rsldm.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
//    bypass thread protection
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//          bind object eachother
        Button myButton = findViewById(R.id.button);
        EditText macAddressEditText = findViewById(R.id.macaddress);
//          mac address processes
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String macAddress = macAddressEditText.getText().toString().trim();

                if (isValidMacAddress(macAddress)) {
                    executor.execute(() -> MagicPackage.sendMagicPacket(macAddress));
                } else {
                    macAddressEditText.setError("Invalid MAC address");
                }
            }
        });
    }
//          be sure mac address format
    private boolean isValidMacAddress(String macAddress) {
        return macAddress.matches("([0-9A-Fa-f]{2}[:-]){5}[0-9A-Fa-f]{2}");
    }
}