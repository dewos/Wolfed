package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

public abstract class IntermediateOperation extends Operation
{
    public IntermediateOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("", inputGraphs, inputGraphs.size(), true);
    }
}
