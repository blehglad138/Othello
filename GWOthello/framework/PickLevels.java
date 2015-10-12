import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.*;


/**
 * A GUI that allows the user to pick the level and everything else they want
 * for their game of Othello
 * 
 * @author Patrick
 * @version May 31, 2014
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: none
 */
public class PickLevels extends JFrame implements ActionListener
{

    /**
     * The few radio buttons that we'll have for the difficulty
     */
    private JRadioButton easy, medium, hard, human, impossible; // human for
                                                                // multiplayer

    /**
     * The buttonGroup that the buttons above are placed in
     */
    private ButtonGroup difficulty;

    /**
     * The box for the difficulties
     */
    private Box difficultBox;

    /**
     * Radio buttons for whether or not the player wants to play online or
     * locally
     */
    private JRadioButton local, online;

    /**
     * the button group for the connections
     */
    private ButtonGroup connection;

    /**
     * The box for the connection type
     */
    private Box connectionBox;

    /**
     * Two labels so that the user knows which text field is for which player's
     * name
     */
    private JLabel playerOne, playerTwo;

    /**
     * The name of the players. pTwoName will not be editable if the user
     * selects a difficulty that is not human
     */
    private JTextField pOneName, pTwoName;

    /**
     * Panel for the names (instead of box layout)
     */
    private JPanel nameOne;

    /**
     * Panel for player two's name
     */
    private JPanel nameTwo;

    /**
     * Button the proceed and play the game
     */
    private JButton proceed;

    /**
     * a string that will help instantiate the game
     */
    private String info = "";


    /**
     * Creates the GUI and sets up how it will look when first run.
     */
    public PickLevels()
    {
        setTitle( "Pick your difficulty, Enter in your name, and choose connection" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        
        // connection
        local = new JRadioButton( "Local Connection", true );
        online = new JRadioButton( "Networked Connection", false );
        local.setActionCommand( "local" );
        online.setActionCommand( "online" );
        local.addActionListener( this );
        online.addActionListener( this );

        connection = new ButtonGroup();
        connection.add( local );
        connection.add( online );

        connectionBox = new Box( BoxLayout.X_AXIS );
        connectionBox.add( new JLabel( "Connection" ) );
        connectionBox.add( Box.createRigidArea( new Dimension( 1, 8 ) ) );
        connectionBox.add( local );
        connectionBox.add( online );
        connectionBox.add( Box.createRigidArea( new Dimension( 1, 50 ) ) );

        human = new JRadioButton( "Human Opponent", true );
        easy = new JRadioButton( "Easy", false );
        medium = new JRadioButton( "Medium", false );
        hard = new JRadioButton( "Hard", false );
        impossible = new JRadioButton( "IMPOSSIBRU!!!", false );

        easy.setActionCommand( "no player 2" );
        medium.setActionCommand( "no player 2" );
        hard.setActionCommand( "no player 2" );
        impossible.setActionCommand( "no player 2" );
        human.setActionCommand( "2 player" );
        human.addActionListener( this );
        easy.addActionListener( this );
        medium.addActionListener( this );
        hard.addActionListener( this );
        impossible.addActionListener( this );

        difficulty = new ButtonGroup();
        difficulty.add( human );
        difficulty.add( easy );
        difficulty.add( medium );
        difficulty.add( hard );
        difficulty.add( impossible );

        difficultBox = new Box( BoxLayout.X_AXIS );
        difficultBox.add( new JLabel( "Difficulty" ) );
        difficultBox.add( Box.createRigidArea( new Dimension( 1, 8 ) ) );
        difficultBox.add( human );
        difficultBox.add( easy );
        difficultBox.add( medium );
        difficultBox.add( hard );
        difficultBox.add( impossible );
        difficultBox.add( Box.createRigidArea( new Dimension( 1, 40 ) ) );

        // Label for player names
        playerOne = new JLabel( "Player One" );
        playerTwo = new JLabel( "Player Two" );

        // Text field
        pOneName = new JTextField( 20 );
        pOneName.setText( "Player 1" );
        pTwoName = new JTextField( 20 ); // set this editable to false if they
        pTwoName.setText( "Player 2" ); // select single player

        nameOne = new JPanel();
        nameTwo = new JPanel();
        nameOne.add( playerOne );
        nameOne.add( pOneName );
        nameTwo.add( playerTwo );
        nameTwo.add( pTwoName );

        proceed = new JButton( "Play" );
        proceed.setActionCommand( "play" );
        proceed.addActionListener( this );
        Box bottom = new Box( BoxLayout.X_AXIS );
        bottom.add( Box.createRigidArea( new Dimension( 1, 8 ) ) );
        bottom.add( proceed );

        // Frame
        setLayout( new BoxLayout( getContentPane(), BoxLayout.Y_AXIS ) );
        add( connectionBox );
        add( difficultBox );
        add( nameOne );
        add( nameTwo );
        add( bottom );
    }


    /**
     * The action performed for every single button in order to get exactly what
     * the player clicked
     * 
     * @param evt
     *            the action that the player did
     */
    public void actionPerformed( ActionEvent evt )
    {
        // all conditions to set text fiels false
        if ( ( evt.getActionCommand().equals( "no player 2" ) ) )
        {
            pTwoName.setEditable( false );
        }
        else if ( evt.getActionCommand().equals( "2 player" ) )
        {
            if ( online.isSelected() )
            {
                pTwoName.setEditable( false );
            }
            else
            {
                pTwoName.setEditable( true );
            }
        }
        if ( evt.getActionCommand().equals( "online" ) )
        {
            pTwoName.setEditable( false );
            pOneName.setEditable( false ); // no need for it
        }
        if ( evt.getActionCommand().equals( "local" ) )
        {
            if ( human.isSelected() )
            {
                pOneName.setEditable( true );
                pTwoName.setEditable( true );
            }
            else
            {
                pOneName.setEditable( true );
            }
        }
        // proceeding
        if ( evt.getActionCommand().equals( "play" ) )
        {
            if ( local.isSelected() )
            {
                String p1name = ( pOneName.getText().isEmpty() ) ? "Player 1"
                    : pOneName.getText();
                String p2name = "";
                String diff = "easy";
                if ( pTwoName.isEditable() )
                {
                    p2name = ( pTwoName.getText().isEmpty() || p2name.equals( p1name )) ? "Player 2"
                        : pTwoName.getText();
                }
                if ( easy.isSelected() )
                {
                    diff = "Easy";
                }
                else if ( medium.isSelected() )
                {
                    diff = "Medium";
                }
                else if ( hard.isSelected() )
                {
                    diff = "Hard";
                }
                else if ( impossible.isSelected() )
                {
                    diff = "Impossible";
                }
                else if ( human.isSelected() )
                {
                    diff = "Human";
                }
                info += diff + ", " + p1name + ", " + p2name;
                this.setVisible( false );

            }
            else if ( online.isSelected() )
            {
                if ( easy.isSelected() || medium.isSelected()
                    || hard.isSelected() || impossible.isSelected() )
                {
                    if ( evt.getActionCommand().equals( "play" ) )
                    {
                        // it closes pickLevels when I dont want it to
                        JOptionPane.showMessageDialog( null,
                            "You must select \"Human Opponent\" when playing online.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE );
                        setVisible( true );
                    }
                }
                else
                {
                    setVisible( false );
                    info += "network, ";
                }
            }
        }
    }


    /**
     * Gets the string in order to help instantiate the game
     * 
     * @return the string created while the player selects what level to play
     */
    public String info()
    {
        return info;
    }
}
