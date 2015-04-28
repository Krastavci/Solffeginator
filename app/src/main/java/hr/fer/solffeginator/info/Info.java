package hr.fer.solffeginator.info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Enum klasa koja povezuje ulazna slova s nazivom nota i njenom numerickom vrijednoscu
 */

public enum Info {

    C("cijela nota", 1.),
    p("polovinka", 0.5),
    c("cetvrtinka", 0.25),
    o("osminka", 0.125),
    s("sesnaestinka", 0.0625);


    private final String name;
    private final Double value;

    /*
    HashMap koji povezuje enum vrijednosti s pocetnim slovom ulaza
     */
    private static final Map<String, Info> lookup = new HashMap<String, Info>();
    static {
        lookup.put("C", Info.C);
        lookup.put("p", Info.p);
        lookup.put("c", Info.c);
        lookup.put("o", Info.o);
        lookup.put("s", Info.s);
    }

    Info(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter koji sluzi za dobivanje imena
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter koji sluzi za dobivanje numericke vrijednosti note
     * @return Double
     */
    public Double getValue() {
        return value;
    }

    /**
     * Getter koji sluzi za dobivanje Enum vrijednosti preko Stringa kratice (ulaza) - npr. getInfo("C") vraca enum cijele note
     * @param s String
     * @return Info
     */
    public static Info getInfo(String s) {
        return lookup.get(s);
    }

}
