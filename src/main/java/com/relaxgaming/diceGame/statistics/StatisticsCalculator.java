/**
 * created by farhad (farhad.yousefi@outlook.com) on 08.08.21
 */
package com.relaxgaming.diceGame.statistics;

public class StatisticsCalculator {

  public void Calc(int winCount) {
    double mean, division = 0, standardsDivisionDiff, variance, totalNum = 1000000;
    mean = winCount / totalNum;
    System.out.println("Mean is " + mean);

    for (int i = 0; i < winCount; i++) {
      division += (1 - mean) * (1 - mean);
    }
    for (int i = 0; i < totalNum - winCount; i++) {
      division += (0 - mean) * (0 - mean);
    }
    variance = division / totalNum;
    System.out.println("Variance is " + variance);

    standardsDivisionDiff = Math.sqrt(variance);
    System.out.println("Standard Deviation is " + standardsDivisionDiff);
  }
}
