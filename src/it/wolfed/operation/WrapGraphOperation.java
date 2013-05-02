package it.wolfed.operation;

import it.wolfed.manipulation.GraphManipulation;
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
     * @param secondGraph
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
        // data una Petri Net -- eleggerla a Worflow (Oppure Workflow Module)
        if(!this.operationGraph.isWorkFlow()){    
            if(this.operationGraph.getInitialPlaces().size() > 0)
            {
                andSplitPattern();
            }
            if(this.operationGraph.getFinalPlaces().size() > 0)
            {
                andJoinPattern();
            }
        }
    }
    
    /**
     * Applica And-Split implicito alle piazze Iniziali
     * @param operationGraph 
     */
    private void andSplitPattern()
    {
        // new initial Place i
        PlaceVertex initialPlaceI = this.operationGraph.insertPlace(null);
        initialPlaceI.setValue("i");
        initialPlaceI.setTokens(1);
        // new and-split transition
        TransitionVertex andSplitTransition = this.operationGraph.insertTransition(null);
        andSplitTransition.setValue("and-split");
        
        this.operationGraph.insertArc(null, initialPlaceI, andSplitTransition);
        
        // for each old initial place i_old, create an Edge (and-split, i_old)
        for (PlaceVertex initialPlace  : this.operationGraph.getInitialPlaces())
        {
            initialPlace.setTokens(0);
            this.operationGraph.insertArc(null, andSplitTransition, initialPlace);
        }
    }
    /**
     * Applica And-Join implicito alle piazze Finali
     * @param operationGraph 
     */
    private void andJoinPattern()
    {
        // new final place o
        PlaceVertex finalPlaceO = this.operationGraph.insertPlace(null);
        finalPlaceO.setValue("o");
        // new and-join transition
        TransitionVertex andJoinTransition = this.operationGraph.insertTransition(null);
        andJoinTransition.setValue("and-join");
        
        this.operationGraph.insertArc(null, andJoinTransition, finalPlaceO);
        
        // for each old final palce o_old, create an Edge (o_old, and-join) 
        for (PlaceVertex finalPlace  : this.operationGraph.getFinalPlaces())
        {
            this.operationGraph.insertArc(null, finalPlace, andJoinTransition);
        }
    }
}