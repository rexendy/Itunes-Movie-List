package com.test.domain.session.model

data class Session(
    var dateLastVisited: String? = null,
    var dateCurrentVisited: String? = null,
    var trackId: Long = 0
)
