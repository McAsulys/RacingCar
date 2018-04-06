package fr.iutlens.mmi.racingcar.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by dubois on 23/04/15.
 */
public class OrientationProxy implements SensorEventListener {

    private final OrientationListener mListener;
    private final Display mDisplay;

    public interface OrientationListener {
        void onOrientationChanged(float[] angle, long stamp);
    }

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];


    public OrientationProxy(Context context, OrientationListener listener)
    {
        this.mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mListener = listener;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mDisplay = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public void resume() {
        mLastAccelerometerSet = false;
        mLastMagnetometerSet = false;
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void pause() {
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);

            int rotation = mDisplay.getRotation();

            SensorManager.getOrientation(mR, mOrientation);

            // Prise en compte de la rotation de l'écran de l'écran
            if(rotation == Surface.ROTATION_270) {
                float tmp  =  mOrientation[1];
                mOrientation[0] += Math.PI/2;
                mOrientation[1] = -mOrientation[2];
                mOrientation[2] = tmp;
            } else if(rotation == Surface.ROTATION_90){
                float tmp  =  mOrientation[1];
                mOrientation[0] -= Math.PI/2;
                mOrientation[1] = mOrientation[2];
                mOrientation[2] = -tmp;
            } else if(rotation == Surface.ROTATION_180){
                mOrientation[0] = -mOrientation[0];
                mOrientation[1] = -mOrientation[1];
                mOrientation[2] = -mOrientation[2];
            }

            mListener.onOrientationChanged(mOrientation,event.timestamp/1000000l);
        }
    }


}
