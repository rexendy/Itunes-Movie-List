package com.test.data.session.repository

import android.content.SharedPreferences
import com.test.data.util.DATE_CURRENT_VISITED
import com.test.data.util.DATE_LAST_VISITED
import com.test.data.util.TRACK_ID
import com.test.domain.session.model.Session
import com.test.domain.session.repository.SessionLocalRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    @Named("MOVIE_SHARED_PREF") val sharedPref: SharedPreferences
): SessionLocalRepository{
    override fun getSession(): Single<Session> {
        return Single.fromCallable {
            Session(
                sharedPref.getString(DATE_LAST_VISITED, "").toString(),
                sharedPref.getString(DATE_CURRENT_VISITED, "").toString(),
                sharedPref.getLong(TRACK_ID, 0)
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun saveSession(session: Session): Completable {
        return Completable.fromAction {
            with(sharedPref.edit()) {
                putString(DATE_LAST_VISITED, session.dateLastVisited)
                putString(DATE_CURRENT_VISITED, session.dateCurrentVisited)
                putLong(TRACK_ID, session.trackId)
                apply()
            }
        }.subscribeOn(Schedulers.io())
    }
}