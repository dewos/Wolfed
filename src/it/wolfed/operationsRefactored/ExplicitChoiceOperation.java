package it.wolfed.operationsRefactored;

import it.wolfed.model.PetriNetGraph;

/**
 *  ExplicitChoice Operation
 */
public class ExplicitChoiceOperation extends Operation {
    private PetriNetGraph firstGraph;
    private PetriNetGraph secondGraph;

    public ExplicitChoiceOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception {
        super(operationGraph);
        this.firstGraph = (new IterationPatternOperation(new PetriNetGraph("1"), firstGraph)).getOperationGraph();
        this.secondGraph = (new IterationPatternOperation(new PetriNetGraph("2"), secondGraph)).getOperationGraph();
        execute();
    }

    @Override
    void process() throws Exception {
        this.operationGraph = (new DefferedChoiceOperation(new PetriNetGraph("ExplicitChoice"), firstGraph, secondGraph)).getOperationGraph();
    }
}
