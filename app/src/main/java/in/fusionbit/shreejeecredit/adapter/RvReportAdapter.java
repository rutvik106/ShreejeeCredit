package in.fusionbit.shreejeecredit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.fusionbit.shreejeecredit.apimodel.Report;
import in.fusionbit.shreejeecredit.viewholder.VhReportItem;

/**
 * Created by rutvikmehta on 02/01/18.
 */

public class RvReportAdapter extends RecyclerView.Adapter {

    private final Context context;

    private final List<Report> reportList;

    public RvReportAdapter(final Context context) {
        this.context = context;
        reportList = new ArrayList<>();
    }

    public void addReport(Report report) {
        reportList.add(report);
        notifyItemInserted(reportList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return VhReportItem.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VhReportItem.bind((VhReportItem) holder, reportList.get(position));
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}
