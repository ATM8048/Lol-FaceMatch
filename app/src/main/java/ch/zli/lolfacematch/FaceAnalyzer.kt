package ch.zli.lolfacematch

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceAnalyzer {
    private val faceDetector: FaceDetector =
        FaceDetection.getClient(
            FaceDetectorOptions
                .Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build(),
        )

    fun analyzeFace(
        bitmap: Bitmap,
        rotationDegree: Int,
        onResult: (List<Face>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val image = InputImage.fromBitmap(bitmap, rotationDegree)

        faceDetector
            .process(image)
            .addOnSuccessListener { faces ->
                onResult(faces)
            }.addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getFaceFeatures(face: Face): FaceFeatures {
        val smilingProbability = face.smilingProbability
        val leftEyeOpenProbability = face.leftEyeOpenProbability
        val rightEyeOpenProbability = face.rightEyeOpenProbability
        val headEulerAngleY = face.headEulerAngleY

        return FaceFeatures(
            smilingProbability = smilingProbability ?: 0f,
            leftEyeOpenProbability = leftEyeOpenProbability ?: 0f,
            rightEyeOpenProbability = rightEyeOpenProbability ?: 0f,
            headEulerAngleY = headEulerAngleY,
        )
    }

    data class FaceFeatures(
        val smilingProbability: Float,
        val leftEyeOpenProbability: Float,
        val rightEyeOpenProbability: Float,
        val headEulerAngleY: Float,
    )
}
