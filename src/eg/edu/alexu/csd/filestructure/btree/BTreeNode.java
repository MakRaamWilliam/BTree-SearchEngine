package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V> {

	private int num;
	boolean leaf;
//	private IBTreeNode parent;
	private List keys,values,children;
	
	@SuppressWarnings("rawtypes")
	public BTreeNode() {
		num=0;
		leaf=true;
		keys= new ArrayList<K>();
		values = new ArrayList<V>();
		children = new ArrayList<IBTreeNode>();
	}
	
	@Override
	public int getNumOfKeys() {		
		return keys.size();
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		num=numOfKeys;
		
	}

	@Override
	public boolean isLeaf() {
		return leaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
      leaf=isLeaf;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<K> getKeys() {
		return keys;
	}

	@Override
	public void setKeys(List<K> keys) {
     this.keys=keys;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> getValues() {
		return values;
	}

	@Override
	public void setValues(List<V> values) {
		this.values = values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
      this.children=children;		
	}

	

}
