package jobshop.solvers.neighborhood;

import jobshop.encodings.Encoding;

import java.util.List;

/** For a particular encoding Enc, a neighborhood allow the generation of the neighbors of
 * a particular solution.
 *
 * @param <Enc> A subclass of Encoding for which this encoding can generate neighbors.
 */
public abstract class Neighborhood<Enc extends Encoding> {

    /** Generates all neighbors for the current solution.  */
    public abstract List<Neighbor<Enc>> generateNeighbors(Enc current);
}
