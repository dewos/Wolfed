/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operationsRefactored;

import it.wolfed.model.PetriNetGraph;

/**
 *
 */
public class ExplicitChoiceOperation extends Operation {
    private PetriNetGraph firstGraph;
    private PetriNetGraph secondGraph;
    private PetriNetGraph operationGraph;

    public ExplicitChoiceOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception {
        super(operationGraph);
        this.firstGraph = (new IterationPatternOperation(new PetriNetGraph("1"), firstGraph)).getOperationGraph();
        this.secondGraph = (new IterationPatternOperation(new PetriNetGraph("2"), secondGraph)).getOperationGraph();
        this.operationGraph = operationGraph;
        execute();
    }

    @Override
    void process() throws Exception {
        this.operationGraph = (new DefferedChoiceOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
    }
}
