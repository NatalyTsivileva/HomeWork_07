package otus.homework.customview.util

import android.graphics.Color
import kotlin.random.Random

object ColorUtil {
	fun randomColor() =
		Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

}