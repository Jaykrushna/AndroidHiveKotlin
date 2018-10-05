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



// AndroidHive Android Introduction To Reactive Programming – RxJava, RxAndroid Java Code Sample in Koltin
// Original Java Code Available On:
// https://www.androidhive.info/RxJava/android-getting-started-with-reactive-programming/#example5

class SplashActivity : AppCompatActivity() {

    private val TAG : String = SplashActivity::class.java.simpleName

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        compositeDisposable.add(
                getNotesObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { it -> Note(it.id, it.note.toUpperCase()) }
                        .subscribeWith(getNotesObserver()))

    }


    private fun getNotesObserver(): DisposableObserver<Note> {
        return object : DisposableObserver<Note>() {

            override fun onNext(note: Note) {
                Log.d(TAG, "Note: " + note.note)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "All notes are emitted!")
            }
        }
    }

    private fun getNotesObservable(): Observable<Note> {
        val notes = prepareNotes()

        return Observable.create { emitter ->
            for (note in notes) {
                if (!emitter.isDisposed) {
                    emitter.onNext(note)
                }
            }

            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
    }

    private fun prepareNotes(): List<Note> {
        val notes = ArrayList<Note>()
        notes.add(Note(1, "buy tooth paste!"))
        notes.add(Note(2, "call brother!"))
        notes.add(Note(3, "watch narcos tonight!"))
        notes.add(Note(4, "pay power bill!"))
        return notes
    }


    data class Note(val id : Int, val note : String)

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

}
