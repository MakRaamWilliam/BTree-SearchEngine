package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;

public class ResultSearch  {
  private ArrayList<ISearchResult> arr = new ArrayList<ISearchResult>();
  private boolean isfind;
   public void addID(String ID) {
	   isfind =false;
	   for(ISearchResult i :arr) {
		   if(i.getId().equals(ID)) {
			   i.setRank(i.getRank()+1);
			   isfind = true; break;
		   }
	   }
	   if(!isfind) {
		   arr.add(new SearchResult(ID, 1));
	   }
   }
   
   public void deleteID(String ID) {
	   isfind = false;
    ArrayList<ISearchResult> temp = new ArrayList<ISearchResult>();
	   for(ISearchResult i :arr) {
		   if(i.getId().equals(ID)) {
			  temp.add(i);
			   isfind = true; 
	   }
   }
	   arr.removeAll(temp);
  }
    public  ArrayList<ISearchResult> getArr  (){
    	return arr;
    }
   
 
   
}
