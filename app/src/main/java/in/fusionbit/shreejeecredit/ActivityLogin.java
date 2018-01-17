package in.fusionbit.shreejeecredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.CheckBox;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeecredit.api.Api;
import in.fusionbit.shreejeecredit.apimodel.UserModel;
import in.fusionbit.shreejeecredit.util.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends ActivityBase {

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.cb_rememberMe)
    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setTitle("Login");

        String[] credentials = SharedPreferences.getUsernamePassword(this);

        if (credentials != null) {
            etUsername.setText(credentials[0]);
            etPassword.setText(credentials[1]);
            cbRememberMe.setChecked(true);
        }

    }

    private void tryLogin() {
        showProgressDialog("Please Wait...", "Logging in...");

        saveCredentials();

        Api.User.tryLogin(etUsername.getText().toString(), etPassword.getText().toString(),
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.code() == 200) {
                            hideProgressDialog();
                            App.setCurrentUser(response.body().getUser().getSession_id());
                            App.setUserLists(response.body());
                            ActivityHome.start(ActivityLogin.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(ActivityLogin.this, "Invalid username or password.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void saveCredentials() {
        if (cbRememberMe.isChecked()) {
            SharedPreferences.
                    saveUsernamePassword(this,
                            etUsername.getText().toString(), etPassword.getText().toString());
        } else {
            SharedPreferences.
                    saveUsernamePassword(this,
                            null, null);
        }
    }

    private boolean validateInput() {
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("Username is required");
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required");
        }
        if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static void start(final Context context) {
        Intent i = new Intent(context, ActivityLogin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        //validate input
        final boolean isInputValid = validateInput();

        //call api
        if (isInputValid) {
            tryLogin();
        }

    }
}
