package in.fusionbit.shreejeecredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.fusionbit.shreejeecredit.adapter.RvFileReportAdapter;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.FileReport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFileReport extends ActivityBase {

    static final String FROM = "FROM";
    static final String TO = "TO";
    static final String SELECTED_USERS = "SELECTED_USERS";
    static final String RECEIVED_STATUS = "RECEIVED_STATUS";

    @BindView(R.id.rv_fileReport)
    RecyclerView rvFileReport;
    @BindView(R.id.srl_fileReport)
    SwipeRefreshLayout srlFileReport;

    private Call<List<FileReport>> getFileReportsCall;

    private String from, to, receivedStatus, adminString;

    private RvFileReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_report);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        from = getIntent().getStringExtra(FROM);
        to = getIntent().getStringExtra(TO);
        adminString = getIntent().getStringExtra(SELECTED_USERS);
        receivedStatus = getIntent().getStringExtra(RECEIVED_STATUS);

        srlFileReport.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getReport();
            }
        });

        setupRecyclerView();

        showProgressDialog("Getting File Reports", "Please Wait...");

        getReport();

    }

    private void setupRecyclerView() {

        rvFileReport.setLayoutManager(new LinearLayoutManager(this));
        rvFileReport.setHasFixedSize(true);

        adapter = new RvFileReportAdapter(this);
        rvFileReport.setAdapter(adapter);


    }

    private void getReport() {
        cancelCall();

        getFileReportsCall = Api.Receipt.getFileReport(from, to, receivedStatus, adminString, App.getSessionId(),
                new Callback<List<FileReport>>() {
                    @Override
                    public void onResponse(Call<List<FileReport>> call, Response<List<FileReport>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().size() > 0) {
                                    adapter.clear();
                                    for (FileReport report : response.body()) {
                                        adapter.addFileReport(report);
                                    }
                                    if (getSupportActionBar() != null) {
                                        getSupportActionBar().setTitle("File Report (" + response.body().size() + ")");
                                    }
                                } else {
                                    Toast.makeText(ActivityFileReport.this, "No reports found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ActivityFileReport.this, "Response was NULL", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityFileReport.this, "RESPONSE CODE: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                        if(srlFileReport.isRefreshing()){
                            srlFileReport.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FileReport>> call, Throwable t) {
                        if (!call.isCanceled()) {
                            Toast.makeText(ActivityFileReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                        if(srlFileReport.isRefreshing()){
                            srlFileReport.setRefreshing(false);
                        }
                    }
                });
    }

    private void cancelCall() {
        if (getFileReportsCall != null) {
            if (!getFileReportsCall.isCanceled()) {
                getFileReportsCall.cancel();
            }
            getFileReportsCall = null;
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(final Context context, final String from, final String to, final String selectedUsers, final String receiveStatus) {
        Intent i = new Intent(context, ActivityFileReport.class);
        i.putExtra(FROM, from);
        i.putExtra(TO, to);
        i.putExtra(SELECTED_USERS, selectedUsers);
        i.putExtra(RECEIVED_STATUS, receiveStatus);
        context.startActivity(i);
    }

}
