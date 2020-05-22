package com.vlaksuga.namechemistry

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest

import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


private lateinit var actionButton: Button




class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-7714576164572355/3238876703"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }
        }

        actionButton = findViewById(R.id.btn_action)


        actionButton.setOnClickListener {

            var inputFirstName: String = findViewById<TextView>(R.id.input_name_first).text.toString().trim().replace("\\s".toRegex(), "")
            var inputSecondName: String = findViewById<TextView>(R.id.input_name_second).text.toString().trim().replace("\\s".toRegex(), "")


            var nameZipList = ArrayList<String>()
            var nameZipReverseList = ArrayList<String>()


            // validation
            // null or blank
            if (inputFirstName.isNullOrBlank() || inputSecondName.isNullOrBlank()) {
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // isKorean only
            if (!inputFirstName.matches("^[가-힣]*\$".toRegex()) || !inputSecondName.matches("^[가-힣]*\$".toRegex())) {
                Toast.makeText(this, "한글만 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // when name.length is 2 then add blank
            if (inputFirstName.length == 2) {
                inputFirstName = (inputFirstName[0] + " " + inputFirstName[1])
            }

            if (inputSecondName.length == 2) {
                inputSecondName = (inputSecondName[0] + " " + inputSecondName[1])
            }

            // name length
            if (inputFirstName.length != 3 || inputSecondName.length != 3) {
                Toast.makeText(this, "이름이 3글자가 아닙니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            for (char in 0..2) {
                nameZipList.add(inputFirstName[char].toString())
                nameZipList.add(inputSecondName[char].toString())
                nameZipReverseList.add(inputSecondName[char].toString())
                nameZipReverseList.add(inputFirstName[char].toString())
            }


            Intent(this, ResultActivity::class.java).also {
                it.putExtra("firstName", inputFirstName)
                it.putExtra("secondName", inputSecondName)
                it.putExtra("list", nameZipList)
                it.putExtra("reverseList", nameZipReverseList)
                startActivity(it)
            }
        }
    }

    // 뒤로가기시 종료
    var mBackWait: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}
