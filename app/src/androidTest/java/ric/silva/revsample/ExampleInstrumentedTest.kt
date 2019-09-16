package ric.silva.revsample

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ric.silva.revsample.utils.PreferenceHelper

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("ric.silva.revsample", appContext.packageName)
    }

    @Test
    fun saveAndGetSharedPreference(){
        val appContext = InstrumentationRegistry.getTargetContext()
        val testString = "testString"
        val key = "testKey"
        PreferenceHelper.setStringPreference(appContext, key, testString)
        assertEquals(testString, PreferenceHelper.getStringPreference(appContext, key))

    }
}
