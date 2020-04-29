package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.sun.org.apache.bcel.internal.classfile.Node;

public class BTree <K extends Comparable<K>, V> implements IBTree<K, V>{

	private IBTreeNode<K,V> root,parent;
	private int mindegree;
    private	boolean isdelete=true;
	private V val; private K ke;
	public BTree(int MinimunDegree) {
		if(MinimunDegree < 2 ) throw new RuntimeErrorException(null);
			root =parent=null;
		mindegree= MinimunDegree;
	}
	
	@Override
	public int getMinimumDegree() {
		return mindegree;
	}

	@Override
	public IBTreeNode getRoot() {
		return root;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void insert(K key, V value) { 
		if(key == null || value == null) throw new RuntimeErrorException(null);
		if(root == null) {
			IBTreeNode node = new BTreeNode();
			root =node;
		   node.getKeys().add(key);
		   node.getValues().add(value); 
	//	   node.setNumOfKeys(root.getNumOfKeys() +1);
			return ;
		}
		if(search(key) != null  ) return ;		
		IBTreeNode<K, V> node = root; parent =null;
		
	 	if(node.getNumOfKeys() == 2 * mindegree - 1) {	
	 		IBTreeNode<K, V> newNode = new BTreeNode();
	 		 newNode.setLeaf(node.isLeaf());
	 		 Split( node, newNode);
	 		 IBTreeNode<K, V> newRoot = new BTreeNode();
			 newRoot.getKeys().add(ke);newRoot.getValues().add(val);
			 newRoot.getChildren().add(node); newRoot.getChildren().add(newNode);
			 root = newRoot; root.setLeaf(false);
			 
	 	}		

	  add( root,key,value);
		
	}
	
	

	private void add(IBTreeNode<K, V> node, K key, V value) {
      int i=0;
 	 while( i<node.getNumOfKeys() &&  node.getKeys().get(i).compareTo(key) < 0 ) i++;
    	
 	if(i < node.getNumOfKeys() && node.getKeys().get(i).compareTo(key) == 0)  return ;
 	 if(node.isLeaf() ) {
 		node.getKeys().add(i, key);
		node.getValues().add(i, value);
	//	node.setNumOfKeys(node.getNumOfKeys() + 1);
		return;	 
 	 } //System.out.println( node.getNumOfKeys()+ "  "+ key);
 	 
 	if(node.getChildren().get(i).getNumOfKeys() == 2 * mindegree - 1) {
 		IBTreeNode<K, V> newNode = new BTreeNode();
		 newNode.setLeaf( node.getChildren().get(i).isLeaf());
		 Split( node.getChildren().get(i), newNode);
		  int j=0;
		 	 while( j<node.getNumOfKeys() &&  node.getKeys().get(j).compareTo(ke) < 0 ) j++;		    	
			node.getKeys().add(j, ke);
			node.getValues().add(j, val);
			node.getChildren().add(j+1,newNode);
	//		System.out.println(node.getChildren().size() + " ss"+ key);
		    add(node, key, value);
		    
 	} else
 		add( node.getChildren().get(i),key,value);
		
		
	}



	private void Split( IBTreeNode<K, V> node, IBTreeNode<K, V> newNode) {
		int i=0;
		 List keys,values,childrens;
			keys= new ArrayList<K>();
			values = new ArrayList<V>();
			childrens = new ArrayList<IBTreeNode>();
		 for(i=node.getNumOfKeys()/2 +1;i<node.getNumOfKeys();i++) {
			keys.add( node.getKeys().get(i) );
			values.add(node.getValues().get(i)) ;			 
         if(!node.isLeaf())	
	         childrens.add(node.getChildren().get(i));
		 }  
		 if(!node.isLeaf())	
	         childrens.add(node.getChildren().get(i));		  
		this.val=node.getValues().get(node.getNumOfKeys()/2); ke=node.getKeys().get(node.getNumOfKeys()/2);
		node.getKeys().remove(ke);  node.getValues().remove(val);
//	System.out.println(val +" val");
		newNode.setKeys(keys); newNode.setValues(values);
		newNode.setChildren(childrens);
		int len = node.getNumOfKeys()-newNode.getNumOfKeys();
		while(node.getNumOfKeys()> len ) {
			int size= node.getNumOfKeys();
			node.getKeys().remove(size-1); node.getValues().remove(size-1);
		}
		if(!node.isLeaf())	{
		while(node.getChildren().size() != node.getNumOfKeys() +1) {
		int size= node.getChildren().size();
			node.getChildren().remove(size-1);
		}
	}
  }

	

	@SuppressWarnings("unchecked")
	@Override
	public V search(K key) {
		if(key == null ) throw new RuntimeErrorException(null);
		return (V) searchNode(getRoot(), key);
	}

	private V searchNode(IBTreeNode<K, V> node, K key) {
     	if(node == null) return null;
	    int i=0; 
	    while (i<node.getNumOfKeys() && node.getKeys().get(i).compareTo( key ) < 0  ) i++;
	    if(i<node.getNumOfKeys() && key.compareTo( node.getKeys().get(i)) == 0 )  return   node.getValues().get(i);
	    if(node.isLeaf()) return null;
	    return searchNode ( node.getChildren().get(i) , key);
	}

	@Override
	public boolean delete(K key) {
	 if(key == null ) throw new RuntimeErrorException(null);
	 if(root == null ) return false ; 
	 
	  checkRemove(root,key);
	  
	  if(root.getNumOfKeys()==0) {
		  if(root.isLeaf())
			  root=null;
		  else  
			  root=root.getChildren().get(0);
	  }
		
		
		return isdelete;
	}
	
	private void checkRemove(IBTreeNode<K, V> node, K key) {// System.out.println("cheeck");
	int	index=getkey(node,key);
	if(index < node.getNumOfKeys() && node.getKeys().get(index).compareTo(key)==0 ) {
		if(node.isLeaf())
			deleteleaf(node,index);
		else 
			deleteInernal(node,index);
	}else {
		if(node.isLeaf()) {
			isdelete=false; 
			return ;
		}
        boolean last =  index==node.getNumOfKeys() ; 
      if(node.getChildren().get(index).getNumOfKeys() < mindegree )
    	  fill(node,index);
      if(last && index>node.getNumOfKeys())
    	  checkRemove(node.getChildren().get(index -1), key);
      else
    	  checkRemove(node.getChildren().get(index), key);
	}
		return ;
	}


	private void fill(IBTreeNode<K, V> node, int index) {// System.out.println("fill");
		if(index !=0 && node.getChildren().get(index-1).getNumOfKeys()>= mindegree )
			borrowleft(node,index);
		else if(index != node.getNumOfKeys() && node.getChildren().get(index+1).getNumOfKeys() >= mindegree )
			borrowright(node,index);
		else {
			if(index != node.getNumOfKeys())
				merge(node,index);
			else 
				merge(node,index-1);
		}
	}

	private void merge(IBTreeNode<K, V> node, int index) {
    IBTreeNode<K,V> children = node.getChildren().get(index);
    IBTreeNode<K,V> sibling = node.getChildren().get(index+1); 
	children.getKeys().add(node.getKeys().get(index));
	children.getValues().add(node.getValues().get(index));
	children.getKeys().addAll(sibling.getKeys());
	children.getValues().addAll(sibling.getValues());
	node.getKeys().remove(index);
	node.getValues().remove(index);
	node.getChildren().remove(index+1);
	if(!sibling.isLeaf()) {
		children.getChildren().addAll(sibling.getChildren());
	}
	
}

	private void borrowright(IBTreeNode<K, V> node, int index) {
		IBTreeNode<K, V> children = node.getChildren().get(index);
		IBTreeNode<K, V> sibling = node.getChildren().get(index+1);
    children.getKeys().add( node.getKeys().get(index));
    children.getValues().add( node.getValues().get(index));
    if(!children.isLeaf()) {
 	   children.getChildren().add( sibling.getChildren().get(0));
	       sibling.getChildren().remove(0);
  }
 	node.getKeys().remove(index);
	node.getValues().remove(index);
	
	node.getKeys().add(index, sibling.getKeys().get(0));
	node.getValues().add(index, sibling.getValues().get(0));
	sibling.getKeys().remove(0);
	sibling.getValues().remove(0);
		
	}

	private void borrowleft(IBTreeNode<K, V> node, int index) {
		IBTreeNode<K, V> children = node.getChildren().get(index);
		IBTreeNode<K, V> sibling = node.getChildren().get(index-1);
    children.getKeys().add(0, node.getKeys().get(index-1));
    children.getValues().add(0, node.getValues().get(index-1));
    int size=sibling.getNumOfKeys();
     if(!children.isLeaf()) {
    	   children.getChildren().add(0, sibling.getChildren().get(size));
	       sibling.getChildren().remove(size);
     }
    	node.getKeys().remove(index-1);
    	node.getValues().remove(index-1);
    	
    	node.getKeys().add(index-1, sibling.getKeys().get(size-1));
    	node.getValues().add(index-1, sibling.getValues().get(size-1));
		sibling.getKeys().remove(size-1);
		sibling.getValues().remove(size-1);
		
	}

	
	private void deleteleaf(IBTreeNode<K, V> node, int index) {
     node.getKeys().remove(index);
     node.getValues().remove(index);	
	}
	private void deleteInernal(IBTreeNode<K, V> node, int index) { //System.out.println("inernaa");
		if(node.getChildren().get(index).getNumOfKeys()>=mindegree ) {
			IBTreeNode<K, V> maxnode = Max(node,index);
			node.getKeys().set(index, maxnode.getKeys().get(maxnode.getNumOfKeys()-1));
			checkRemove(node.getChildren().get(index), maxnode.getKeys().get(maxnode.getNumOfKeys()-1) );
		}
		else if(node.getChildren().get(index+1).getNumOfKeys()>=mindegree) {
			IBTreeNode<K, V> minNode = Min(node,index);
			node.getKeys().set(index, minNode.getKeys().get(0));
			checkRemove(node.getChildren().get(index+1), minNode.getKeys().get(0));
		}
		else {
			K nodeK = node.getKeys().get(index);
			merge(node, index);
			checkRemove(node.getChildren().get(index), nodeK);
		}
	}
	
	
	private IBTreeNode<K, V> Min(IBTreeNode<K, V> node, int index) { //System.out.println("minn");
		IBTreeNode<K, V> minnode = node.getChildren().get(index+1);
		while(minnode.isLeaf() == false)
		minnode=minnode.getChildren().get(0);
		return minnode;
	}

	private IBTreeNode<K, V> Max(IBTreeNode<K, V> node, int index) { //System.out.println("maa");
		IBTreeNode<K, V> maxnode = node.getChildren().get(index);
		while(maxnode.isLeaf() == false)
			maxnode=maxnode.getChildren().get(maxnode.getNumOfKeys());
		return maxnode;
	}

	private int getkey(IBTreeNode<K, V> node, K key) {
	int i=0;
	while(i<node.getNumOfKeys() && node.getKeys().get(i).compareTo(key)<0 ) i++;
		return i;
	}

	public void inorder(IBTreeNode<K, V> node) {
		int i; 
		for (i = 0; i < node.getNumOfKeys(); i++) 
		{ 

			if (!node.isLeaf()) 
				inorder(node.getChildren().get(i));
			System.out.println(node.getKeys().get(i)+ "  " + getHeight(node)  ) ;
		} 
		if (!node.isLeaf()) 
			inorder(node.getChildren().get(i));
	  
	}
	
//	private void traverseTreeInorder(IBTreeNode<Integer, String> node, List<Integer> keys, List<String> vals) {
//		int i; 
//		for (i = 0; i < node.getNumOfKeys(); i++) 
//		{ 
//
//			if (!node.isLeaf()) 
//				traverseTreeInorder(node.getChildren().get(i), keys, vals);
//			keys.add(node.getKeys().get(i));
//			vals.add(node.getValues().get(i));
//		} 
//		if (!node.isLeaf()) 
//			traverseTreeInorder(node.getChildren().get(i), keys, vals);
//	}
	
	 int getHeight (IBTreeNode<?, ?> node) {
		if (node.isLeaf()) return 0;

		return node.getNumOfKeys() > 0 ? 1 + getHeight(node.getChildren().get(0)) : 0;
	}

}
