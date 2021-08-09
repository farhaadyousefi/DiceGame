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

public class DiceGameApplication {

  public static final int MAX_TASKS = 1000000;

  public static void main(String[] args) {
    List<OneSixInFourRollsTask> inFourRollsTasks = IntStream.range(0, MAX_TASKS)
        .mapToObj(i -> new OneSixInFourRollsTask()).collect(Collectors.toList());

    List<DoubleSixesInTwentyFourRollsTask> inTwentyFourRollsTasks = IntStream.range(0, MAX_TASKS)
        .mapToObj(i -> new DoubleSixesInTwentyFourRollsTask())
        .collect(Collectors.toList());

    Concurrency concurrency = new ConcurrencyImpl();

    Map<String, List<? extends DiceTask>> input = new HashMap<>();
    input.put(new OneSixInFourRollsTask().gameName(), inFourRollsTasks);
    input.put(new DoubleSixesInTwentyFourRollsTask().gameName(), inTwentyFourRollsTasks);

    concurrency.useParallelStream(input);
  }

}
