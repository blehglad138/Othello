import java.util.*;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;


/**
 *  An AI that cheats and is impossible to beat.
 *
 *  @author  Charlie
 *  @version May 30, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 *
 *  @author  Sources: none
 */
public class ImpossibleAI extends StupidComputerOthelloPlayer
{
    /**
     * A color so taht it's not a boring white
     */
    private static Color whiteish = new Color( -2031617 );


    /**
     * Constructor that uses OthelloPlayer's constructor
     * @param world world that the game is in
     */
    public ImpossibleAI( OthelloWorld world )
    {
        super( world, "Computer", whiteish );
    }

    /**
     * Constructor that uses OthelloPlayer's constructor
     * @param world world that the game is in
     * @param col color of the computer AI
     */
    public ImpossibleAI( OthelloWorld world, Color col )
    {
        super( world, "ImpossibleAI", col );
    }


    /**
     * Play method which implements how the AI will play
     */
    public void play()
    {
        ArrayList<Location> choices = getAllowedPlays();
        if ( Math.random() > 0.9 || choices.isEmpty() || outRage() )
            doucheBaggery();
        else
        {
            Location playLoc = choices.get( (int)( Math.random() * choices.size() ) );
            getGrid().put( playLoc, new Piece( getColor() ) );
            
            for ( Location adjacent : getGrid().getOccupiedAdjacentLocations( playLoc ) )
            {
                Location next = getNextLocationWithColor( playLoc, adjacent );
                if ( next != null && next != adjacent )
                {
                    flipColorOfPieces( adjacent, next );
                }
            }
        }
    }

    public boolean canPlay()
    {
        return getAllowedPlays().size() != 0 || !isBoardFullOfMe();
    }


    private void doucheBaggery()
    {
        Grid<Piece> grid = getGrid();
        for ( int row = 0; row < grid.getNumRows(); row++ )
        {
            for ( int col = 0; col < grid.getNumCols(); col++ )
            {
                Location l = new Location( row, col );
                if ( grid.get( l ) == null )
                    grid.put( l, new Piece( getColor()) );
                else
                    grid.get( l ).setColor( getColor() );
            }
        }
    }


    // stub version due to being child of othello player
    public Location getPlay()
    {
        // TODO Auto-generated method stub
        return null;
    }


    public boolean isBoardFullOfMe()
    {
        boolean narcissism = true;
        for ( Location l : getGrid().getOccupiedLocations() )
        {
            if ( !getGrid().get( l ).getColor().equals( getColor() ) )
                narcissism = false;
        }
        return narcissism;
    }
    public boolean outRage()
    {
        boolean rage = true;
        for ( Location l : getGrid().getOccupiedLocations() )
        {
            if ( getGrid().get( l ).getColor().equals( getColor() ) )
                rage = false;
        }
        return rage;
    }
}
