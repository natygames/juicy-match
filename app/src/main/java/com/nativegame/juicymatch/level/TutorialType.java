package com.nativegame.juicymatch.level;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.game.tutorial.BombTutorial;
import com.nativegame.juicymatch.game.tutorial.CakeTutorial;
import com.nativegame.juicymatch.game.tutorial.CandyTutorial;
import com.nativegame.juicymatch.game.tutorial.CombineTutorial;
import com.nativegame.juicymatch.game.tutorial.CookieTutorial;
import com.nativegame.juicymatch.game.tutorial.GeneratorTutorial;
import com.nativegame.juicymatch.game.tutorial.GloveTutorial;
import com.nativegame.juicymatch.game.tutorial.HammerTutorial;
import com.nativegame.juicymatch.game.tutorial.HoneyTutorial;
import com.nativegame.juicymatch.game.tutorial.IceTutorial;
import com.nativegame.juicymatch.game.tutorial.LockTutorial;
import com.nativegame.juicymatch.game.tutorial.Match3Tutorial;
import com.nativegame.juicymatch.game.tutorial.Match4Tutorial;
import com.nativegame.juicymatch.game.tutorial.Match5Tutorial;
import com.nativegame.juicymatch.game.tutorial.MatchLTutorial;
import com.nativegame.juicymatch.game.tutorial.MatchTTutorial;
import com.nativegame.juicymatch.game.tutorial.PieTutorial;
import com.nativegame.juicymatch.game.tutorial.PipeTutorial;
import com.nativegame.juicymatch.game.tutorial.ShellTutorial;
import com.nativegame.juicymatch.game.tutorial.StarfishTutorial;
import com.nativegame.juicymatch.game.tutorial.Tutorial;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum TutorialType {
    NONE,
    MATCH_3,
    MATCH_4,
    MATCH_T,
    MATCH_L,
    MATCH_5,
    COMBINE,
    LOCK,
    COOKIE,
    CAKE,
    CANDY,
    PIE,
    ICE,
    HONEY,
    STARFISH,
    SHELL,
    PIPE,
    GENERATOR,
    HAMMER,
    BOMB,
    GLOVE;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getDrawableId() {
        switch (this) {
            case MATCH_3:
                return R.drawable.ui_tutorial_match_3;
            case MATCH_4:
                return R.drawable.ui_tutorial_match_4;
            case MATCH_T:
                return R.drawable.ui_tutorial_match_t;
            case MATCH_L:
                return R.drawable.ui_tutorial_match_l;
            case MATCH_5:
                return R.drawable.ui_tutorial_match_5;
            case COMBINE:
                return R.drawable.ui_tutorial_combine;
            case LOCK:
                return R.drawable.ui_tutorial_lock;
            case COOKIE:
                return R.drawable.ui_tutorial_cookie;
            case CAKE:
                return R.drawable.ui_tutorial_cake;
            case CANDY:
                return R.drawable.ui_tutorial_candy;
            case PIE:
                return R.drawable.ui_tutorial_pie;
            case ICE:
                return R.drawable.ui_tutorial_ice;
            case HONEY:
                return R.drawable.ui_tutorial_honey;
            case STARFISH:
                return R.drawable.ui_tutorial_starfish;
            case SHELL:
                return R.drawable.ui_tutorial_shell;
            case PIPE:
                return R.drawable.ui_tutorial_pipe;
            case GENERATOR:
                return R.drawable.ui_tutorial_generator;
            case HAMMER:
                return R.drawable.ui_tutorial_hammer;
            case BOMB:
                return R.drawable.ui_tutorial_bomb;
            case GLOVE:
                return R.drawable.ui_tutorial_glove;
            default:
                throw new IllegalArgumentException("TutorialType drawable not found!");
        }
    }

    public int getStringId() {
        switch (this) {
            case MATCH_3:
                return R.string.txt_tutorial_match_3;
            case MATCH_4:
                return R.string.txt_tutorial_match_4;
            case MATCH_T:
                return R.string.txt_tutorial_match_t;
            case MATCH_L:
                return R.string.txt_tutorial_match_l;
            case MATCH_5:
                return R.string.txt_tutorial_match_5;
            case COMBINE:
                return R.string.txt_tutorial_combine;
            case LOCK:
                return R.string.txt_tutorial_lock;
            case COOKIE:
                return R.string.txt_tutorial_cookie;
            case CAKE:
                return R.string.txt_tutorial_cake;
            case CANDY:
                return R.string.txt_tutorial_candy;
            case PIE:
                return R.string.txt_tutorial_pie;
            case ICE:
                return R.string.txt_tutorial_ice;
            case HONEY:
                return R.string.txt_tutorial_honey;
            case STARFISH:
                return R.string.txt_tutorial_starfish;
            case SHELL:
                return R.string.txt_tutorial_shell;
            case PIPE:
                return R.string.txt_tutorial_pipe;
            case GENERATOR:
                return R.string.txt_tutorial_generator;
            case HAMMER:
                return R.string.txt_tutorial_hammer;
            case BOMB:
                return R.string.txt_tutorial_bomb;
            case GLOVE:
                return R.string.txt_tutorial_glove;
            default:
                throw new IllegalArgumentException("TutorialType String not found!");
        }
    }

    public Tutorial getTutorial(Engine engine) {
        switch (this) {
            case MATCH_3:
                return new Match3Tutorial(engine);
            case MATCH_4:
                return new Match4Tutorial(engine);
            case MATCH_T:
                return new MatchTTutorial(engine);
            case MATCH_L:
                return new MatchLTutorial(engine);
            case MATCH_5:
                return new Match5Tutorial(engine);
            case COMBINE:
                return new CombineTutorial(engine);
            case LOCK:
                return new LockTutorial(engine);
            case COOKIE:
                return new CookieTutorial(engine);
            case CAKE:
                return new CakeTutorial(engine);
            case CANDY:
                return new CandyTutorial(engine);
            case PIE:
                return new PieTutorial(engine);
            case ICE:
                return new IceTutorial(engine);
            case HONEY:
                return new HoneyTutorial(engine);
            case STARFISH:
                return new StarfishTutorial(engine);
            case SHELL:
                return new ShellTutorial(engine);
            case PIPE:
                return new PipeTutorial(engine);
            case GENERATOR:
                return new GeneratorTutorial(engine);
            case HAMMER:
                return new HammerTutorial(engine);
            case BOMB:
                return new BombTutorial(engine);
            case GLOVE:
                return new GloveTutorial(engine);
            default:
                throw new IllegalArgumentException("Tutorial not found!");
        }
    }
    //========================================================

}
