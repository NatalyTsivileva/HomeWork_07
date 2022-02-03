package otus.homework.customview.model.pie

import kotlin.math.*

data class PieChart(
	val sectors: List<PieSector>
) {

	var centerX: Float = 0f
	var centerY: Float = 0f

	var degreeMap = mutableMapOf<PieSector, PieSectorDegree>()

	init {
		val amountOfAllSector = sectors.sumOf { it.amount }.toFloat()

		var totalDegree = 0f

		sectors.forEach { sector ->
			val currentDegree = (sector.amount * 360) / amountOfAllSector
			totalDegree += currentDegree
			degreeMap.put(sector, PieSectorDegree(currentDegree, totalDegree))
		}
	}

	//fun getDegree(pieSector: PieSector) = degreeMap[pieSector]

	fun getPieSector(x: Float, y: Float): PieSector? = if (isPointInsideCircle(x, y)) {
		val pointDegree = getPointDegree(x, y)
		degreeMap
			.entries
			.find {
				pointDegree <= it.value.increasedDegree
			}
			?.key
	} else
		null


	private fun isPointInsideCircle(x: Float, y: Float): Boolean {
		val lCircle = sqrt((y - centerY).pow(2) + (x - centerX).pow(2))

		val a = atan2(y - centerY, x - centerX)
		val b = atan(tan(a) * centerX / centerY)

		val _x = centerX * cos(b)
		val _y = centerY * sin(b)

		val lEllipse = sqrt(_x.pow(2) + _y.pow(2))

		return if (abs(lCircle - lEllipse) < 0.0001)
			true
		else
			lCircle < lEllipse
	}

	fun ellipseSectorEndCoordinate(angle: Float, width: Float, height: Float): Pair<Float, Float> {
		var t = atan(width / 2f* tan(angle * PI / 180f) / (height / 2f))
		when (angle) {
			in 90f..179f -> t += PI
			in 180f..270f -> t+= PI
		}
		val x = centerX +(width / 2 * cos(t))
		val y = centerY + (height / 2 * sin(t))

		return Pair(
			x.toFloat(), y.toFloat()
		)
	}

	fun ellipseSectorMiddleCoordinate(angle: Float, width: Float, height: Float): Pair<Float, Float> {
		var t = atan(width / 2f* tan(angle/2 * PI/2 / 180f) / (height / 2f))
		when (angle) {
			in 90f..179f -> t += PI
			in 180f..270f -> t+= PI
		}
		val x = centerX +(width / 2 * cos(t))
		val y = centerY + (height / 2 * sin(t))

		return Pair(
			x.toFloat(), y.toFloat()
		)
	}

	private fun getPointDegree(x: Float, y: Float): Double {
		val f = atan((y - centerY) / (x - centerX))
		var grad = f * (180 / PI)

		if (x < centerX) grad += 180
		else
			if (y < centerY) grad += 360

		return grad
	}

	data class PieSectorDegree(
		/*градусы конкретно для этого сектора*/
		val degree: Float,

		/*градусы для этого сектора + оффсет от начала*/
		val increasedDegree: Float
	)

}