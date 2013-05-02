package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;

/**
 * Wrap Petri Net Graph Operation.
 */
public class WrapGraphOperation extends Operation
{
    PetriNetGraph firstGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @throws Exception  
     */
    public WrapGraphOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph) throws Exception
    {
        super(operationGraph);
        this.operationGraph = (new FullMergeOperation(operationGraph, firstGraph)).getOperationGraph();
        execute();
    }
   
   
    @Override
    void process()
    {
        if(operationGraph.getInitialPlaces().size() > 1)
        {
            andSplitPattern();
        }
        
        if(operationGraph.getFinalPlaces().size() > 1)
        {
            andJoinPattern();
        }
    }
    
    /**
     * And-Split for initial places
     */
    private void andSplitPattern()
    {
        // new initial Place i
        PlaceVertex initialPlaceI = operationGraph.insertPlace(null);
        initialPlaceI.setTokens(1);
        
        // new and-split transition
        TransitionVertex andSplitTransition = operationGraph.insertTransition(null);
        
        operationGraph.insertArc(null, initialPlaceI, andSplitTransition);
        
        // for each old initial place i_old, create an Edge (and-split, i_old)
        for (PlaceVertex initialPlace  : operationGraph.getInitialPlaces())
        {
            initialPlace.setTokens(0);
            operationGraph.insertArc(null, andSplitTransition, initialPlace);
        }
    }
    /**
     * And-Join for final places
     */
    private void andJoinPattern()
    {
        // new final place o
        PlaceVertex finalPlaceO = this.operationGraph.insertPlace(null);

        // new and-join transition
        TransitionVertex andJoinTransition = this.operationGraph.insertTransition(null);
        
        this.operationGraph.insertArc(null, andJoinTransition, finalPlaceO);
        
        // for each old final palce o_old, create an Edge (o_old, and-join) 
        for (PlaceVertex finalPlace  : this.operationGraph.getFinalPlaces())
        {
            this.operationGraph.insertArc(null, finalPlace, andJoinTransition);
        }
    }
}