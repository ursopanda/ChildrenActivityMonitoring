package app.edi.palmprothesismotionmonitoring;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Handler;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import database.Data;
import database.DatabaseHandler;
import database.Device;
import database.Event;
import lv.edi.BluetoothLib.BluetoothService;
import lv.edi.SmartWearProcessing.Sensor;

public class MainActivity extends AppCompatActivity implements ProcessingServiceEventListener {
    private PatientApplication application;
    private final int REQUEST_ENABLE_BT = 1;
    private MenuItem btConnect;
    private ToggleButton startProcessingButton;
    private long sessionTimer, timeInMilliSeconds = 0L;
    Handler handler = new Handler();

    public static int prescribedFlexion = 70;
    public static long prescribedLength = 12999000L;
    public static int prescribedAmount = 10;

    private Vector<Sensor> sensors;

    // elements of UI
    private Button connectBTButton;
    private TextView bluetoothStatus;
    private EditText childName;
    private TextView dataStatusTextView = (TextView) findViewById(R.id.dataStatusTextView);
    private TextView currentNameTextView = (TextView) findViewById(R.id.currentNameTextView);
    private TextView currentActivityTextView = (TextView) findViewById(R.id.currentActivityTextView);
    private TextView processingStatusTextView = (TextView) findViewById(R.id.processingStatusTextView);


    private String btStatus;
    private String batteryStatus;
    private boolean isProcessingRunning;
    private boolean isDataOK;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // DB instance initialization
    DatabaseHandler db = new DatabaseHandler(this);
    Spinner mySpinner=(Spinner) findViewById(R.id.actions_spinner);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        startProcessingButton = (ToggleButton) findViewById(R.id.button_start);
        setSupportActionBar(toolbar);

