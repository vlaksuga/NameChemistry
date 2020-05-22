package com.vlaksuga.namechemistry



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

import kotlinx.android.synthetic.main.activity_result.*
import kotlin.math.abs



class ResultActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // admob iniit
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)




        /*
         const f = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
         const s = ['ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'];
         const t = ['', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
         */

        val STROKE_KOREAN_F : List<Int> = listOf(2, 4, 2, 3, 6, 5, 4, 4, 8, 2, 4, 1, 3, 6, 4, 3, 4, 4, 3)
        val STROKE_KOREAN_S : List<Int> = listOf(2, 3, 3, 4, 2, 3, 3, 4, 2, 4, 5, 3, 3, 2, 4, 5, 3, 3, 1, 2, 1)
        val STROKE_KOREAN_T : List<Int> = listOf(0, 2, 4, 4, 2, 5, 5, 3, 5, 7, 9, 9, 7, 9, 9, 8, 4, 4, 6, 2, 4, 1, 3, 4, 3, 4, 4, 3)
        val ga : Long = 44032

        val strokeSum = ArrayList<Int> ()
        val sumStep1 = ArrayList<Int> ()
        val sumStep2 = ArrayList<Int> ()
        val sumStep3 = ArrayList<Int> ()
        val sumStep4 = ArrayList<Int> ()

        val strokeSumReverse = ArrayList<Int> ()
        val sumStep1Reverse = ArrayList<Int> ()
        val sumStep2Reverse = ArrayList<Int> ()
        val sumStep3Reverse = ArrayList<Int> ()
        val sumStep4Reverse = ArrayList<Int> ()


        // get List form Main
        val firstNameResource = intent.getStringExtra("firstName")
        val secondNameResource = intent.getStringExtra("secondName")
        val nameResource = intent.getSerializableExtra("list") as ArrayList<*>
        val nameResourceReverse = intent.getSerializableExtra("reverseList") as ArrayList<*>

        // char resource
        val inputFirstChar = nameResource[0]
        val inputSecondChar = nameResource[1]
        val inputThirdChar = nameResource[2]
        val inputFourthChar = nameResource[3]
        val inputFifthChar = nameResource[4]
        val inputSixthChar = nameResource[5]

        val inputFirstCharReverse = nameResourceReverse[0]
        val inputSecondCharReverse = nameResourceReverse[1]
        val inputThirdCharReverse = nameResourceReverse[2]
        val inputFourthCharReverse = nameResourceReverse[3]
        val inputFifthCharReverse = nameResourceReverse[4]
        val inputSixthCharReverse = nameResourceReverse[5]

        // char to index int
        for(i in 0..5) {
            val char = nameResource[i]
            val charReverse = nameResourceReverse[i]
            val uniLong : Long = (char.toString())[0].toLong() - ga
            val uniLongReverse : Long = (charReverse.toString())[0].toLong() - ga
            val fnum = (uniLong / 588).toInt()
            val fnumReverse = (uniLongReverse / 588).toInt()
            val snum = ((uniLong - (fnum*588))/28).toInt()
            val snumReverse = ((uniLongReverse - (fnumReverse*588))/28).toInt()
            val tnum = (uniLong % 28).toInt()
            val tnumReverse = (uniLongReverse % 28).toInt()
            val sumOrigin = when(nameResource[i]) {
                " " -> 0
                else -> STROKE_KOREAN_F[fnum] + STROKE_KOREAN_S[snum] + STROKE_KOREAN_T[tnum]
            }
            val sumOriginReverse = when(nameResourceReverse[i]) {
                " " -> 0
                else -> STROKE_KOREAN_F[fnumReverse] + STROKE_KOREAN_S[snumReverse] + STROKE_KOREAN_T[tnumReverse]
            }
            if (sumOrigin < 10) {
                strokeSum.add(sumOrigin)
            } else {
                strokeSum.add(sumOrigin - 10)
            }
            if (sumOriginReverse < 10) {
                strokeSumReverse.add(sumOriginReverse)
            } else {
                strokeSumReverse.add(sumOriginReverse - 10)
            }
        }

        // STEP 1
        for(i in 0..4) {
            val stepOne : Int = strokeSum[i] + strokeSum[i+1]
            val stepOneReverse : Int = strokeSumReverse[i] + strokeSumReverse[i+1]
            if (stepOne < 10) {
                sumStep1.add(stepOne)
            } else {
                sumStep1.add(stepOne - 10)
            }
            if (stepOneReverse < 10) {
                sumStep1Reverse.add(stepOneReverse)
            } else {
                sumStep1Reverse.add(stepOneReverse - 10)
            }
        }

        // STEP 2
        for(i in 0..3) {
            val stepTwo : Int = sumStep1[i] + sumStep1[i+1]
            val stepTwoReverse : Int = sumStep1Reverse[i] + sumStep1Reverse[i+1]
            if (stepTwo < 10) {
                sumStep2.add(stepTwo)
            } else {
                sumStep2.add(stepTwo - 10)
            }
            if (stepTwoReverse < 10) {
                sumStep2Reverse.add(stepTwoReverse)
            } else {
                sumStep2Reverse.add(stepTwoReverse - 10)
            }
        }

        // STEP 3
        for(i in 0..2) {
            val stepThree : Int = sumStep2[i] + sumStep2[i+1]
            val stepThreeReverse : Int = sumStep2Reverse[i] + sumStep2Reverse[i+1]
            if (stepThree < 10) {
                sumStep3.add(stepThree)
            } else {
                sumStep3.add(stepThree - 10)
            }
            if (stepThreeReverse < 10) {
                sumStep3Reverse.add(stepThreeReverse)
            } else {
                sumStep3Reverse.add(stepThreeReverse - 10)
            }
        }

        // STEP 4
        for(i in 0..1) {
            val stepFour : Int = sumStep3[i] + sumStep3[i+1]
            val stepFourReverse : Int = sumStep3Reverse[i] + sumStep3Reverse[i+1]
            if (stepFour < 10) {
                sumStep4.add(stepFour)
            } else {
                sumStep4.add(stepFour - 10)
            }
            if (stepFourReverse < 10) {
                sumStep4Reverse.add(stepFourReverse)
            } else {
                sumStep4Reverse.add(stepFourReverse - 10)
            }
        }

        // final
        val finalScore : Int = ( sumStep4[0] *10 ) + (sumStep4[1])
        val finalScoreReverse : Int = ( sumStep4Reverse[0] *10 ) + (sumStep4Reverse[1])
        val scoreGap : Int = abs(finalScore - finalScoreReverse)

        // to real input name
        val realInputNameFirst = firstNameResource!!.toString().trim().replace("\\s".toRegex(), "")
        val realInputNameSecond = secondNameResource!!.toString().trim().replace("\\s".toRegex(), "")

        // comment
        val comment : String = when {
            finalScore > finalScoreReverse -> "${realInputNameFirst}님이 ${realInputNameSecond}님을 $scoreGap% 더 좋아합니다."
            finalScore == finalScoreReverse -> "${realInputNameFirst}님과 ${realInputNameSecond}님은 서로 똑같이 좋아합니다."
            else -> "${realInputNameSecond}님이 ${realInputNameFirst}님을 $scoreGap% 더 좋아합니다."
        }

        // select icon
        val scoreIcon = when {
            finalScore < 25 -> R.drawable.ic_under25
            finalScore < 50 -> R.drawable.ic_under50
            finalScore < 75 -> R.drawable.ic_under75
            else  ->  R.drawable.ic_score_else
        }

        val scoreIconReverse = when {
            finalScoreReverse < 25 -> R.drawable.ic_under25
            finalScoreReverse < 50 -> R.drawable.ic_under50
            finalScoreReverse < 75 -> R.drawable.ic_under75
            else  ->  R.drawable.ic_score_else
        }


        // show result
        // TODO LIST와 FOR구문으로 변경하기
        result_char_first.text = inputFirstChar.toString()
        result_char_second.text = inputSecondChar.toString()
        result_char_third.text = inputThirdChar.toString()
        result_char_fourth.text = inputFourthChar.toString()
        result_char_fifth.text = inputFifthChar.toString()
        result_char_sixth.text = inputSixthChar.toString()
        result_char_first_reverse.text = inputFirstCharReverse.toString()
        result_char_second_reverse.text = inputSecondCharReverse.toString()
        result_char_third_reverse.text = inputThirdCharReverse.toString()
        result_char_fourth_reverse.text = inputFourthCharReverse.toString()
        result_char_fifth_reverse.text = inputFifthCharReverse.toString()
        result_char_sixth_reverse.text = inputSixthCharReverse.toString()

        sum_char_first.text = strokeSum[0].toString()
        sum_char_second.text = strokeSum[1].toString()
        sum_char_third.text = strokeSum[2].toString()
        sum_char_fourth.text = strokeSum[3].toString()
        sum_char_fifth.text = strokeSum[4].toString()
        sum_char_sixth.text = strokeSum[5].toString()
        sum_char_first_reverse.text = strokeSumReverse[0].toString()
        sum_char_second_reverse.text = strokeSumReverse[1].toString()
        sum_char_third_reverse.text = strokeSumReverse[2].toString()
        sum_char_fourth_reverse.text = strokeSumReverse[3].toString()
        sum_char_fifth_reverse.text = strokeSumReverse[4].toString()
        sum_char_sixth_reverse.text = strokeSumReverse[5].toString()

        step_one_first.text = sumStep1[0].toString()
        step_one_second.text = sumStep1[1].toString()
        step_one_third.text = sumStep1[2].toString()
        step_one_fourth.text = sumStep1[3].toString()
        step_one_fifth.text = sumStep1[4].toString()
        step_one_first_reverse.text = sumStep1Reverse[0].toString()
        step_one_second_reverse.text = sumStep1Reverse[1].toString()
        step_one_third_reverse.text = sumStep1Reverse[2].toString()
        step_one_fourth_reverse.text = sumStep1Reverse[3].toString()
        step_one_fifth_reverse.text = sumStep1Reverse[4].toString()


        step_two_first.text = sumStep2[0].toString()
        step_two_second.text = sumStep2[1].toString()
        step_two_third.text = sumStep2[2].toString()
        step_two_fourth.text = sumStep2[3].toString()
        step_two_first_reverse.text = sumStep2Reverse[0].toString()
        step_two_second_reverse.text = sumStep2Reverse[1].toString()
        step_two_third_reverse.text = sumStep2Reverse[2].toString()
        step_two_fourth_reverse.text = sumStep2Reverse[3].toString()

        step_three_first.text = sumStep3[0].toString()
        step_three_second.text = sumStep3[1].toString()
        step_three_third.text = sumStep3[2].toString()
        step_three_first_reverse.text = sumStep3Reverse[0].toString()
        step_three_second_reverse.text = sumStep3Reverse[1].toString()
        step_three_third_reverse.text = sumStep3Reverse[2].toString()

        step_four_first.text = sumStep4[0].toString()
        step_four_second.text = sumStep4[1].toString()
        step_four_first_reverse.text = sumStep4Reverse[0].toString()
        step_four_second_reverse.text = sumStep4Reverse[1].toString()

        final_score_text.text = finalScore.toString()
        final_score_text_reverse.text = finalScoreReverse.toString()

        result_comment.text = comment
        result_icon.setImageResource(scoreIcon)
        result_icon_reverse.setImageResource(scoreIconReverse)

        // TODO android shareSheet 성공메세지
        btn_share_result.setOnClickListener {
            val appURLString : String = "https://play.google.com/store/apps/details?id=com.vlaksuga.namechemistry"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "이름으로 알아보는 궁합앱 : 이름캐미 다운받기 \n $appURLString ")
            val shareIntent = Intent.createChooser(intent, "공유하기")
            startActivity(shareIntent)
        }

        // set Background res for two letter
        if (result_char_third.text.isBlank()) {
            result_char_third.setBackgroundResource(R.drawable.ic_noname)
            result_char_fourth_reverse.setBackgroundResource(R.drawable.ic_noname_grey)
        }

        if (result_char_fourth.text.isBlank()) {
            result_char_third_reverse.setBackgroundResource(R.drawable.ic_noname_reverse)
            result_char_fourth.setBackgroundResource(R.drawable.ic_noname_grey)
        }


        // animation Handler

        val animationInputName: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_input_name)
        val animationStepOne: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_step_one)
        val animationStepTwo: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_step_two)
        val animationStepThree: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_step_three)
        val animationStepFour: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_step_four)
        val animationFinalScore: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_final_score)
        val animationResultIcon: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_result_icon)
        val animationResultComment: Animation = AnimationUtils.loadAnimation(this, R.anim.ani_result_comment)

        input_name.visibility = View.GONE
        step_one.visibility = View.GONE
        step_two.visibility = View.GONE
        step_three.visibility = View.GONE
        step_four.visibility = View.GONE
        final_score.visibility = View.GONE
        result_icon.visibility = View.GONE

        input_name_reverse.visibility = View.GONE
        step_one_reverse.visibility = View.GONE
        step_two_reverse.visibility = View.GONE
        step_three_reverse.visibility = View.GONE
        step_four_reverse.visibility = View.GONE
        final_score_reverse.visibility = View.GONE
        result_icon_reverse.visibility = View.GONE

        result_comment.visibility = View.INVISIBLE

        val handler: Handler = Handler()
        handler.postDelayed({
            input_name.startAnimation(animationInputName)
            input_name.visibility = View.VISIBLE
            input_name_reverse.startAnimation(animationInputName)
            input_name_reverse.visibility = View.VISIBLE
        }, 500)

        handler.postDelayed({
            step_one.startAnimation(animationStepOne)
            step_one.visibility = View.VISIBLE
            step_one_reverse.startAnimation(animationStepOne)
            step_one_reverse.visibility = View.VISIBLE
        }, 1000)

        handler.postDelayed({
            step_two.startAnimation(animationStepTwo)
            step_two.visibility = View.VISIBLE
            step_two_reverse.startAnimation(animationStepTwo)
            step_two_reverse.visibility = View.VISIBLE
        }, 1500)

        handler.postDelayed({
            step_three.startAnimation(animationStepThree)
            step_three.visibility = View.VISIBLE
            step_three_reverse.startAnimation(animationStepThree)
            step_three_reverse.visibility = View.VISIBLE
        }, 2000)

        handler.postDelayed({
            step_four.startAnimation(animationStepFour)
            step_four.visibility = View.VISIBLE
            step_four_reverse.startAnimation(animationStepFour)
            step_four_reverse.visibility = View.VISIBLE
        }, 2500)

        handler.postDelayed({
            final_score.startAnimation(animationFinalScore)
            final_score.visibility = View.VISIBLE
            final_score_reverse.startAnimation(animationFinalScore)
            final_score_reverse.visibility = View.VISIBLE
        }, 3000)

        handler.postDelayed({
            result_icon.startAnimation(animationResultIcon)
            result_icon.visibility = View.VISIBLE
            result_icon_reverse.startAnimation(animationResultIcon)
            result_icon_reverse.visibility = View.VISIBLE
        }, 3500)

        handler.postDelayed({
            result_comment.startAnimation(animationResultComment)
            result_comment.visibility = View.VISIBLE
        }, 4000)

    }


}