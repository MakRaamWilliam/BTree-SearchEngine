package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.*;
import java.io.*;

import com.sun.xml.internal.txw2.Document;

public class SearchEngine implements ISearchEngine {

	private IBTree<String, ResultSearch > btree;
	
	public SearchEngine(int mindegree) {
		btree = new BTree<String, ResultSearch>(mindegree);
	}
	
	@Override
	public void indexWebPage(String filePath) {
      if(filePath == null || filePath == "" || !new File(filePath).exists() )  {
    	       throw new RuntimeErrorException(null);
      }
		
        org.w3c.dom.Document document = getdoc(filePath);
		 
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nList = document.getElementsByTagName("doc");		 
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
		 Node node = nList.item(temp);
		 if (node.getNodeType() == Node.ELEMENT_NODE)
		 {
		       Element eElement = (Element) node;
	        String ID =  eElement.getAttribute("id");
	        String content = eElement.getTextContent().toLowerCase();
	        ResultSearch result ;
            String words []= content.split("\\W+");
            for(String word : words) {
            	word =word.trim();
            	if(word.length() == 0) continue;

            	if(btree.search(word)==null ) {
            		result = new ResultSearch();
            		btree.insert(word, result);
            	}else {
            		result=btree.search(word);
            	}
            	result.addID(ID);
            }
	         
		 } 
		}
	}
	
	private org.w3c.dom.Document getdoc(String path) {
		   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=null;
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			}
			 
			//Build Document
			org.w3c.dom.Document document=null ;
			try {
				document = builder.parse(new File(path));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return document;
	}
	
	
	
		

	@Override
	public void indexDirectory(String directoryPath) {
	      if(directoryPath == null || directoryPath == "" || !new File(directoryPath).exists() )  throw new RuntimeErrorException(null);
	          
	      File folder = new File(directoryPath);
	      File[] listOfFiles = folder.listFiles();
          ArrayList<String> names = new ArrayList<String>();
	      for (File file : listOfFiles) {
	          if (file.isFile()) {
	        	  names.add(directoryPath+"//"+file.getName());
	          }else if(file.isDirectory()) { String dic = directoryPath+"//"+ file.getName() +"//";
	        	  getnames(names,file,dic);
	          }
	      }
	      for(String na : names)
	    	 indexWebPage(na);

	}

	private void getnames(ArrayList<String> names, File file,String dic) {
		  for (File subfile : file.listFiles()) {
	          if (subfile.isFile()) {
	        	  names.add(dic+ subfile.getName());
	          }else if(subfile.isDirectory()) { 
	        	  getnames(names,subfile,dic+subfile.getName()+"//");
	          }
	      }
		
	}

	@Override
	public void deleteWebPage(String filePath) {
	      if(filePath == null || filePath == "" || !new File(filePath).exists() )  throw new RuntimeErrorException(null);

		org.w3c.dom.Document document = getdoc(filePath);
			document.getDocumentElement().normalize();
			Element root = document.getDocumentElement();
			NodeList nList = document.getElementsByTagName("doc");		 
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
			 Node node = nList.item(temp);
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
			       Element eElement = (Element) node;
		        String ID =  eElement.getAttribute("id");
		        String content = eElement.getTextContent().toLowerCase();
		        ResultSearch result ;
	            String words []= content.split("\\W+");
	            for(String word : words) {
	            	word =word.trim();
	            	if(word.length() == 0) continue;

	            	if(btree.search(word)==null ) {
	            		continue;
	            	}else {
	            		result=btree.search(word);
	            	}
	            	  result.deleteID(ID);
	            	if(result.getArr() != null && result.getArr().size() == 0)
	            		btree.delete(word);
	            }
		         
			 } 
			}
		
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
      if(word == null ) throw new RuntimeErrorException(null);
      word = word.toLowerCase().trim();
      ResultSearch result;
      result = btree.search(word);
      if(result == null) return new ResultSearch().getArr() ;
  	Collections.sort(result.getArr(), new Comparator<ISearchResult>() {
		@Override
		public int compare(ISearchResult o1, ISearchResult o2) {
			return o1.getRank() - o2.getRank();
		}
	});
      
      
		return result.getArr();
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
	if(sentence == null ) throw new RuntimeErrorException(null);
	List<ISearchResult> op = new ArrayList<ISearchResult>();
	sentence =  sentence.trim().toLowerCase();
	 String words []=sentence.split("\\W+"); 
	 if(words.length ==0) return op;
	 op = searchByWordWithRanking(words[0]);
	 for(int i=1;i<words.length;i++) {
		 List<ISearchResult> temp = new ArrayList<ISearchResult>();
		 List<ISearchResult> tempword = searchByWordWithRanking(words[i]);
		 for(ISearchResult l1:op ) {
			 for(ISearchResult l2:tempword ) {
				 if(l1.getId().equals(l2.getId())) {
					 temp.add(new SearchResult(l1.getId(), Math.min(l1.getRank(), l2.getRank())));
				 }
			 }
		 }
    op=temp;		 
	 }
	  	Collections.sort(op, new Comparator<ISearchResult>() {
			@Override
			public int compare(ISearchResult o1, ISearchResult o2) {
				return o1.getRank() - o2.getRank();
			}
		});
		
		return op;
	}

}
