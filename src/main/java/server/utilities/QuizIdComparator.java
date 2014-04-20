package server.utilities;

import server.interfaces.Quiz;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator to sort lists of Quizzes by their IDs
 *
 * @author Michael Bragg
 */
public class QuizIdComparator implements Comparator<Quiz>, Serializable {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(Quiz o1, Quiz o2) {
        if (o1.equals(o2)) {
            return 0;
        } else if (o1.getQuizID() > o2.getQuizID()) {
            return 1;
        } else {
            return -1;
        }
    }
}
