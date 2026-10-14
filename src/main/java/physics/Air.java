package physics;

/**
 * Air, modelled as a very low density fluid used to compute drag.
 */
public class Air extends Fluid{
    // Creates air using its standard density at sea level.
    protected Air() {
        super(1.29f);
    }
}
