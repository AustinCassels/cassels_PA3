import java.security.SecureRandom;
import java.util.Scanner;

public class LearnMulti {
    private static int countCorrectAnswers = 0;

    private static int countWrongAnswers = 0;

    private static int countProblems = 0;

    private static int difficultyLevel = 0;

    private static int gameArithmeticOption = 0;

    private static int questionArithmeticOption = 0;

    public static void main(String[] args) {
        //declare variables
        Scanner scnr;
        SecureRandom randGen;
        int[] questionNumbers;
        double userAnswer;
        int responseNumber;
        String response;
        int difficulty;
        int gameMode;
        boolean continueSession;

        //initialize variables
        scnr = new Scanner(System.in);
        randGen = new SecureRandom();
        questionNumbers = new int[2];
        response = "";
        continueSession = true;

        //get difficulty
        difficulty = getDifficultyLevel(scnr);

        //set difficulty level from user
        setDifficultyLevel(difficulty);

        //get arithmetic type
        gameMode = getArithmeticOption(scnr);

        //set arithmetic type
        setArithmeticOption(gameMode);

        //get initial question
        generateQuestion(questionNumbers, randGen);

        //loop user wants to stop
        while(continueSession) {
            //prompt user
            System.out.printf("How much is %d %s %d? ", questionNumbers[0], getArithmeticSymbol(), questionNumbers[1]);

            //increase problem count
            increaseProblem();

            //get user answer
            userAnswer = scnr.nextDouble();

            //check if answer is correct
            if (isAnswerCorrect(userAnswer, questionNumbers)) {
                //get positive response
                response = getResponse(true, randGen);

                //increment correct count
                increaseCorrect();
            }
            else {
                //get negative response
                response = getResponse(false, randGen);

                //increment wrong count
                increaseWrong();
            }

            //print message
            System.out.printf("%s\n\n", response);

            //check if 10 answers have been typed
            if (getProblemCount() % 10 == 0) {
                //print correct/incorrect count
                printAnswerStats();

                //check if answer correct is satisfactory and print message accordingly
                if (getCorrectPercentage() >= 75) {
                    System.out.println("Congratulations, you are ready to go to the next level!");
                } else {
                    System.out.println("Please ask your teacher for extra help.");
                }

                //create space
                System.out.println();

                //prompt for another session
                System.out.print("Would you like to start another session? 1 for yes, 0 for no: ");
                if (scnr.nextInt() == 1) {
                    //create space
                    System.out.println("\n");

                    //reset
                    resetCounters();

                    //get difficulty
                    difficulty = getDifficultyLevel(scnr);

                    //set difficulty level from user
                    setDifficultyLevel(difficulty);

                    //get arithmetic type
                    gameMode = getArithmeticOption(scnr);

                    //set arithmetic type
                    setArithmeticOption(gameMode);
                } else {
                    continueSession = false;
                }
            }

            //generate new numbers
            generateQuestion(questionNumbers, randGen);
        }
    }

    private static void printArithmeticMenu() {
        System.out.println("Select an option below for what you'd like to study:");
        System.out.println("1: Addition");
        System.out.println("2: Multiplication");
        System.out.println("3: Subtraction");
        System.out.println("4: Division");
        System.out.println("5: Mixed mode");
        System.out.println();
    }

    public static boolean isAnswerCorrect(double answer, int[] numbers) {
        double correctAnswer = -1.0;

        //determine correct answer for arithmetic selection
        switch(questionArithmeticOption) {
            case 1:
                correctAnswer = (double)numbers[0] + numbers[1];
                break;
            case 2:
                correctAnswer = (double)numbers[0] * numbers[1];
                break;
            case 3:
                correctAnswer = (double)numbers[0] - numbers[1];
                break;
            case 4:
                correctAnswer = (double)numbers[0] / numbers[1];
                break;
        }

        //return true/false
        return Math.abs(answer - correctAnswer) < 0.0001;
    }

    public static String getResponse(boolean correct, SecureRandom random) {
        int responseNumber;
        String response = "";

        //generate random response number
        responseNumber = random.nextInt(4) + 1;

        if (correct) {
            //determine positive response
            switch (responseNumber) {
                case 1:
                    response = "Very good!";
                    break;
                case 2:
                    response = "Excellent";
                    break;
                case 3:
                    response = "Nice work!";
                    break;
                case 4:
                    response = "Keep up the good work!";
                    break;
                default:
                    break;
            }
        }
        else {
            //determine negative response
            switch (responseNumber) {
                case 1:
                    response = "No. Please try again.";
                    break;
                case 2:
                    response = "Wrong. Try once more.";
                    break;
                case 3:
                    response = "Don't give up!";
                    break;
                case 4:
                    response = "No. Keep trying.";
                    break;
                default:
                    break;
            }
        }

        return response;
    }

    public static int getArithmeticOption(Scanner scnr) {
        //declare variable
        int option;

        //print options
        printArithmeticMenu();

        //prompt and retrieve difficulty
        System.out.print("Please enter your selection: ");
        option = scnr.nextInt();

        //validate/reprompt for input
        while (option > 5 || option < 1) {
            //get difficulty
            System.out.print("Try again! Please enter your selection: ");
            option = scnr.nextInt();
        }

        //create space
        System.out.println();

        return option;
    }

    public static void setArithmeticOption(int option) {
        gameArithmeticOption = option;
        if (option != 5)
            questionArithmeticOption = option;
    }

    public static String getArithmeticSymbol() {
        //determine correct operator
        switch(questionArithmeticOption) {
            case 1:
                return "+";
            case 2:
                return "*";
            case 3:
                return "-";
            case 4:
                return "/";
        }

        return null;
    }

    public static void generateQuestion(int[] numbers, SecureRandom random) {
        numbers[0] = random.nextInt((int)Math.pow(10.0, difficultyLevel));
        numbers[1] = random.nextInt((int)Math.pow(10.0, difficultyLevel));

        //if game mode is mixed, generate random current arethmetic mode
        if (gameArithmeticOption == 5)
            questionArithmeticOption = random.nextInt(4) + 1;

        //make sure not to divide by zero
        if (questionArithmeticOption == 4)
            while(numbers[1] == 0)
                numbers[1] = random.nextInt((int)Math.pow(10.0, difficultyLevel));
    }

    public static int getDifficultyLevel(Scanner scnr) {
        //declare variable
        int difficulty;

        //prompt and retrieve difficulty
        System.out.print("Please enter a difficulty number (1-4): ");
        difficulty = scnr.nextInt();

        //validate/reprompt for input
        while (difficulty > 4 || difficulty < 1) {
            //get difficulty
            System.out.print("Try again! Please enter a difficulty number (1-4): ");
            difficulty = scnr.nextInt();
        }

        //create space
        System.out.println();

        //return difficulty input
        return difficulty;
    }

    public static void increaseCorrect() {
        countCorrectAnswers++;
    }

    public static void increaseWrong() {
        countWrongAnswers++;
    }

    public static void increaseProblem() {
        countProblems++;
    }

    public static int getProblemCount() {
        return countProblems;
    }

    public static double getCorrectPercentage() {
        return (double)countCorrectAnswers / (countCorrectAnswers + countWrongAnswers) * 100;
    }

    public static void resetCounters() {
        countCorrectAnswers = 0;
        countWrongAnswers = 0;
        countProblems = 0;
    }

    public static void setDifficultyLevel(int level) {
        difficultyLevel = level;
    }

    public static void printAnswerStats() {
        System.out.printf("You answered %d correct!\n", countCorrectAnswers);
        System.out.printf("You answered %d incorrect.\n\n", countWrongAnswers);
    }
}