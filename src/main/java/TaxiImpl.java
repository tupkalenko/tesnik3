import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaxiImpl implements Taxi {

    private static final int MIN_RUN_TIME = 1 * 1_000;
    private static final int MAX_RUN_TIME = 5 * 1_000;

    private final Queue<Order> orders = new ConcurrentLinkedQueue<>();
    private final Random random = new Random();

    private final Dispatcher dispatcher;
    private final int taxiId;

    public TaxiImpl(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.taxiId = random.nextInt(100);
    }

    @Override
    public void run() {
        new Thread(() -> {
            System.out.printf("Taxi [%s] started working in thread %s \n",
                    taxiId, Thread.currentThread().getName());
            while (true) {
                while (Objects.isNull(orders.peek())) {}
                Order order = orders.poll();
                System.out.printf("Taxi [%s] executing order [%s] in thread [%s] \n",
                        taxiId, order.getId(), Thread.currentThread().getName());
                try {
                    Thread.sleep(random.nextInt(MAX_RUN_TIME) + MIN_RUN_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                } finally {
                    System.out.printf("Taxi [%s] finished processing order [%s] \n", taxiId, order.getId());
                    dispatcher.notifyAvailable(this);
                }
            }
        }).start();
    }

    @Override
    public void placeOrder(Order order) {
        orders.offer(order);
    }

    @Override
    public List<Order> getExecutedOrders() {
        return new ArrayList<>(orders);
    }
}
