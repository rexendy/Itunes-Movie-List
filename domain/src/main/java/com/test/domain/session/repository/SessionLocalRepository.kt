package com.test.domain.session.repository

import com.test.domain.session.model.Session
import io.reactivex.Completable
import io.reactivex.Single

/**
 * SessionLocalRepository
 * data management is from local db
 */
interface SessionLocalRepository {
    /**
     * @return session object
     */
    fun getSession(): Single<Session>

    /**
     * save session
     */
    fun saveSession(session: Session): Completable
}