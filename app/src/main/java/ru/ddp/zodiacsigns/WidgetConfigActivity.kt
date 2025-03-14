package ru.ddp.zodiacsigns

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class WidgetConfigActivity : AppCompatActivity() {
    private lateinit var viewModel: ZodiacViewModel

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_config)

        viewModel = ViewModelProvider(this)[ZodiacViewModel::class.java]

        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        editTextDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cleanString = s.toString().replace(".", "")

                if (cleanString.length > 4) return

                val formatted = StringBuilder()

                for (i in cleanString.indices) {
                    formatted.append(cleanString[i])
                    if (i == 1 && cleanString.length > 2) formatted.append(".")
                }

                editTextDate.removeTextChangedListener(this)
                editTextDate.setText(formatted.toString())
                editTextDate.setSelection(formatted.length)
                editTextDate.addTextChangedListener(this)
            }
        })

        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, year, month, day ->
                val formattedDate = String.format("%02d.%02d", day, month + 1)
                editTextDate.setText(formattedDate)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            datePicker.show()
        }

        buttonSave.setOnClickListener {
            val dateParts = editTextDate.text.toString().split(".")
            if (dateParts.size == 2) {
                val day = dateParts[0].toInt()
                val month = dateParts[1].toInt()
                viewModel.saveBirthDate(day, month)

                // Обновляем виджет
                val intent = Intent(this, ZodiacWidget::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                }
                sendBroadcast(intent)

                finish()
            }
        }
    }
}
