package eg.edu.alexu.csd.filestructure.btree;

public class SearchResult implements ISearchResult {

	private int Rank;
	private String ID;
	
	public SearchResult(String ID,int Rank) {
		this.ID=ID;
		this.Rank=Rank;
	}
	
	@Override
	public String getId() {
		return ID;
	}

	@Override
	public void setId(String id) {
       this.ID=id;		
	}

	@Override
	public int getRank() {
		return Rank;
	}

	@Override
	public void setRank(int rank) {
      this.Rank=rank;		
	}

}
