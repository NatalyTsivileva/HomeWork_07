package otus.homework.customview.model.pie

import otus.homework.customview.model.data.Data
import otus.homework.customview.util.ColorUtil.randomColor

object PieSliceCreator {

	fun createSlices(data: List<Data>): List<PieSector> = data.groupBy {
		it.category
	}.map { entry: Map.Entry<String, List<Data>> ->
		val categoryAmount = entry.value.sumOf { it.amount }
		PieSector(
			category = entry.key,
			amount = categoryAmount,
			sliceColor = randomColor()
		)
	}
}