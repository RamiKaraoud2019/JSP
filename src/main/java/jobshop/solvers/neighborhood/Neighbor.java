package jobshop.solvers.neighborhood;

import jobshop.encodings.Encoding;

/** This class provides a representation of neighbor by allowing to transform
 * a solution in a particular encoding Enc into the neighbor and back.
 *
 * @param <Enc> A subclass of Encoding for that can be transformed into a neighbor and back.
 * */
public abstract class Neighbor<Enc extends Encoding> {

    /** Transform the given solution into the neighbor. */
    public abstract void applyOn(Enc current);

    /** Transform the neighbor back into the original solution. */
    public abstract void undoApplyOn(Enc current);

}
