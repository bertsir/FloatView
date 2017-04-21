package cn.bertsir.floatbutton;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import cn.bertsir.cameralibrary.CameraUtils;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor defaultSensor;
    private static final String TAG = "MainActivity";
    private SurfaceView sfv;
    private CardView cv;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获得传感器管理
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//设置类型
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, defaultSensor, SensorManager.SENSOR_DELAY_GAME);//注册传感器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);//注销传感器
        CameraUtils.getInstance().closeCamera(sfv,MainActivity.this);//释放摄像头
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        changeLocation(event.values[1], event.values[2]);
    }


    private void changeLocation(float y, float z) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) cv.getLayoutParams();
        layoutParams.setMargins((int) z * 2, (int) y * 2, 0, 0);//乘2的作用是为了让效果明显点
        cv.setLayoutParams(layoutParams);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void initView() {
        sfv = (SurfaceView) findViewById(R.id.sfv);
        cv = (CardView) findViewById(R.id.cv);
        lv = (ListView) findViewById(R.id.lv);
        CameraUtils.getInstance().openCamera(sfv,MainActivity.this);
        lv.setAdapter(new MyAdapter());

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(getApplicationContext(),R.layout.item,null);
            }
            return convertView;
        }
    }
}
