package in.fusionbit.shreejeecredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.fusionbit.shreejeecredit.adapter.RvReportAdapter;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.Report;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReport extends ActivityBase {

    @BindView(R.id.rv_report)
    RecyclerView rvReport;
    private Call<List<Report>> getReportCall;

    RvReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        setTitle("Report");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String fromDate = getIntent().getStringExtra(Constants.IntentExtra.FROM_DATE);
        final String toDate = getIntent().getStringExtra(Constants.IntentExtra.TO_DATE);
        final String modeOfPayment = getIntent().getStringExtra(Constants.IntentExtra.MODE_OF_PAYMENT);
        final String selectedUsers = getIntent().getStringExtra(Constants.IntentExtra.SELECTED_USERS);

        getReport(fromDate, toDate, modeOfPayment, selectedUsers);

        rvReport.setLayoutManager(new LinearLayoutManager(this));
        //rvReport.setHasFixedSize(true);

        adapter = new RvReportAdapter(this);
        rvReport.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancelCall();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }

    private void getReport(final String fromDate, final String toDate, final String modeOfPayment,
                           final String selectedUsers) {
        showProgressDialog("Getting Report...", "Please Wait...");
        cancelCall();
        getReportCall = Api.Receipt.getReport(fromDate, toDate, modeOfPayment, selectedUsers,
                new Callback<List<Report>>() {
                    @Override
                    public void onResponse(Call<List<Report>> call, final Response<List<Report>> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                for (Report report : response.body()) {
                                    adapter.addReport(report);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setTitle("Report (" + response.body().size() + ")");
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Report>> call, Throwable t) {
                        Toast.makeText(ActivityReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        hideProgressDialog();
                    }
                });
    }

    private void cancelCall() {
        if (getReportCall != null) {
            if (!getReportCall.isCanceled()) {
                getReportCall.cancel();
            }
            getReportCall = null;
        }
    }

    public static void start(final Context context,
                             String fromDate, String toDate, String modeOfPayment, String selectedUsers) {

        Intent i = new Intent(context, ActivityReport.class);
        i.putExtra(Constants.IntentExtra.FROM_DATE, fromDate);
        i.putExtra(Constants.IntentExtra.TO_DATE, toDate);
        i.putExtra(Constants.IntentExtra.MODE_OF_PAYMENT, modeOfPayment);
        i.putExtra(Constants.IntentExtra.SELECTED_USERS, selectedUsers);

        context.startActivity(i);

    }


}
