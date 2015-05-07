package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */
public enum Sound {
    DRUM, METRONOME_HEAVY, METRONOME_LIGHT, ORGAN;

    public static String soundName(Sound s) {
        switch(s){
            case DRUM:
                return "bubnjevi.wav";
            case METRONOME_HEAVY:
                return "metronom_heavy.wav";
            case METRONOME_LIGHT:
                return "metronom_light.wav";
            case ORGAN:
                return "organ.wav";
        }
        return null;
    }
}