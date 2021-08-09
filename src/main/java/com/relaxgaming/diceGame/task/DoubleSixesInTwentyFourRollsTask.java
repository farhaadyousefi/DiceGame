/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame.task;

import java.util.Random;
import java.util.stream.IntStream;

public class DoubleSixesInTwentyFourRollsTask implements DiceTask {

  public Boolean playGame() {
    int min = 1, max = 6;
    Random random = new Random();
    Boolean result = IntStream.rangeClosed(1, 24).anyMatch(
        n -> random.nextInt((max - min) + min) + min == max &&
            random.nextInt((max - min) + min) + min == max);

    if (result) {
      System.out.println("congratulations, you win the game ");
    } else {
      System.out.println("oh no, sure you are lucky next time ");
    }

    return result;
  }

  @Override
  public String gameName() {
    return "double sixes in twenty four rolls";
  }
}
