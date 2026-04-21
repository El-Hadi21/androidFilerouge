package com.example.accidentalert;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.accidentalert.adapter.IssueAdapter;
import com.example.accidentalert.fragment.Screen1Fragment;
import com.example.accidentalert.fragment.Screen2Fragment;
import com.example.accidentalert.fragment.Screen3Fragment;
import com.example.accidentalert.fragment.Screen4Fragment;
import com.example.accidentalert.fragment.Screen5Fragment;
import com.example.accidentalert.interfaces.ClickableIssue;
import com.example.accidentalert.interfaces.Notifiable;
import com.example.accidentalert.model.Issue;
import com.example.accidentalert.model.IssueList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Notifiable, ClickableIssue {

    private Fragment[] tabFragments = new Fragment[5];
    private int currentFragmentIndex = 0;
    private boolean isStarting = true;
    private LocationManager locationManager;
    public String lastKnownLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabFragments[0] = new Screen1Fragment();
        tabFragments[1] = new Screen2Fragment();
        tabFragments[2] = new Screen3Fragment();
        tabFragments[3] = new Screen4Fragment();
        tabFragments[4] = new Screen5Fragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, tabFragments[0])
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) switchFragment(0);
            else if (itemId == R.id.nav_list) switchFragment(1);
            else if (itemId == R.id.nav_map) switchFragment(2);
            else if (itemId == R.id.nav_feed) switchFragment(3);
            else if (itemId == R.id.nav_profile) switchFragment(4);
            return true;
        });

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS
        }, 1);

        initLocationManager();
        createNotificationChannel();
    }

    private void switchFragment(int index) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, tabFragments[index]);
        if (!isStarting) {
            tx.addToBackStack(null);
        } else {
            isStarting = false;
        }
        tx.commit();
        currentFragmentIndex = index;
    }

    @Override
    public void onClick(int numFragment) {
        switchFragment(numFragment);
    }

    @Override
    public void onDataChange(int numFragment, Object object, int actionCode, Object argsAction) {
        if (actionCode == 1) {
            String location = (String) argsAction;
            sendSOSNotification(location);
            Toast.makeText(this, "Alerte envoyée ! Position : " + location, Toast.LENGTH_LONG).show();
        } else if (actionCode == 2) {
            IssueList.getInstance().addIssue((Issue) argsAction);
            switchFragment(1);
        } else if (actionCode == 3) {
            // Status updated via RatingBar
        }
    }

    @Override
    public void onFragmentDisplayed(int fragmentId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        int[] menuIds = {R.id.nav_home, R.id.nav_list, R.id.nav_map, R.id.nav_feed, R.id.nav_profile};
        bottomNavigationView.setSelectedItemId(menuIds[fragmentId]);
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value, IssueAdapter adapter, List<Issue> items) {
        items.get(itemIndex).setStatus(value);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(List<Issue> items, int itemIndex) {
        Issue selected = items.get(itemIndex);
        Bundle args = new Bundle();
        args.putParcelable("issue", selected);
        Screen1Fragment detailFrag = new Screen1Fragment();
        detailFrag.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public android.content.Context getContext() {
        return this;
    }

    private void sendSOSNotification(String location) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "SOS_CHANNEL")
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle("SOS Accident Alert")
                .setContentText("Alerte envoyée — " + location)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        androidx.core.app.NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                "SOS_CHANNEL",
                "SOS Notifications",
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    private void initLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lastKnownLocation = location.getLatitude() + ", " + location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            });
        }
    }
}
