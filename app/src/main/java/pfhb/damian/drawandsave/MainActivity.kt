package pfhb.damian.drawandsave

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.*
import android.os.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import pfhb.damian.drawandsave.PaintView.Companion.pathList
import java.io.File
import java.io.FileOutputStream


open class MainActivity : AppCompatActivity() {

    companion object {
        var path = Path()
        var paintBrush = Paint()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val saveBtn = findViewById<Button>(R.id.zapisz)
        val clearBtn = findViewById<Button>(R.id.clear)

        saveBtn.setOnClickListener{
            save()
        }
        clearBtn.setOnClickListener{
            pathList.clear()
            path.reset()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ResourceType")
    public fun save() {
        val view : LinearLayout = findViewById<LinearLayout>(R.id.paint_inc)
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        val location : String = this.externalCacheDir!!.absolutePath.toString() + "/sign" + (1000..9999).random() + ".png"
        val file = File(location)
        try {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                    val saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
                    if(saved)
                    Toast.makeText(baseContext, "Zapis udany, lokacja: " + location, Toast.LENGTH_LONG)
                        .show()
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(baseContext, "Zapis sie nie udal:" + e.toString(), Toast.LENGTH_LONG).show()

        }

    }

}