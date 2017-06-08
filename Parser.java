import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author Anthony Argento
 * @date 16 June 2017
 * @class CMSC 330
 * @purpose Based on provide grammar, takes input from tokens produced by Tokenizer class and generates GUI.
 *          If parsing does not match match the grammar included in this comment an error will be generated.
 *
 *          *** Project 1 GrammarTokens ***
 *
 *          gui ::=
 *              Window STRING '(' NUMBER ',' NUMBER ')' layout widgets End '.'
 *          layout ::=
 *              Layout layout_type ':'
 *          layout_type ::= Flow |
 *              Grid '(' NUMBER ',' NUMBER [',' NUMBER ',' NUMBER] ')' widgets ::=
 *          widget widgets |
 *              widget
 *          widget ::=
 *              Button STRING ';' |
 *              Group radio_buttons End ';' |
 *              Label STRING ';' |
 *              Panel layout widgets End ';' |
 *              Textfield NUMBER ';'
 *          radio_buttons ::=
 *              radio_button radio_buttons |
 *              radio_button
 *          radio_button ::=
 *              Radio STRING ';'
 */

public class Parser extends JFrame {

    private GrammarTokens token;
    private Tokenizer tokenizer;
    private GrammarTokens isWindow;

    /* define java swing components */
    private JFrame jFrame;
    private JPanel jPanel;
    private ButtonGroup buttonGroup;

    /* constructor */
    private Parser() {

        /* try-catch statement to open and parse test file. Throws custom exception Error and IOException */
        try {
            String inputFile = "/Users/aargento/Desktop/MyTest.txt";
            tokenizer = new Tokenizer(inputFile);
            tokenizer.parseFile();
            token = tokenizer.nextToken();
            this.generateGUI();
        }//end try

        catch (Error | IOException e) {
            e.printStackTrace();
        }//end catch

    }//end Parser

    /* generateGUI method */
    private boolean generateGUI() throws Error, IOException {

        /* variables for GUI size */
        int width, height;

        /* if WINDOW token create jFrame */
        if(token == GrammarTokens.WINDOW) {
            isWindow = GrammarTokens.WINDOW;
            jFrame = new JFrame();
            token = tokenizer.nextToken();

            /* if STRING token set jFrame title */
            if(token == GrammarTokens.STRING) {
                jFrame.setTitle(tokenizer.sTokenizer.sval);
                token = tokenizer.nextToken();

                /* if P_LEFT token get next token */
                if(token == GrammarTokens.P_LEFT) {
                    token = tokenizer.nextToken();

                    /* if NUMBER token store width parameter */
                    if(token == GrammarTokens.NUMBER) {
                        width = (int) tokenizer.sTokenizer.nval;
                        token = tokenizer.nextToken();

                        /* if COMMA token get next token */
                        if(token == GrammarTokens.COMMA) {
                            token = tokenizer.nextToken();

                            /* if NUMBER token store height parameter */
                            if(token == GrammarTokens.NUMBER) {
                                height = (int) tokenizer.sTokenizer.nval;
                                token = tokenizer.nextToken();

                                /* if P_RIGHT token set jFrame width and height */
                                if(token == GrammarTokens.P_RIGHT) {
                                    jFrame.setSize(width, height);
                                    token = tokenizer.nextToken();

                                    /* step into layoutProduction method */
                                    if(layoutProduction()) {

                                        /* step into widgetsProduction method */
                                        if(widgetsProduction()) {

                                            /* if END token get next token */
                                            if(token == GrammarTokens.END) {
                                                token = tokenizer.nextToken();

                                                /* if PERIOD token display GUI */
                                                if(token == GrammarTokens.PERIOD) {
                                                    jFrame.setVisible(true);
                                                    return true;
                                                }//end PERIOD statement

                                                else {
                                                    throw new Error("Unknown Location.");
                                                }//end else statement

                                            }//end END statement

                                        }//end widgetsProduction statement

                                    }//end layoutProduction statement

                                }//end P_RIGHT statement

                            }//end NUMBER statement

                        }//end COMMA statement

                    }//end NUMBER statement

                }//end P_LEFT statement

            }//end STRING statement

        }//end WINDOW statement

        return false;

    }//end generateGUI


    private boolean layoutProduction() throws Error, IOException {

        /* if LAYOUT token get next token */
        if(token == GrammarTokens.LAYOUT) {
            token = tokenizer.nextToken();

            /* step into layoutForm */
            if(layoutForm()) {

                /* if COLON token get next token */
                if(token == GrammarTokens.COLON) {
                    token = tokenizer.nextToken();
                    return true;
                }//end COLON statement

                else {
                    throw new Error("Layout Production.");
                }
            }

        }//end LAYOUT statement

        return false;

    }//end layoutProduction


