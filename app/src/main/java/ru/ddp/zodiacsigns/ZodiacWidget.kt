package ru.ddp.zodiacsigns

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class ZodiacWidget : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, ZodiacWidget::class.java)
            val widgetIds = appWidgetManager.getAppWidgetIds(componentName)
            onUpdate(context, appWidgetManager, widgetIds)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (widgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    companion object {
        @SuppressLint("DefaultLocale")
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val prefs = context.getSharedPreferences("ZodiacPrefs", Context.MODE_PRIVATE)
            val day = prefs.getInt("day", -1)
            val month = prefs.getInt("month", -1)

            val viewModel = ZodiacViewModel(context.applicationContext as Application)
            val sign = if (day != -1 && month != -1) {
                viewModel.getZodiacSign(day, month)
            } else {
                "Выберите дату"
            }

            val description = viewModel.getZodiacDescription(sign)

            val imageResId = when (sign) {
                "Овен" -> R.drawable.icon_aries
                "Телец" -> R.drawable.icon_taurus
                "Близнецы" -> R.drawable.icon_gemini
                "Рак" -> R.drawable.icon_cancer
                "Лев" -> R.drawable.icon_leo
                "Дева" -> R.drawable.icon_virgo
                "Весы" -> R.drawable.icon_libra
                "Скорпион" -> R.drawable.icon_scorpio
                "Стрелец" -> R.drawable.icon_sagittarius
                "Козерог" -> R.drawable.icon_capricorn
                "Водолей" -> R.drawable.icon_aquarius
                "Рыбы" -> R.drawable.icon_pisces
                else -> R.drawable.icon_constellation
            }

            val intent = Intent(context, WidgetConfigActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val views = RemoteViews(context.packageName, R.layout.zodiac_widget).apply {
                setTextViewText(R.id.textViewSign, sign)
                setTextViewText(R.id.textViewDate, if (day != -1 && month != -1) String.format("%02d.%02d", day, month) else "Не задано")
                setTextViewText(R.id.textViewDescription, description)
                setImageViewResource(R.id.imageViewSign, imageResId)
                setOnClickPendingIntent(R.id.buttonChangeDate, pendingIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}


