import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import info.gridworld.grid.Location;

import java.awt.Color;


/**
 * Testing to make sure that every method works as intended
 * 
 * @author Charlie Huang
 * @version May 21, 2014
 * @author Period: TODO
 * @author Assignment: GWOthello
 * 
 * @author Sources: TODO
 */
public class JUnitTestOthello
{
    /** The world */
    private OthelloWorld world;

    /** The array of two players (human, computer) */
    private OthelloPlayer[] players;


    // Othello Game test
    @Test
    public void othelloGameConstructor1()
    {
        OthelloGame g = new OthelloGame( false );
        assertNotNull( g );
    }


    @Test
    public void othelloGameConstructor2()
    {
        OthelloGame g = new OthelloGame( false );
        assertNotNull( g.getOthelloWorld() );
    }


    @Test
    public void othelloGameConstructor3()
    {
        OthelloGame g = new OthelloGame( false, "Easy", "bill", "boog" );
        assertNotNull( g.getOthelloWorld() );
    }


    @Test
    public void othelloGameConstructor4()
    {
        OthelloGame g = new OthelloGame( false, "Easy", "bill" );
        assertNotNull( g.getOthelloWorld() );
    }


    @Test
    public void othelloGamePlayGame()
    {
        OthelloGame g = new OthelloGame( false, "Easy", "Bill", "boog" );
        OthelloPlayer[] players = {
            new MediumAI( g.getOthelloWorld(), Color.BLACK ),
            new MediumAI( g.getOthelloWorld() ) };
        g.setPlayers( players );
        OthelloWorld w = g.getOthelloWorld();
        for ( int row = 0; row < w.getGrid().getNumRows(); row++ )
        {
            for ( int col = 0; col < w.getGrid().getNumCols(); col++ )
            {
                Location l = new Location( row, col );
                if ( w.getGrid().get( l ) == null )
                    w.getGrid().put( l, new Piece( Color.BLACK ) );
                else
                    w.getGrid().get( l ).setColor( Color.BLACK );
            }
        }
        g.playGame();
        assertTrue( g.toString().contains( "64" )
            && g.toString().contains( g.getPlayers()[0].getName() ) );
    }
    


    // Battery of AI tests
    @Test
    public void stupidAIconstructor()
    {
        OthelloGame g = new OthelloGame( false, "Easy", "Bob", "boog" );
        assertNotNull( g.getPlayers()[1] );
    }


    @Test
    public void stupidAiGetPlay()
    {
        OthelloGame g = new OthelloGame( false, "Easy", "Bob", "boog" );
        assertTrue( g.getOthelloWorld()
            .getGrid()
            .isValid( g.getPlayers()[1].getPlay() ) );
    }


    @Test
    public void mediumAIConstructors()
    {
        OthelloGame g = new OthelloGame( false, "Medium", "Bob", "boog" );
        assertNotNull( g.getPlayers()[1] );
        OthelloPlayer[] p = { new MediumAI( g.getOthelloWorld(), Color.BLACK ),
            g.getPlayers()[1] };
        g.setPlayers( p );
        assertNotNull( g.getPlayers()[0] );
    }


    @Test
    public void mediumAIGetPlay()
    {
        OthelloGame g = new OthelloGame( false, "Medium", "Bob", "boog" );
        for ( int i = 1; i < 3; i++ )
        {
            g.getOthelloWorld()
                .getGrid()
                .put( new Location( i, i ), new Piece( Color.BLACK ) );
        }
        assertEquals( new Location( 0, 0 ), g.getPlayers()[1].getPlay() );
    }


    @Test
    public void minimaxConstructor()
    {
        OthelloGame g = new OthelloGame( false, "Hard", "Bob", "boog" );
        assertNotNull( g.getPlayers()[1] );
    }


    @Test
    public void minimaxGetPlay()
    {
        OthelloGame g = new OthelloGame( false, "Hard", "Bob", "boog" );
        for ( int i = 1; i < 3; i++ )
        {
            g.getOthelloWorld()
                .getGrid()
                .put( new Location( i, i ), new Piece( Color.BLACK ) );
        }
        assertEquals( new Location( 0, 0 ), g.getPlayers()[1].getPlay() );
    }


    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUnitTestOthello.class );
    }


    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUnitTestOthello" );
    }

}
