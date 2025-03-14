package ru.ddp.zodiacsigns

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class ZodiacViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("ZodiacPrefs", Context.MODE_PRIVATE)

    fun getZodiacSign(day: Int, month: Int): String {
        return when {
            month == 1 && day >= 20 || month == 2 && day <= 18 -> "Водолей"
            month == 2 && day >= 19 || month == 3 && day <= 20 -> "Рыбы"
            month == 3 && day >= 21 || month == 4 && day <= 19 -> "Овен"
            month == 4 && day >= 20 || month == 5 && day <= 20 -> "Телец"
            month == 5 && day >= 21 || month == 6 && day <= 20 -> "Близнецы"
            month == 6 && day >= 21 || month == 7 && day <= 22 -> "Рак"
            month == 7 && day >= 23 || month == 8 && day <= 22 -> "Лев"
            month == 8 && day >= 23 || month == 9 && day <= 22 -> "Дева"
            month == 9 && day >= 23 || month == 10 && day <= 22 -> "Весы"
            month == 10 && day >= 23 || month == 11 && day <= 21 -> "Скорпион"
            month == 11 && day >= 22 || month == 12 && day <= 21 -> "Стрелец"
            else -> "Козерог"
        }
    }

    fun saveBirthDate(day: Int, month: Int) {
        prefs.edit().putInt("day", day).putInt("month", month).apply()
    }

    fun getZodiacDescription(sign: String): String {
        return when (sign) {
            "Овен" -> "Овен – энергичный, страстный и смелый лидер."
            "Телец" -> "Телец – терпеливый, надежный и любящий комфорт."
            "Близнецы" -> "Близнецы – умные, любознательные и общительные."
            "Рак" -> "Рак – эмоциональный, заботливый и семейный."
            "Лев" -> "Лев – уверенный, харизматичный и творческий."
            "Дева" -> "Дева – организованный, внимательный и трудолюбивый."
            "Весы" -> "Весы – гармоничный, дипломатичный и элегантный."
            "Скорпион" -> "Скорпион – страстный, загадочный и целеустремленный."
            "Стрелец" -> "Стрелец – свободолюбивый, оптимистичный и авантюрный."
            "Козерог" -> "Козерог – амбициозный, дисциплинированный и прагматичный."
            "Водолей" -> "Водолей – оригинальный, независимый и изобретательный."
            "Рыбы" -> "Рыбы – чувствительный, мечтательный и креативный."
            else -> "Нет информации."
        }
    }
}
