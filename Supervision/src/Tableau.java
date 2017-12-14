

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import org.eclipse.swt.widgets.MessageBox;

import javax.swing.JOptionPane;

//CTRL + SHIFT + O pour générer les imports

public class Tableau extends JFrame {
	
  private JTable tableau;
  private JButton Identification = new JButton("Identification");
  private JButton Supprimer = new JButton("Supprimer");

  public Tableau(){
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Supervision");
    this.setSize(450, 300);

    Object[][] data = {
      {"", "", "", "", ""},
      {"", "", "", "", ""},
      {"", "", "", "", ""},
      {"", "", "", "", ""},
      {"", "", "", "", ""},
      {"", "", "", "", ""}
    };

    String  title[] = {"Nom", "Performance", "Memoire", "Disque", "Reseau"};
    this.tableau = new JTable(data, title);

    JPanel pan = new JPanel();

    //Gestion du Popup
    Identification.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {     
    	Popup popup = new Popup ();
    	Hashtable<String, String> result = popup.login(tableau);
    	String toto1 = result.get("user");
    	String toto2 = result.get("pass");
    	String titi1 = "admin";
    	String titi2 = "admin";
    	String titi3 = "user";
    	String titi4 = "user";

    	//permet de valider l'ecriture de l'id et du password avec l'affichage du popup
    	/*if (toto1.equals(titi1) && toto2.equals(titi2))
    	{
    		javax.swing.JOptionPane.showMessageDialog(null,"Oooouuuiiiiiii !!!");
    	}
    	
    	if (toto1.equals(titi3) && toto2.equals(titi4))
    	{
    		javax.swing.JOptionPane.showMessageDialog(null,"Nnnnnaaaaaaannnnn !!!");
    	}
    	
    	if (toto1 != titi1 || toto1 != titi3 && toto2 != titi2 || toto2 != titi4)
    	{
    		javax.swing.JOptionPane.showMessageDialog(null,"Error");
    	}*/
    	
        Identification.setEnabled(false);
        Supprimer.setEnabled(true);
      }  
      
    });
    
    Supprimer.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {

        IdentificationSize(75, 16);
        Identification.setEnabled(true);
        Supprimer.setEnabled(false);
      }         
    });

    Supprimer.setEnabled(false);
    pan.add(Identification);
    pan.add(Supprimer);

    this.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
    this.getContentPane().add(pan, BorderLayout.SOUTH);
  }
  
  /**
   * Identification la taille d'une ligne et d'une colonne
   * J'ai mis deux boucles afin que vous puissiez voir 
   * comment parcourir les colonnes et les lignes
   */
  public void IdentificationSize(int width, int height){
    //Nous créons un objet TableColumn afin de travailler sur notre colonne
    TableColumn col;
    for(int i = 0; i < tableau.getColumnCount(); i++){
      if(i == 1){
        //On récupère le modèle de la colonne
        col = tableau.getColumnModel().getColumn(i);
        //On lui affecte la nouvelle valeur
        col.setPreferredWidth(width);
      }
    }            
    for(int i = 0; i < tableau.getRowCount(); i++){
      //On affecte la taille de la ligne à l'indice spécifié !
      if(i == 1)
        tableau.setRowHeight(i, height);
      }
    }
   
  public static void main(String[] args){
	  Tableau fen = new Tableau();
    fen.setVisible(true);
  }   
}