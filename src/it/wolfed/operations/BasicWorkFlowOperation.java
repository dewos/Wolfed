package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import java.util.List;

/**
 * Aggiunge una WorkFlowNet P -> T -> P
 * ai grafi selezionati
 * 
 * @author Fabio
 */
public class BasicWorkFlowOperation extends Operation
{  
    public BasicWorkFlowOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super(inputGraphs, "basic");
        
        if(inputGraphs.size() != 1)
        {
            throw new Exception("Only one WorkFlow Net required");
        }
    }

    @Override
    void process()
    {
        PlaceVertex px = getOperationGraph().insertPlace(null);
        PlaceVertex p2 = getOperationGraph().insertPlace("p2");
        TransitionVertex t1 = getOperationGraph().insertTransition(null);

        getOperationGraph().insertArc(null, px, t1);
        getOperationGraph().insertArc("a1", t1, p2);
    }
}