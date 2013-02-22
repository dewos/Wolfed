/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

/**
 *
 *
 */
public abstract class IntermediateOperation extends Operation {

    public IntermediateOperation(List<PetriNetGraph> inputGraphs) throws Exception {
        super("_", inputGraphs, inputGraphs.size(), true);
        
        
    }
}
