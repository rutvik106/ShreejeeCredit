package in.fusionbit.shreejeecredit.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.ActivityPrintReceipt;
import in.fusionbit.shreejeecredit.R;
import in.fusionbit.shreejeecredit.apimodel.Report;

/**
 * Created by rutvikmehta on 02/01/18.
 */

public class VhReportItem extends RecyclerView.ViewHolder {

    private final Context context;

    Report model;
    @BindView(R.id.tv_reportRasidNo)
    TextView tvReportRasidNo;
    @BindView(R.id.tv_reportAccountNo)
    TextView tvReportAccountNo;
    @BindView(R.id.tv_reportPaymentMode)
    TextView tvReportPaymentMode;
    @BindView(R.id.tv_reportPaymentDate)
    TextView tvReportPaymentDate;
    @BindView(R.id.tv_reportPaymentAmount)
    TextView tvReportPaymentAmount;
    @BindView(R.id.tv_reportCustomerName)
    TextView tvReportCustomerName;
    @BindView(R.id.tv_reportCustomerContact)
    TextView tvReportCustomerContact;
    @BindView(R.id.tv_reportRemarks)
    TextView tvReportRemarks;
    @BindView(R.id.tv_reportCreatedBy)
    TextView tvReportCreatedBy;

    private VhReportItem(final Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VhReportItem create(final Context context, ViewGroup parent) {
        return new VhReportItem(context,
                LayoutInflater.from(context).inflate(R.layout.single_report_item, parent, false));
    }

    public static void bind(VhReportItem vh, Report model) {
        vh.model = model;
        vh.tvReportRasidNo.setText(String.valueOf(model.getRasid_no()));
        vh.tvReportAccountNo.setText(model.getAc_no());
        vh.tvReportPaymentMode.setText(model.getPayment_mode());
        vh.tvReportPaymentDate.setText(model.getPayment_dateString());
        vh.tvReportPaymentAmount.setText(String.valueOf(model.getPayment_amount()));
        vh.tvReportCustomerName.setText(model.getCustomer_name());
        vh.tvReportCustomerContact.setText(model.getCustomer_number());
        vh.tvReportCreatedBy.setText(model.getAdmin_name());
        vh.tvReportRemarks.setText(model.getRemarks());
    }

    @OnClick(R.id.btn_reportPrint)
    public void onViewClicked() {
        ActivityPrintReceipt.start(context, model.getEmi_payment_id());
    }
}
