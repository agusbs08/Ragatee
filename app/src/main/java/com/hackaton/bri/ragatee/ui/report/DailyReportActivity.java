package com.hackaton.bri.ragatee.ui.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.Report;

import java.util.ArrayList;

public class DailyReportActivity extends BaseActivity implements DailyReportView {

    private ArrayList<Report> dailyReports;
    private RecyclerView rvReport;
    private DailyReportRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        initData();
        initComponent();

        rvReport.setLayoutManager(new LinearLayoutManager(this));
        rvReport.setAdapter(adapter);
    }

    private void initData() {
        dailyReports = (ArrayList<Report>) getIntent().getSerializableExtra("daily_reports");
    }

    private void initComponent() {
        rvReport = findViewById(R.id.rv_report);
        adapter = new DailyReportRecyclerViewAdapter(dailyReports);
    }
}
