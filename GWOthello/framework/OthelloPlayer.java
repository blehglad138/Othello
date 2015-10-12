import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.awt.Color;


/**
 * OthelloPlayer.java
 * 
 * This is the top-level class for all player classes.
 * 
 * @author Patrick Lee
 * @author 5111742
 * @version 4/4/13
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: None
 */
public abstract class OthelloPlayer
{
    /** The world */
    private OthelloWorld world;

    /** The grid */
    private Grid<Piece> grid; // OthelloGame uses Grid<Piece>

    /** The name of the player ("Human" or "Computer") */
    private String name;

    /** The color of this player's game pieces */
    private Color color;


    /**
     * Constructs an Othello player object.
     * 
     * @param w
     *            the world
     * @param n
     *            the name ("Human" or "Computer")
     * @param c
     *            the color
     */
    public OthelloPlayer( OthelloWorld w, String n, Color c )
    {
        world = w;
        name = n;
        color = c;
        grid = world.getGrid();
    }


    /**
     * Gets the next move.
     * 
     * @return the location of the next move
     */
    public abstract Location getPlay();


    /**
     * Gets the player name.
     * 
     * @return the player name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Gets the Othello world.
     * 
     * @return the Othello world
     */
    public OthelloWorld getWorld()
    {
        return world;
    }


    public Grid<Piece> getGrid()
    {
        return grid;
    }


    public Color getColor()
    {
        return color;
    }


    /**
     * Determines if the player can make a play.
     * 
     * @return true if the player can play; false otherwise
     */
    public boolean canPlay()
    {
        return getAllowedPlays().size() != 0;
    }


    /**
     * Computers the list of locations that the player may play
     * 
     * @return allowed (legal) plays (locations) for this player
     */
    public ArrayList<Location> getAllowedPlays()
    {
        ArrayList<Location> allowed = new ArrayList<Location>();
        for ( Location empty : getEmptyLocations() )
        {
            for ( Location adjacent : grid.getOccupiedAdjacentLocations( empty ) )
            {
                Location second = getNextLocationWithColor( empty, adjacent );
                if ( second != null && !second.equals( adjacent ) )
                {
                    allowed.add( empty );
                }
            }
        }
        return allowed;
    }





    /**
     * Determines if this play is allowed by the rules
     * 
     * @param loc
     *            location to be checked
     * @return true if this location is allowed to be played; false otherwise
     */
    public boolean isAllowedPlay( Location loc )
    {
        return getAllowedPlays().contains( loc );
    }


    /**
     * Make the play indicated by calling getPlay. (Place a piece and "flip" the
     * appropriate other pieces.)
     */
    public void play()
    {
        Location playLoc = getPlay();
        grid.put( playLoc, new Piece( color ) );
        for ( Location adjacent : grid.getOccupiedAdjacentLocations( playLoc ) )
        {
            Location next = getNextLocationWithColor( playLoc, adjacent );
            if ( next != null && next != adjacent )
            {
                flipColorOfPieces( adjacent, next );
            }
        }
    }


    /**
     * Determines the empty locations on the board.
     * 
     * @return a list of the empty board locations
     */
    protected ArrayList<Location> getEmptyLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for ( int i = 0; i < grid.getNumRows(); i++ )
        {
            for ( int x = 0; x < grid.getNumCols(); x++ )
            {
                Location loc = new Location( i, x );
                if ( grid.isValid( loc ) && grid.get( loc ) == null )
                {
                    locs.add( loc );
                }
            }
        }
        return locs;
    }


    /**
     * Obtains the empty locations on the grid.
     * 
     * @param tGrid
     *            The grid of the game
     * @return the empty locations in an arrayList
     */
    protected ArrayList<Location> getEmptyLocations( Grid<Piece> tGrid )
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for ( int i = 0; i < tGrid.getNumRows(); i++ )
        {
            for ( int x = 0; x < tGrid.getNumCols(); x++ )
            {
                Location loc = new Location( i, x );
                if ( tGrid.isValid( loc ) && tGrid.get( loc ) == null )
                {
                    locs.add( loc );
                }
            }
        }
        return locs;
    }


    /**
     * Finds the next location with this player's color beginning with second in
     * the direction from first to second.
     * 
     * @param first
     *            first location
     * @param second
     *            second location
     * @return next location with this player's color in the direction from
     *         first to second starting with second
     */
    protected Location getNextLocationWithColor( Location first, Location second )
    {
        int dir = first.getDirectionToward( second );
        while ( true )
        {
            if ( !grid.isValid( second ) )
            {
                return null;
            }
            if ( grid.get( second ) == null )
            {
                return null;
            }
            if ( grid.get( second ).getColor().equals( color ) )
            {
                return second;
            }
            second = second.getAdjacentLocation( dir );
        }
    }





    /**
     * Changes (flips) the color of the pieces to the current player's color in
     * the locations from start (included) to end (not included)
     * 
     * @param start
     *            first location to color (flip)
     * @param end
     *            first location past last location to color (flip)
     */
    protected void flipColorOfPieces( Location start, Location end )
    {
        int dir = start.getDirectionToward( end );
        Location current = start;

        while ( grid.get( current ) != grid.get( end ) )
        {
            grid.get( current ).setColor( color );
            current = current.getAdjacentLocation( dir );
        }
    }





    /**
     * Sets the player's name to newName
     * 
     * @param newName
     *            new player name
     */
    public void setName( String newName )
    {
        name = newName;
    }

}
