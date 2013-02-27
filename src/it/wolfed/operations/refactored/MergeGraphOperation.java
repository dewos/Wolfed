
package it.wolfed.operations.refactored;

import com.mxgraph.model.mxCell;
import it.wolfed.model.ArcEdge;
import it.wolfed.model.InterfaceVertex;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MergeGraphOperation extends Operation
{
    protected List<PetriNetGraph> inputGraphs;
    
    /**
     * Shortcut for two input Graphs.
     * 
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph  
     */
    public MergeGraphOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph)
    {
        super(operationGraph);
        inputGraphs = new ArrayList<>();
        inputGraphs.add(firstGraph);
        inputGraphs.add(secondGraph);
        execute();
    }
    
    /**
     * Shortcut for one input Graph.
     * 
     * @param operationGraph
     * @param firstGraph
     */
    public MergeGraphOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph)
    {
        super(operationGraph);
        inputGraphs = new ArrayList<>();
        inputGraphs.add(firstGraph);
        execute();
    }
    
    /**
     * Merge n graphs in operationGraph;
     * 
     * @param operationGraph
     * @param inputGraphs 
     */
    public MergeGraphOperation(PetriNetGraph operationGraph, List<PetriNetGraph> inputGraphs)
    {
        super(operationGraph);
        this.inputGraphs = inputGraphs;
        execute();
    }
    
    @Override
    void process()
    {
        mxCell clone = null;
        Object parent = operationGraph.getDefaultParent();
        
        for (int i = 0; i < inputGraphs.size(); i++)
        {
            PetriNetGraph net = inputGraphs.get(i);

            for (Object cellObj : net.getChildCells(net.getDefaultParent()))
            {
                mxCell cell = (mxCell) cellObj;

                if(cell instanceof PlaceVertex)
                {
                    clone = new PlaceVertex(parent, getPrefix(i + 1) + cell.getId(), cell.getValue(), 0, 0);
                } 
                else if(cell instanceof TransitionVertex)
                {
                    clone = new TransitionVertex(parent, getPrefix(i + 1) + cell.getId(), cell.getValue(), 0, 0);
                }
                else if(cell instanceof InterfaceVertex)
                {
                    clone = new InterfaceVertex(parent, getPrefix(i + 1) + cell.getId(), cell.getValue());
                }
                // @todo check instanceof Arc when the mouserelease creation of arcs will be type-zed
                // ArcEdge e mxCell.isEdge()
                else if(cell.isEdge())
                {
                    // Get
                    Vertex source = operationGraph.getVertexById(getPrefix(i + 1) + cell.getSource().getId());
                    Vertex target = operationGraph.getVertexById(getPrefix(i + 1) + cell.getTarget().getId());

                    // Clone
                    clone = new ArcEdge(parent, getPrefix(i + 1) + cell.getId(), cell.getValue());

                    clone.setSource(source);
                    clone.setTarget(target);
                    clone.setId(getPrefix(i + 1) + cell.getId());
                }
                else
                {
                    //clone = cell.clone()
                }

                operationGraph.addCell(clone);
            }
        }
    }
}
