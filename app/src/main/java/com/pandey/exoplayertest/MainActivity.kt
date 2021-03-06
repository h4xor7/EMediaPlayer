package com.pandey.exoplayertest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util


class MainActivity : AppCompatActivity(){

    private lateinit var  playerView :PlayerView
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         playerView = findViewById(R.id.video_view);
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }

    }

    override fun onResume() {
        super.onResume()
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }

    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }

    }

    private fun releasePlayer() {
        /**Before you release and destroy the player, store the following information:

        Play/pause state using getPlayWhenReady.
        Current playback position using getCurrentPosition.
        Current window index using getCurrentWindowIndex
         taki jb fir se aaye wps to whi se start ho jaha se chhor k gye the
         */


        if (player != null) {
            playWhenReady = player!!.playWhenReady;
            playbackPosition = player!!.currentPosition;
            currentWindow = player!!.currentWindowIndex;
            player!!.release();
            player = null;
        }

    }


    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }

    }
    private fun hideSystemUi() {

        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        player?.setMediaItem(mediaItem)


        /** save info le rha */
        /** Here's what's happening:
        setPlayWhenReady tells the player whether to
        start playing as soon as all resources for playback have been acquired.
        Because playWhenReady is initially true, playback starts automatically
        the first time the app is run.
        seekTo tells the player to seek to a certain position within a specific window.
        Both currentWindow and playbackPosition are initialized to zero so that playback
        starts from the very start the first time the app is run.
        prepare tells the player to acquire all the resources required for playback.
         */
        player?.playWhenReady = playWhenReady;
        player?.seekTo(currentWindow, playbackPosition);
        player?.prepare();

    }


}