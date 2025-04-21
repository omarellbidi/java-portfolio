package composite;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.util.Optional;

/**
 * Manages items and implements functionalities like XML parsing, price retrieval, and item removal.
 */
public class ItemManager {
    private ItemList root;

    public ItemManager() {
        root = null;
    }

    
      //Reads and parses the XML data, building the item hierarchy
     
    public void readXml(InputStream xmlData) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlData);

        Element rootElement = doc.getDocumentElement();
        if (!rootElement.getTagName().equals("list")) {
            throw new IllegalArgumentException("Invalid root element in XML");
        }

        String rootName = rootElement.getAttribute("name");
        root = new ItemList(rootName);
        parseList(rootElement, root);
    }

    /**
     * Helper method to recursively parse a list element and populate the item hierarchy
     */
    private void parseList(Element listElement, ItemList parentList) {
        NodeList children = listElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String tagName = element.getTagName();
                String name = element.getAttribute("name");

                double price = 0.0;
                if (element.hasAttribute("price")) {
                    try {
                        price = Double.parseDouble(element.getAttribute("price"));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid price value for item: " + name);
                    }
                }

                switch (tagName) {
                    case "book":
                        String isbn = element.getAttribute("isbn");
                        parentList.addItem(new Book(name, price, isbn));
                        break;
                    case "cd":
                        parentList.addItem(new CD(name, price));
                        break;
                    case "list":
                        ItemList newList = new ItemList(name);
                        parentList.addItem(newList);
                        parseList(element, newList);
                        break;
                }
            }
        }
    }

    
      // Retrieves the price of an item or list by name
     
    public Optional<Double> getPrice(String item) {
        if (root == null) return Optional.empty();
        Item foundItem = root.findItem(item);
        return foundItem != null ? Optional.of(foundItem.getPrice()) : Optional.empty();
    }

    
     // Removes an item by name, unless it is the root

    public boolean removeItem(String item) {
        if (root == null || root.getName().equals(item)) {
            return false; // Cannot remove the root
        }
        return root.removeItem(item);
     }

    
     //Changes the price of an individual item (not a list)
    
    public boolean changePrice(String item, double price) {
        if (price <= 0) {
            return false;
        }
        Item foundItem = root.findItem(item);
        if (foundItem != null && !foundItem.isComposite()) {
            foundItem.setPrice(price);
            return true;
        }
        return false;
    }
}
