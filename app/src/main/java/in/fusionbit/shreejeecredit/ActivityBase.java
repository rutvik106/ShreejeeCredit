package in.fusionbit.shreejeecredit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rutvikmehta on 16/12/17.
 */

public class ActivityBase extends AppCompatActivity {

    ProgressDialog progressDialog;

    public void showProgressDialog(final String title, final String message) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
    }
}
