package jk.innovations.mordenx.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jk.innovations.mordenx.R
import io.reactivex.disposables.Disposable


// This Sampler Code for Learning Purpose only for Kotlin Developer
// AndroidHive Android Introduction To Reactive Programming – RxJava, RxAndroid Java Code Sample in Koltin
// Original Java Code Available On:
// https://www.androidhive.info/RxJava/rxjava-operators-just-range-from-repeat/#repeat

class SplashActivity : AppCompatActivity() {

    private val TAG : String = SplashActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Observable.range(1, 4)
                .repeat(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(i: Int) {
                        Log.d(TAG, "onNext: $i")
                    }

                    override fun onError(e: Throwable) {}

                    override fun onComplete() {
                        Log.d(TAG, "All numbers emitted!")
                    }
                })

    }

}
