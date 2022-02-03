package otus.homework.customview.model.data

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import otus.homework.customview.R

data class Data(
	val id: Int,
	val name: String,
	val amount: Int,
	val category: String,
	val time: Long
)  {


	companion object {
		fun getListFromRaw(
			context: Context,
			@RawRes rawRes: Int = R.raw.payload
		): List<Data> {
			val text = context.resources.openRawResource(rawRes).bufferedReader().readText()
			return Gson().fromJson(text, object : TypeToken<List<Data>>() {}.type)
		}
	}

}