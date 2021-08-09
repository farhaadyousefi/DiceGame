/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame;

import com.relaxgaming.diceGame.concurrency.Concurrency;
import com.relaxgaming.diceGame.concurrency.ConcurrencyImpl;
import com.relaxgaming.diceGame.task.DiceTask;
import com.relaxgaming.diceGame.task.DoubleSixesInTwentyFourRollsTask;
import com.relaxgaming.diceGame.task.OneSixInFourRollsTask;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiceGameApplicationTests {

  public static final int MAX_TASKS = 1000000;
  private final Concurrency concurrency;
  private final Map<String, List<? extends DiceTask>> input;

  public DiceGameApplicationTests() {
    List<OneSixInFourRollsTask> inFourRollsTasks = IntStream.range(0, MAX_TASKS)
        .mapToObj(i -> new OneSixInFourRollsTask()).collect(Collectors.toList());

    List<DoubleSixesInTwentyFourRollsTask> inTwentyFourRollsTasks = IntStream.range(0, MAX_TASKS)
        .mapToObj(i -> new DoubleSixesInTwentyFourRollsTask())
        .collect(Collectors.toList());

    concurrency = new ConcurrencyImpl();

    input = new HashMap<>();
    input.put(new OneSixInFourRollsTask().gameName(), inFourRollsTasks);
    input.put(new DoubleSixesInTwentyFourRollsTask().gameName(), inTwentyFourRollsTasks);
  }

  @Test
  @DisplayName("Test run all tasks by sequential stream")
  void test_use_sequential_stream_ok() {
    concurrency.useSequential(input);
  }

  @Test
  @DisplayName("Test run all tasks by Parallel stream")
  void test_use_Parallel_stream_ok() {
    concurrency.useParallelStream(input);
  }

  @Test
  @DisplayName("Test run all tasks by completable future")
  void test_use_completable_future_ok() {
    concurrency.useCompletableFuture(input);
  }

  @Test
  @DisplayName("Test run all tasks by completable future with executor")
  void test_use_completable_future_with_executor_ok() {
    concurrency.useCompletableFutureWithExecutor(input);
  }

}
