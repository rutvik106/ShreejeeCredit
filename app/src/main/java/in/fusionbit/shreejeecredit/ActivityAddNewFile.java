package in.fusionbit.shreejeecredit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.BrokerName;
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
    AppCompatAutoCompleteTextView actFileDealerName;
    @BindView(R.id.et_fileRemarks)
    TextInputEditText etFileRemarks;

    private DatePickerDialog fileDatePickerDialog;
    private Calendar selectedFileDate = Calendar.getInstance();

    private boolean etContactNoClicked = false;
    private Call<List<BrokerName>> searchBroker;


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


        actFileDealerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etContactNoClicked = false;
                return false;
            }
        });

        actFileDealerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etContactNoClicked = true;

                actFileDealerName.requestFocus();

                actFileDealerName.dismissDropDown();
                final BrokerName term = (BrokerName) adapterView.getItemAtPosition(i);

                //Toast.makeText(ActivityCreateReceipt.this, term, Toast.LENGTH_SHORT).show();

                if (term != null) {
                    if (!term.getBroker_name().isEmpty()) {
                        Log.i(App.APP_TAG, term.getBroker_name());
                        final String name = term.getBroker_name();

                        actFileDealerName.setText(name);
                    }
                }
            }
        });


        actFileDealerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                    cancelBrokerSearchCall();

                    searchBroker = Api.Receipt.getBrokerNamesLike(charSequence.toString(),
                            new Callback<List<BrokerName>>() {
                                @Override
                                public void onResponse(Call<List<BrokerName>> call, Response<List<BrokerName>> response) {
                                    if (response.body() != null) {

                                        final ArrayAdapter<BrokerName> adapter = new ArrayAdapter<BrokerName>
                                                (ActivityAddNewFile.this,
                                                        android.R.layout.select_dialog_item, response.body()) {

                                            @NonNull
                                            @Override
                                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                                View view = LayoutInflater.from(ActivityAddNewFile.this)
                                                        .inflate(android.R.layout.select_dialog_item, parent, false);
                                                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getBroker_name());
                                                return view;
                                            }

                                            @Override
                                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                                View view = LayoutInflater.from(ActivityAddNewFile.this)
                                                        .inflate(android.R.layout.select_dialog_item, parent, false);
                                                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getBroker_name());
                                                return view;
                                            }
                                        };

                                        actFileDealerName.setAdapter(adapter);//setting the adapter data into the
                                        if (!etContactNoClicked) {
                                            actFileDealerName.showDropDown();
                                        }
                                    }


                                }

                                @Override
                                public void onFailure(Call<List<BrokerName>> call, Throwable t) {
                                    if (!call.isCanceled()) {
                                        Toast.makeText(ActivityAddNewFile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else {
                    actFileDealerName.setAdapter(null);
                    actFileDealerName.dismissDropDown();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void cancelBrokerSearchCall() {
        if (searchBroker != null) {
            if (!searchBroker.isCanceled()) {
                searchBroker.cancel();
            }
            searchBroker = null;
        }
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
            final String dealerName = actFileDealerName.getText().toString();
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

        if (actFileDealerName.getText().toString().isEmpty()) {
            actFileDealerName.setError("Required");
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
