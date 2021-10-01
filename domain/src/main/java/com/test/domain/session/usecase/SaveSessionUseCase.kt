package com.test.domain.session.usecase

import com.test.domain.core.CompletableWithParamUseCase
import com.test.domain.session.model.Session
import com.test.domain.session.repository.SessionLocalRepository

class SaveSessionUseCase(private val repository: SessionLocalRepository):
    CompletableWithParamUseCase<Session> {
    override fun execute(t: Session) = repository.saveSession(t)
}