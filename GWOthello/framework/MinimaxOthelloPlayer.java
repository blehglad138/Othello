import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.*;
import java.awt.Color;


/**
 * Hard AI to give the player a challenge
 * 
 * @author Charlie Huang
 * @version May 30, 2014
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: none?
 */
public class MinimaxOthelloPlayer extends OthelloPlayer
{

    /**
     * A color close to white
     */
    private static Color whiteish = new Color( -2031617 );

    /**
     * the number of turns the ai looks ahead
     */
    private final int maxDepth = 4;

    /**
     * the weight values of this board
     */
    private int[][] posValues = { { 100, -25, 10, 5, 5, 10, -25, 100, },
        { -25, -25, 1, 1, 1, 1, -25, -25, }, { 10, 1, 5, 2, 2, 5, 1, 10, },
        { 5, 1, 2, 1, 1, 2, 1, 5, }, { 5, 1, 2, 1, 1, 2, 1, 5, },
        { 10, 1, 5, 2, 2, 5, 1, 10, }, { -25, -25, 1, 1, 1, 1, -25, -25, },
        { 100, -25, 10, 5, 5, 10, -25, 100, }, };;


    /**
     * Constructs the minimax player
     * 
     * @param w
     *            the world that it is in
     */
    public MinimaxOthelloPlayer( OthelloWorld w )
    {
        super( w, "Computer", whiteish );
    }


    /**
     * Get's the play that the computer is going to do
     */
    @Override
    public Location getPlay()
    {
        ArrayList<Location> moves = getAllowedPlays( getGrid(), whiteish );
        Grid<Piece> copyGrid = copyGrid( getGrid() );
        copyGrid.put( moves.get( 0 ), new Piece( getColor() ) );
        flipAll( moves.get( 0 ), copyGrid, getColor() );
        int bestVal = valueOfBoard( getColor(), copyGrid, maxDepth );
        Location bestLoc = moves.get( 0 );
        for ( int i = 1; i < moves.size(); i++ )
        {
            Location m = moves.get( i );
            copyGrid = copyGrid( getGrid() );
            copyGrid.put( m, new Piece( getColor() ) );
            flipAll( m, copyGrid, getColor() );
            int curVal = valueOfBoard( getColor(), copyGrid, maxDepth );
            if ( curVal > bestVal
                || ( curVal == bestVal && Math.random() >= 0.5 ) )
            {
                bestVal = curVal;
                bestLoc = m;
            }
        }

        return bestLoc;
    }


    /**
     * 
     * calculates the value of a baord, for a particular player
     * 
     * @param grid
     *            the given board
     * @param col
     *            the player's piece color
     * @return
     */
    private int evaluate( Grid<Piece> grid, Color col )
    {
        int value = 0;
        for ( Location loc : grid.getOccupiedLocations() )
        {
            if ( grid.get( loc ) != null )
            {
                int pieceVal = 1 + posValues[loc.getRow()][loc.getCol()];
                // System.out.println( loc + " has value " + pieceVal );
                value += ( grid.get( loc ).getColor().equals( col ) ) ? pieceVal
                    : -pieceVal;
            }
        }
        return value;
    }


    /**
     * 
     * calculates the minimax part of the ai, using negamax
     * 
     * @param col
     *            the color for the player
     * @param grid
     *            the grid to be used
     * @param depth
     *            the current depth level
     * @return
     */
    private int minimax( Color col, Grid<Piece> grid, int depth )
    {

        if ( depth <= 0 )
        {
            return evaluate( grid, col );
        }
        ArrayList<Location> moves = getAllowedPlays( grid, col );

        if ( moves.isEmpty() )
        {
            return evaluate( grid, col );
        }

        // printActualGrid();
        Grid<Piece> copyGrid = copyGrid( grid );
        copyGrid.put( moves.get( 0 ), new Piece( col ) );
        flipAll( moves.get( 0 ), copyGrid, col );
        int bestVal = valueOfBoard( col, copyGrid, depth - 1 );
        for ( int i = 1; i < moves.size(); i++ )
        {
            Location m = moves.get( i );
            // printActualGrid();
            copyGrid = copyGrid( grid );
            // printGrid( copyGrid );
            copyGrid.put( m, new Piece( col ) );
            flipAll( m, copyGrid, col );
            // System.out.println( "Flipped stuff around " + m );
            int curVal = valueOfBoard( col, copyGrid, depth - 1 );
            if ( curVal > bestVal
                || ( curVal == bestVal && Math.random() >= 0.5 ) )
            {
                // System.out.println( "Current best: " + bestVal );
                bestVal = curVal;
            }
        }
        return bestVal;
    }


