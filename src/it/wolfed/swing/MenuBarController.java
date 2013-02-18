
package it.wolfed.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarController extends JMenuBar
{   
    public MenuBarController(final GraphEditor editor)
    {
        // File
        {
            JMenu file = new JMenu("File");
            file.setMnemonic('F');
            add(file);

            // New
            JMenuItem newItem = new JMenuItem("New");
            newItem.setMnemonic('N');
            newItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.newFile();
                }
            });
            
            file.add(newItem);

            // Open
            JMenuItem openItem = new JMenuItem("Open");
            openItem.setMnemonic('O');
            openItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.openFile();
                }
            });
            
            file.add(openItem);

            // Open
            JMenuItem saveItem = new JMenuItem("Save");
            saveItem.setMnemonic('v');
            file.add(saveItem);	

            // Exit
            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.setMnemonic('x');
            exitItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.dispose();
                }
            });
            file.add(exitItem);
        }
            
        // Operations
        {
            JMenu operationMenu = new JMenu("Operations");
            operationMenu.setMnemonic('p');
            add(operationMenu);
            
            String[] operations = {
                "BasicWorkFlow",
                "Merge",
                "Alternation",
                "DefferedChoice",
                "ExplicitChoice",
                "Iteration",
                "MutualExclusion",
                "OneOrMoreIteration",
                "OneServePerTime",
                "Parallelism",
                "Selection",
                "Sequencing",
                "ZeroOrMoreIteration",
                "BasicWorkFlow",
            };
            
            for(String operation : operations)
            {
                JMenuItem operationItem = new JMenuItem(operation);
                operationItem.addActionListener(new ActionListener() 
                {  
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        editor.executeOperation(e.getActionCommand());
                    }
                });
                
                operationMenu.add(operationItem);
            }
        }
            
        // Layout
        {
            JMenu layoutMenu = new JMenu("Layouts");
            layoutMenu.setMnemonic('l');
            add(layoutMenu);
            
            String[] layouts = {
                //"VerticalTree",
                "HorizontalTree",
                //"Organic",
                //"Circle",
                "Hierarchical",
                //"EdgeLabel",
                //"Orthogonal",
                //"ParallelEdge",
                //"Partition",
                //"Stack"
            };

            for(String layout : layouts)
            {
                JMenuItem layoutItem = new JMenuItem(layout);
                layoutItem.addActionListener(new ActionListener() 
                {  
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        editor.applyLayout(e.getActionCommand());
                    }
                });
                
                layoutMenu.add(layoutItem);
            }
        }
        
        // About
        {
            JMenu aboutMenu = new JMenu("About");
            aboutMenu.setMnemonic('a');
            add(aboutMenu); 
        }
    }
}
