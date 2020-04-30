package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.w3c.dom.*;
//import org.xml.sax.SAXException;
//
//import com.sun.org.apache.xpath.internal.operations.String;
//
//import javax.xml.parsers.*;
//import java.io.*;
public class testing {

	public static void main(String[] args)   {
		
		
		ISearchEngine searchEngine = new SearchEngine(10);
	//	en.indexWebPage("res\\wiki_00");
	//	en.indexDirectory("res");
		searchEngine.indexDirectory("res");
//		searchEngine.deleteWebPage("res\\wiki_00");
		

		
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder=null;
//		try {
//			builder = factory.newDocumentBuilder();
//		} catch (ParserConfigurationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		 
//		//Build Document
//		Document document =null;
//		try {
//			document = builder.parse(new File("wiki_00"));
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//		//Normalize the XML Structure; It's just too important !!
//		document.getDocumentElement().normalize();
//		 
//		//Here comes the root node
//		Element root = document.getDocumentElement();
//		System.out.println(root.getNodeName());
//		 
//		//Get all employees
//		NodeList nList = document.getElementsByTagName("doc");
//		System.out.println("============================" + nList.getLength());
//		 
//		for (int temp = 0; temp < 2; temp++)
//		{
//		 Node node = nList.item(temp);
//		 System.out.println("");    //Just a separator
//		 if (node.getNodeType() == Node.ELEMENT_NODE)
//		 {
//		    //Print each employee's detail
//		    Element eElement = (Element) node;
//		    System.out.println("Employee id : "    + eElement.getAttribute("id"));
//		    System.out.println(eElement.getTextContent() );
//	//	    System.out.println("Last Name : "   + eElement.getElementsByTagName("lastName").item(0).getTextContent());
//	//	    System.out.println("Location : "    + eElement.getElementsByTagName("location").item(0).getTextContent());
//		 }
//		}
		
		
		
		
		
		
//		BTree btree = new BTree(3);
//		List<Integer> inp = Arrays.asList(new Integer[]{1, 3, 7, 10, 11, 13, 14, 15, 18, 16, 19, 24, 25, 26, 21, 4, 5, 20, 22, 2, 17, 12, 6});
////	 System.out.println(inp.size() + " ssad");
//		for (int i : inp) {
//			btree.insert(i, "Soso" + i);
//	//		System.out.println(btree.search(key));
//		}
//            btree.inorder(btree.getRoot());
//            
//		tree.insert(4, 4);
//		tree.insert(8, 8);
//		tree.insert(0,0);
//		tree.insert(12, 12);
//		tree.insert(3, 3);
//		tree.insert(10, 10);
//		tree.insert(16, 16);
//		tree.insert(14, 14);
//		tree.insert(6, 6);
//		tree.insert(11, 11);
//        tree.insert(13, 13);		
    //    tree.inorder(btree.getRoot());
//	System.out.println(	tree.search(16) );
    //    tree.inorder(tree.getRoot());
        
		ArrayList<Integer> arr= new ArrayList<Integer>();
		arr.add(2);arr.add(4);arr.add(6);
		arr.add(2, 5);
	//	System.out.println(arr.get(3));
	}

}
