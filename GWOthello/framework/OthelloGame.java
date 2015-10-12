import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import java.awt.Color;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JList;


/**
 * OthelloGame.java
 * 
 * An <CODE>OthelloGame</CODE> object represents an Othello game.
 * 
 * @author Patrick Lee
 * @author 5111742
 * @version 4/4/13
 * @author Period: 3
 * @author Assignment: GWOthello
 * 
 * @author Sources: None
 */
public class OthelloGame extends World<Piece>
{
    /** The world */
    private OthelloWorld world;

    /** The array of two players (human, computer) */
    private OthelloPlayer[] players;

    /** The index into players for the next player to play */
    private int playerIndex;

    /**
     * Whiteish color (a little bit of blue in it)
     */
    private static Color whiteish = new Color( -2031617 );

    /**
     * Result string to display the message
     */
    public String result = "";


    /**
     * Constructs an Othello game.<br>
     * Postcondition: <CODE>players.length == 2</CODE>; <CODE>players[0]</CODE>
     * contains a human Othello player; <CODE>players[1]</CODE> contains a
     * computer Othello player; The world has been shown.
     * 
     * @param show
     *            if true world is displayed. Used for testing
     */
    public OthelloGame( boolean show )
    {
        world = new OthelloWorld( this );
        players = new OthelloPlayer[2];
        players[0] = new MediumAI( world, whiteish );
        players[1] = new HumanOthelloPlayer( world );
        playerIndex = 0;
        if ( show )
        {
            world.show();
            world.getFrame().setSize( 442, 526 );
        }
    }


    /**
     * Constructs an OthelloGame with the few parameters
     * 
     * @param show
     *            whether or not to show the game
     * @param ai
     *            the difficulty of the game
     * @param p1name
     *            player one's name
     * @param p2name
     *            player two's name
     */
    public OthelloGame( boolean show, String ai, String p1name, String p2name )
    {
        world = new OthelloWorld( this );
        players = new OthelloPlayer[2];
        players[0] = new HumanOthelloPlayer( world, p1name, Color.BLACK );
        if ( p1name.equals( "DevModeRainbowMausCake" ) )
        {
            players[0] = new ImpossibleAI( world, Color.black );
        }
        else if ( p1name.equals( "DevModeRainbowMausMedium" ) )
        {
            players[0] = new MediumAI( world, Color.black );
        }
        if ( ai.equals( "Human" ) )
        {
            players[1] = new HumanOthelloPlayer( world, p2name, whiteish );
        }
        else if ( ai.equals( "Easy" ) )
        {
            players[1] = new StupidComputerOthelloPlayer( world );
        }
        else if ( ai.equals( "Medium" ) )
        {
            players[1] = new MediumAI( world );
        }
        else if ( ai.equals( "Hard" ) )
        {
            players[1] = new MinimaxOthelloPlayer( world );
        }
        else if ( ai.endsWith( "Impossible" ) )
        {
            players[1] = new ImpossibleAI( world );
        }
        if ( show )
        {
            world.show();
            world.getFrame().setSize( 442, 526 ); // was 442 by 552 with button
        }
    }


    /**
     * Constructs and OthelloGame with just one player's name (not really needed
     * if you re-code OthelloRunner
     * 
     * @param show
     *            whether or not to show the game
     * @param ai
     *            difficulty of the computer
     * @param p1name
     *            player one's name
     */
    public OthelloGame( boolean show, String ai, String p1name )
    {
        this( show, ai, p1name, "" );
    }


    /**
     * Plays the game until it is over (no player can play).
     */
    public void playGame()
    {
        while ( true )
        {
            OthelloPlayer othello = players[playerIndex];
            if ( othello.canPlay() )
                othello.play();
            playerIndex = ( playerIndex == 0 ) ? 1 : 0;
            world.setMessage( toString() );
            if ( !players[0].canPlay() && !players[1].canPlay() )
            {
                if ( players[1] instanceof MinimaxOthelloPlayer
                    && toString().contains( players[0].getName() ) )
                {
                    HardWin h = new HardWin();
                    h.setVisible( true );
                    h.display();
                }
                break;
            }
        }
    }


    /**
     * Creates a string with the current game state. (used for the GUI message).
     */
    public String toString()
    {
        int numBlack = 0;
        int numWhite = 0;

        Grid<Piece> board = world.getGrid();

        for ( Location loc : board.getOccupiedLocations() )
            if ( board.get( loc ).getColor().equals( Color.BLACK ) )
                numBlack++;
            else
                numWhite++;

        String result = "Black: " + numBlack + "    white: " + numWhite + "\n";
        if ( !players[0].canPlay() && !players[1].canPlay() )
            if ( numBlack > numWhite )
            {
                result += players[0].getName() + " won!";
            }
            else if ( numBlack < numWhite )
            {
                result += players[1].getName() + " won!";
            }
            else
            {
                result += "It's a tie!";
            }
        else
            result += players[playerIndex].getName() + " to play.";
        return result;
    }


    /**
     * Returns the result (the string)
     * 
     * @return the result
     */
    public String getResult()
    {
        return result;
    }


    // accessors used primarily for testing

    protected OthelloWorld getOthelloWorld()
    {
        return world;
    }


    protected OthelloPlayer[] getOthelloPlayer()
    {
        return players;
    }


    protected int getPlayerIndex()
    {
        return playerIndex;
    }


    // for networking
    /**
     * Sets up the world for networking with the port
     * 
     * @param og
     *            the game
     * @param port
     *            the port to connect to
     */
    public void setWorld( OthelloGame og, int port )
    {
        world = new NetworkWorld( og, port );
        world.show();
    }


    /**
     * Sets players to the one's passed (for networking)
     * 
     * @param op
     *            players made in the network
     */
    public void setPlayers( OthelloPlayer[] op )
    {
        players = op;
    }


    /**
     * The players in the game
     * 
     * @return the players
     */
    public OthelloPlayer[] getPlayers()
    {
        return players;
    }
}