    private boolean layoutForm() throws Error, IOException {

        int rows, col, hSpace, vSpace;

        switch (token) {

            /* FLOW layout */
            case FLOW:
                if (isWindow == GrammarTokens.WINDOW) {
                    jFrame.setLayout(new FlowLayout());
                } else {
                    jPanel.setLayout(new FlowLayout());
                }

                token = tokenizer.nextToken();

                return true;

            /* GRID layout */
            case GRID:

                token = tokenizer.nextToken();

                /* if P_LEFT token get next token */
                if (token == GrammarTokens.P_LEFT) {
                    token = tokenizer.nextToken();

                    /* if NUMBER token set row parameter */
                    if (token == GrammarTokens.NUMBER) {
                        rows = (int) tokenizer.sTokenizer.nval;
                        token = tokenizer.nextToken();

                        /* if COMMA token get next token */
                        if (token == GrammarTokens.COMMA) {
                            token = tokenizer.nextToken();

                            /* if NUMBER token set col parameter */
                            if (token == GrammarTokens.NUMBER) {
                                col = (int) tokenizer.sTokenizer.nval;
                                token = tokenizer.nextToken();

                                /* if COMMA token get next token */
                                if (token == GrammarTokens.COMMA) {
                                    token = tokenizer.nextToken();

                                     /* if NUMBER token set hSpace parameter */
                                    if (token == GrammarTokens.NUMBER) {
                                        hSpace = (int) tokenizer.sTokenizer.nval;
                                        token = tokenizer.nextToken();

                                        /* if COMMA token get next token */
                                        if (token == GrammarTokens.COMMA) {
                                            token = tokenizer.nextToken();

                                            /* if NUMBER token set vSpace parameter */
                                            if (token == GrammarTokens.NUMBER) {
                                                vSpace = (int) tokenizer.sTokenizer.nval;
                                                token = tokenizer.nextToken();

                                                /* if P_RIGHT token set row, col, hSpace, vSpace */
                                                if (token == GrammarTokens.P_RIGHT) {

                                                    if (isWindow == GrammarTokens.WINDOW) {
                                                        jFrame.setLayout(new GridLayout(rows, col, hSpace, vSpace));
                                                    } else {
                                                        jPanel.setLayout(new GridLayout(rows, col, hSpace, vSpace));
                                                    }

                                                    token = tokenizer.nextToken();

                                                    return true;

                                                }//end P_RIGHT statement

                                            }//end NUMBER statement

                                        }//end COMMA statement

                                    }//end NUMBER statement

                                }//end COMMA statement

                            }//end NUMBER statement

                        }//end COMMA statement

                    }//end NUMBER statement

                }//end P_LEFT statement

            default:
                return false;

        }//end switch

    }//end layoutForm


    private boolean widgetsProduction() throws Error, IOException {

        /* step into widgetType method */
        if (widgetType()) {

            /* recursive call to widgetsProduction method */
            if (widgetsProduction()) {
                return true;
            }

            return true;
        }

        return false;
    }//end widgetsProduction


