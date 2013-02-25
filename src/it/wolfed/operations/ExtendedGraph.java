/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

/**
 *
 * @author gbpellizzi
 */
public class ExtendedGraph extends Iteration{

    public ExtendedGraph(List<PetriNetGraph> inputGraphs) throws Exception {
        super("", inputGraphs);
    }
    
    
    @Override
    void compose() {
        
    }
    
}
