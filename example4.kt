package jk.innovations.mordenx.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import jk.innovations.mordenx.R

// I am try to implement on kotlin from 
// Original Java Code Available On:
// https://www.androidhive.info/RxJava/android-getting-started-with-reactive-programming/#example4


class SplashActivity : AppCompatActivity() {


    private val TAG : String = SplashActivity::class.java.simpleName

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animalsObservable : Observable<String> = getAnimalsObservable()

        val animalsObserver : DisposableObserver<String> = getAnimalsObserver()

        val animalsObserverAllCaps : DisposableObserver<String> = getAnimalsAllCapsObserver()


        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter{ it -> it.toLowerCase().startsWith("b") }
                        .subscribeWith(animalsObserver))


        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter{ it -> it.toLowerCase().startsWith("c") }
                        .map { it -> it.toUpperCase() }
                        .subscribeWith(animalsObserverAllCaps))
    }

    private fun getAnimalsObserver() : DisposableObserver<String>
    {
        return object : DisposableObserver<String>() {
            override fun onComplete() {
                Log.d(TAG, "All items are emitted!")
            }

            override fun onNext(s: String) {
                Log.d(TAG, "Name: $s")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

        }
    }

    private fun getAnimalsAllCapsObserver() : DisposableObserver<String> {
        return object :  DisposableObserver<String>() {
            override fun onComplete() {
                Log.d(TAG, "All items are emitted!")
            }

            override fun onNext(s: String) {
                Log.d(TAG, "Name: $s")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

        }
    }

    private fun getAnimalsObservable() : Observable<String> {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog")
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

}
