/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame.task;

import java.util.Random;
import java.util.stream.IntStream;

public class OneSixInFourRollsTask implements DiceTask {

  public Boolean playGame() {
    int MIN = 1, MAX = 6;
    Random random = new Random();
    Boolean result = IntStream.rangeClosed(1, 4).anyMatch(
        n -> random.nextInt((MAX - MIN) + MIN) + MIN == MAX);

      if (result) {
        System.out.println("congratulations, you win the game ");
      } else {
        System.out.println("oh no, sure you are lucky next time ");
      }

    return result;
  }

  @Override
  public String gameName() {
    return "one sixes in four rolls";
  }

}
