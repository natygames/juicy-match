package com.nativegame.match3game.effect.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.nativegame.match3game.R;

import java.util.HashMap;

/**
 * SoundManager control sound by calling sound event
 */

public class SoundManager {

    private static final int MAX_STREAMS = 8;
    private static final float DEFAULT_MUSIC_VOLUME = 0.3f;

    private static final String PREFS_NAME = "prefs_setting";
    private static final String SOUNDS_PREF_KEY = "sound";
    private static final String MUSIC_PREF_KEY = "music";

    private final Context mContext;
    private final SharedPreferences mPrefs;
    private MediaPlayer mBgPlayer;
    private SoundPool mSoundPool;

    private HashMap<SoundEvent, Integer> mSoundsMap;

    private boolean mSoundEnabled;
    private boolean mMusicEnabled;

    public SoundManager(Context context) {
        mContext = context;
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mSoundEnabled = mPrefs.getBoolean(SOUNDS_PREF_KEY, true);
        mMusicEnabled = mPrefs.getBoolean(MUSIC_PREF_KEY, true);
        loadIfNeeded();
    }

    private void loadIfNeeded() {
        if (mSoundEnabled) {
            loadSounds();
        }
        if (mMusicEnabled) {
            loadMusic();
        }
    }

    private void loadSounds() {
        createSoundPool();
        mSoundsMap = new HashMap<>();
        loadEventSound(mContext, SoundEvent.COMB01, R.raw.combo1_sound);
        loadEventSound(mContext, SoundEvent.COMB02, R.raw.combo2_sound);
        loadEventSound(mContext, SoundEvent.COMB03, R.raw.combo3_sound);
        loadEventSound(mContext, SoundEvent.COMBO4, R.raw.combo4_sound);

        loadEventSound(mContext, SoundEvent.CRACKER_EXPLODE, R.raw.cracker_explode_sound);
        loadEventSound(mContext, SoundEvent.COOKIE_EXPLODE, R.raw.cookie_explode_sound);
        loadEventSound(mContext, SoundEvent.ICE_CREAM_EXPLODE, R.raw.ice_cream_explode_sound);

        loadEventSound(mContext, SoundEvent.ICE_EXPLODE, R.raw.ice_explode_sound);
        loadEventSound(mContext, SoundEvent.LOCK_EXPLODE, R.raw.lock_explode_sound);
        loadEventSound(mContext, SoundEvent.COLLECT_STAR_FISH, R.raw.collect_starfish_sound);

        loadEventSound(mContext, SoundEvent.SQUARE_EXPLODE, R.raw.square_explode_sound);
        loadEventSound(mContext, SoundEvent.VERTICAL_EXPLODE, R.raw.vertical_explode_sound);
        loadEventSound(mContext, SoundEvent.FRUIT_APPEAR, R.raw.fruit_appear_sound);
        loadEventSound(mContext, SoundEvent.FRUIT_BOUNCING, R.raw.fruit_bouncing_sound);
        loadEventSound(mContext, SoundEvent.FRUIT_UPGRADE, R.raw.fruit_upgrade_sound);
        loadEventSound(mContext, SoundEvent.ICE_CREAM_UPGRADE, R.raw.ice_cream_upgrade_sound);

        loadEventSound(mContext, SoundEvent.GAME_INTRO, R.raw.intro_sound);
        loadEventSound(mContext, SoundEvent.GAME_WIN, R.raw.win_sound);
        loadEventSound(mContext, SoundEvent.GAME_OVER, R.raw.game_over_sound);
        loadEventSound(mContext, SoundEvent.SCORE_COUNT, R.raw.score_count_sound);
        loadEventSound(mContext, SoundEvent.SCORE_GET_STAR, R.raw.score_get_star_sound);
        loadEventSound(mContext, SoundEvent.ADD_BONUS, R.raw.add_bonus_sound);
        loadEventSound(mContext, SoundEvent.SWEEP1, R.raw.sweep1_sound);
        loadEventSound(mContext, SoundEvent.SWEEP2, R.raw.sweep2_sound);
        loadEventSound(mContext, SoundEvent.BUTTON_CLICK, R.raw.btn_sound);
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        } else {
            // Use SoundPool.Builder on API 21
            // http://developer.android.com/reference/android/media/SoundPool.Builder.html
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }

    private void loadEventSound(Context context, SoundEvent event, int fileID) {
        int soundId = mSoundPool.load(context, fileID, 1);
        mSoundsMap.put(event, soundId);
    }

    private void loadMusic() {
        // Important to not reuse it. It can be on a strange state
        mBgPlayer = MediaPlayer.create(mContext, R.raw.happy_and_joyful_children);
        mBgPlayer.setLooping(true);
        mBgPlayer.setVolume(DEFAULT_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
    }

    public void playSoundForSoundEvent(SoundEvent event) {
        if (!mSoundEnabled) {
            return;
        }
        Integer soundId = mSoundsMap.get(event);
        if (soundId != null) {
            // Left Volume, Right Volume, priority (0 == lowest), loop (0 == no) and rate (1.0 normal playback rate)
            mSoundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void pauseBgMusic() {
        if (mMusicEnabled) {
            mBgPlayer.pause();
        }
    }

    public void resumeBgMusic() {
        if (mMusicEnabled && !mBgPlayer.isPlaying()) {
            mBgPlayer.start();
        }
    }

    public void unloadMusic() {
        if (mBgPlayer != null) {
            mBgPlayer.stop();
            mBgPlayer.release();
        }
    }

    public void unloadSounds() {
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
            mSoundsMap.clear();
        }
    }

    public void toggleMusicStatus() {
        mMusicEnabled = !mMusicEnabled;
        if (mMusicEnabled) {
            loadMusic();
            resumeBgMusic();
        } else {
            unloadMusic();
        }
        // Save it to preferences
        mPrefs.edit()
                .putBoolean(MUSIC_PREF_KEY, mMusicEnabled)
                .apply();
    }

    public void toggleMusicStatusInGame() {
        mMusicEnabled = !mMusicEnabled;
        // Save it to preferences
        mPrefs.edit()
                .putBoolean(MUSIC_PREF_KEY, mMusicEnabled)
                .apply();
    }

    public void toggleSoundStatus() {
        mSoundEnabled = !mSoundEnabled;
        if (mSoundEnabled) {
            loadSounds();
        } else {
            unloadSounds();
        }
        // Save it to preferences
        mPrefs.edit()
                .putBoolean(SOUNDS_PREF_KEY, mSoundEnabled)
                .apply();
    }

    public boolean getMusicStatus() {
        return mMusicEnabled;
    }

    public boolean getSoundStatus() {
        return mSoundEnabled;
    }
}
