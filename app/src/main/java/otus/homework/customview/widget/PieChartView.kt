package otus.homework.customview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import otus.homework.customview.Logger
import otus.homework.customview.model.pie.PieChart
import otus.homework.customview.model.pie.PieSector

class PieChartView(context: Context, attr: AttributeSet?) : View(context, attr) {

	private val minimumPieHeight: Int = 400
	private val minimumPieWidth: Int = 450

	private val outerOval = RectF()


	private val paint = Paint().apply {
		style = Paint.Style.FILL
	}

	private val legendPaint = Paint().apply {
		style = Paint.Style.FILL
		color = Color.WHITE
		textSize = 25f
	}

	private val path = Path()

	private var startAngle = 0f

	private var pieChart = PieChart(emptyList())

	fun setSlice(list: List<PieSector>) {
		pieChart = PieChart(list)
		invalidate()
	}

	var clickListener: OnPieChartClickListener? = null


	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val widthMode = MeasureSpec.getMode(widthMeasureSpec)
		val heightMode = MeasureSpec.getMode(heightMeasureSpec)
		val width = MeasureSpec.getSize(widthMeasureSpec)
		val height = MeasureSpec.getSize(heightMeasureSpec)


		when {
			(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) ||
					(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.UNSPECIFIED) -> {
				setMeasuredDimension(minimumPieWidth, minimumPieHeight)
				pieChart.centerX = (minimumPieWidth / 2).toFloat()
				pieChart.centerY = (minimumPieHeight / 2).toFloat()
			}


			widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.UNSPECIFIED -> {
				setMeasuredDimension(widthMeasureSpec, minimumPieHeight)
				pieChart.centerX = (width / 2).toFloat()
				pieChart.centerY = (minimumPieHeight / 2).toFloat()
			}


			else -> {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec)
				pieChart.centerX = (width / 2).toFloat()
				pieChart.centerY = (height / 2).toFloat()
			}
		}

	}

	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)
		canvas?.save()

		outerOval.set(
			0f,
			0f,
			measuredWidth.toFloat(),
			measuredHeight.toFloat()
		)


		pieChart.degreeMap.forEach {
			paint.color = it.key.sliceColor
			canvas?.drawArc(outerOval, startAngle, it.value.degree, true, paint)


			val coord = pieChart.ellipseSectorEndCoordinate(
				it.value.increasedDegree,
				measuredWidth.toFloat(),
				measuredHeight.toFloat()
			)

			path.moveTo(pieChart.centerX, pieChart.centerY)
			path.lineTo(coord.first, coord.second)

			canvas?.drawTextOnPath(it.key.amount.toString(), path, 100f, -0f, legendPaint)
			path.reset()
			startAngle += it.value.degree

			Logger.logPie("onDraw() object=${this.hashCode()} category=${it.key.category}," +
					"degree=${it.value}, startAngle=${startAngle}, " +
					"coordX=${coord.first}, coordY=${coord.second}")
		}


		canvas?.restore()
	}

	override fun onTouchEvent(event: MotionEvent?): Boolean {
		return if (event?.action == MotionEvent.ACTION_DOWN) {
			val x = event.x
			val y = event.y

			val sector = pieChart.getPieSector(x, y)

			if (sector != null) {
				clickListener?.onSectorClick(sector)
			}
			true
		} else
			super.onTouchEvent(event)
	}

	interface OnPieChartClickListener {
		fun onSectorClick(sector: PieSector)
	}
}