import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DispatcherImpl implements Dispatcher {
    private static Dispatcher INSTANCE;

    private final Queue<Taxi> taxis = new ConcurrentLinkedQueue();

    public static Dispatcher getInstance() {
        if (Objects.isNull(INSTANCE)) {
            synchronized (DispatcherImpl.class) {
                if (Objects.isNull(INSTANCE)) {
                    INSTANCE = new DispatcherImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void notifyAvailable(Taxi taxi) {
        taxis.offer(taxi);
    }

    @Override
    public void run() {
        new Thread(() -> {
            System.out.printf("Dispatcher started working in thread %s \n", Thread.currentThread().getName());
            while (true) {
                while (Objects.isNull(taxis.peek())) {}
                taxis.poll().placeOrder(constructOrder());
            }
        }).start();
    }

    private Order constructOrder() {
        return new Order(UUID.randomUUID(), new Random().nextInt());
    }
}
