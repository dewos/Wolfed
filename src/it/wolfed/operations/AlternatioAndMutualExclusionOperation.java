/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlternatioAndMutualExclusionOperation {
    private final List<PetriNetGraph> graphs;
    private PetriNetGraph resultGraph;
    private String operationName;
    public AlternatioAndMutualExclusionOperation(String opName, List<PetriNetGraph> editorGraphs) {
        graphs = editorGraphs;
        this.operationName = opName;
        init();
    }

    private void init() {
        try {
            List<PetriNetGraph> inputNet = new ArrayList<>();
            inputNet.add(getGraphs().get(0));
            
            List<PetriNetGraph> inputNet2 = new ArrayList<>();
            inputNet2.add(getGraphs().get(1));
            ExtendedGraph extendedGraph1 = new ExtendedGraph(inputNet);
            ExtendedGraph extendedGraph2 = new ExtendedGraph(inputNet2);
            
            List<PetriNetGraph> inputNetAlternation = new ArrayList<>();
            inputNetAlternation.add(extendedGraph1.getOperationGraph());
            inputNetAlternation.add(extendedGraph2.getOperationGraph());
            
            List<ExtendedGraph> inputExtendedNetAlternation = new ArrayList<>();
            inputExtendedNetAlternation.add(extendedGraph1);
            inputExtendedNetAlternation.add(extendedGraph2);
            
            switch(operationName){
                case Constants.OPERATION_ALTERNATION:
                    IntermediateAlternationOperation alternationOperation = new IntermediateAlternationOperation(Constants.OPERATION_ALTERNATION, inputNetAlternation, inputExtendedNetAlternation);
                    resultGraph = alternationOperation.alternation();
                    break;
                case Constants.OPERATION_MUTUALEXCLUSION:
                    IntermediateAlternationOperation mutualExclusionOperation = new IntermediateAlternationOperation(Constants.OPERATION_MUTUALEXCLUSION, inputNetAlternation, inputExtendedNetAlternation);
                    resultGraph = mutualExclusionOperation.mutualExclusion();
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(AlternatioAndMutualExclusionOperation.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    
    public List<PetriNetGraph> getGraphs() {
        return graphs;
    }
    
    public PetriNetGraph getOperationGraph() {
        return resultGraph;
    }
}
