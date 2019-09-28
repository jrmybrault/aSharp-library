package com.jbr.asharplibrary.random

import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk

val mockResources = mockk<Resources>(relaxed = true) {
    every { getString(any()) } returns RandomStringGenerator.generate()
    every { getString(any()) } returns RandomStringGenerator.generate()
    every { getString(any()) } returns RandomStringGenerator.generate()
    every { getString(any()) } returns RandomStringGenerator.generate()
}

fun createMockResourcesString(fakeString: String = RandomStringGenerator.generate()) = mockk<Resources>(relaxed = true) {
    every { getString(any()) } returns fakeString
    every { getString(any(), any()) } returns fakeString
    every { getQuantityString(any(), any()) } returns fakeString
    every { getQuantityString(any(), any(), any(), any()) } returns fakeString
}
