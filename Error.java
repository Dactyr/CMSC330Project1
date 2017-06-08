/**
 *
 * @author Anthony Argento
 * @date 16 June 2017
 * @class CMSC 330
 * @purpose Define custom exception. Append message from error location to provide feedback as general
 *          area of problem.
 *
 */

class Error extends java.lang.Exception {

    Error(String message) {
        super("Error exists in " + message);
    }

}//end Error