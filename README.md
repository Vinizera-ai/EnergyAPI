# Energy API

**Energy API for Hytale**

Energy API is a developer-friendly energy management API designed for Hytale server-side modding. It provides a standardized foundation for power generation, storage, and consumption ensuring interoperability between different industrial and tech mods.

## Features

- **Standardized Energy System**: Defines core interfaces (`EnergyStorage`, `EnergyProducer`, `EnergyConsumer`) to ensure all tech mods speak the same language.
- **Transactional Consistency**: Built on the Hytale Transfer API (`Transaction`, `TransactionContext`) to support atomic operations. Simulate, validate, and commit energy transfers safely.
- **Utility Helpers**: `EnergyStorageUtil` provides robust methods for moving energy, finding extractable power, and handling simulations.
- **Base Implementations**: Includes ready-to-use components like `SingleBufferEnergyStorage` and `BaseEnergyProducer` to fast-track development.
- **Debug & Testing**: Contains `InfiniteEnergyStorage` and other tools to simplify testing in creative environments.

## Usage

### Creating an Energy Storage
For a simple battery or machine buffer, use `SingleBufferEnergyStorage`. This handles the basics of storing energy with a fixed capacity.

```java
import com.doctorreborn.hytale.api.energy.v1.base.SingleBufferEnergyStorage;

// Create a storage with 10,000 capacity
// Note: This implementation has infinite insertion/extraction speed by default.
SingleBufferEnergyStorage battery = new SingleBufferEnergyStorage(10000);
```

### Implementing a Producer
Extend `BaseEnergyProducer` to create a custom power source (e.g., a Solar Panel).

```java
import com.doctorreborn.hytale.api.energy.v1.base.BaseEnergyProducer;

public class SolarPanel extends BaseEnergyProducer {
    public SolarPanel() {
        // Define energy production per tick (e.g., 10 units)
        super(10); 
    }
    
    // Additional logic for when the panel is active/inactive can be added here
}
```

### Moving Energy
Use `EnergyStorageUtil` to safely transfer power between two storages. The `move` method handles the transaction logic for you, ensuring that energy isn't lost or duplicated if the transfer fails.

```java
import com.doctorreborn.hytale.api.energy.v1.EnergyStorageUtil;
import com.shailist.hytale.api.transfer.v1.transaction.TransactionContext;

// ... inside your tick or event loop

// Get current transaction or open a new one
TransactionContext tx = TransactionContext.current(); 

// Transfer up to 100 units from generator to battery
long transferred = EnergyStorageUtil.move(generator, battery, () -> true, 100, tx);
```

## License

This project is licensed under the **LGPL-3.0** (GNU Lesser General Public License v3.0).
See the [LICENSE](LICENSE) file for details.