        application = (PatientApplication) getApplication();
        application.btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (application.btAdapter == null) {
            Toast.makeText(this, getString(R.string.no_bt_support), Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!application.btAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
        if (application.btService == null) {
            application.btService = new BluetoothService(application.sensors, application.BATTERY_PACKET);
            application.btService.registerBluetoothEventListener(application);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        bluetoothStatus = (TextView) findViewById(R.id.btStatusTextView);
        childName = (EditText) findViewById(R.id.nameEditText);

        // Populate the spinner with Activity list
        Spinner spinner = (Spinner) findViewById(R.id.actions_spinner);
       // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.actions_array, android.R.layout.simple_spinner_item);
       // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Thread is used to update UI elements every second
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Check for bluetoothStatus
                                if (application.btService.isConnected())
                                    bluetoothStatus.setText("Connected");
                                else
                                    bluetoothStatus.setText("Not connected");
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        application.mainActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("MAIN_ACTIVITY", "CREATING MENU ");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        btConnect = menu.findItem(R.id.action_bt);
        if (application.btService != null) {
            Log.d("MAIN_ACTIVITY", "BT SERVICE STATUS " + application.btService.isConnected() + " " + application.btService.isConnecting());
            if (application.btService.isConnected()) {
                btConnect.setIcon(R.drawable.check);
                return true;
            }
            if (application.btService.isConnecting()) {
                btConnect.setIcon(R.drawable.loading);
                return true;
            }
            if (!(application.btService.isConnected())) {
                btConnect.setIcon(R.drawable.not);
                return true;
            }
        }
        Log.d("MAIN_ACTIVITY", btConnect.toString());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_bt) {
            //if is connecting block this button
            if (application.btService.isConnecting()) {
                return false;
            }
            if (!(application.btService.isConnected())) {
                if (application.btDevice == null) {
                    Toast.makeText(this, getString(R.string.must_select_bt_device), Toast.LENGTH_SHORT).show();
                    return false;
                }
                application.btService.connectDevice(application.btDevice);
            } else {
                application.btService.disconnectDevice();
            }


        }

        if (id == R.id.action_calibrate) {
            Intent intent = new Intent(this, CalibrationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, getString(R.string.bt_must_be_turned_on), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void btConnected() {
        runOnUiThread(new Runnable() {
            public void run() {
                btConnect.setIcon(R.drawable.check);
            }
        });
    }

    public void btDisconnected() {
        runOnUiThread(new Runnable() {
            public void run() {
                btConnect.setIcon(R.drawable.not);
            }
        });
    }

    public void btConnecting() {
        runOnUiThread(new Runnable() {
            public void run() {
                btConnect.setIcon(R.drawable.loading);
            }
        });
    }

    public void onClickStartProcessing(View v) {

        if (startProcessingButton.isChecked()) {
            if (!application.btService.isConnected()) {
                Toast.makeText(this, getString(R.string.must_connect_bt), Toast.LENGTH_SHORT).show();
                startProcessingButton.setChecked(false);
                return;
            } else {
                //TODO Take parameters from DB, not hardcoded
                application.processingService = new ProcessingService(application.sensors, 40, prescribedFlexion, prescribedLength);
                application.processingService.registerProcessingServiceEventListener(this);
                application.processingService.startProcessing(20);

                processingStatusTextView.setText("STARTED");
                // Thread is used to update TextView objects every second
                Thread t = new Thread() {

                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(20);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.addData(new Data(System.currentTimeMillis(),
                                                application.processingService.getAcc1X(), application.processingService.getAcc1Y(), application.processingService.getAcc1Z(),
                                                application.processingService.getAcc2X(), application.processingService.getAcc2Y(), application.processingService.getAcc2Z(),
                                                application.processingService.getMagn1X(), application.processingService.getMagn1Y(), application.processingService.getMagn1Z(),
                                                application.processingService.getMagn2X(), application.processingService.getMagn2Y(), application.processingService.getMagn2Z(),
                                                "DEVICE ID"));

                                        dataStatusTextView.setText("OK");
                                    }
                                });
                            }
                        } catch (InterruptedException e) {}
                    }
                };
                t.start();
            }
                // TODO Starting Timer of Rehabilitation

        } else {
            application.processingService.stopProcessing();

            // Starting timer for rehab session length
            sessionTimer = SystemClock.uptimeMillis();
            //handler.postDelayed(updateTimer, 0); CURRENTLY produces EXCEPTION. RUNNABLE DOESN'T get valu of sessionTime view
        }
    }



    // Updatinng timer value
    public Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - sessionTimer;
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onProcessingResult(float angle) {
        final float anglef = angle;
        runOnUiThread(new Runnable() {
            public void run() {
                int progress = (int) (100 * anglef / 90);
                long sessionTimeL = application.processingService.getSessionLength();
                String time = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(sessionTimeL) % 60, TimeUnit.MILLISECONDS.toSeconds(sessionTimeL) % 60);
            }
        });
    }

    @Override
    public void onMovementCount(int count) {
        final int countf = count;
        runOnUiThread(new Runnable() {
            public void run() {
//                Log.d("MAIN_ACTIVITY", "movement count view " + movementAmount);
//                movementAmount.setText("" + countf);
            }
        });
    }

    @Override
    public void onStopProcessing() {
        runOnUiThread(new Runnable() {
            public void run() {
                startProcessingButton.setChecked(false);
                Toast.makeText(getApplicationContext(), "Processing finished!", Toast.LENGTH_SHORT).show();
                //TODO Here we push data to DB tables!
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.edi.palmprothesismotionmonitoring/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.edi.palmprothesismotionmonitoring/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    // Button onClick methods' implementation
    public void onClickConnectBluetooth() {

        application.btService.connectDevice(application.btDevice);

        // Save data about the devices (sensor and phone MAC adresses) to the DB
        if (application.btService.isConnected()) {
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            String macAddress = wInfo.getMacAddress();

            db.addDevice(new Device(macAddress, application.btDevice.getAddress()));
        }
    }

    public void onClickSaveNameAndActionButton() {
        String childNameString = childName.getText().toString();
        String selectedAction = mySpinner.getSelectedItem().toString();

        db.addEvent(new Event(
                System.currentTimeMillis(), selectedAction, childNameString, btStatus, batteryStatus, isProcessingRunning, isDataOK
        ));

        currentNameTextView.setText(childNameString);
        currentActivityTextView.setText(selectedAction);
    }

    public void onClickClearName() {childName.setText("");}

    public void onClickStopMonitoring() {
        onStopProcessing();
        application.processingService.stopProcessing();

        processingStatusTextView.setText("STOPPED");
    }
}
