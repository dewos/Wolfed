package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;

/**
 * Sequencing Operation.
 */
public class SequencingOperation extends Operation
{
    PetriNetGraph firstGraph;
    PetriNetGraph secondGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public SequencingOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.secondGraph = getIfIsWorkFlow(secondGraph);
        this.operationGraph = (new FullMergeOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Process Sequencing.
     * 
     * FistGraph:
     *
     *  N1_P1 ◎ → N1_T1 ❒ → N1_P2 ◯
     * 
     * -------------------------------
     * 
     * SecondGraph:
     * 
     *  N2_P1 ◎ → N2_T1 ❒ → N2_P2 ◯
     * 
     * -------------------------------
     * 
     * ResultGraph:
     * 
     * N1_P1 ◎ → N1_T1 ❒ -> P* ◯ → N2_T2 ❒ → N2_P2 ◯
     * P* = (N1_P2 + N2_P1)
     */
    @Override
    void process()
    {
        Vertex finalPlaceAsFirst = getEquivalentVertex(1, firstGraph.getFinalPlaces().get(0));
        Vertex initialPlaceAsSecond = getEquivalentVertex(2, secondGraph.getInitialPlaces().get(0));

        cloneIncomingEdges(finalPlaceAsFirst, initialPlaceAsSecond);
        removeVertexAndHisEdges(finalPlaceAsFirst);
        
        // set token to initial place
        operationGraph.getInitialPlaces().get(0).setTokens(1);
    }
}