package in.fusionbit.shreejeecredit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.adapter.CheckSpinnerAdapter;
import in.fusionbit.shreejeecredit.apimodel.UserModel;
import in.fusionbit.shreejeecredit.model.CheckSpinnerModel;

import static in.fusionbit.shreejeecredit.Constants.ModeOfPayment.ALL;


public class ActivitySearchReceipt extends ActivityBase {

    @BindView(R.id.et_fromDate)
    TextInputEditText etFromDate;
    @BindView(R.id.et_toDate)
    TextInputEditText etToDate;
    @BindView(R.id.spin_users)
    Spinner spinUsers;
    @BindView(R.id.report_rb_cash)
    RadioButton reportRbCash;
    @BindView(R.id.report_rb_cheque)
    RadioButton reportRbCheque;
    @BindView(R.id.report_rb_all)
    RadioButton reportRbAll;
    @BindView(R.id.report_rg_modeOfPayment)
    RadioGroup reportRgModeOfPayment;
    @BindView(R.id.btn_generateReport)
    Button btnGenerateReport;

    Calendar fromDate = Calendar.getInstance();
    DatePickerDialog datePickerFromDate;

    Calendar toDate = Calendar.getInstance();
    DatePickerDialog datePickerToDate;

    CheckSpinnerAdapter adapter;

    String modeOfPayment = ALL;

    @BindView(R.id.tv_selectUser)
    TextView tvSelectUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_receipt);
        ButterKnife.bind(this);
        setTitle("Receipt Reports");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePickerFromDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                fromDate.set(Calendar.YEAR, year);
                fromDate.set(Calendar.MONTH, monthOfYear);
                fromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etFromDate.setText(date);
            }
        }, fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH));

        //datePickerFromDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFromDate.show();
            }
        });

        etFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerFromDate.show();
                }
            }
        });

        datePickerToDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                toDate.set(Calendar.YEAR, year);
                toDate.set(Calendar.MONTH, monthOfYear);
                toDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etToDate.setText(date);
            }
        }, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH),
                toDate.get(Calendar.DAY_OF_MONTH));

        //datePickerToDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerToDate.show();
            }
        });

        etToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerToDate.show();
                }
            }
        });


        if (App.getCurrentUser().getUser().getAdmin_rights()
                .contains(Constants.UserRights.CAN_GENERATE_REPORT_FOR_OTHER_USERS) ||
                App.getCurrentUser().getUser().getAdmin_rights()
                        .contains(Constants.UserRights.FULL)) {

            tvSelectUser.setVisibility(View.VISIBLE);
            spinUsers.setVisibility(View.VISIBLE);

            final List<CheckSpinnerModel> models = new ArrayList<>();

            for (UserModel.UserListBean user : App.getCurrentUser().getUser_list()) {
                models.add(new CheckSpinnerModel(user.getAdmin_id(), user.getAdmin_name()));
            }

        /*models.add(new CheckSpinnerModel(11, "Jeet"));
        models.add(new CheckSpinnerModel(22, "Sanket"));
        models.add(new CheckSpinnerModel(33, "Rutvik"));*/

            adapter = new CheckSpinnerAdapter(this, 0, models);

            spinUsers.setAdapter(adapter);
        }


        reportRgModeOfPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.report_rb_all) {
                    modeOfPayment = ALL;
                } else if (id == R.id.report_rb_cheque) {
                    modeOfPayment = Constants.ModeOfPayment.CHEQUE;
                } else if (id == R.id.report_rb_cash) {
                    modeOfPayment = Constants.ModeOfPayment.CASH;
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(final Context context) {
        Intent i = new Intent(context, ActivitySearchReceipt.class);
        context.startActivity(i);
    }

    @OnClick(R.id.btn_generateReport)
    public void onViewClicked() {
        ActivityReport.start(this, etFromDate.getText().toString(),
                etToDate.getText().toString(), modeOfPayment,
                adapter != null ? adapter.getSelectedUsers() : "");
    }


}
