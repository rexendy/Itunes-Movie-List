package com.test.domain.session.usecase

import com.test.domain.core.SingleUseCase
import com.test.domain.session.model.Session
import com.test.domain.session.repository.SessionLocalRepository

class GetSessionUseCase(private val repository: SessionLocalRepository): SingleUseCase<Session> {
    override fun execute() = repository.getSession()
}