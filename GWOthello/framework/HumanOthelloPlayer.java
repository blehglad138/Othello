import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * HumanOthelloPlayer.java
 * 
 * A <CODE>HumanOthelloPlayer</CODE> object represents a
 * human Othello player.

 *  @author  Patrick
 *  @version May 30, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 *
 *  @author  Sources: none
 */
public class HumanOthelloPlayer extends OthelloPlayer
{
	/**
	 * Constructs a human Othello player.
	 * @param world the world
	 */
	public HumanOthelloPlayer(OthelloWorld world)
	{
		super(world, "Human", Color.BLACK);
	}
	
	/**
	 * Constructs a human
	 * @param world OthelloWorld
	 * @param col a color for the human player
	 */
	public HumanOthelloPlayer( OthelloWorld world, Color col )
	{
	    super( world, "Ninja", col );
	}
	
	/**
	 * Another constructor for a human player
	 * @param world OthelloWorld that the game will be in
	 * @param name name of the player
	 * @param col color of the player
	 */
	public HumanOthelloPlayer( OthelloWorld world, String name, Color col)
	{
	    super( world, name, col);
	}

	/**
	 * Retrieves the next play for the human.
	 * Postcondition: the returned location is an allowed play.
	 * @return the location for the next play
	 */
	public Location getPlay()
	{
		Location loc;
		do
		{
			loc = getWorld().getPlayerLocation();
		}
		while (! isAllowedPlay(loc));
		return loc;
	}
}
