package com.test.horizonchaseturbo.domain.model

data class PoliceCar(
    val offsetX: Float,
    var offsetY: Float,
    var hasCollided: Boolean = false,
    var passed: Boolean = false
)
