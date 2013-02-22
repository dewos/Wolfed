/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

/**
 *
 * @author gbpellizzi
 */
public class OneOrMoreIterationOperation extends Iteration{

    public OneOrMoreIterationOperation(List<PetriNetGraph> inputGraphs) throws Exception {
        super("OneOrMore", inputGraphs);
        
    }

    @Override
    void compose() {
        TransitionVertex intermediateTransition = getOperationGraph().insertTransition(null);
        PetriNetGraph net0 = getInputGraphs().get(0);
        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));
        getOperationGraph().insertArc(null, finalPlaceAsN0, intermediateTransition);
        getOperationGraph().insertArc(null, intermediateTransition, initialPlaceAsN0);
    }
    
}
