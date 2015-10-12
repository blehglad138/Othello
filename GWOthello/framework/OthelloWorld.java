import info.gridworld.world.World;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.concurrent.Semaphore;


/**
 * OthelloWorld.java
 * 
 * An <CODE>OthelloWorld</CODE> object represents an Othello world.
 * 
 * @author Patrick Lee
 * @author 5111742
 * @version TODO 5/2/14
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: none
 */
public class OthelloWorld extends World<Piece>
{
    /** The Othello game */
    private OthelloGame game;

    /**
     * A semaphore to prevent getPlayerLocation from executing before
     * setPlayerLocation
     */
    private Semaphore lock;

    /** The last selected player location */
    private Location playerLocation;

    /**
     * The color white with the RGB value of -2031617
     */
    private static Color whiteish = new Color( -2031617 );


    /**
     * Construct an Othello world game The Othello game
     * 
     * @param game
     *            the OthelloGame passed to the world
     */
    public OthelloWorld( OthelloGame game )
    {
        super( new BoundedGrid<Piece>( 8, 8 ) );
        this.game = game;
        lock = new Semaphore( 0 );
        playerLocation = null;
        setMessage( "Othello - You are black.  Click a cell to play." );

        System.setProperty( "info.gridworld.gui.selection", "hide" );
        System.setProperty( "info.gridworld.gui.tooltips", "hide" );
        System.setProperty( "info.gridworld.gui.watermark", "hide" );

        add( new Location( 3, 3 ), new Piece( whiteish ) );
        add( new Location( 3, 4 ), new Piece( Color.BLACK ) );
        add( new Location( 4, 3 ), new Piece( Color.BLACK ) );
        add( new Location( 4, 4 ), new Piece( whiteish ) );
    }


    /**
     * Handles the mouse location click.
     * 
     * @param loc
     *            the location that was clicked
     * @return true because the click has been handled
     */
    @Override
    public boolean locationClicked( Location loc )
    {
        setPlayerLocation( loc );
        return true;
    }


    /**
     * Gets the game that we're playing in
     * 
     * @return the game that we're playing in
     */
    public OthelloGame getGame()
    {
        return game;
    }


    /**
     * Sets <CODE>playerLocation</CODE>.
     * 
     * @param loc
     *            the location to be used to set the player location
     */
    private void setPlayerLocation( Location loc )
    {
        lock.drainPermits(); // Remove all permits
        playerLocation = loc;
        lock.release(); // Allow getPlayerLocation to run once
    }


    /**
     * Gets the last player location chosen by the human player.
     * 
     * @return the last location chosen by the human player
     */
    public Location getPlayerLocation()
    {
        try
        {
            lock.acquire(); // Block until setPlayerLocation runs
            return playerLocation;
        }
        catch ( InterruptedException e )
        {
            throw new RuntimeException( "Had catastrophic InterruptedException" );
        }
    }


    /**
     * Sets the message in the GridWorld GUI.<br>
     * Postcondition: At least a second has elapsed before returning.
     * 
     * @param msg
     *            the message text
     */
    @Override
    public void setMessage( String msg )
    {
        super.setMessage( msg );
        try
        {
            Thread.sleep( 2000 );
        }
        catch ( InterruptedException e )
        {
            System.out.println( "InterruptedException occurred." );
        }
    }

}
