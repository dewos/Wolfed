package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;

/**
 * Sequencing Operation.
 */
public class IterationPatternOperation extends Operation
{
    PetriNetGraph firstGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @throws Exception  
     */
    public IterationPatternOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.operationGraph = (new MergeGraphsOperation(operationGraph, firstGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Iteration Pattern.
     * 
     * firstGraph   = P1 -> T1 -> P1
     * result       = initial* -> T3* -> P1 -> T1 -> P1 -> T4* -> final*
     */
    @Override
    void process()
    {
        insertInitialPattern();
        insertFinalPattern();
    }
    
    /**
     * Iteration Initial Pattern.
     * 
     * firstGraph   = P1 -> T1 -> P1
     * result       = initial* -> T3* -> P1 -> T1 -> P1
     */
    private void insertInitialPattern()
    {
        PlaceVertex initialPlace = operationGraph.insertPlace(null);
        TransitionVertex initialTransition = operationGraph.insertTransition(null);
        PlaceVertex initialPlaceAsFirst = (PlaceVertex) getEquivalentVertex(1, firstGraph.getInitialPlaces().get(0));
        
        operationGraph.insertArc(null, initialPlace, initialTransition);
        operationGraph.insertArc(null, initialTransition, initialPlaceAsFirst);
        
        // Remove torkens if any
        initialPlaceAsFirst.setTokens(0);
    }

    /**
     * Iteration Final Pattern.
     * 
     * firstGraph   = P1 -> T1 -> P1
     * result       = P1 -> T1 -> P1 -> T4* -> final*
     */
    private void insertFinalPattern()
    {
        PlaceVertex finalPlace = operationGraph.insertPlace(null);
        TransitionVertex finalTransition = operationGraph.insertTransition(null);
        Vertex finalPlaceAsFirst = getEquivalentVertex(1, firstGraph.getFinalPlaces().get(0));

        operationGraph.insertArc(null, finalTransition, finalPlace);
        operationGraph.insertArc(null, finalPlaceAsFirst, finalTransition);
    }
}