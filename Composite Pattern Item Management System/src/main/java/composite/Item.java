package composite;

/**
 * Interface representing an item in the Composite pattern.
 * Can be a leaf (e.g., Book, CD) or a composite (e.g., ItemList).
 */
public interface Item {
    String getName();
    double getPrice();
    boolean isComposite();

     // Sets the price of the item. Default implementation throws UnsupportedOperationException for composites.
     
  default void setPrice(double price) {
        throw new UnsupportedOperationException("Price cannot be set for composite items.");
    }
}
