package net.kojizo.android.pm25viewer;

import gueei.binding.app.BindingActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BindingActivity {

    private MainViewModel viewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAndBindRootView(R.layout.activity_main, viewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
