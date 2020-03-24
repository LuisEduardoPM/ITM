package com.example.sensores

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.widget.Toast


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var deviceSensors: List<Sensor> = emptyList()
    private var sensor: Sensor? = null
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var receiver = CustomReceiver()
        val filter = IntentFilter ("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver , filter)

        boton1.setOnClickListener{
            /*val intent = Intent ("com.workingdev.DOSOMETHING")
            sendBroadcast(intent)

             */

            var cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
            uwu(activeNetwork)

        }






        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if(sensor == null)
            println("TYPE_ACCELEROMETER no soportado")
        else println ("TYPE_ACCELEROMETER wey SIIIIIIIIIIIIIIIIIIIIIIIIII")
        /*
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        deviceSensors.forEach {
            println(it)
        }
        */

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR){

            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(
                rotationMatrix, event.values
            )

            // Remap coordinate system
            val remappedRotationMatrix = FloatArray(16)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix
            )

            // Convert to orientations
            val orientations = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientations)
            /*
            for (i in 0..2) {
                orientations[i] = Math.toDegrees(orientations[i]).toFloat()
            }

             */

            /*
            var x = event.values[0]
            var y = event.values[1]
            var z = event.values[2]

             */
            //println("TAMAÑO DEL ARREGLO: "+event.values.size)

            /*
            println("uno: "+event.values[0])
            println("dos: "+event.values[1])
            println("tres: "+event.values[2])

             */

            //println("X: " + x + " Y: " + y + " Z:" + z)
            /*
            event.values.forEach {
                println(it)
            }

             */

/*
            setContentView(R.layout.activity_main)
            val textView = findViewById(R.id.eje_x) as TextView
            textView.text = "X = " + (orientations[0]+3)

            val textView2 = findViewById(R.id.eje_y) as TextView
            textView2.text = "Y = " + (orientations[1]+3)

            val textView3 = findViewById(R.id.eje_z) as TextView
            textView3.text = "Z = " + (orientations[2]+3)

 */




            flecha.rotation =  360f * ((-orientations[0] / 3) )



        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
    }

    inner class CustomReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
            uwu(activeNetwork)

        }
    }

    private fun uwu(networkInfo: NetworkInfo?) {



        var estado = networkInfo?.state
        var tipo = networkInfo?.type
        var nombretipo = networkInfo?.typeName
        var otro = networkInfo?.extraInfo





        var mensaje="Estado: "+estado+"\nTipo: "+tipo + "\nNombre Tipo: "+ nombretipo //+ "\nExtra info: " + otro


        Toast.makeText(this , mensaje , Toast.LENGTH_LONG).show()
    }




}
