import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;

public class VastParser {

    public static void parseVast(File file) {
        try {
           
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            
            doc.getDocumentElement().normalize();

            
            NodeList adList = doc.getElementsByTagName("Ad");

           
            for (int i = 0; i < adList.getLength(); i++) {
                Node adNode = adList.item(i);
                if (adNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element adElement = (Element) adNode;
                    String adId = adElement.getAttribute("id");
                    System.out.println("Ad ID: " + adId);

                   
                    Element adSystem = (Element) adElement.getElementsByTagName("AdSystem").item(0);
                    System.out.println("Ad System: " + (adSystem != null ? adSystem.getTextContent() : "Unknown"));

                    
                    Element impression = (Element) adElement.getElementsByTagName("Impression").item(0);
                    System.out.println("Impression URL: " + (impression != null ? impression.getTextContent() : "None"));

                    
                    NodeList creatives = adElement.getElementsByTagName("Creative");
                    for (int j = 0; j < creatives.getLength(); j++) {
                        Element creative = (Element) creatives.item(j);
                        Element linear = (Element) creative.getElementsByTagName("Linear").item(0);
                        if (linear != null) {
                            System.out.println("Creative Type: Linear");

                           
                            NodeList mediaFiles = linear.getElementsByTagName("MediaFile");
                            for (int k = 0; k < mediaFiles.getLength(); k++) {
                                Element mediaFile = (Element) mediaFiles.item(k);
                                System.out.println("MediaFile URL: " + mediaFile.getTextContent());
                            }
                        } else {
                            System.out.println("Creative Type: Non-Linear");
                        }
                    }
                    System.out.println("---------------------------------");
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error: Unable to parse VAST XML");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        File vastFile = new File("D:\\Class_Drive\\FSD_PRO\\java_program1\\READ_VAST_TAG\\src\\VAST.xml");

        if (vastFile.exists()) {
            parseVast(vastFile);
        } else {
            System.out.println("File does not exist: " + vastFile.getPath());
        }
    }
}
