package io.maestro.common.command;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandWithDestinationTests {

    private static final Object COMMAND = new Object();

    @Test
    public void to_shouldCarryTheDestinationAndTheCommand(){
        //when
        CommandWithDestination command = CommandWithDestination.to("order-service", COMMAND);
        //then
        assertEquals("order-service", command.getDestination());
        assertSame(COMMAND, command.getCommand());
        assertTrue(command.getHeaders().isEmpty());
    }

    @Test
    public void shouldCarrySagaSuppliedHeaders(){
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("tenant", "acme");
        //when
        CommandWithDestination command
                = new CommandWithDestination("order-service", COMMAND, headers);
        //then
        assertEquals("acme", command.getHeaders().get("tenant"));
    }

    @Test
    public void whenHeadersAreNull_thenShouldBeTreatedAsNoHeaders(){
        //when
        CommandWithDestination command
                = new CommandWithDestination("order-service", COMMAND, null);
        //then
        assertTrue(command.getHeaders().isEmpty());
    }

    @Test
    public void headers_shouldNotBeAffectedByLaterChangesToTheSourceMap(){
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("tenant", "acme");
        CommandWithDestination command
                = new CommandWithDestination("order-service", COMMAND, headers);
        //when
        headers.put("tenant", "tampered");
        //then
        assertEquals("acme", command.getHeaders().get("tenant"));
    }

    @Test
    public void headers_shouldNotBeModifiableByCallers(){
        //given
        CommandWithDestination command = CommandWithDestination.to("order-service", COMMAND);
        //when
        //then
        assertThrows(UnsupportedOperationException.class,
                     () -> command.getHeaders().put("x", "y"));
    }

    @Test
    public void whenDestinationIsMissing_thenExpectIllegalArgumentException(){
        //when
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class,
                               () -> CommandWithDestination.to("  ", COMMAND));
        //then
        assertEquals("A command needs a destination channel", exception.getMessage());
    }

    @Test
    public void whenDestinationIsNull_thenExpectIllegalArgumentException(){
        //when
        //then
        assertThrows(IllegalArgumentException.class,
                     () -> CommandWithDestination.to(null, COMMAND));
    }

    @Test
    public void whenCommandIsMissing_thenExpectIllegalArgumentException(){
        //when
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class,
                               () -> CommandWithDestination.to("order-service", null));
        //then
        assertEquals("A command to order-service needs a payload", exception.getMessage());
    }
}
