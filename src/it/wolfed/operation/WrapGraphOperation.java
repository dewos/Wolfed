package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;

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
        // data una Petri Net -- eleggerla a Worflow (Oppure WorflowModule)
        // inserire qui la logica
        
        
    }
}