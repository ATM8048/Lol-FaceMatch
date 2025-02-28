package ch.zli.lolfacematch.analysis

import android.graphics.Bitmap
import ch.zli.lolfacematch.data.FaceFeatures
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
        faceDetector
            .process(InputImage.fromBitmap(bitmap, rotationDegree))
            .addOnSuccessListener { faces -> onResult(faces) }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getFaceFeatures(face: Face): FaceFeatures =
        FaceFeatures(
            smilingProbability = face.smilingProbability ?: 0f,
            leftEyeOpenProbability = face.leftEyeOpenProbability ?: 0f,
            rightEyeOpenProbability = face.rightEyeOpenProbability ?: 0f,
            headEulerAngleY = face.headEulerAngleY,
        )
}
