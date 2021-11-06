import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private int value;

    public Order(UUID id, int value) {
        this.id = id;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return value == order.value && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
