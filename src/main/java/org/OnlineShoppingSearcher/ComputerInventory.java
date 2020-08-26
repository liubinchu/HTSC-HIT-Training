package org.OnlineShoppingSearcher;

import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author liubi
 * @date 2020-08-26 17:28
 **/
public class ComputerInventory {
    private LinkedList<Computer> inventoryList = new LinkedList<>();

    // SRP
    public boolean addComputer(String serialNumber, double price, ComputerSpec computerSpec) {
        Computer c = new Computer(serialNumber, price, computerSpec);
        return this.inventoryList.add(c);
    }

    // SRP
    public Computer getComputer(String serialNumber) {
        for (Computer c : this.inventoryList) {
            if (c.getSerialNumber().equals(serialNumber)) {
                return c;
            }
        }
        return null;
    }

    // SRP
    // search 与 mathch 的职责分离
    public List<Computer> search(ComputerSpec computerSpec) {
        List<Computer> res = new LinkedList<>();
        for (Computer c : this.inventoryList) {
            // SRP
            if (c.getComputerSpec().matches(computerSpec)) {
                res.add(c);
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
