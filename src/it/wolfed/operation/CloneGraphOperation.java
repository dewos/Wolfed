package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;

/**
 * Clone Operation.
 */
public class CloneGraphOperation extends Operation
{
    PetriNetGraph firstGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public CloneGraphOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph) throws Exception
    {
        super(operationGraph);
        this.operationGraph = (new FullMergeOperation(operationGraph, firstGraph)).getOperationGraph();
        execute();
    }
   
   
    @Override
    void process()
    {
        // Nothing
    }
}