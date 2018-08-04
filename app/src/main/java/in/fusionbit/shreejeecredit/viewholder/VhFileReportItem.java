package in.fusionbit.shreejeecredit.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.fusionbit.shreejeecredit.R;
import in.fusionbit.shreejeecredit.apimodel.FileReport;

public class VhFileReportItem extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_fileReportNo)
    TextView tvFileReportNo;
    @BindView(R.id.tv_fileReportDate)
    TextView tvFileReportDate;
    @BindView(R.id.tv_fileReportCustomerName)
    TextView tvFileReportCustomerName;
    @BindView(R.id.tv_fileReportVehicleMode)
    TextView tvFileReportVehicleMode;
    @BindView(R.id.tv_fileReportLoanAmount)
    TextView tvFileReportLoanAmount;
    @BindView(R.id.tv_fileReportNetPayment)
    TextView tvFileReportNetPayment;
    @BindView(R.id.tv_fileReportReceiveStatus)
    TextView tvFileReportReceiveStatus;
    @BindView(R.id.tv_fileReportRemarks)
    TextView tvFileReportRemarks;
    @BindView(R.id.tv_fileReportCreatedBy)
    TextView tvFileReportCreatedBy;
    private Context context;

    private VhFileReportItem(final Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VhFileReportItem create(final Context context, final ViewGroup parent) {
        return new VhFileReportItem(context, LayoutInflater
                .from(context).inflate(R.layout.single_file_report_item, parent, false));
    }

    public static void bind(final VhFileReportItem vh, final FileReport model) {
        vh.tvFileReportNo.setText(model.getFile_no());
        vh.tvFileReportDate.setText(model.getFile_date());
        vh.tvFileReportCustomerName.setText(model.getCustomer_name());
        vh.tvFileReportVehicleMode.setText(model.getVehicle_model());
        vh.tvFileReportLoanAmount.setText(model.getLoan_amount());
        vh.tvFileReportNetPayment.setText(model.getNet_payment());
        vh.tvFileReportReceiveStatus.setText(model.getReceived());
        vh.tvFileReportRemarks.setText(model.getRemarks());
        vh.tvFileReportCreatedBy.setText(model.getCreated_by());
    }

}
