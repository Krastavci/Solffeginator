package hr.fer.solffeginator.info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Enum klasa koja povezuje ulazna slova s vrijednoscu na ljestvici
 */

public enum Ljestvica {

    c("c", 0),
    d("d", 2),
    e("e", 4),
    f("f", 6),
    g("g", 8),
    a("a", 10),
    h("h", 12);


    private final String name;
    private final int value;

    /*
    HashMap koji povezuje enum vrijednosti s pocetnim slovom ulaza
     */
    private static final Map<String, Ljestvica> lookup = new HashMap<String, Ljestvica>();
    static {
        lookup.put("c", Ljestvica.c);
        lookup.put("d", Ljestvica.d);
        lookup.put("e", Ljestvica.e);
        lookup.put("f", Ljestvica.f);
        lookup.put("g", Ljestvica.g);
        lookup.put("a", Ljestvica.a);
        lookup.put("h", Ljestvica.h);
    }

    Ljestvica(String name, int value) {
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

    public int getValue() { return value; }

    /**
     * Getter koji sluzi za dobivanje Enum vrijednosti preko Stringa kratice (ulaza) - npr. getInfo("C") vraca enum cijele note
     * @param s String
     * @return Info
     */
    public static Ljestvica getLjestvica(String s) {
        return lookup.get(s);
    }

}
