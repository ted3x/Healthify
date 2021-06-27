package ge.c0d3in3.healthify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ge.c0d3in3.healthify.databinding.ActivityMainBinding
import ge.c0d3in3.healthify.services.SensorListener
import ge.c0d3in3.healthify.utils.gone
import ge.c0d3in3.healthify.utils.show


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setToolbarTitle(titleRes: Int?) {
        if (titleRes == null) binding.toolbarLayout.gone()
        else {
            binding.toolbarTv.setText(titleRes)
            binding.toolbarLayout.show()
        }
    }

    fun setBackButtonVisibility(visible: Boolean) {
        if (visible) binding.toolbarBackBtn.show()
        else binding.toolbarBackBtn.gone()
    }
}