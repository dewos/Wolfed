package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public class ExplicitChoiceOperation extends Operation
{
    public ExplicitChoiceOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("exp_choice", inputGraphs, 2, true);
    }

    @Override
    void process()
    {
        insertInitialPattern();
        insertFinalPattern();
    }

    private void insertInitialPattern()
    {
        PlaceVertex pi = getOperationGraph().insertPlace("initial");
        TransitionVertex ti_1 = getOperationGraph().insertTransition("ti_1");
        TransitionVertex ti_2 = getOperationGraph().insertTransition("ti_2");

        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);
        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));
        Vertex initialPlaceAsN1 = getEquivalentVertex(net1, net1.getInitialPlaces().get(0));


        getOperationGraph().insertArc(null, pi, ti_1);
        getOperationGraph().insertArc(null, pi, ti_2);

        getOperationGraph().insertArc(null, ti_1, initialPlaceAsN0);

        getOperationGraph().insertArc(null, ti_2, initialPlaceAsN1);
    }

    private void insertFinalPattern()
    {
        PlaceVertex po = getOperationGraph().insertPlace("final");
        TransitionVertex to_1 = getOperationGraph().insertTransition("to_1");
        TransitionVertex to_2 = getOperationGraph().insertTransition("to_2");
        
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));
        Vertex finalPlaceAsN1 = getEquivalentVertex(net1, net1.getFinalPlaces().get(0));

        getOperationGraph().insertArc(null, to_1, po);
        getOperationGraph().insertArc(null, to_2, po);

        getOperationGraph().insertArc(null, finalPlaceAsN0, to_1);
        getOperationGraph().insertArc(null, finalPlaceAsN1, to_2);
    }
}
