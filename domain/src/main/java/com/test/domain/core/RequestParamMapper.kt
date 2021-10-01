package com.test.domain.core

class RequestParamMapper {
    private val body: MutableMap<String, Any> = mutableMapOf()

    fun add(key: String, value: Any): RequestParamMapper {
        body[key] = value
        return this
    }

    fun add(param: Pair<String, Any>): RequestParamMapper {
        val (key, value) = param
        return this.add(key = key, value = value)
    }

    fun plus(param: Map<String, Any>): RequestParamMapper {
        body.putAll(param)
        return this
    }

    fun toBody(): Map<String, Any> = body.toMap()
}