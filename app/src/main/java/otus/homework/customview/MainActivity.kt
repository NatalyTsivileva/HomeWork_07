package otus.homework.customview

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import otus.homework.customview.model.data.Data
import otus.homework.customview.model.pie.PieSector
import otus.homework.customview.model.pie.PieSliceCreator
import otus.homework.customview.widget.PieChartView

class MainActivity : AppCompatActivity() {

	var pieChartList: List<Data> = emptyList()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		pieChartList = Data.getListFromRaw(applicationContext)
	}

	override fun onStart() {
		super.onStart()
		initFirstPieChart()
	}

	private var listener = object : PieChartView.OnPieChartClickListener {
		override fun onSectorClick(sector: PieSector) {
			Toast.makeText(applicationContext,"Нажали на ${sector}",Toast.LENGTH_SHORT).show()
		}
	}

	private fun initFirstPieChart() {
		findViewById<PieChartView>(R.id.pieChart1)?.apply {
			setSlice(PieSliceCreator.createSlices(pieChartList))
			clickListener = listener
		}

		findViewById<PieChartView>(R.id.pieChart2)?.apply {
			setSlice(PieSliceCreator.createSlices(pieChartList))
            clickListener = listener
        }


		findViewById<PieChartView>(R.id.pieChart3)?.apply {
			setSlice(PieSliceCreator.createSlices(pieChartList))
            clickListener=listener
		}
	}
}