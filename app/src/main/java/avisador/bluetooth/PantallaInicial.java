package avisador.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Set;


public class PantallaInicial extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_inicial);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.DeviceNoBT);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            // Device support Bluetooth
            CheckBox hasBT = (CheckBox) findViewById(R.id.hasDeviceBT);
            hasBT.setChecked(true);

            Context context = getApplicationContext();
            CharSequence text = getString(R.string.DeviceYesBT);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if (mBluetoothAdapter.isEnabled()) {

            ToggleButton activaBT = (ToggleButton) findViewById(R.id.toggleButton_ActivarBT);
            activaBT.setChecked(true);

            TextView etiquetaActivaBT = (TextView) findViewById(R.id.label_activa_BT);
            etiquetaActivaBT.setText(R.string.ToggleButton_DesactivaBT);
        } else {

            ToggleButton activaBT = (ToggleButton) findViewById(R.id.toggleButton_ActivarBT);
            activaBT.setChecked(false);


            TextView etiquetaActivaBT = (TextView) findViewById(R.id.label_activa_BT);
            etiquetaActivaBT.setText(R.string.ToggleButton_ActivarBT);

            /*Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_inicial, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void evento_toggleButton_OnOffBT(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {

            TextView etiquetaActivaBT = (TextView) findViewById(R.id.label_activa_BT);
            etiquetaActivaBT.setText(R.string.ToggleButton_DesactivaBT);

            /*Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);*/

            BluetoothAdapter.getDefaultAdapter().enable();


        } else {

            TextView etiquetaActivaBT = (TextView) findViewById(R.id.label_activa_BT);
            etiquetaActivaBT.setText(R.string.ToggleButton_ActivarBT);

            BluetoothAdapter.getDefaultAdapter().disable();
        }
    }

    public void evento_BuscarAvisador(View view) {


        final ListView ListaDevBT = (ListView) findViewById((R.id.ListaBT));
        BluetoothAdapter ListaDevEmparejados = BluetoothAdapter.getDefaultAdapter();

       ArrayAdapter lista= new ArrayAdapter( this, android.R.layout.simple_list_item_1);


        Set<BluetoothDevice> pairedDevices = ListaDevEmparejados.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {


                lista.add( device.getName() + "\n" + device.getAddress());

            }

            ListaDevBT.setAdapter( lista);
        }

        ListaDevBT.setVisibility(View.VISIBLE);

        // Control de evento para seleccion de dispositivo

        ListaDevBT.setOnItemClickListener( new  AdapterView.OnItemClickListener() {
            public void onItemClick( AdapterView<?> parent, View view, int position, long id) {

                // Carga la seleccion
                String item = (String) ListaDevBT.getAdapter().getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();


                ((Button) findViewById(R.id.button_buscar_avisador)).setText(item);

                ListaDevBT.setVisibility( View.INVISIBLE);

            }
        });

    }
}
