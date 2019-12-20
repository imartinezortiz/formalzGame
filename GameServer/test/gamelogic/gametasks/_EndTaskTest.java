/*
 * This program has been developed by students from the bachelor Computer Science at Utrecht University within the Software and Game project course (time-period)
 * (c)Copyright Utrecht University (Department of Information and Computing Sciences)
 */
package gamelogic.gametasks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import connection.Client;
import connection.Connection;
import gamelogic.gamestate.GameState;
import logger.AbstractLogger;

public class _EndTaskTest
{
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Client MClient;
    @Mock
    AbstractLogger MLogger;
    @Mock
    Connection MConnection;
    @Mock
    GameState MGameState;

    @Before
    public void initMocks()
    {
        when(MClient.getLogger()).thenReturn(MLogger);
        when(MClient.getConnection()).thenReturn(MConnection);
        when(MClient.getState()).thenReturn(MGameState);
    }

    /**
     * Test whether an unknown command is handled correctly.
     */
    @Test
    public void testUnknownCommand()
    {
        EndTask endTask = new EndTask(MClient);

        assertFalse(endTask.tryCommand("notrealcommand", "notrealarguments"));
    }

    /**
     * Test whether the end command is handled correctly.
     */
    @Test
    public void testEndCommandGood()
    {
        when(MGameState.end()).thenReturn(true);

        EndTask endTask = new EndTask(MClient);

        assertTrue(endTask.tryCommand("end", "nothing"));

        verify(MLogger, times(1)).log("Game ended");
        verify(MConnection, times(1)).sendEnd();
    }

    /**
     * Test whether the end command is handled correctly.
     */
    @Test
    public void testEndCommandBad()
    {
        when(MGameState.end()).thenReturn(false);

        EndTask endTask = new EndTask(MClient);

        assertTrue(endTask.tryCommand("end", "nothing"));

        verify(MLogger, times(1)).log(anyString());
    }
}
