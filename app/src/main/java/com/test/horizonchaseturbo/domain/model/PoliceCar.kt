package com.test.horizonchaseturbo.domain.model

data class PoliceCar(
    val offsetX: Float,
    val offsetY: Float,
    val hasCollided: Boolean = false,
    val passed: Boolean = false
)
