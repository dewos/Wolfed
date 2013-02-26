/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

public class AlternationOperation extends IntermediateAlternationOperation{
    public AlternationOperation(List<PetriNetGraph> inputGraphs, List<IterationOperation> extendedGraphs) throws Exception {
        super("alternation", inputGraphs, extendedGraphs);
        compose();
    }
    
    void compose() {
        getOperationGraph().insertArc(null, intermediatePlace, initialTransitionAsN0);
        getOperationGraph().insertArc(null, finalTransitionAsN1, intermediatePlace);
    }
    
}
