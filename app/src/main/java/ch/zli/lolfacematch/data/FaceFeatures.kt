package ch.zli.lolfacematch.data

data class FaceFeatures(
    val smilingProbability: Float,
    val leftEyeOpenProbability: Float,
    val rightEyeOpenProbability: Float,
    val headEulerAngleY: Float,
)
