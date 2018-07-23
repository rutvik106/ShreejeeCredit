package in.fusionbit.shreejeecredit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.FileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddNewFile extends ActivityBase {

    @BindView(R.id.et_fileDate)
    TextInputEditText etFileDate;
    @BindView(R.id.et_fileCustomerName)
    TextInputEditText etFileCustomerName;
    @BindView(R.id.et_fileVehicleModel)
    TextInputEditText etFileVehicleModel;
    @BindView(R.id.et_fileLoanAmount)
    TextInputEditText etFileLoanAmount;
    @BindView(R.id.et_fileNetPayment)
    TextInputEditText etFileNetPayment;
    @BindView(R.id.et_fileDealerName)
    AppCompatAutoCompleteTextView etFileDealerName;
    @BindView(R.id.et_fileRemarks)
    TextInputEditText etFileRemarks;

    private DatePickerDialog fileDatePickerDialog;
    private Calendar selectedFileDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_file);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add New File");
        }

        fileDatePickerDialog = new DatePickerDialog(ActivityAddNewFile.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                selectedFileDate.set(Calendar.YEAR, year);
                selectedFileDate.set(Calendar.MONTH, monthOfYear);
                selectedFileDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setFileDate();
            }
        }, selectedFileDate.get(Calendar.YEAR), selectedFileDate.get(Calendar.MONTH),
                selectedFileDate.get(Calendar.DAY_OF_MONTH));

        fileDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        etFileDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileDatePickerDialog.show();
            }
        });

        etFileDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    fileDatePickerDialog.show();
                }
            }
        });

        setFileDate();

    }

    private void setFileDate() {
        String date = selectedFileDate.get(Calendar.DAY_OF_MONTH) + "/" +
                selectedFileDate.get(Calendar.MONTH) + "/" + selectedFileDate.get(Calendar.YEAR);
        etFileDate.setText(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_saveFile)
    public void onViewClicked() {

        if (fieldsAreValid()) {

            showProgressDialog("Adding File...", "Please Wait...");

            final String date = etFileDate.getText().toString();
            final String customerName = etFileCustomerName.getText().toString();
            final String vehicleModel = etFileVehicleModel.getText().toString();
            final String loanAmount = etFileLoanAmount.getText().toString();
            final String netPayment = etFileNetPayment.getText().toString();
            final String dealerName = etFileDealerName.getText().toString();
            final String remarks = etFileRemarks.getText().toString();

            Api.Receipt.addNewFile(date, customerName, vehicleModel, loanAmount, netPayment,
                    dealerName, remarks, App.getSessionId(), new Callback<FileResponse>() {
                        @Override
                        public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getResponse().getStatus() == 1) {
                                        Toast.makeText(ActivityAddNewFile.this,
                                                "File Added Successfully (File No: " + response.body().getResponse().getFile_no() + ")",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ActivityAddNewFile.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ActivityAddNewFile.this, "Response is NULL", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ActivityAddNewFile.this,
                                        "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<FileResponse> call, Throwable t) {
                            Toast.makeText(ActivityAddNewFile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                        }
                    });
        }

    }

    private boolean fieldsAreValid() {
        boolean valid = true;

        if (etFileCustomerName.getText().toString().isEmpty()) {
            etFileCustomerName.setError("Required");
            valid = false;
        }

        if (etFileDealerName.getText().toString().isEmpty()) {
            etFileDealerName.setError("Required");
            valid = false;
        }

        if (etFileVehicleModel.getText().toString().isEmpty()) {
            etFileVehicleModel.setError("Required");
            valid = false;
        }

        if (etFileLoanAmount.getText().toString().isEmpty() || Integer.valueOf(etFileLoanAmount.getText().toString()) < 0) {
            etFileLoanAmount.setError("Required and must be greater than 0");
            valid = false;
        }

        if (etFileNetPayment.getText().toString().isEmpty() || Integer.valueOf(etFileNetPayment.getText().toString()) < 0) {
            etFileNetPayment.setError("Required and must be greater than 0");
            valid = false;
        }

        return valid;
    }

    public static void start(Context context) {
        Intent i = new Intent(context, ActivityAddNewFile.class);
        context.startActivity(i);
    }
}
