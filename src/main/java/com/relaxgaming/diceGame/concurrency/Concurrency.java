/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame.concurrency;

import com.relaxgaming.diceGame.task.DiceTask;
import java.util.List;
import java.util.Map;

public interface Concurrency {

  void useSequential(Map<String, List<? extends DiceTask>> diceTaskListMap);

  void useParallelStream(Map<String, List<? extends DiceTask>> diceTaskListMap);

  void useCompletableFuture(Map<String, List<? extends DiceTask>> diceTaskListMap);

  void useCompletableFutureWithExecutor(Map<String, List<? extends DiceTask>> diceTaskListMap);

}
