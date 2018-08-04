package in.fusionbit.shreejeecredit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.fusionbit.shreejeecredit.apimodel.FileReport;
import in.fusionbit.shreejeecredit.viewholder.VhFileReportItem;

public class RvFileReportAdapter extends RecyclerView.Adapter {

    private final Context context;

    private final List<FileReport> fileReportList;

    public RvFileReportAdapter(final Context context) {
        this.context = context;
        fileReportList = new ArrayList<>();
    }

    public void addFileReport(final FileReport fileReport) {
        fileReportList.add(fileReport);
        notifyItemInserted(fileReportList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return VhFileReportItem.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VhFileReportItem.bind((VhFileReportItem) holder, fileReportList.get(position));
    }

    @Override
    public int getItemCount() {
        return fileReportList.size();
    }

    public void clear() {
        fileReportList.clear();
        notifyDataSetChanged();
    }
}
