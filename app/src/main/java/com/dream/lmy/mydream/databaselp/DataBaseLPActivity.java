package com.dream.lmy.mydream.databaselp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dream.lmy.mydream.R;

import org.litepal.tablemanager.Connector;

public class DataBaseLPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_lp);
    }

    private void litePalDataBase(){
        SQLiteDatabase database = Connector.getDatabase();

    }
}
