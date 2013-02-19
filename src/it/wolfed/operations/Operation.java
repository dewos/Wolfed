package it.wolfed.operations;

import com.mxgraph.model.mxCell;
import it.wolfed.model.ArcEdge;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public abstract class Operation
{  
    protected List<PetriNetGraph> inputGraphs;
    protected PetriNetGraph operationGraph;

    public Operation(String name, List<PetriNetGraph> inputGraphs, int numRequestedGraphs, boolean checkisWorkflow) throws Exception
    {
        this.inputGraphs = inputGraphs;

        if(inputGraphs.size() != numRequestedGraphs)
        {
            throw new Exception(numRequestedGraphs +" input graph required. " + inputGraphs.size() + " found.");
        }
        
        for (PetriNetGraph net : inputGraphs)
        {
            if (checkisWorkflow == true)
            {
                if(! net.isWorkFlow())
                {
                    throw new Exception("WorkFlow required! " + net.getId() + "failed!");
                }
            }

            name += "_" + net.getId();
        }

        this.operationGraph = new PetriNetGraph(name);
        execute();
    }

    public PetriNetGraph getOperationGraph()
    {
        return operationGraph;
    }

    public List<PetriNetGraph> getInputGraphs()
    {
        return inputGraphs;
    }
    
    /**
     * Esegue l'operazione sul grafo dell'operazione.
     * 
     * @return void
     */
    private void execute()
    {
        getOperationGraph().getModel().beginUpdate();

        try
        {
            merge();
            process();
        }
        catch (CloneNotSupportedException ex)        
        {
            ex.printStackTrace();
        }        
        finally
        {
            getOperationGraph().getModel().endUpdate();
        }
    };
    
    /**
     * Il processo che modifica il grafo dell'operazione.
     * 
     * @return void
     */
    abstract void process();

    /**
     * Effettua l'unione disgiunta di n Petri Net.
     * 
     * Aggiunge un prefisso a tutti gli id in base
     * alla chiave del grafo di input
     */
    private void merge() throws CloneNotSupportedException
    {
        mxCell clone;
        Object parent = getOperationGraph().getDefaultParent();
        
        for (int i = 0; i < getInputGraphs().size(); i++)
        {
            PetriNetGraph net = getInputGraphs().get(i);

            for (Object cellObj : net.getChildCells(net.getDefaultParent()))
            {
                mxCell cell = (mxCell) cellObj;

                if(cell instanceof PlaceVertex)
                {
                    clone = new PlaceVertex(parent, getPrefix(net) + cell.getId(), cell.getValue());
                } 
                else if(cell instanceof TransitionVertex)
                {
                    clone = new TransitionVertex(parent, getPrefix(net) + cell.getId(), cell.getValue());
                }
                // ArcEdge e mxCell.isEdge()
                else if(cell.isEdge())
                {
                    // Get
                    Vertex source = getOperationGraph().getVertexById(getPrefix(net) + cell.getSource().getId());
                    Vertex target = getOperationGraph().getVertexById(getPrefix(net) + cell.getTarget().getId());

                    // Clone
                    clone = new ArcEdge(parent, getPrefix(net) + cell.getId(), cell.getValue());

                    clone.setSource(source);
                    clone.setTarget(target);
                    clone.setId(getPrefix(net) + cell.getId());
                }
                else
                {
                    clone = (mxCell) cell.clone();
                }

                getOperationGraph().addCell(clone);
            }
        }
    }
    
    /**
     * Cerca, nella rete operazionale, un vertice con lo stesso id 
     * di quello di una rete diversa (valutando il prefisso).
     * 
     * @param sameGraph     il grafo che contiene il vertice
     * @param sameVertex    l'id da cercare
     * @return 
     */
    public Vertex getSameVertexById(PetriNetGraph sameGraph, Vertex sameVertex)
    {
        return getOperationGraph().getVertexById(getPrefix(sameGraph) + sameVertex.getId());
    }
    

    /**
     * Ottiene il prefisso del grafo di input.
     * 
     * @param inputGraph
     * @return String
     */
    public String getPrefix(PetriNetGraph inputGraph)
    {
        String key = "?_";
        
        for (int i = 0; i < getInputGraphs().size(); i++)
        {
            PetriNetGraph graph = getInputGraphs().get(i);

            if(inputGraph == graph)
            {
                return "n" + String.valueOf(i) + "_";
            }
        }
        
        return key;
    }
}