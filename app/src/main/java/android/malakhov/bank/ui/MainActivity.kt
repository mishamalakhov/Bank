package android.malakhov.bank.ui

import android.malakhov.bank.R
import android.malakhov.bank.data.model.SettingsEntity
import android.malakhov.bank.databinding.ActivityMainBinding
import android.malakhov.bank.ui.settings.SettingsFragment
import android.malakhov.bank.ui.сurrency.CurrencyFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var settingsBtn: ImageView? = null
    private var backBtn: ImageView? = null
    private var checkMark: ImageView? = null
    private var title: TextView? = null
    private var binding: ActivityMainBinding? = null
    private var settings =
        ArrayList<SettingsEntity>()  //Setting list form SettingsFragment, witch will send to CurrencyFragment


    fun setSettings(list: ArrayList<SettingsEntity>) {
        settings.clear()
        settings.addAll(list)
    }

    fun setSettingBtnVisible(){
        settingsBtn?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        //set currencies fragment first
        supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            CurrencyFragment(),
            "Currency"
        )
            .commit()

        init()

        //Check mark button click -> send settings to CurrencyFragment
        checkMark?.setOnClickListener {
            val fragment: CurrencyFragment =
                supportFragmentManager.findFragmentByTag("Currency") as CurrencyFragment
            onBackPressed()
            fragment.setSettings(settings)
            fragment.updateUi()
            checkMark?.visibility = View.GONE
            settingsBtn?.visibility = View.VISIBLE
        }

        //Button settings onClick
        settingsBtn?.setOnClickListener {

            checkMark?.visibility = View.VISIBLE
            settingsBtn?.visibility = View.GONE

            if (supportFragmentManager.findFragmentByTag("Settings") == null) {
                title?.text = resources.getString(R.string.settings)

                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag("Currency")!!)
                    .add(
                        R.id.fragment_container,
                        SettingsFragment(),
                        "Settings"
                    ).addToBackStack(null)
                    .commit()
            }
        }

        //Back to previous fragment or exit from application
        backBtn?.setOnClickListener {

            checkMark?.visibility = View.GONE
            settingsBtn?.visibility = View.VISIBLE

            val frag: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
            onBackPressed()
            if (frag is CurrencyFragment)
                this.onBackPressed()
            else {
                title?.text = resources.getString(R.string.Currencies)
                supportFragmentManager.popBackStack()
            }
        }
    }

    //Initialize variables
    private fun init() {
        settingsBtn = binding?.settings
        backBtn = binding?.back
        checkMark = binding?.checkMark
        title = binding?.title
    }
}