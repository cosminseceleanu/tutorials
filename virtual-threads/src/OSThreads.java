import java.util.stream.IntStream;

public class OSThreads {

  public static void main(String[] args) {
    var threads = IntStream.range(0, 100_000)
        .mapToObj(i -> {
          var t = makeThread(() -> looper(1));
          t.start();
          System.out.println(i + " thread started");
          return t;
        })
        .toList();

    // Join all the threads
    threads.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  public static Thread makeThread(Runnable task) {
    return new Thread(task);
  }

  private static void looper(int count) {
    var tid = Thread.currentThread().threadId();
    if (count > 1000) {
      return;
    }
    try {
      Thread.sleep(10);
      if (count % 100 == 0) {
        System.out.println("Thread id: " + tid + " : " + count);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    looper(count + 1);
  }
}
