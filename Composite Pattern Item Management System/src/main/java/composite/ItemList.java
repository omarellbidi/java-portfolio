package composite;

import java.util.ArrayList;
import java.util.List;


public class ItemList implements Item {
    private String name;
    private List<Item> items;

    public ItemList(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    
      //Adds an item to the list.
    
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Recursively removes an item by name.
     * Returns true if the item was found and removed, false otherwise.
     */
    public boolean removeItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                items.remove(i);
                return true;
            }
            if (item.isComposite() && ((ItemList) item).removeItem(name)) {
                return true;
            }
        }
        return false; 
    }

    /**
     * Recursively finds an item by name.
     * Returns the found item or null if not found.
     */
    public Item findItem(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
            if (item.isComposite()) {
                Item found = ((ItemList) item).findItem(name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null; 
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    @Override
    public boolean isComposite() {
        return true;
    }
}
