/*
 * SPDX-FileCopyrightText: LunarisAOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.models

/**
 * Sentinel value for 1:1 square aspect ratio. CameraX has no native 1:1 support;
 * we capture in 4:3 and show a visual crop overlay on the viewfinder.
 */
const val ASPECT_RATIO_1_1 = 3
