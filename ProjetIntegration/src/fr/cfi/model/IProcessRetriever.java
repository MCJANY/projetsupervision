package fr.cfi.model;

import java.util.List;

public interface IProcessRetriever {

	public static final int COLONNE_NAME_INDEX=0;//
	public static final int COLONNE_NPM_INDEX=1;//
	public static final int COLONNE_PATH_INDEX=2;//
	public static final int COLONNE_PID_INDEX=3;//
	
	public List<String[]> execGetProcess() ;
	
	public String[] getColonnes();
}
