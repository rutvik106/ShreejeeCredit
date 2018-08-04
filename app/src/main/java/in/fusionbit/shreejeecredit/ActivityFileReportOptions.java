package in.fusionbit.shreejeecredit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;
import android.view.View;
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

public class ActivityFileReportOptions extends ActivityBase {

    @BindView(R.id.et_fileFromDate)
    TextInputEditText etFileFromDate;
    @BindView(R.id.et_fileToDate)
    TextInputEditText etFileToDate;
    @BindView(R.id.tv_selectUserFile)
    TextView tvSelectUserFile;
    @BindView(R.id.spin_usersFile)
    Spinner spinUsersFile;
    @BindView(R.id.report_rb_received)
    RadioButton reportRbReceived;
    @BindView(R.id.report_rb_notReceived)
    RadioButton reportRbNotReceived;
    @BindView(R.id.report_rg_receivedStatus)
    RadioGroup reportRgReceivedStatus;

    Calendar fromDate = Calendar.getInstance();
    DatePickerDialog datePickerFromDate;

    Calendar toDate = Calendar.getInstance();
    DatePickerDialog datePickerToDate;

    CheckSpinnerAdapter adapter;

    private String receivedStatus = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_report_options);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("File Report");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        datePickerFromDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                fromDate.set(Calendar.YEAR, year);
                fromDate.set(Calendar.MONTH, monthOfYear);
                fromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etFileFromDate.setText(date);
            }
        }, fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH));

        //datePickerFromDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        etFileFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFromDate.show();
            }
        });

        etFileFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                etFileToDate.setText(date);
            }
        }, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH),
                toDate.get(Calendar.DAY_OF_MONTH));

        //datePickerToDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        etFileToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerToDate.show();
            }
        });

        etFileToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

            tvSelectUserFile.setVisibility(View.VISIBLE);
            spinUsersFile.setVisibility(View.VISIBLE);

            final List<CheckSpinnerModel> models = new ArrayList<>();

            for (UserModel.UserListBean user : App.getCurrentUser().getUser_list()) {
                models.add(new CheckSpinnerModel(user.getAdmin_id(), user.getAdmin_name()));
            }

        /*models.add(new CheckSpinnerModel(11, "Jeet"));
        models.add(new CheckSpinnerModel(22, "Sanket"));
        models.add(new CheckSpinnerModel(33, "Rutvik"));*/

            adapter = new CheckSpinnerAdapter(this, 0, models);

            spinUsersFile.setAdapter(adapter);
        }


        reportRgReceivedStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.report_rb_received) {
                    receivedStatus = "1";
                } else if (id == R.id.report_rb_notReceived) {
                    receivedStatus = "0";
                }
            }
        });

        reportRbReceived.setChecked(true);

    }


    @OnClick(R.id.btn_generateFileReport)
    public void onViewClicked() {
        ActivityFileReport.start(this, etFileFromDate.getText().toString(),
                etFileToDate.getText().toString(), adapter != null ? adapter.getSelectedUsers() : "", receivedStatus);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(final Context context) {
        Intent i = new Intent(context, ActivityFileReportOptions.class);
        context.startActivity(i);
    }

}
