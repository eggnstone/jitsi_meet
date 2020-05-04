package com.gunschu.jitsi_meet

import android.content.Context
import android.content.Intent
import android.util.Log
import com.gunschu.jitsi_meet.JitsiMeetPlugin.Companion.JITSI_PLUGIN_TAG
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions

import android.os.Bundle
import android.os.Handler

private const val SELF_DESTROY_JITSI_MEET_ACTIVITY__DURATION_IN_SECONDS = "SelfDestroyJitsiMeetActivity_DurationInSeconds"

public class SelfDestroyJitsiMeetActivity() : JitsiMeetActivity() {
    val SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG = "SelfDestroyJitsiMeetActivity"
    var alreadyDestroyed = false

    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var durationInSeconds: Int = getIntent().getIntExtra(SELF_DESTROY_JITSI_MEET_ACTIVITY__DURATION_IN_SECONDS, -1)
        var durationInMillisconds: Long = durationInSeconds * 1000L;
        Log.d(SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG, "onCreate: durationInSeconds: $durationInSeconds")

        val delayedHandler = Handler()
        delayedHandler.postDelayed({
            if (alreadyDestroyed) {
                Log.d(SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG, "Not selfdestroying because already destroyed.")
            } else {
                try {
                    Log.d(SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG, "Selfdestroying ... now!")
                    leave()
                } catch (e: Exception) {
                    Log.e(SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG, "Could not selfdestroy: $e")
                }
            }
        }, durationInMillisconds)
    }

    override public fun onDestroy() {
        Log.d(SELF_DESTROY_JITSI_MEET_ACTIVITY_TAG, "onDestroy")
        alreadyDestroyed = true
        super.onDestroy()
    }
}
