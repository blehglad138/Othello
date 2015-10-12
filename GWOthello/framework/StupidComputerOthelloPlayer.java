import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.awt.Color;


/**
 * StupidComputerOthelloPlayer.java
 * 
 * A <CODE>StupidComputerPlayer</CODE> object represents a computer player who
 * selects a move by randomly chosing from the allowed locations.
 * 
 * @author Patrick Lee
 * @author 5111742
 * @version 4/4/13
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: None
 */
public class StupidComputerOthelloPlayer extends OthelloPlayer
{
    /**
     * A whiteish color so that the piece has a nice 3d effect
     */
    private static Color whiteish = new Color( -2031617 );


    /**
     * Constructs a stupid computer player.
     * 
     * @param world
     *            the world
     */
    public StupidComputerOthelloPlayer( OthelloWorld world )
    {
        super( world, "Computer", whiteish );
    }
    
    public StupidComputerOthelloPlayer( OthelloWorld world, String name, Color col )
    {
        super( world, name, col );
    }


    /**
     * Determines a randomly selected allowed move.
     * 
     * @return a random allowed more (if there is one); null otherwise
     */
    public Location getPlay()
    {
        ArrayList<Location> allowedMoves = getAllowedPlays();
        if ( allowedMoves.isEmpty() )
        {
            return null;
        }
        else
        {
            return allowedMoves.get( (int)( Math.random() + allowedMoves.size() - 1 ) );
            // -1 cause size is larger than the index
        }
    }
}
