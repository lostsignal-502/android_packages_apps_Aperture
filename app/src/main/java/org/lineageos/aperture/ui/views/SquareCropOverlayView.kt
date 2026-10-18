/*
 * SPDX-FileCopyrightText: LunarisAOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Semi-transparent bars drawn over the viewfinder to indicate the 1:1 square crop region.
 * Bars appear at top and bottom, each (height - width) / 2 pixels tall.
 */
class SquareCropOverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.argb(160, 0, 0, 0)
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        if (width >= height) return
        val barHeight = (height - width) / 2f
        canvas.drawRect(0f, 0f, width.toFloat(), barHeight, paint)
        canvas.drawRect(0f, height - barHeight, width.toFloat(), height.toFloat(), paint)
    }
}
