/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame.concurrency;

import com.relaxgaming.diceGame.statistics.StatisticsCalculator;
import com.relaxgaming.diceGame.task.DiceTask;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConcurrencyImpl implements Concurrency {

  private StatisticsCalculator calculator;

  public void useSequential(Map<String, List<? extends DiceTask>> diceTaskMap) {
    System.out.printf("##############method name: %s \n", Thread.currentThread().getStackTrace()[1]);
    diceTaskMap.forEach((name, task) -> {
      Instant startTime = Instant.now();
      int result = (int) task.stream().map(DiceTask::playGame).filter(e -> e).count();
      System.out.printf("total winner of %s game is %d \n", name, result);
      calculator = new StatisticsCalculator();
      calculator.Calc(result);
      Instant endTime = Instant.now();
      printDuration(name, startTime, endTime);
    });
  }

  public void useParallelStream(Map<String, List<? extends DiceTask>> diceTaskList) {
    System.out.printf("##############method name: %s \n", Thread.currentThread().getStackTrace()[1]);
    diceTaskList.forEach((name, task) -> {
      Instant startTime = Instant.now();
      int result = (int) task.parallelStream().map(DiceTask::playGame).filter(e -> e).count();
      System.out.printf("total winner of '%s' game is %d \n", name, result);
      calculator = new StatisticsCalculator();
      calculator.Calc(result);
      Instant endTime = Instant.now();
      printDuration(name, startTime, endTime);
    });
  }

  public void useCompletableFuture(Map<String, List<? extends DiceTask>> diceTaskList) {
    System.out.printf("##############method name: %s \n", Thread.currentThread().getStackTrace()[1]);

    diceTaskList.forEach((name, task) -> {
      Instant startTime = Instant.now();
      List<CompletableFuture<Boolean>> futures = task.stream()
          .map(tmpTask -> CompletableFuture.supplyAsync(() -> tmpTask.playGame()))
          .collect(Collectors.toList());

      getTaskResult(name, startTime, futures);
    });
  }

  public void useCompletableFutureWithExecutor(Map<String, List<? extends DiceTask>> diceTaskList) {
    System.out.printf("##############method name: %s \n", Thread.currentThread().getStackTrace()[1]);
    final int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors() + 1;
    System.out.println("NUM_OF_THREADS = " + NUM_OF_THREADS);

    AtomicInteger i = new AtomicInteger();
    diceTaskList.forEach((name, task) -> {
      i.getAndIncrement();

      Instant startTime = Instant.now();
      ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREADS);

      List<CompletableFuture<Boolean>> futures = task.stream()
          .map(tmpTask -> CompletableFuture.supplyAsync(() -> tmpTask.playGame(), executor))
          .collect(Collectors.toList());

      getTaskResult(name, startTime, futures);

      if (i.get() == diceTaskList.size()) {
        executor.shutdown();
      }

    });
  }

  private void getTaskResult(String name, Instant startTime, List<CompletableFuture<Boolean>> futures) {
    int result = (int) futures.stream().map(CompletableFuture::join).filter(e -> e).count();
    System.out.printf("total winner of '%s' game is %d \n", name, result);
    calculator = new StatisticsCalculator();
    calculator.Calc(result);
    Instant endTime = Instant.now();
    printDuration(name, startTime, endTime);
  }

  private void printDuration(String name, Instant start, Instant end) {
    Duration timeElapsed = Duration.between(start, end);
    System.out.printf("Processed %s task in %d milliseconds\n", name, timeElapsed.toMillis());
  }
}
