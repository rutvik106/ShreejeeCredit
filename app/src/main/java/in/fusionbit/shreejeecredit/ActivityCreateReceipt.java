package in.fusionbit.shreejeecredit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.ReceiptResponse;
import in.fusionbit.shreejeecredit.apimodel.Term;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fusionbit.shreejeecredit.Constants.ModeOfPayment.CASH;
import static in.fusionbit.shreejeecredit.Constants.ModeOfPayment.CHEQUE;
import static in.fusionbit.shreejeecredit.Constants.OnAccount.INSTALLMENT;
import static in.fusionbit.shreejeecredit.Constants.OnAccount.ODI;
import static in.fusionbit.shreejeecredit.Constants.OnAccount.OTHERS;

public class ActivityCreateReceipt extends ActivityBase {

    @BindView(R.id.et_acNo)
    TextInputEditText etAcNo;
    @BindView(R.id.et_receivedFrom)
    TextInputEditText etReceivedFrom;
    @BindView(R.id.tv_paymentMethod)
    TextView tvPaymentMethod;
    @BindView(R.id.rb_cash)
    AppCompatRadioButton rbCash;
    @BindView(R.id.rb_cheque)
    AppCompatRadioButton rbCheque;
    @BindView(R.id.rg_modeOfPayment)
    RadioGroup rgModeOfPayment;
    @BindView(R.id.et_chequeOrDdNo)
    TextInputEditText etChequeOrDdNo;
    @BindView(R.id.et_chequeOrDdDate)
    TextInputEditText etChequeOrDdDate;
    @BindView(R.id.et_chequeOrDdBank)
    AppCompatAutoCompleteTextView etChequeOrDdBank;
    /*@BindView(R.id.et_chequeOrDdDrawnOn)
    TextInputEditText etChequeOrDdDrawnOn;*/
    @BindView(R.id.ll_paymentMethodChequeOrDd)
    LinearLayout llPaymentMethodChequeOrDd;
    @BindView(R.id.tv_accountOf)
    TextView tvAccountOf;
    @BindView(R.id.rb_inst)
    AppCompatRadioButton rbInst;
    @BindView(R.id.rb_odi)
    AppCompatRadioButton rbOdi;
    @BindView(R.id.rb_other)
    AppCompatRadioButton rbOther;
    @BindView(R.id.rg_accountOf)
    RadioGroup rgAccountOf;
    @BindView(R.id.et_remarks)
    TextInputEditText etRemarks;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.til_chequeOrDdNo)
    TextInputLayout tilChequeOrDdNo;
    @BindView(R.id.til_chequeOrDdDate)
    TextInputLayout tilChequeOrDdDate;
    @BindView(R.id.til_chequeOrDdBank)
    TextInputLayout tilChequeOrDdBank;

    Calendar selectedChequeOrDdDate = Calendar.getInstance();
    DatePickerDialog datePickerChequeOrDdDate;
    @BindView(R.id.et_chequeOrDdBranch)
    AppCompatAutoCompleteTextView etChequeOrDdBranch;
    @BindView(R.id.til_chequeOrDdBranch)
    TextInputLayout tilChequeOrDdBranch;
    @BindView(R.id.et_receiptDate)
    TextInputEditText etReceiptDate;
    @BindView(R.id.et_contactNo)
    AppCompatAutoCompleteTextView etContactNo;
    @BindView(R.id.cb_sendSms)
    CheckBox cbSendSms;
    @BindView(R.id.et_customerName)
    TextInputEditText etCustomerName;
    private String modeOfPayment = CASH;
    private String onAccount = INSTALLMENT;
    private Call<List<Term>> searchBankNames;
    private Call<List<Term>> searchBranchNames;
    private Call<List<Term>> searchContact;

    /*Calendar selectedChequeOrDdDrawnDate = Calendar.getInstance();
    DatePickerDialog datePickerChequeOrDdDrawnDate;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);
        setTitle("Create New Receipt");
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar today = Calendar.getInstance();
        etReceiptDate.setText(today.get(Calendar.DAY_OF_MONTH) + "/" + (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.YEAR));

        datePickerChequeOrDdDate = new DatePickerDialog(ActivityCreateReceipt.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                selectedChequeOrDdDate.set(Calendar.YEAR, year);
                selectedChequeOrDdDate.set(Calendar.MONTH, monthOfYear);
                selectedChequeOrDdDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etChequeOrDdDate.setText(date);
            }
        }, selectedChequeOrDdDate.get(Calendar.YEAR), selectedChequeOrDdDate.get(Calendar.MONTH),
                selectedChequeOrDdDate.get(Calendar.DAY_OF_MONTH));

        datePickerChequeOrDdDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        /*datePickerChequeOrDdDrawnDate = new DatePickerDialog(ActivityCreateReceipt.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                selectedChequeOrDdDrawnDate.set(Calendar.YEAR, year);
                selectedChequeOrDdDrawnDate.set(Calendar.MONTH, monthOfYear);
                selectedChequeOrDdDrawnDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etChequeOrDdDrawnOn.setText(date);
            }
        }, selectedChequeOrDdDrawnDate.get(Calendar.YEAR), selectedChequeOrDdDrawnDate.get(Calendar.MONTH),
                selectedChequeOrDdDrawnDate.get(Calendar.DAY_OF_MONTH));

        datePickerChequeOrDdDrawnDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());*/

        rgModeOfPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_cheque) {
                    modeOfPayment = CHEQUE;
                    llPaymentMethodChequeOrDd.setVisibility(View.VISIBLE);
                } else {
                    modeOfPayment = CASH;
                    llPaymentMethodChequeOrDd.setVisibility(View.GONE);
                }
            }
        });

        rgAccountOf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_inst) {
                    onAccount = INSTALLMENT;
                } else if (id == R.id.rb_odi) {
                    onAccount = ODI;
                } else {
                    onAccount = OTHERS;
                }
            }
        });

        etChequeOrDdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerChequeOrDdDate.show();
            }
        });

        etChequeOrDdDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerChequeOrDdDate.show();
                }
            }
        });

        /*etChequeOrDdDrawnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerChequeOrDdDrawnDate.show();
            }
        });

        etChequeOrDdDrawnOn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerChequeOrDdDrawnDate.show();
                }
            }
        });*/

        etChequeOrDdBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2) {
                    cancelSearchBankCall();
                    searchBankNames = Api.Term.getBankNames(charSequence.toString(), new Callback<List<Term>>() {
                        @Override
                        public void onResponse(Call<List<Term>> call, Response<List<Term>> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    final ArrayList<String> terms = new ArrayList<>();
                                    for (Term term : response.body()) {
                                        terms.add(term.getLabel());
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                            (ActivityCreateReceipt.this, android.R.layout.select_dialog_item, terms);

                                    etChequeOrDdBank.setAdapter(adapter);//setting the adapter data into the
                                    if (charSequence.length() == 3) {
                                        etChequeOrDdBank.showDropDown();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Term>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etChequeOrDdBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2) {
                    cancelSearchBranchCall();
                    searchBranchNames = Api.Term.getBankBranchNames(charSequence.toString(),
                            etChequeOrDdBank.getText().toString(), new Callback<List<Term>>() {
                                @Override
                                public void onResponse(Call<List<Term>> call, Response<List<Term>> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null) {
                                            final ArrayList<String> terms = new ArrayList<>();
                                            for (Term term : response.body()) {
                                                terms.add(term.getLabel());
                                            }
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                                    (ActivityCreateReceipt.this, android.R.layout.select_dialog_item, terms);

                                            etChequeOrDdBranch.setAdapter(adapter);//setting the adapter data into the
                                            if (charSequence.length() == 3) {
                                                etChequeOrDdBranch.showDropDown();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Term>> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etContactNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etContactNo.dismissDropDown();
                final Term term = (Term) adapterView.getItemAtPosition(i);

                //Toast.makeText(ActivityCreateReceipt.this, term, Toast.LENGTH_SHORT).show();

                if (term != null) {
                    if (!term.getLabel().isEmpty() && term.getLabel().contains("|")) {
                        Log.i(App.APP_TAG, term.getLabel());
                        final String contact = term.getLabel().substring(0, term.getLabel().indexOf("|"));
                        final String name = term.getLabel()
                                .substring(term.getLabel().indexOf("|") + 1, term.getLabel().length());

                        etContactNo.setText(contact.trim());
                        etCustomerName.setText(name.trim());
                        if (term.getAccount_no() != null) {
                            etAcNo.setText(term.getAccount_no());
                        }
                        Log.i(App.APP_TAG, "Contact No: " + contact);
                        Log.i(App.APP_TAG, "Name: " + name);
                    }
                }
            }
        });

        etContactNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2) {
                    cancelSearchBranchCall();
                    searchContact = Api.Term.getContact(charSequence.toString(),
                            new Callback<List<Term>>() {
                                @Override
                                public void onResponse(Call<List<Term>> call, Response<List<Term>> response) {
                                    if (response.body() != null) {

                                        final ArrayAdapter<Term> adapter = new ArrayAdapter<Term>
                                                (ActivityCreateReceipt.this,
                                                        android.R.layout.select_dialog_item, response.body()) {

                                            @NonNull
                                            @Override
                                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                                View view = LayoutInflater.from(ActivityCreateReceipt.this)
                                                        .inflate(android.R.layout.select_dialog_item, parent, false);
                                                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getLabel());
                                                return view;
                                            }

                                            @Override
                                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                                View view = LayoutInflater.from(ActivityCreateReceipt.this)
                                                        .inflate(android.R.layout.select_dialog_item, parent, false);
                                                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getLabel());
                                                return view;
                                            }
                                        };

                                        etContactNo.setAdapter(adapter);//setting the adapter data into the
                                        if (charSequence.length() == 3) {
                                            etContactNo.showDropDown();
                                        }
                                    }


                                }

                                @Override
                                public void onFailure(Call<List<Term>> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkRights();


    }

    private void checkRights() {
        if (App.getCurrentUser().getUser().getAdmin_rights().contains(Constants.UserRights.CAN_ADD_CHEQUE) ||
                App.getCurrentUser().getUser().getAdmin_rights().contains(Constants.UserRights.FULL)) {
            rbCheque.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(final Context context) {
        Intent i = new Intent(context, ActivityCreateReceipt.class);
        context.startActivity(i);
    }

    @OnClick(R.id.btn_submitReceipt)
    public void onViewClicked() {
        validateFields();
    }

    private void validateFields() {
        boolean valid = true;
        if (TextUtils.isEmpty(etAcNo.getText())) {
            etAcNo.setError("Required");
            valid = false;
        }
        /*if (TextUtils.isEmpty(etReceivedFrom.getText())) {
            etReceivedFrom.setError("Required");
            valid = false;
        }*/
        /*if (TextUtils.isEmpty(etAmount.getText())) {
            etAmount.setError("Required");
            valid = false;
        }*/
        if (TextUtils.isEmpty(etContactNo.getText())) {
            etContactNo.setError("Required");
            valid = false;
        }
        if (TextUtils.isEmpty(etCustomerName.getText())) {
            etCustomerName.setError("Required");
            valid = false;
        }

        if (rbCheque.isChecked()) {
            /*if (TextUtils.isEmpty(etChequeOrDdBank.getText())) {
                etChequeOrDdBank.setError("Required");
                valid = false;
            }*/
            /*if (TextUtils.isEmpty(etChequeOrDdBranch.getText())) {
                etChequeOrDdBranch.setError("Required");
                valid = false;
            }*/
            if (TextUtils.isEmpty(etChequeOrDdNo.getText())) {
                etChequeOrDdNo.setError("Required");
                valid = false;
            }
            /*if (TextUtils.isEmpty(etChequeOrDdDate.getText())) {
                etChequeOrDdDate.setError("Required");
                valid = false;
            }*/
        }
        if (valid) {
            confirmSubmit();
        }
    }

    private void confirmSubmit() {
        new AlertDialog.Builder(this)
                .setTitle("Submit Receipt")
                .setMessage("Are you sure you want to submit this receipt?")
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        submitReceipt();
                    }
                }).setNegativeButton("CANCEL", null)
                .show();
    }

    private void submitReceipt() {

        String sendSms = "0";
        if (cbSendSms.isChecked()) {
            sendSms = "1";
        }

        showProgressDialog("Submitting Receipt...", "Please Wait...");
        Api.Receipt.submitReceipt(etReceiptDate.getText().toString(), etAcNo.getText().toString(),
                etReceivedFrom.getText().toString(), etAmount.getText().toString(), modeOfPayment,
                etChequeOrDdNo.getText().toString(), etChequeOrDdBank.getText().toString(), etChequeOrDdBranch.getText().toString(),
                etChequeOrDdDate.getText().toString(), onAccount, etRemarks.getText().toString(),
                etContactNo.getText().toString(),
                etCustomerName.getText().toString(),
                sendSms,
                new Callback<ReceiptResponse>() {
                    @Override
                    public void onResponse(Call<ReceiptResponse> call, Response<ReceiptResponse> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResponse().getReceipt_no() > 0) {
                                    Toast.makeText(ActivityCreateReceipt.this,
                                            response.body().getResponse().getReceipt_no() +
                                                    " Receipt added successfully", Toast.LENGTH_SHORT).show();
                                    ActivityPrintReceipt.start(ActivityCreateReceipt.this,
                                            response.body().getResponse().getReceipt_no());
                                    ActivityCreateReceipt.this.finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiptResponse> call, Throwable t) {
                        hideProgressDialog();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        cancelSearchBankCall();
        cancelSearchBranchCall();
        cancelSearchContactCall();
        super.onDestroy();
    }

    private void cancelSearchBankCall() {
        if (searchBankNames != null) {
            if (!searchBankNames.isCanceled()) {
                searchBankNames.cancel();
            }
            searchBankNames = null;
        }
    }

    private void cancelSearchBranchCall() {
        if (searchBranchNames != null) {
            if (!searchBranchNames.isCanceled()) {
                searchBranchNames.cancel();
            }
            searchBranchNames = null;
        }
    }

    private void cancelSearchContactCall() {
        if (searchContact != null) {
            if (!searchContact.isCanceled()) {
                searchContact.cancel();
            }
            searchContact = null;
        }
    }
}
/*
    ArrayAdapter<Term> adapter = new ArrayAdapter<Term>
            (ActivityCreateReceipt.this, android.R.layout.select_dialog_item,
                    response.body()) {

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(ActivityCreateReceipt.this)
                    .inflate(android.R.layout.select_dialog_item, parent, false);
            ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getLabel());
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(ActivityCreateReceipt.this)
                    .inflate(android.R.layout.select_dialog_item, parent, false);
            ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getLabel());
            return view;
        }
    };*/
