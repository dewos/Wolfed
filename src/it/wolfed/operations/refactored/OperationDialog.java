package it.wolfed.operations.refactored;

import it.wolfed.swing.*;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.operations.IterationOperation;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import layout.SpringUtilities;

public class OperationDialog extends JDialog
{
    private WolfedEditor editor;
    private List<PetriNetGraph> selectedGraphs;
    private int requiredGraphs;
    // for Alternation and MutualExclusion operations
    private List<IterationOperation> inputExtendedNetAlternation;
    private List<PetriNetGraph> inputNetAlternation;
    /**
     * 
     * @todo  refactor this
     * @param editor
     * @param requiredGraphs 
     */
    public OperationDialog(WolfedEditor editor, int requiredGraphs)
    {
        this.editor = editor;
        this.requiredGraphs = requiredGraphs;
        this.selectedGraphs = new ArrayList<>();

        // Init
        setModal(true);// Stop thread
        setTitle("Select " + requiredGraphs + " graphs.");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Get selection window
        JComponent newContentPane = createSelectionWindow();
        newContentPane.setOpaque(true);
        setContentPane(newContentPane);

        //Display the window
        setVisible(true);
    }

    public List<PetriNetGraph> getSelectedGraphs()
    {
        return selectedGraphs;
    }

    private JPanel createSelectionWindow()
    {
        SpringLayout layout = new SpringLayout();
        JPanel window = new JPanel(layout);
        final List<JComboBox> boxList = new ArrayList<>();


        for (int i = 0; i < requiredGraphs; i++)
        {
            JComboBox box = new JComboBox();
            
            for (PetriNetGraph graph : editor.getOpenedGraphs())
            {
                if(graph != editor.getSelectedGraph())
                {
                    box.addItem(graph);
                }
            }
            
            JLabel label = new JLabel("Graph " + (i));
            label.setLabelFor(box);

            boxList.add(box);
            window.add(label);
            window.add(box);
        }



        //Lay out the panel.
        SpringUtilities.makeCompactGrid(window,
                requiredGraphs, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);       //xPad, yPad

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedGraphs.clear();
                
                // The selected graph is always the n0
                selectedGraphs.add(editor.getSelectedGraph());
                
                for(JComboBox box : boxList)
                {
                    selectedGraphs.add((PetriNetGraph) box.getSelectedItem()); 
                }
                
                if(selectedGraphs.size() == requiredGraphs + 1)
                {
                    setVisible(false);
                }
            }
        });


        JPanel mainWindow = new JPanel(new BorderLayout());
        mainWindow.add(window, BorderLayout.PAGE_START);
        mainWindow.add(selectButton, BorderLayout.PAGE_END);

        return mainWindow;
    }
    
    /**
     * create esxtendedGraphs for Alternation and MutualExcusion Operations
     * @throws Exception 
     */
    void setExtendedGraph() throws Exception{
        List<PetriNetGraph> inputNet = new ArrayList<>();
            inputNet.add(getSelectedGraphs().get(0));
            
            List<PetriNetGraph> inputNet2 = new ArrayList<>();
            inputNet2.add(getSelectedGraphs().get(1));
            
            IterationOperation extendedGraph1 = new IterationOperation("", inputNet);
            IterationOperation extendedGraph2 = new IterationOperation("", inputNet2);
    
            inputNetAlternation = new ArrayList<>();
            inputNetAlternation.add(extendedGraph1.getOperationGraph());
            inputNetAlternation.add(extendedGraph2.getOperationGraph());
            
            
            inputExtendedNetAlternation = new ArrayList<>();
            inputExtendedNetAlternation.add(extendedGraph1);
            inputExtendedNetAlternation.add(extendedGraph2);
    }
    
    List<PetriNetGraph> getInputGraphs(){
        return inputNetAlternation;
    }
    List<IterationOperation> getExtendedSelectedGraphs(){
        return inputExtendedNetAlternation;
    }
}