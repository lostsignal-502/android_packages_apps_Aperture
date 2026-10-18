/*
 * SPDX-FileCopyrightText: LunarisAOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.models

import android.graphics.ColorMatrix
import org.lineageos.aperture.R

enum class PhotoFilter(val labelResId: Int, val colorMatrix: FloatArray) {
    NONE(R.string.filter_none, FloatArray(20).also { m ->
        m[0] = 1f; m[6] = 1f; m[12] = 1f; m[18] = 1f // identity
    }),
    VIVID(R.string.filter_vivid, ColorMatrix().also { it.setSaturation(1.8f) }.array.copyOf()),
    NOIR(R.string.filter_noir, ColorMatrix().also { it.setSaturation(0f) }.array.copyOf()),
    WARM(R.string.filter_warm, floatArrayOf(
        1.20f, 0.00f, 0.00f, 0f,  8f,
        0.00f, 1.05f, 0.00f, 0f,  3f,
        0.00f, 0.00f, 0.80f, 0f, -10f,
        0.00f, 0.00f, 0.00f, 1f,   0f,
    )),
    COOL(R.string.filter_cool, floatArrayOf(
        0.80f, 0.00f, 0.00f, 0f, -8f,
        0.00f, 1.00f, 0.00f, 0f,  0f,
        0.00f, 0.00f, 1.30f, 0f, 12f,
        0.00f, 0.00f, 0.00f, 1f,  0f,
    )),
    // Film-style B&W: green-boosted luminance (0.27R+0.67G+0.06B) + contrast crush
    BW(R.string.filter_bw, floatArrayOf(
        0.27f, 0.67f, 0.06f, 0f, -12f,
        0.27f, 0.67f, 0.06f, 0f, -12f,
        0.27f, 0.67f, 0.06f, 0f, -12f,
        0.00f, 0.00f, 0.00f, 1f,   0f,
    )),
}
