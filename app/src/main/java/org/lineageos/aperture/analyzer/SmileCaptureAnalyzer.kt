/*
 * SPDX-FileCopyrightText: LunarisAOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class SmileCaptureAnalyzer(
    private val onSmileDetected: () -> Unit
) : ImageAnalysis.Analyzer {

    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setMinFaceSize(0.25f)
            .build()
    )

    private val isProcessing = AtomicBoolean(false)
    private val lastTriggerMs = AtomicLong(0L)

    override fun analyze(image: ImageProxy) {
        if (isProcessing.getAndSet(true)) {
            image.close()
            return
        }

        val inputImage = InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees)

        detector.process(inputImage)
            .addOnSuccessListener { faces ->
                val now = System.currentTimeMillis()
                val smiling = faces.any { it.smilingProbability?.let { p -> p >= SMILE_THRESHOLD } == true }
                if (smiling && now - lastTriggerMs.get() > DEBOUNCE_MS) {
                    lastTriggerMs.set(now)
                    onSmileDetected()
                }
            }
            .addOnCompleteListener {
                isProcessing.set(false)
                image.close()
            }
    }

    fun close() {
        detector.close()
    }

    companion object {
        private const val SMILE_THRESHOLD = 0.85f
        private const val DEBOUNCE_MS = 3_000L
    }
}
