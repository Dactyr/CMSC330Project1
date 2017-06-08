import java.io.*;

/**
 *
 * @author Anthony Argento
 * @date 16 June 2017
 * @class CMSC 330
 * @purpose Take input file and generate tokens based on the BNF metasymbols of the provided grammar
 *          defined in Parser.java
 *
 */

class Tokenizer {

    public StreamTokenizer sTokenizer;

    private InputStream inputFile;
    private GrammarTokens[] metasymbol = {
        GrammarTokens.COMMA, GrammarTokens.COLON, GrammarTokens.SCOLON, GrammarTokens.PERIOD, GrammarTokens.P_LEFT, GrammarTokens.P_RIGHT
    };

    public Tokenizer(String file) throws FileNotFoundException {
        inputFile = new FileInputStream(file);
    }

    public void parseFile() {
        sTokenizer = new StreamTokenizer(inputFile);
    }

    /* retrived next token from input file */
    public GrammarTokens nextToken() throws Error, IOException {

        int currentToken = sTokenizer.nextToken();

        switch(currentToken) {

            /* Number case */
            case StreamTokenizer.TT_NUMBER: {
                return GrammarTokens.NUMBER;
            }//end Number case

            /* Word case */
            case StreamTokenizer.TT_WORD: {
                sTokenizer.ordinaryChar('.');

                GrammarTokens[] values = GrammarTokens.values();

                for (GrammarTokens i : values) {
                    if (i.toString().equals(sTokenizer.sval.toUpperCase())) {
                        return i;
                    }
                }

                throw new Error("Invalid Token = " + " '" + sTokenizer.sval +"' ");
            }//end Word case

            /* " case */
            case '"': {
                sTokenizer.quoteChar('"');
                return GrammarTokens.STRING;
            }//end " case

            /* Default case */
            default:
                String tokens = ",:;.()";
                int i = 0;

                while (i < tokens.length()) {
                    if (currentToken == tokens.charAt(i)) {
                        return metasymbol[i];
                    }
                    i++;
                }//end while loop

        }//end currentToken switch statement

        throw new Error("Invalid Token = " + " '" + sTokenizer.sval +"' ");

    }//end nextToken

}//end Tokenizer