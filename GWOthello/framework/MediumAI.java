import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Medium AI for our super othello game using the basic priority queue idea.
 * 
 * @author Patrick Lee
 * @version May 7, 2014
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: none/Charlie
 */
public class MediumAI extends OthelloPlayer
{

    /**
     * The board that the pieces are on
     */
    private Grid<Piece> board;

    /**
     * A color that is slightly blue, parameter is the RGB value
     */
    private static Color whiteish = new Color( -2031617 );


    /**
     * Constructs a mediumAI computer player.
     * 
     * @param world
     *            the world
     */
    public MediumAI( OthelloWorld world )
    {
        super( world, "Computer", whiteish );
        board = world.getGrid();
    }


    /**
     * Another constructor that includes the color
     * 
     * @param world
     *            world that the player is in
     * @param col
     *            color for the piece
     */
    public MediumAI( OthelloWorld world, Color col )
    {
        super( world, "Computer", col );
        board = world.getGrid();
    }


    /**
     * gets the play of this AI by using the regions and the value calculator
     */
    @Override
    public Location getPlay()
    {
        ArrayList<Location> allowedMoves = getAllowedPlays();
        // move be key and
        HashMap<Location, Integer> moveValues = new HashMap<Location, Integer>();
        Location key = null; // key for the hashmap
        // last region 5
        Location reg5L = new Location( board.getNumRows() - 1,
            board.getNumCols() - 1 );
        // start region 5
        Location reg5S = new Location( 0, 0 );
        // 2nd region 5
        Location reg5Sec = new Location( 0, board.getNumCols() - 1 );
        // 3rd region 5
        Location reg5Th = new Location( board.getNumRows() - 1, 0 );
        // region 2 and 4
        ArrayList<Location> badReg = new ArrayList<Location>();
        for ( int c = 0; c < board.getNumCols(); c++ )
        {
            badReg.add( new Location( 1, c ) );
            badReg.add( new Location( board.getNumRows() - 2, c ) );
        }
        for ( int r = 0; r < board.getNumRows(); r++ )
        {
            badReg.add( new Location( r, 1 ) );
            badReg.add( new Location( r, board.getNumCols() - 2 ) );
        }
        // end regions 2 and 4
        // region 3...
        ArrayList<Location> region3 = new ArrayList<Location>();
        for ( int r = 2; r < board.getNumRows() - 2; r++ )
        {
            region3.add( new Location( r, 0 ) );
            region3.add( new Location( r, board.getNumCols() - 1 ) );
        }
        for ( int c = 2; c < board.getNumCols() - 2; c++ )
        {
            region3.add( new Location( 0, c ) );
            region3.add( new Location( board.getNumRows() - 1, c ) );
        }
        // end region 3
        int numberOfPieces = 0;
        if ( canPlay() )
        {
            for ( Location loc : allowedMoves )
            {
                if ( loc.equals( reg5L ) || loc.equals( reg5S )
                    || loc.equals( reg5Sec ) || loc.equals( reg5Th ) )
                {
                    numberOfPieces = valueCalculator( loc, 10 );
                    moveValues.put( loc, numberOfPieces );
                }
                else if ( badReg.contains( loc ) )
                {
                    // region 2 and 4 will have region value of 1 cause low
                    // priority
                    numberOfPieces = valueCalculator( loc, -10 );
                    moveValues.put( loc, numberOfPieces );
                }
                else if ( region3.contains( loc ) )
                {
                    numberOfPieces = valueCalculator( loc, 4 );
                    moveValues.put( loc, numberOfPieces );
                }
                else
                {
                    numberOfPieces = valueCalculator( loc, -5 );
                    moveValues.put( loc, numberOfPieces );
                }

            }
            if ( moveValues.keySet().size() > 0 ) // probably don't need because
                                                  // it canPlay
            {
                int bigVal = Integer.MIN_VALUE;
                for ( Location loc : moveValues.keySet() )
                {
                    if ( moveValues.get( loc ).intValue() > bigVal
                        || ( moveValues.get( loc ).intValue() >= bigVal && Math.random() > 0.5 ) )
                    {
                        bigVal = moveValues.get( loc ).intValue();
                        key = loc;
                    }
                }
            }
        }
        return key;
    }

    private int flipColCount = 0;


    /**
     * Modified version of flipColorOfPieces so that it doesn't flip but instead
     * gets the counter of how many would flip
     * 
     * @param start start location to count
     * @param end ending location to count
     * @return the number that would be flipped
     */
    private int flipColor( Location start, Location end )
    {
        int dir = start.getDirectionToward( end );
        Location current = start;
        while ( board.get( current ) != board.get( end ) )
        {
            // board.get( current ).setColor( red ); this would flip the colors
            // if uncommented
            current = current.getAdjacentLocation( dir );
            flipColCount++;
        }
        return flipColCount;
    }


    /**
     * For testing
     * 
     * @return what flipColor returns...odd way to test but it works.
     */
    protected int getFlipColor()
    {
        return flipColCount;
    }

    private int value = 0;


    /**
     * Helper method so that I don't need to re-write the for loop in every if
     * statement in the methods that call valueCalculator
     * 
     * @param l
     *            Location of the current pieces
     * @param priority
     *            Region 5 has priority 10 (for more bias) Region 2 has priority
     *            4 Region 4 has priority 3 Region 1 has priority 6 Region 3 has
     *            priority 7 (for less bias so that it goes corner all the time)
     * @return the value to go in the map
     */
    private int valueCalculator( Location l, int priority )
    {
        for ( Location adjacent : board.getOccupiedAdjacentLocations( l ) )
        {
            Location next = getNextLocationWithColor( l, adjacent );
            if ( next != null && next != adjacent )
            {
                value = flipColor( adjacent, next );
            }
        }
        return value + priority;
    }


    /**
     * For testing purposes
     * 
     * @return returns the value of something
     */
    protected int valueCalcVal()
    {
        return value;
    }
}
