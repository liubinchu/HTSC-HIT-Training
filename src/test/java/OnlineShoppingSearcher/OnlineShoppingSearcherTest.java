package OnlineShoppingSearcher;

import org.OnlineShoppingSearcher.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * @author liubi
 * @date 2020-08-26 19:47
 **/
public class OnlineShoppingSearcherTest {
    @Test
    public void searchTestSuccess() {
        ComputerInventory inventory = new ComputerInventory();

        ComputerSpec computerSpec = new ComputerSpec()
                .addProperty(Brand.class, Brand.Apple)
                .addProperty(PCType.class, PCType.laptop)
                .addProperty(PCClass.class, PCClass.Game_PC);

        inventory.addComputer(UUID.randomUUID().toString(), 32145.5, computerSpec);

        ComputerSpec searchGoal = new ComputerSpec()
                .addProperty(Brand.class, Brand.Apple)
                .addProperty(PCType.class, PCType.laptop);

        List<Computer> searchRes = inventory.search(searchGoal);
        //System.out.println(searchRes);
        Assert.assertTrue(!searchRes.isEmpty());
    }

    @Test
    public void searchTestFail() {
        ComputerInventory inventory = new ComputerInventory();

        ComputerSpec computerSpec = new ComputerSpec()
                .addProperty(Brand.class, Brand.Apple)
                .addProperty(PCType.class, PCType.laptop)
                .addProperty(PCClass.class, PCClass.Game_PC);

        inventory.addComputer(UUID.randomUUID().toString(), 32145.5, computerSpec);

        ComputerSpec searchGoal = new ComputerSpec()
                .addProperty(Brand.class, Brand.Lenovo)
                .addProperty(PCType.class, PCType.laptop);

        List<Computer> searchRes = inventory.search(searchGoal);
        //System.out.println(searchRes);
        Assert.assertTrue(searchRes.isEmpty());
    }
}
