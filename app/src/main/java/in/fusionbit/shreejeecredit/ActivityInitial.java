package in.fusionbit.shreejeecredit;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityInitial extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityLogin.start(ActivityInitial.this);
            }
        }, 1800);

    }
}
