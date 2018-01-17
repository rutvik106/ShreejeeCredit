package in.fusionbit.shreejeecredit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.fusionbit.shreejeecredit.R;
import in.fusionbit.shreejeecredit.model.CheckSpinnerModel;

/**
 * Created by rutvikmehta on 23/12/17.
 */

public class CheckSpinnerAdapter extends ArrayAdapter<CheckSpinnerModel> {

    private List<CheckSpinnerModel> spinnerModelList;

    public CheckSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<CheckSpinnerModel> objects) {
        super(context, resource, objects);
        this.spinnerModelList = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getSelectedItems(position, convertView, parent);
    }

    public View getSelectedItems(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null, false);

        String selected = "";

        for (CheckSpinnerModel model : spinnerModelList) {
            if (model.isChecked()) {
                selected = selected + model.getLabel() + ", ";
            }
        }

        if (selected.isEmpty()) {
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText("None Selected");
        } else {
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(selected.substring(0, selected.length() - 2));
        }

        return convertView;

    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.check_spinner_row, null, false);
            viewHolder.checkBox = (AppCompatCheckBox) convertView.findViewById(R.id.cb_checkSpinnerItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setText(spinnerModelList.get(position).getLabel());
        viewHolder.checkBox.setChecked(spinnerModelList.get(position).isChecked());
        viewHolder.checkBox.setTag(position);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int getPosition = (Integer) view.getTag();

                if (((AppCompatCheckBox) view).isChecked()) {
                    spinnerModelList.get(getPosition).setChecked(true);
                } else {
                    spinnerModelList.get(getPosition).setChecked(false);
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

            }
        });

        return convertView;

    }

    private class ViewHolder {
        AppCompatCheckBox checkBox;
    }

    public String getSelectedUsers() {
        String selected = "";
        for (CheckSpinnerModel model : spinnerModelList) {
            if (model.isChecked()) {
                selected = selected + model.getId() + ",";
            }
        }

        return selected.isEmpty() ? "" : selected.substring(0, selected.length() - 1);
    }
}
