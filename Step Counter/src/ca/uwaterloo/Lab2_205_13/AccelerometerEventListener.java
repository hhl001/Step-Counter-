package ca.uwaterloo.Lab2_205_13;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//The class of Accelerometer Sensor
public class AccelerometerEventListener implements SensorEventListener{
	
	//Declares both the text label displayed and the graph label
	LineGraphView graphAccelerometer;
	LineGraphView graph2;
	TextView output;
	Button but1;
	
	//Count the number of steps.
	int count = 0;
	
	//The state machine for each mode of a step
	int mode = 0;
	final int mode_waiting = 0;
	final int fall = 1;
	final int rise = 2;

	//A constant for low pass filter
	float c = (float)2;
	
	//Used to store the previous smooth value
	float[] smoothedAccel = new float[2];
	
	//The smooth values for linear acceleration sensor
	float[] tempdAccel = new float[2];
	
	//Constructor for the Accelerometer class with the parameter of text and a graph
	public AccelerometerEventListener(TextView outputView, LineGraphView graph, LineGraphView graphA, Button but1){
		output = outputView;
		graphAccelerometer = graph;
		graph2 = graphA;
		this.but1 = but1;
	}
	
	public void onAccuracyChanged(Sensor s, int i) {
		
	}
	
	public void onSensorChanged(SensorEvent se) {
		
		//Sends sensor values to the graph.
		graphAccelerometer.addPoint(se.values);		
		
		if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {						
			
			//Save the previous smooth value
			tempdAccel[0] = smoothedAccel[0];
			tempdAccel[1] = smoothedAccel[1];			
			
			//Smooth values for y and z
			smoothedAccel[0] += (se.values[1] - smoothedAccel[0])/c;
			smoothedAccel[1] += (se.values[2] - smoothedAccel[1])/c;			
			graph2.addPoint(smoothedAccel);			
			
			//The finite state machine for a step
			switch(mode){
			
			//Turns the mode from waiting to fall if it is falling
			case 0: if(smoothedAccel[1]-tempdAccel[1] < -0.15 && smoothedAccel[0]-tempdAccel[0] > 0) this.mode = this.fall;						
					else {
						this.mode = mode_waiting;								
					}
					break;
			
			//Turns the mode from fall to rise if it is rising
			case 1:	if(smoothedAccel[1]-tempdAccel[1] > 0.3 && smoothedAccel[0]-tempdAccel[0] < 0) this.mode = this.rise;
					break;
					
			//Count a step and turn the mode back to waiting
			case 2:	count++;
					this.mode = mode_waiting;				
					break;
					
			default: break;
			}
			
			//Creat a button to reset number of steps and turn the mode to waiting.
			but1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					count = 0;
					mode = mode_waiting;
				}
			});
			
			//Outputs to the text label.
			output.setText(String.format("Step: %d \nMode: %d", count, mode));
		}
		
	}
		
}
