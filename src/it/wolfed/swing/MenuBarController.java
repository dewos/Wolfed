
package it.wolfed.swing;

import it.wolfed.util.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarController extends JMenuBar
{
    /**
     * Available operations (in menu).
     */
    private String[] operations =
    {
        Constants.OPERATION_ALTERNATION,
        Constants.OPERATION_CLONEGRAPH,
        Constants.OPERATION_DEFFEREDCHOICE,
        Constants.OPERATION_EXPLICITCHOICE,
        Constants.OPERATION_FULLMERGE,
        Constants.OPERATION_ITERATIONONEORMORE,
        Constants.OPERATION_ITERATIONONESERVEPERTIME,
        Constants.OPERATION_ITERATIONZEROORMORE,
        Constants.OPERATION_MUTUALEXCLUSION,
        Constants.OPERATION_PARALLELISM,
        Constants.OPERATION_SEQUENCING
    };
    
    /**
     * Available layouts (in menu).
     */
    private String[] layouts =
    {
        Constants.LAYOUT_VERTICALTREE,
        Constants.LAYOUT_HIERARCHICAL,
        //Constants.LAYOUT_ORGANIC,
    };
    
    
    public MenuBarController(final WolfedEditor editor)
    {
        // File
        {
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic('f');
            add(fileMenu);

            // New
            JMenuItem newItem = new JMenuItem("New");
            newItem.setMnemonic('n');
            newItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.newFile();
                }
            });
            
            fileMenu.add(newItem);

            // Open
            JMenuItem openItem = new JMenuItem("Open");
            openItem.setMnemonic('p');
            openItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.openFile();
                }
            });
            
            fileMenu.add(openItem);

            // Save Pnml
            JMenuItem savePnmlItem = new JMenuItem("Save PNML");
            savePnmlItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.saveFile(Constants.EDITOR_EXPORT_PNML);
                }
            });
            fileMenu.add(savePnmlItem);
            
            // Save Dot
            JMenuItem saveDotItem = new JMenuItem("Save DOT");
            saveDotItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.saveFile(Constants.EDITOR_EXPORT_DOT);
                }
            });
            fileMenu.add(saveDotItem);	

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
            fileMenu.add(exitItem);
        }
            
        // Operations
        {
            JMenu operationMenu = new JMenu("Operations");
            operationMenu.setMnemonic('o');
            add(operationMenu);
            
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

            for(String layout : layouts)
            {
                JMenuItem layoutItem = new JMenuItem(layout);
                layoutItem.addActionListener(new ActionListener() 
                {  
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        editor.executeLayout(null, e.getActionCommand());
                    }
                });
                
                layoutMenu.add(layoutItem);
            }
        }
        
        // Help
        {
            JMenu helpMenu = new JMenu("Help");
            helpMenu.setMnemonic('h');
            JMenuItem aboutItem = new JMenuItem("About");
            aboutItem.addActionListener(new ActionListener() 
            {  
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    editor.showAbout();
                }
            });

            helpMenu.add(aboutItem);
            
            add(helpMenu); 
        }
    }
}
