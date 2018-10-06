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

// AndroidHive Android Introduction To Reactive Programming – RxJava, RxAndroid Java Code Sample in Koltin
// Original Java Code Available On:
// https://www.androidhive.info/RxJava/rxjava-operators-introduction/#chaining-operators

class SplashActivity : AppCompatActivity() {

    private val TAG : String = SplashActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Observable.range(1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it -> it % 2 == 0}
                .map { it -> "$it is even number"}
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(s: String) {
                        Log.d(TAG, "onNext: $s")
                    }

                    override fun onError(e: Throwable) {}

                    override fun onComplete() {
                        Log.d(TAG, "All numbers emitted!")
                    }
                })

    }

}
