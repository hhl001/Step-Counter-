package ca.uwaterloo.Lab2_205_13;

import java.util.Arrays;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	 //ALL the logics go through HERE.
	public static class PlaceholderFragment extends Fragment {
		
		public PlaceholderFragment() {
		}

		//Essentially functions as the MainActivity class
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			//change the content of ¡°Hello World¡± label to a LAB1 title    
			TextView tv = (TextView) rootView.findViewById(R.id.title);
						tv.setText("Detecting Footsteps");
			
			//Declare output label LinearLayout as mainLayout
			//Declare text label as tv1, tv2 and tv3, represent separately as Accelerometer Sensor, 
			//Magnetic Sensor and Rotation Sensor.
			LinearLayout mainLayout = (LinearLayout) rootView.findViewById(R.id.sensor);
			TextView tv1 = new TextView(getActivity());
			
			Button but1 = (Button)rootView.findViewById(R.id.but);
			
			//Adding more labels using addView method.
			//Uses the implements feature to go up to context to implement.
			mainLayout.addView(tv1);
			mainLayout.setOrientation(LinearLayout.VERTICAL);
			
			//Instantiates SensorManager.
			SensorManager sensorManager = (SensorManager)
					rootView.getContext().getSystemService(SENSOR_SERVICE);
			
			/**
			 * Sensor lightSensor =
			 * sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			 * SensorEventListener light = new LightSensorEventListener(tv1);
			 * sensorManager.registerListener(light, lightSensor,
			 * SensorManager.SENSOR_DELAY_NORMAL);
			 */
			
			//Uses a graph of the accelerometer sensor readings over time.
			LineGraphView graph;
			graph = new LineGraphView(getActivity(), 100,
					Arrays.asList("x", "y", "z"));
			mainLayout.addView(graph);
			graph.setVisibility(View.VISIBLE);
			
			LineGraphView graph2;
			graph2 = new LineGraphView(getActivity(), 100,
					//Arrays.asList("output1"));
			Arrays.asList("smoothY","smoothZ"));
			mainLayout.addView(graph2);
			graph2.setVisibility(View.VISIBLE);
			
			
			//Creates an object of Accelerometer Sensor.
			Sensor accerlerometer =
					sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);			
			SensorEventListener ac = new AccelerometerEventListener(tv1, graph, graph2, but1);
			sensorManager.registerListener(ac, accerlerometer, SensorManager.SENSOR_DELAY_NORMAL);
						
			return rootView;
		}
	}

}
