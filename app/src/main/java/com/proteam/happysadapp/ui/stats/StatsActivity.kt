package com.proteam.happysadapp.ui.stats

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.proteam.happysadapp.BaseApplication
import com.proteam.happysadapp.R
import com.proteam.happysadapp.data.constant.MoodStatusConstant
import com.proteam.happysadapp.ui.HomeViewModel
import com.tribe.fitness.base.view.BaseActivity
import com.tribe.fitness.base.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.rootView
import kotlinx.android.synthetic.main.activity_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class StatsActivity : BaseActivity<HomeViewModel>() {

    val SLICE_COLORS = intArrayOf(
        ColorTemplate.rgb("#2ecc71"),
        ColorTemplate.rgb("#507FB1")
    )

    val PERCENTAGE_COLORS = intArrayOf(
        ColorTemplate.rgb("#2B2B2B"),
        ColorTemplate.rgb("#FFFFFF")
    )

    val PERCENTAGE_COLORS_HAPPY_TRANSPARENT = intArrayOf(
        Color.TRANSPARENT,
        ColorTemplate.rgb("#FFFFFF")
    )

    val PERCENTAGE_COLORS_SAD_TRANSPARENT = intArrayOf(
        ColorTemplate.rgb("#2B2B2B"),
        Color.TRANSPARENT
    )
    @Inject
    open lateinit var viewModelFactory: ViewModelProviderFactory

    override fun getLayout(): Int {
        return R.layout.activity_stats
    }

    override fun attachComponent() {
        super.attachComponent()
        BaseApplication.getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        makeFullScreen()
        ivBack.setOnClickListener { finish() }
        initPieChart()
        lifecycleScope.launch(Dispatchers.IO) {
            val happyCountList = viewModel?.getAllData(MoodStatusConstant.HAPPY)
            val sadCountList = viewModel?.getAllData(MoodStatusConstant.SAD)
            var happyCount = 0;
            if (!happyCountList.isNullOrEmpty()) {
                happyCount = happyCountList.size
            }

            var sadCount = 0;
            if (!sadCountList.isNullOrEmpty()) {
                sadCount = sadCountList.size
            }

            val total = happyCount + sadCount
            if (total > 0) {
                val happyCountPercentage = if (happyCount <= 0) 0 else (happyCount % total) * total
                val sadCountPercentage = if (sadCount <= 0) 0 else (sadCount % total) * total
                withContext(Dispatchers.Main)
                {
                    if (happyCount <= 0) {
                        setData(0f, 100f)
                    } else if (sadCount <= 0) {
                        setData(100f, 0f)
                    } else {
                        setData(happyCountPercentage.toFloat(), sadCountPercentage.toFloat())
                    }
                }
            }
        }
    }

    private fun makeFullScreen() {
        rootView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }


    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(5.toFloat(), 10.toFloat(), 5.toFloat(), 5.toFloat())
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        pieChart.setCenterTextTypeface(
            Typeface.createFromAsset(
                getAssets(),
                "font/Avenir-Black.ttf"
            )
        )
        pieChart.setCenterText("Mood stats")
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.setCenterTextSize(16f)

        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.TRANSPARENT)

        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.setHoleRadius(58f)
        pieChart.setTransparentCircleRadius(61f)
        pieChart.setDrawCenterText(true)
        pieChart.setRotationAngle(0.toFloat())
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.setDrawEntryLabels(false)

        // entry label styling
//        pieChart.setEntryLabelColor(Color.TRANSPARENT)
//        pieChart.setEntryLabelTypeface(Typeface.createFromAsset(getAssets(), "font/Avenir-Black.ttf"))
//        pieChart.setEntryLabelTextSize(20f)

        val legend: Legend = pieChart.getLegend()
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
//        legend.setDrawInside(false)
        legend.textColor = Color.WHITE
        legend.textSize = 14f
        legend.typeface = Typeface.createFromAsset(getAssets(), "font/Avenir-Book.ttf")
//        pieChart.setLe
    }

    private fun setData(happyCountPercentage: Float, sadCountPercentage: Float) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(happyCountPercentage, "Happy"))
        entries.add(PieEntry(sadCountPercentage, "Sad"))

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in SLICE_COLORS) colors.add(c)
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(20f)

        val pcolors = ArrayList<Int>()
        if (happyCountPercentage < 1) {
            for (c in PERCENTAGE_COLORS_HAPPY_TRANSPARENT) pcolors.add(c)
        } else if (sadCountPercentage < 1) {
            for (c in PERCENTAGE_COLORS_SAD_TRANSPARENT) pcolors.add(c)
        } else {
            for (c in PERCENTAGE_COLORS) pcolors.add(c)
        }
        data.setValueTextColors(pcolors)
        data.setValueTypeface(Typeface.createFromAsset(getAssets(), "font/Avenir-Black.ttf"))
        pieChart.setData(data)
        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }
}
