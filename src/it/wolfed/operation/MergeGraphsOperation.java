package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

public class MergeGraphsOperation extends Operation
{  
    public MergeGraphsOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("merge", inputGraphs, 2, false);
    }
    
    @Override
    void process()
    {
       // do nothing :) merge in progress in Operation class
    }
}