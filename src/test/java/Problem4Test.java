/*
 * DO NOT MAKE ANY CHANGES UNLESS SPECIFIED OTHERWISE
 */

import Problem4.CompoundingResult;
import Problem4.MillionDollarMaker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Problem4Test {
    @Rule
    public TestName name = new TestName();  // https://stackoverflow.com/questions/17230413/get-the-name-of-currently-executing-test-method-in-before-in-junit
    final float ONE_MILLION = 1000000f;

    public static class TestCase {
        // what's the class visibility of the following variables?
        // Is it "public"?
        // Or "private"?
        // Or "protected"?
        // What's "protected" anyway?
        final float initDeposit;
        final float monthlyContribution;
        final int lengthInYear;
        final float interestRate;

        CompoundingResult expectedResult;

        public TestCase(float initDeposit, float monthlyContribution,
                        int lengthInYear, float interestRate,
                        float expectInvested, float expectAccumulated) {
            this.initDeposit = initDeposit;     // why do we need "this" here?
            this.monthlyContribution = monthlyContribution;
            this.lengthInYear = lengthInYear;
            this.interestRate = interestRate;
            expectedResult = new CompoundingResult(expectInvested, expectAccumulated);
        }

        // Generated by IntelliJ.
        @Override
        public String toString() {
            return "TestCase{" +
                    "initDeposit=" + initDeposit +
                    ", monthlyContribution=" + monthlyContribution +
                    ", lengthInYear=" + lengthInYear +
                    ", interestRate=" + interestRate +
                    ", expectedResult=" + expectedResult +
                    '}';
        }
    }

    private static void runTests(String testName, List<TestCase> testCases) {
        for (TestCase testCase : testCases) {
            CompoundingResult actual = MillionDollarMaker.calculate(testCase.initDeposit,
                    testCase.monthlyContribution,
                    testCase.lengthInYear,
                    testCase.interestRate
            );
            assertEquals("Test " + testName + ", case " + testCase, testCase.expectedResult, actual);
        }
    }

    @Test
    public void varyingYears() {
        List<TestCase> testCases = new ArrayList<TestCase>() {
            {
                add(new TestCase(100, 20, 1, 10,
                        340, 350));

                add(new TestCase(100, 20, 2, 10,
                        580, 625));

                add(new TestCase(100, 20, 5, 10,
                        1300, 1626.28f));   // what's the appendix f for?

                add(new TestCase(100, 20, 10, 10,
                        2500, 4084.36f));
            }
        };

        runTests(name.getMethodName(), testCases);
    }

    @Test
    public void negativeInputs() {
        List<TestCase> testCases = new ArrayList<TestCase>() {
            {
                add(new TestCase(-2, 20, 1, 10,
                        0, 0));

                add(new TestCase(100, -1, 1, 10,
                        0, 0));

                add(new TestCase(100, 20, -2, 10,
                        0, 0));

                add(new TestCase(100, 20, 1, -5,
                        340, 335));

                add(new TestCase(100, 20, 1, -150,
                        0, 0));

                add(new TestCase(100, 20, 1, 150,
                        0, 0));
            }
        };

        runTests(name.getMethodName(), testCases);
    }

    @Test
    public void varyingRate() {
        float initDeposit = 100;
        float monthlyContribution = 40;
        int lengthInYear = 10;
        float expectInvested = 4900;
        float[] expectAccumulated = {7909.34f, 13079.34f, 21835.94f};
        float[] interestRates = {10, 20, 30};

        assertEquals(expectAccumulated.length, interestRates.length);

        List<TestCase> testCases = new ArrayList<>();

        for (int i = 0; i < interestRates.length; i++) {
            testCases.add(new TestCase(initDeposit, monthlyContribution, lengthInYear, interestRates[i],
                    expectInvested, expectAccumulated[i]));

        }

        runTests(name.getMethodName(), testCases);
    }

    @Test
    public void varyingInitDeposit() {
        float interestRate = 20;
        float monthlyContribution = 500;
        int lengthInYear = 10;
        float[] expectInvested = {60100f, 61000f, 65000f};
        float[] expectAccumulated = {156371.27f, 161943.86f, 186710.77f};
        float[] initDeposits = {100, 1000, 5000};

        assertEquals(expectAccumulated.length, expectInvested.length);
        assertEquals(initDeposits.length, expectInvested.length);

        List<TestCase> testCases = new ArrayList<>();

        for (int i = 0; i < initDeposits.length; i++) {
            testCases.add(new TestCase(initDeposits[i], monthlyContribution, lengthInYear, interestRate,
                    expectInvested[i], expectAccumulated[i]));
        }

        runTests(name.getMethodName(), testCases);
    }

    @Test
    public void varyingMonthlyContribution() {
        float initDeposit = 1000;
        float interestRate = 20;
        int lengthInYear = 10;
        float[] expectInvested = {121000f, 241000f, 361000f};
        float[] expectAccumulated = {317695.92f, 629200.11f, 940704.29f};
        float[] monthlyContributions = {1000, 2000, 3000};

        assertEquals(expectAccumulated.length, expectInvested.length);
        assertEquals(monthlyContributions.length, expectInvested.length);

        List<TestCase> testCases = new ArrayList<>();

        for (int i = 0; i < monthlyContributions.length; i++) {
            testCases.add(new TestCase(initDeposit, monthlyContributions[i], lengthInYear, interestRate,
                    expectInvested[i], expectAccumulated[i]));
        }

        runTests(name.getMethodName(), testCases);
    }

    @Test
    public void waysToReachOneMillion() {
        List<TestCase> testCases = new ArrayList<TestCase>() {
            {
                add(new TestCase(7000, 4000, 10, 15,
                        487000, 1002897.38f));

                add(new TestCase(500, 500, 25, 15,
                        150500, 1293217.58f));

                add(new TestCase(500, 100, 35, 15,
                        42500, 1123991.95f));

                // average stock market return (see shorturl.at/aHJT6)
                add(new TestCase(1000, 300, 35, 10,
                        127000, 1003790.16f));
                /*
                TODO: add your ways of making a million dollar using compounding interest
                 */
            }
        };

        for (TestCase testCase : testCases) {
            CompoundingResult actual = MillionDollarMaker.calculate(testCase.initDeposit,
                    testCase.monthlyContribution,
                    testCase.lengthInYear,
                    testCase.interestRate
            );

            assertTrue(actual.getAccumulated() >= ONE_MILLION);
        }
    }
}

/*
    TODO: Bonus Points. Add your answer here.
 */