    private boolean widgetType() throws Error, IOException {

        String buttonTitle, label;
        int fieldSize;

        switch(token) {

            /* case for BUTTON token */
            case BUTTON:
                token = tokenizer.nextToken();

                /* if STRING token set button title */
                if(token == GrammarTokens.STRING) {
                    buttonTitle = tokenizer.sTokenizer.sval;
                    token = tokenizer.nextToken();

                    /* if SCOLON token add button to jFrame or jPanel */
                    if(token == GrammarTokens.SCOLON) {
                        if(isWindow == GrammarTokens.WINDOW) {
                            jFrame.add(new JButton(buttonTitle));
                        }

                        else {
                            jPanel.add(new JButton(buttonTitle));
                        }

                        token = tokenizer.nextToken();
                        return true;
                    }//end SCOLON statement

                    else {
                        throw new Error("Button Widget.");
                    }

                }//end STRING statement

            /* case for GROUP token */
            case GROUP:

                /* if GROUP token get next token */
                if (token == GrammarTokens.GROUP) {
                    buttonGroup = new ButtonGroup();
                    token = tokenizer.nextToken();

                    /* step into rGroup method */
                    if(rGroup()) {

                        /* if END token get next token */
                        if(token == GrammarTokens.END) {
                            token = tokenizer.nextToken();

                            /* if SCOLON token get next token */
                            if(token == GrammarTokens.SCOLON) {
                                token = tokenizer.nextToken();
                                return true;
                            }//end SCOLON statement

                            else {
                                throw new Error("Group Widget.");
                            }
                        }//end END statement

                    }//end rGroup method statement

                }//end GROUP statement

            /* case for LABEL token */
            case LABEL:

                /* if LABEL token get next token */
                if (token == GrammarTokens.LABEL) {
                    token = tokenizer.nextToken();

                    /* if STRING token set label to value */
                    if(token == GrammarTokens.STRING) {
                        label = tokenizer.sTokenizer.sval;
                        token = tokenizer.nextToken();

                        /* if SCOLON token add label to jFrame or jPanel */
                        if(token == GrammarTokens.SCOLON) {
                            if(isWindow == GrammarTokens.WINDOW) {
                                jFrame.add(new JLabel(label));
                            }

                            else {
                                jPanel.add(new JLabel(label));
                            }

                            token = tokenizer.nextToken();

                            return true;
                        }

                        else {
                            throw new Error("Label Widget");
                        }
                    }//end STRING statement

                }//end LABEL statement

            /* case for PANEL token */
            case PANEL:
                if (token == GrammarTokens.PANEL) {
                    if(isWindow == GrammarTokens.WINDOW) {
                        jFrame.add(jPanel = new JPanel());
                    }

                    else {
                        jPanel.add(jPanel = new JPanel());
                    }

                    isWindow = GrammarTokens.PANEL;
                    token = tokenizer.nextToken();

                    /* step into layoutProduction method */
                    if(layoutProduction()) {

                        /* step into widgetProduction method */
                        if(widgetsProduction()) {

                            if(token == GrammarTokens.END) {
                                token = tokenizer.nextToken();

                                /* if SCOLON token get next token */
                                if(token == GrammarTokens.SCOLON) {
                                    isWindow = GrammarTokens.WINDOW;
                                    token = tokenizer.nextToken();
                                    return true;
                                }

                                else {
                                    throw new Error("Panel Widget.");
                                }
                            }
                        }//end widgetsProduction

                    }//end layoutProduction

                }//end PANEL statement

            /* case for TEXTFIELD token */
            case TEXTFIELD:
                if (token == GrammarTokens.TEXTFIELD) {
                    token = tokenizer.nextToken();

                    /* if NUMBER token set text field size */
                    if(token == GrammarTokens.NUMBER) {
                        fieldSize = (int) tokenizer.sTokenizer.nval;
                        token = tokenizer.nextToken();

                        /* if SCOLON token get next token */
                        if(token == GrammarTokens.SCOLON) {

                            if(isWindow == GrammarTokens.WINDOW) {
                                jFrame.add(new JTextField(fieldSize));
                            }

                            else {
                                jPanel.add(new JTextField(fieldSize));
                            }

                            token = tokenizer.nextToken();

                            return true;

                        }//end SCOLON statement

                        else {
                            throw new Error("Textfield Widget." );
                        }
                    }//end NUMBER statement

                }//end TEXTFIELD statement

            default:
                return false;
        }
    }

    /* rGroup method */
    private boolean rGroup() throws Error, IOException {

        /* step into rButton method */
        if(rButton()) {

            /* recursive call to rGroup method */
            if(rGroup()) {
                return true;
            }

            return true;
        }

        return false;

    }//end rGroup method


    /* rButton method */
    private boolean rButton() throws Error, IOException {
        String radioTitle;

        /* if RADIO token get next token */
        if(token == GrammarTokens.RADIO) {
            token = tokenizer.nextToken();

            /* if STRING token get next token */
            if(token == GrammarTokens.STRING) {
                radioTitle = tokenizer.sTokenizer.sval;
                token = tokenizer.nextToken();

                /* if SCOLON token get next token */
                if(token == GrammarTokens.SCOLON) {
                    JRadioButton radioButton = new JRadioButton(radioTitle);
                    buttonGroup.add(radioButton);

                    if(isWindow == GrammarTokens.WINDOW) {
                        jFrame.add(radioButton);
                    }

                    else {
                        jPanel.add(radioButton);
                    }

                    token = tokenizer.nextToken();

                    return true;

                }//end SCOLON statement

                else {
                    throw new Error("Radio Button Widget." );
                }

            }//end STRING statement

        }//end RADIO statement

        return false;

    }//end rButton

    /* Main method */
    public static void main(String [] args) throws IOException {
        new Parser();
    }//end main

}//end Parser