    /**
     * 
     * alternately called recursively with minimax to implement negamax
     * 
     * @param col
     *            ze color of the player
     * @param grid
     *            the grid to be used
     * @param depth
     *            the current depth level
     * @return
     */
    private int valueOfBoard( Color col, Grid<Piece> grid, int depth )
    {
        Color opCol = ( col.equals( whiteish ) ) ? Color.BLACK : whiteish;
        return -minimax( opCol, grid, depth );
    }


    /**
     * 
     * copies the grid for the ai's reference
     * 
     * @param grid
     *            the grid to copy
     * @return a copy of the grid
     */
    private Grid<Piece> copyGrid( Grid<Piece> grid )
    {
        Grid<Piece> copyGrid = new BoundedGrid<Piece>( 8, 8 );
        for ( int column = 0; column < grid.getNumCols(); column++ )
        {
            for ( int row = 0; row < grid.getNumRows(); row++ )
            {
                Location l = new Location( row, column );
                if ( grid.get( l ) != null )
                {
                    copyGrid.put( l, new Piece( grid.get( l ).getColor() ) );
                    // System.out.println( "copied" + l + " "
                    // + grid.get( l ).getColor() );
                }
            }
        }
        // System.out.println( "finish 1 copy" );

        // for( int i = 0; i < copyGrid.getNumRows(); i++ )
        // {
        // for( int k = 0; i < copyGrid.getNumCols(); i++ )
        // {
        // copyGrid.put( new Location( i, k), new Piece( whiteish) );
        // }
        // }
        return copyGrid;
    }


    /**
     * Gets the next location with the same color as the current player's piece
     * 
     * @param first
     *            first location to start at
     * @param second
     *            location to stop at
     * @param col
     *            color of the piece
     * @param tGrid
     *            the world that it's in
     * @return the next location with the same color
     */
    private Location getNextLocationWithColor(
        Location first,
        Location second,
        Color col,
        Grid<Piece> tGrid )
    {
        int dir = first.getDirectionToward( second );
        while ( true )
        {
            if ( !tGrid.isValid( second ) )
            {
                return null;
            }
            if ( tGrid.get( second ) == null )
            {
                return null;
            }
            if ( tGrid.get( second ).getColor().equals( col ) )
            {
                return second;
            }
            second = second.getAdjacentLocation( dir );
        }
    }


    /**
     * flips all the pieces for a particular move
     * 
     * @param loc
     *            the location played
     * @param grid
     *            the grid it was played on
     * @param col
     *            the color to flip to
     */
    private void flipAll( Location loc, Grid<Piece> grid, Color col )
    {
        for ( Location adjacent : grid.getOccupiedAdjacentLocations( loc ) )
        {
            Location next = getNextLocationWithColor( loc, adjacent, col, grid );
            // System.out.println( "checking: " + next );
            if ( next != null && !next.equals( adjacent ) && !next.equals( loc ) )
            {
                flipColorOfPieces( adjacent, next, col, grid );
                // System.out.println( "flipped " + next );
            }
        }
    }


    /**
     * 
     * flips color of pieces to the color given from start to end, inclusively
     * to exclusively on the passed grid
     * 
     * @param start
     *            the first location to flip
     * @param end
     *            the location at which the method should stop flipping
     * @param col
     *            the color the pieces should be flipped to
     * @param tGrid
     *            the grid on which the pieces are being flipped
     */
    private void flipColorOfPieces(
        Location start,
        Location end,
        Color col,
        Grid<Piece> tGrid )
    {
        int dir = start.getDirectionToward( end );
        Location current = start;

        while ( !tGrid.get( current ).equals( tGrid.get( end ) ) )
        {
            tGrid.get( current ).setColor( col );
            current = current.getAdjacentLocation( dir );
        }
    }


    /**
     * gets allowed plays according to the rules of othello
     * 
     * @param grid
     *            the grid to be used
     * @param col
     *            the color of the player who is checking what moves he can make
     * @return an arraylist of allowed plays
     */
    private ArrayList<Location> getAllowedPlays( Grid<Piece> grid, Color col )
    {
        ArrayList<Location> allowed = new ArrayList<Location>();
        for ( Location empty : getEmptyLocations( grid ) )
        {
            for ( Location adjacent : grid.getOccupiedAdjacentLocations( empty ) )
            {
                Location second = getNextLocationWithColor( empty,
                    adjacent,
                    col,
                    grid );
                if ( second != null && !second.equals( adjacent ) )
                {
                    allowed.add( empty );
                }
            }
        }
        return allowed;
    }

}
