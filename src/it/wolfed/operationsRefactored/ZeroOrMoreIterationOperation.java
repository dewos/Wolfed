/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operationsRefactored;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.TransitionVertex;

/**
 *  ZeroOrMoreIteration Operation
 * 
 */
public class ZeroOrMoreIterationOperation extends Operation{
    public ZeroOrMoreIterationOperation(PetriNetGraph operationGraph, PetriNetGraph iterationGraph) throws Exception{
        super(operationGraph);
        
        this.operationGraph = (new OneOrMoreIterationOperation(operationGraph, iterationGraph)).getOperationGraph();
        
        execute();
    }

    @Override
    void process() throws Exception {
        TransitionVertex zeroTransition = this.operationGraph.insertTransition(null);
        getOperationGraph().insertArc(null, this.operationGraph.getInitialPlaces().get(0) , zeroTransition);
        getOperationGraph().insertArc(null, zeroTransition, this.operationGraph.getFinalPlaces().get(0));
    }
    
}
