package com.myapplicationdev.demofilereadwriting;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnWrite, btnRead;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tv = findViewById(R.id.tv);
        checkPermission();

        // TODO: Folder creation
        String folderLocation = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Folder"; // Location of the folder

        // by converting the given pathname string into an abstract pathname,
        // a new File object is created.
        File folder = new File(folderLocation);

        // checks to see if the file/directory specified by this abstract pathname exists.
        if (!folder.exists()) {
            boolean result = folder.mkdir(); // This function creates the directory specified by the abstract pathname.

            if (result) {
                Log.d("File Read/Write", "Folder created");
            }

        }


        btnWrite.setOnClickListener(v -> {
            checkPermission();

            // TODO: File creation and writing
            try {
                String folderLocation1 = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/Folder";
                // A new File instance is created by combining a parent pathname string and a child pathname string.
                File targetFile = new File(folderLocation1, "data.txt");
                FileWriter writer = new FileWriter(targetFile, true);
                /* true – for appending to  existing data
                 false – for overwriting  existing data */


                writer.write("Hello world" + "\n"); // Writes a string.
                writer.flush(); // Flushes the stream.
                writer.close();// Closes the stream by first flushing it.
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                e.printStackTrace(); // This throwable and its backtrace are printed to the standard error stream.
            }


        });
        btnRead.setOnClickListener(v -> {
            checkPermission();
            String folderLocation12 = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Folder";
            File targetFile = new File(folderLocation12, "data.txt");

            // Tests whether the targetFile denoted by this abstract pathname exists.
            if (targetFile.exists()) {

                StringBuilder data = new StringBuilder();

                try {
                    // Creates a new FileReader, given the File to read from.
                    FileReader reader = new FileReader(targetFile);
                    BufferedReader br = new BufferedReader(reader);
                    String line = br.readLine();

                    // if line is not null
                    while (line != null) {
                        data.append(line).append("\n");
                        line = br.readLine();
                    }

                    tv.setText(data.toString());

                    // closing both BufferedReader & FileReader
                    br.close();
                    reader.close();
                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Log.d("Content", data.toString());
            }

        });

    }

    // TODO : check for permission
    private boolean checkPermission() {

        int permissionCheck_Write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_Read = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED
                && permissionCheck_Read == PermissionChecker.PERMISSION_GRANTED) {
            return true;

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;

        }
    }

}