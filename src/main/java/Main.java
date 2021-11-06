import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher = DispatcherImpl.getInstance();
        Taxi[] taxis = {
                new TaxiImpl(dispatcher),
                new TaxiImpl(dispatcher),
                new TaxiImpl(dispatcher),
        };

        dispatcher.run();

        Arrays.stream(taxis).forEach(it -> {
            it.run();
            dispatcher.notifyAvailable(it);
        });

        while (true) {}
    }
}
